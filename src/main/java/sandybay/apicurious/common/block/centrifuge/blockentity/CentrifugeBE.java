package sandybay.apicurious.common.block.centrifuge.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.util.ProblemReporter;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.housing.ITicker;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStacksResourceHandler;
import sandybay.apicurious.api.recipe.CentrifugeRecipe;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.menu.CentrifugeMenu;
import sandybay.apicurious.common.registrar.BlockRegistrar;

import java.util.List;
import java.util.Map;

public class CentrifugeBE extends BlockEntity implements ITicker, MenuProvider
{
  private static final int SLOT_COUNT = 10;
  private static final int INPUT_SLOT = 0;
  private static final int OUTPUT_SLOT_START = 1;
  private static final int OUTPUT_SLOT_END = SLOT_COUNT;

  private static final int DATA_WORK = 0;
  private static final int DATA_MAX_WORK = 1;
  private static final int DATA_COUNT = 2;

  // Value meaning "not currently working".
  private static final int NO_WORK = -1;

  private final ConfigurableItemStacksResourceHandler inventory;

  /** Last input stack we reacted to; used to detect the input slot changing. */
  private ItemResource lastSeenInput = ItemResource.EMPTY;
  private CentrifugeRecipe recipe;
  private int work = NO_WORK;
  private int maxWork;

  /**
   * Set after loading from NBT when a recipe was mid-progress. The recipe object itself
   * isn't persisted, so on the first tick after load we re-resolve it against whatever is
   * currently sitting in the input slot, instead of letting the normal "input changed"
   * detection wipe the in-progress work back to zero.
   */
  private boolean pendingResume = false;

  private final ContainerData containerData = new ContainerData()
  {
    @Override
    public int get(int pIndex)
    {
      return switch (pIndex)
      {
        case DATA_WORK -> work;
        case DATA_MAX_WORK -> maxWork;
        default -> throw new IllegalArgumentException("Invalid index: " + pIndex);
      };
    }

    @Override
    public void set(int pIndex, int pValue)
    {
      throw new IllegalStateException("Cannot set values through IIntArray");
    }

    @Override
    public int getCount()
    {
      return DATA_COUNT;
    }
  };

  public CentrifugeBE(BlockPos pPos, BlockState pBlockState)
  {
    super(BlockRegistrar.CENTRIFUGE.getType(), pPos, pBlockState);
    this.inventory = new ConfigurableItemStacksResourceHandler(SLOT_COUNT).setOnSlotChanged((stack, slot) -> this.setChanged());
  }

  @Override
  public @NotNull Component getDisplayName()
  {
    return Component.translatable("apicurious.menu.centrifuge");
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player)
  {
    if (getLevel() == null) {return null;}
    return new CentrifugeMenu(containerId, playerInventory, ContainerLevelAccess.create(getLevel(), getBlockPos()), this);
  }

  @Override
  public void serverTick(Level level, BlockPos pos, BlockState state)
  {
    if (getLevel() == null) return;

    if (pendingResume)
    {
      resumeAfterLoad();
    }

    ItemResource stack = inventory.getResource(INPUT_SLOT);

    if (inputWasRemovedOrSwapped(stack))
    {
      cancelWork();
      this.lastSeenInput = stack;
      return;
    }

    if (shouldStartNewRecipe(stack))
    {
      startNewRecipe(stack);
      return;
    }

    if (isWorking() && work == NO_WORK)
    {
      startWork();
      return;
    }

    if (!isWorking() || work <= 0) return;

    work--;
    if (work == 0)
    {
      tryFinishCraft();
    }
  }

  @Override
  public void clientTick(Level level, BlockPos pos, BlockState state) {}

  private boolean isWorking()
  {
    return recipe != null;
  }

  private boolean inputWasRemovedOrSwapped(ItemResource stack)
  {
    boolean inputChanged = this.lastSeenInput != stack;
    return (stack.isEmpty() || inputChanged) && (isWorking() || work != NO_WORK);
  }

  private boolean shouldStartNewRecipe(ItemResource stack)
  {
    boolean inputChanged = this.lastSeenInput != stack;
    return !stack.isEmpty() && !isWorking() && inputChanged;
  }

  private void startNewRecipe(ItemResource stack)
  {
    this.recipe = findRecipe();
    if (this.recipe == null) return;
    this.lastSeenInput = stack;
    startWork();
  }

  private void resumeAfterLoad()
  {
    pendingResume = false;

    ItemResource stack = inventory.getResource(INPUT_SLOT);
    if (!stack.isEmpty())
    {
      this.recipe = findRecipe();
      this.lastSeenInput = stack;
    }

    if (this.recipe == null)
    {
      cancelWork();
    }
  }

  public ConfigurableItemStacksResourceHandler getInventory()
  {
    return inventory;
  }

  public ContainerData getContainerData()
  {
    return containerData;
  }

  @Override
  protected void saveAdditional(ValueOutput output)
  {
    super.saveAdditional(output);
    this.inventory.serialize(output);
    output.putInt("work", work);
    output.putInt("maxWork", maxWork);
  }

  @Override
  protected void loadAdditional(ValueInput input)
  {
    super.loadAdditional(input);
    this.inventory.deserialize(input);
    this.work = input.getIntOr("work", NO_WORK);
    this.maxWork = input.getIntOr("maxWork", 0);
    this.pendingResume = this.work != NO_WORK;
  }

  @Override
  public CompoundTag getUpdateTag(HolderLookup.Provider registries)
  {
    try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), Apicurious.LOGGER)) {
      TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
      output.store(super.getUpdateTag(registries));
      this.inventory.serialize(output);
      output.putInt("work", work);
      output.putInt("maxWork", maxWork);
      return output.buildResult();
    }
  }

  @Override
  public void handleUpdateTag(ValueInput input)
  {
    super.handleUpdateTag(input);
    this.inventory.deserialize(input);
    this.work = input.getIntOr("work", NO_WORK);
    this.maxWork = input.getIntOr("maxWork", 0);
  }

  @Nullable
  @Override
  public Packet<ClientGamePacketListener> getUpdatePacket()
  {
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public void onDataPacket(Connection net, ValueInput valueInput)
  {
    this.inventory.deserialize(valueInput);
    this.work = valueInput.getIntOr("work", NO_WORK);
    this.maxWork = valueInput.getIntOr("maxWork", 0);
  }

  private void cancelWork()
  {
    this.recipe = null;
    this.work = NO_WORK;
    this.maxWork = 0;
  }

  private void startWork()
  {
    this.work = this.maxWork = recipe.duration();
  }

  private CentrifugeRecipe findRecipe()
  {
    Registry<CentrifugeRecipe> recipes = getLevel().registryAccess()
            .lookup(ApicuriousRegistries.CENTRIFUGE_RECIPES)
            .orElseThrow();

    return recipes.entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(r -> r.matches(this))
            .findFirst()
            .orElse(null);
  }

  private void tryFinishCraft()
  {
    ItemResource inputStack = this.inventory.getResource(INPUT_SLOT);
    List<ItemStack> outputs = this.recipe.resolve(this);

    try (Transaction tx = Transaction.openRoot())
    {
      if (!canInsertOutputs(outputs, tx))
      {
        this.work++;
        return;
      }

      if (this.inventory.extract(INPUT_SLOT, inputStack, 1, tx) == 1)
      {
        insertOutputs(outputs, tx);
        tx.commit();
        cancelWork();
      }
    }
  }

  private boolean canInsertOutputs(List<ItemStack> outputs, Transaction tx)
  {
    for (ItemStack output : outputs)
    {
      if (!canInsertSingle(output, tx)) return false;
    }
    return true;
  }

  /**
   * Dry-run: simulates placing {@code stack} into the output slots. Runs inside a nested
   * transaction that's never committed, so anything it inserts is automatically rolled back.
   */
  private boolean canInsertSingle(ItemStack stack, Transaction tx)
  {
    try (Transaction txt = Transaction.open(tx))
    {
      ItemResource item = ItemResource.of(stack);
      return insertIntoOutputSlots(item, stack.getCount(), txt) == stack.getCount();
    }
  }

  private void insertOutputs(List<ItemStack> outputs, Transaction tx)
  {
    for (ItemStack output : outputs)
    {
      ItemResource item = ItemResource.of(output);
      insertIntoOutputSlots(item, output.getCount(), tx);
    }
  }

  /**
   * Attempts to insert up to {@code count} of {@code item} across the output slots.
   * Returns the amount actually inserted.
   */
  private int insertIntoOutputSlots(ItemResource item, int count, Transaction tx)
  {
    int remaining = count;
    for (int i = OUTPUT_SLOT_START; i < OUTPUT_SLOT_END && remaining > 0; i++)
    {
      remaining -= this.inventory.insert(i, item, remaining, tx);
    }
    return count - remaining;
  }

}