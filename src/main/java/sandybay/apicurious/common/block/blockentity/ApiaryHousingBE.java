package sandybay.apicurious.common.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.bee.IBeeItem;
import sandybay.apicurious.api.housing.blockentity.SimpleBlockHousingBE;
import sandybay.apicurious.api.register.DataComponentRegistration;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.trait.Fertility;
import sandybay.apicurious.common.bee.species.trait.Lifespan;
import sandybay.apicurious.common.block.housing.ApiaryBlock;
import sandybay.apicurious.common.config.ApicuriousMainConfig;
import sandybay.apicurious.common.menu.ApiaryMenu;
import sandybay.apicurious.common.network.PacketHandler;
import sandybay.apicurious.common.network.packets.GuiDataPacket;
import sandybay.apicurious.common.register.BlockRegistration;
import sandybay.apicurious.common.register.ItemRegistration;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ApiaryHousingBE extends SimpleBlockHousingBE
{

  private final ContainerData containerData = new ContainerData()
  {
    @Override
    public int get(int pIndex)
    {
      return switch (pIndex)
      {
        case 0 -> isActive ? 1 : 0;
        case 1 -> currentWork;
        case 2 -> maxWork;
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
      return 3;
    }
  };

  public ApiaryHousingBE(BlockPos pos, BlockState blockState)
  {
    super(BlockRegistration.APIARY.getType(), pos, blockState);
  }

  /**
   * Apiary Workflow: <br>
   * <ul>
   *   <li>Princess and Drone in Slots 0, 1</li>
   *   <ul>
   *     <li>Then perform work for 3.75 sec</li>
   *     <ul>
   *       <li>(75 ticks)</li>
   *     </ul>
   *     <li>Create Queen for Princess/Drone combo and insert into Slot 0</li>
   *   </ul>
   *   <li>Queen in Slot 0</li>
   *   <ul>
   *     <li>Validate Run</li>
   *     <li>If valid for run</li>
   *     <ul>
   *       <li>Cache the lifespan of Queen to a field</li>
   *       <li>Set tick duration value to 550</li>
   *       <li>Decrement tick duration each tick</li>
   *       <li>Every 200 ticks attempt to produce a flower</li>
   *       <li>Once tick duration hits 0</li>
   *       <ul>
   *         <li>Attempt to generate output</li>
   *         <li>Set tick duration to 550 again</li>
   *         <li>Decrement local lifespan field by 1</li>
   *         <li>If Lifespan field reaches 0</li>
   *         <li>Create a new princess adn 1-X drones (Fertility-dependant) with genetics of the queen.</li>
   *       </ul>
   *     </ul>
   *   </ul>
   * </ul>
   */
  @Override
  public void serverTick(Level level, BlockPos pos, BlockState state)
  {
    if (!this.isActive)
    {
      validate(level, pos, true);
      if (getErrorList().isEmpty())
      {
        updateGuiData(); // Perform extra update just to clear any junk data on the client.
        if (currentWork == 0 && maxWork == 0)
        {
          this.currentWork = 75;
          this.maxWork = currentWork;
          this.clearErrors();
        }
        if (this.currentWork != 0)
        {
          this.currentWork--;
          if (this.currentWork == 0)
          {
            ItemStack princess = getInventory().getStackInSlot(0);
            BeeSpecies species = princess.get(DataComponentRegistration.BEE_SPECIES);
            ItemStack queen = new ItemStack(ItemRegistration.QUEEN);
            queen.set(DataComponentRegistration.BEE_SPECIES, species);
            getInventory().extractItem(0, 1, false);
            getInventory().extractItem(1, 1, false);
            getInventory().setStackInSlot(0, queen);
            changeActiveState(state, true);
            this.maxWork = 0;
            if (ApicuriousMainConfig.main_config.debug.get()) Apicurious.LOGGER.info("Successfully turned Princess of type %s, into Queen of type %s".formatted(species.getReadableName().getString(), species.getReadableName().getString()));
          }
        }
      } else
      {
        updateGuiData();
      }
    } else
    {
      validate(level, pos, false);
      ItemStack stack = getInventory().getStackInSlot(0);
      if (stack.has(DataComponentRegistration.BEE_SPECIES))
      {
        BeeSpecies species = stack.get(DataComponentRegistration.BEE_SPECIES);
        handleInitialRunData(species);
      }
      if (getErrorList().isEmpty())
      {
        updateGuiData(); // Perform extra update just to clear any junk data on the client.

        if (stack.getItem() instanceof IBeeItem bee && bee.getBeeType() == EnumBeeType.QUEEN)
        {
          if (stack.has(DataComponentRegistration.BEE_SPECIES))
          {
            BeeSpecies species = stack.get(DataComponentRegistration.BEE_SPECIES);
            if (species == null) return;
            handlePollination(level, (ApiaryBlock) level.getBlockState(pos).getBlock(), stack);
            // TODO: Implement effect occurrences here.
            if (!handleOutput(species)) updateGuiData();
            this.currentWork--;
            if (this.currentWork == 0)
            {
              resetApiary(state);
              handleQueenLifecycleEnd(species);
            }
          }
        }
      } else
      {
        updateGuiData();
      }
    }
  }

  private void handleInitialRunData(BeeSpecies species)
  {
    if (this.currentWork == 0 && this.maxWork == 0)
    {
      Holder<Lifespan> lifespanHolder = species.getProductionData().getLifespan();
      if (!lifespanHolder.isBound())
        throw new IllegalArgumentException("Lifespan was unbound for species: %s, REPORT THIS!".formatted(species.getReadableName().getString()));
      this.currentWork = this.maxWork = getModifiedLifeSpan();
    }
  }

  private void handlePollination(Level level, ApiaryBlock apiary, ItemStack stack)
  {
    if (Math.abs(this.currentWork - this.maxWork) % 375 == 0 && apiary.shouldPollinate(level.getRandom(), stack))
    {
      Predicate<BlockPos> filter = new LimitedFilter<>(filteredPos ->
              level.getBlockState(filteredPos).is(BlockTags.DIRT) &&
                      level.getBlockState(filteredPos.above()).isAir() &&
                      level.random.nextFloat() > 0.85f, 2);
      List<BlockPos> found = this.territory.stream().filter(filter).toList();
      if (!stack.has(DataComponentRegistration.BEE_SPECIES)) return;
      BeeSpecies species = stack.get(DataComponentRegistration.BEE_SPECIES);
      if (species == null) return;
      Registry<Block> blockRegistry = level.registryAccess().registry(Registries.BLOCK).orElseThrow();
      for (BlockPos f : found)
      {
        Optional<Holder<Block>> flower = blockRegistry.getRandomElementOf(BlockTags.SMALL_FLOWERS, level.random);
        level.setBlock(f.above(), flower.get().value().defaultBlockState(), Block.UPDATE_ALL);
      }
    }
  }

  private boolean handleOutput(BeeSpecies species)
  {
    if (Math.abs(this.currentWork - this.maxWork) % getModifiedOutputDuration() == 0)
    {
      if (ApicuriousMainConfig.main_config.debug.get()) Apicurious.LOGGER.info(String.valueOf(getModifiedOutputDuration()));
      List<ItemStack> outputs = species.getOutputData().getOutputs();
      for (ItemStack output : outputs)
      {
        if (!canOutputSuccessfully(output)) return false;
        ItemStack out = output;
        for (int i = 5; i < 12; i++)
        {
          if (getInventory().insertItem(i, out.copy(), true) != out)
          {
            out = getInventory().insertItem(i, out.copy(), false);
            if (out.isEmpty())
            {
              break;
            }
          }
        }
      }
      for (int i = 2; i < 5; i++)
      {
        ItemStack frame = getInventory().getStackInSlot(i);
        frame.hurtAndBreak(1, (ServerLevel) level, null, item ->
        {
        });
      }
    }
    return true;
  }

  private void resetApiary(BlockState state)
  {
    changeActiveState(state, false);
    this.currentWork = 0;
    this.maxWork = 0;
    this.territory = null;
  }

  private void handleQueenLifecycleEnd(BeeSpecies species)
  {
    getInventory().extractItem(0, 1, false);
    ItemStack princess = new ItemStack(ItemRegistration.PRINCESS.get(), 1);
    Fertility fertility = species.getProductionData().getFertility().value();
    ItemStack drones = new ItemStack(ItemRegistration.DRONE.get(), fertility.getOffspring());
    princess.set(DataComponentRegistration.BEE_SPECIES, species);
    drones.set(DataComponentRegistration.BEE_SPECIES, species);
    for (int i = 5; i < 12; i++)
    {
      if (getInventory().insertItem(i, princess, true) != princess)
      {
        getInventory().insertItem(i, princess, false);
        break;
      }
    }
    for (int i = 5; i < 12; i++)
    {
      if (getInventory().insertItem(i, drones, true) != drones)
      {
        getInventory().insertItem(i, drones, false);
        break;
      }
    }
  }

  public void updateGuiData()
  {
    if (getLevel() == null) return;
    //TODO change this, this is just for debug
    for (ServerPlayer player : getLevel().getServer().getPlayerList().getPlayers())
    {
      if (player instanceof ServerPlayer serverPlayer)
        PacketHandler.sendTo(new GuiDataPacket(getErrorList()), serverPlayer);
    }
  }

  @Override
  public void clientTick(Level level, BlockPos pos, BlockState state)
  {
    if (shouldRenderParticles)
    {
      // TODO: Render Particles
    }
  }

  public ContainerData getContainerData()
  {
    return containerData;
  }

  @Override
  public Component getDisplayName()
  {
    return Component.translatable("apicurious.menu.apiary");
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
  {
    if (getLevel() == null) return null;
    return new ApiaryMenu(id, inventory, ContainerLevelAccess.create(getLevel(), getBlockPos()), this);
  }

  private static class LimitedFilter<T> implements Predicate<T>
  {
    final int limit;
    private final Predicate<T> delegate;
    int matches = 0;

    public LimitedFilter(Predicate<T> delegate, int limit)
    {
      this.delegate = delegate;
      this.limit = limit;
    }

    public boolean test(T toTest)
    {
      if (this.matches > this.limit) return false;
      boolean result = delegate.test(toTest);
      if (result) matches++;
      return result;
    }
  }
}
