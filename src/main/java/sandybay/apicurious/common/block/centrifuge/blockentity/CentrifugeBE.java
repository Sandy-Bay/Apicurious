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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
  private final ConfigurableItemStacksResourceHandler inventory;
  private ItemResource curr;
  private CentrifugeRecipe recipe;
  private int work;
  private int maxWork;
  private final ContainerData containerData = new ContainerData()
  {
    @Override
    public int get(int pIndex)
    {
      return switch (pIndex)
      {
        case 0 -> work;
        case 1 -> maxWork;
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
      return 2;
    }
  };

  public CentrifugeBE(BlockPos pPos, BlockState pBlockState)
  {
    super(BlockRegistrar.CENTRIFUGE.getType(), pPos, pBlockState);
    this.inventory = new ConfigurableItemStacksResourceHandler(10).setInputFilter((stack, slot) -> slot == 0);
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
    if (getLevel() == null) {return;}
    ItemResource stack = inventory.getResource(0);
    if ((stack.isEmpty() || this.curr != stack) && (recipe != null || work != -1))
    {
      this.recipe = null;
      this.work = -1;
      this.maxWork = 0;
      this.curr = stack;
      return;
    }
    if (!stack.isEmpty() && this.recipe == null && this.curr != stack)
    {
      Registry<CentrifugeRecipe> recipes = getLevel().registryAccess().registry(ApicuriousRegistries.CENTRIFUGE_RECIPES).orElseThrow();
      this.recipe = recipes.entrySet().stream().filter(entry -> entry.getValue().matches(this)).map(Map.Entry::getValue).findFirst().orElse(null);
      if (this.recipe == null) {return;}
      this.work = this.maxWork = recipe.duration();
      this.curr = stack;
      return;
    }
    if (!stack.isEmpty() && this.recipe != null && this.work == -1)
    {
      this.work = this.maxWork = recipe.duration();
    }
    if (this.work > 0)
    {
      this.work--;
      if (this.work == 0)
      {
        ItemResource comb = this.inventory.getResource(0);
        comb.toStack().shrink(1);
        this.inventory.set(0, comb, comb.toStack().getCount());
        List<ItemStack> outputs = this.recipe.resolve(this);
        for (ItemStack output : outputs)
        {
          ItemStack out = output;
          for (int i = 1; i < 10; i++)
          {
            if (this.inventory.insert(i, ItemResource.of(out.copy()), out.getCount(), true) != out)
            {
              out = this.inventory.insert(i, ItemResource.of(out.copy()), out.getCount(), false);
              if (out.isEmpty())
              {
                break;
              }
            }
          }
        }
        this.work = -1;
        this.maxWork = 0;
      }
    }
  }

  @Override
  public void clientTick(Level level, BlockPos pos, BlockState state) {}

  public ConfigurableItemStacksResourceHandler getInventory()
  {
    return inventory;
  }

  public ContainerData getContainerData()
  {
    return containerData;
  }

  // World Save / Read Methods
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
    this.work = input.getIntOr("work", 0);
    this.maxWork = input.getIntOr("maxWork", 0);
  }

  // Data Syncing Methods
  @Override
  public CompoundTag getUpdateTag(HolderLookup.Provider registries)
  {
    CompoundTag tag = super.getUpdateTag(registries);
    tag.put("inventory", inventory.serializeNBT(registries));
    tag.putInt("work", work);
    tag.putInt("maxWork", maxWork);
    return tag;
  }

  @Override
  public void handleUpdateTag(ValueInput input)
  {
    super.handleUpdateTag(input);
    this.inventory.deserialize(input);
    this.work = input.getIntOr("work", 0);
    this.maxWork = input.getIntOr("maxWork", 0);
  }

  // Data Update Methods
  @Nullable
  @Override
  public Packet<ClientGamePacketListener> getUpdatePacket()
  {
    return ClientboundBlockEntityDataPacket.create(this, (be, reg) ->
    {
      CompoundTag tag = new CompoundTag();
      tag.put("inventory", inventory.serializeNBT(reg));
      tag.putInt("work", work);
      tag.putInt("maxWork", maxWork);
      return tag;
    });
  }

  @Override
  public void onDataPacket(Connection net, ValueInput valueInput)
  {
    this.inventory.deserialize(valueInput);
    this.work = valueInput.getIntOr("work", 0);
    this.maxWork = valueInput.getIntOr("maxWork", 0);
  }
}
