package sandybay.apicurious.api.housing.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.bee.IBeeItem;
import sandybay.apicurious.api.bee.genetic.IGenome;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.housing.BaseHousingBlock;
import sandybay.apicurious.api.housing.HousingError;
import sandybay.apicurious.api.housing.HousingValidation;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.api.item.IFrameItem;
import sandybay.apicurious.api.network.PacketHandler;
import sandybay.apicurious.api.network.packets.GuiDataPacket;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.LimitedFilter;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.genetic.allele.Fertility;
import sandybay.apicurious.common.bee.genetic.allele.Lifespan;
import sandybay.apicurious.common.bee.genetic.allele.Speed;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.block.housing.ApiaryBlock;
import sandybay.apicurious.common.config.ApicuriousMainConfig;
import sandybay.apicurious.common.register.ItemRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public abstract class SimpleBlockHousingBE extends BaseHousingBE
{

  public final HousingValidation validation;
  // Server-sided Data
  private final ConfigurableItemStackHandler inventory;
  private final List<HousingError> errorList = new ArrayList<>();
  public Set<BlockPos> territory;
  public boolean isActive = false;
  public int currentWork;
  public int maxWork;
  public boolean shouldRenderParticles;

  public SimpleBlockHousingBE(BlockEntityType<?> type, BlockPos pos, BlockState state)
  {
    super(type, pos, state);
    this.inventory = new ConfigurableItemStackHandler(12)
            .setInputFilter((stack, slot) ->
            {
              if (slot == 0 && (stack.getItem() instanceof IBeeItem beeItem && beeItem.getBeeType() != EnumBeeType.DRONE))
                return true;
              if (slot == 1 && stack.getItem() instanceof IBeeItem beeItem && beeItem.getBeeType() == EnumBeeType.DRONE)
                return true;
              return (slot >= 2 && slot <= 4) && stack.getItem() instanceof IFrameItem;
            })
            .setSlotLimit(0, 1)
            .setSlotLimit(2, 1)
            .setSlotLimit(3, 1)
            .setSlotLimit(4, 1)
            .setOnSlotChanged((stack, slot) ->
            {
              this.setChanged();
              if (stack.isEmpty() && slot >= 5) errorList.remove(HousingError.FULL_INVENTORY);
            });
    this.validation = new HousingValidation(this);
  }

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
            ItemStack drone = getInventory().getStackInSlot(1);
            Genome princessGenome = princess.get(DataComponentRegistrar.GENOME);
            Genome droneGenome = drone.get(DataComponentRegistrar.GENOME);
            ItemStack queen = new ItemStack(ItemRegistration.QUEEN);
            if (princessGenome != null && droneGenome != null)
            {
              Genome queenGenome;
              IMutation mutation = getPotentialMutation();
              if (mutation == null)
              {
                queenGenome = (Genome) princessGenome.combineGenomes(droneGenome, level.getRandom());
              } else
              {
                BeeSpecies mutatedSpecies = (BeeSpecies) mutation.getOutput().value();
                queenGenome = mutatedSpecies.getSpeciesDefaultGenome(level);
              }
              queen.set(DataComponentRegistrar.GENOME, queenGenome);
              getInventory().extractItem(0, 1, false);
              getInventory().extractItem(1, 1, false);
              getInventory().setStackInSlot(0, queen);
              changeActiveState(state, true);
              this.maxWork = 0;
              if (ApicuriousMainConfig.main_config.debug.get())
                Apicurious.LOGGER.info("Successfully turned Princess of type %s, into Queen of type %s"
                        .formatted(
                                princessGenome.getSpecies(true).value().getReadableName().getString(),
                                queenGenome.getSpecies(true).value().getReadableName().getString()
                        )
                );
            }
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
      if (stack.has(DataComponentRegistrar.GENOME))
      {
        Genome genome = stack.get(DataComponentRegistrar.GENOME);
        handleInitialRunData(genome);
      }
      if (getErrorList().isEmpty())
      {
        updateGuiData(); // Perform extra update just to clear any junk data on the client.

        if (stack.getItem() instanceof IBeeItem bee && bee.getBeeType() == EnumBeeType.QUEEN)
        {
          if (stack.has(DataComponentRegistrar.GENOME))
          {
            Genome genome = stack.get(DataComponentRegistrar.GENOME);
            if (genome == null) return;
            handlePollination(level, (ApiaryBlock) level.getBlockState(pos).getBlock(), stack);
            // TODO: Implement effect occurrences here.
            // Only do output if it's an apiary
            if (getBlockState().getBlock() instanceof ApiaryBlock && !handleOutput(genome)) updateGuiData();
            this.currentWork--;
            if (this.currentWork == 0)
            {
              resetHousing(state);
              handleQueenLifecycleEnd(genome);
            }
          }
        }
      } else
      {
        updateGuiData();
      }
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

  @Override
  public void saveData(CompoundTag tag, HolderLookup.Provider registries, boolean clientOnly, boolean alwaysSave)
  {
    CompoundTag apiaryData = new CompoundTag();
    apiaryData.putBoolean("isActive", isActive);
    if (clientOnly)
    {
      tag.putBoolean("shouldRenderParticles", shouldRenderParticles);
    } else
    {
      if (alwaysSave || inventory.hasChanged()) apiaryData.put("inventory", inventory.serializeNBT(registries));
    }
    tag.put("apiary_data", apiaryData);
  }

  @Override
  public void readData(CompoundTag tag, HolderLookup.Provider registries, boolean clientOnly, boolean alwaysSave)
  {
    CompoundTag apiaryData = tag.getCompound("apiary_data");
    if (apiaryData.contains("shouldRenderParticles"))
      shouldRenderParticles = apiaryData.getBoolean("shouldRenderParticles");
    this.isActive = apiaryData.getBoolean("isActive");
    if (apiaryData.contains("inventory")) inventory.deserializeNBT(registries, apiaryData.getCompound("inventory"));
  }

  public ConfigurableItemStackHandler getInventory()
  {
    return inventory;
  }

  public void changeActiveState(BlockState state, boolean shouldBeActive)
  {
    if (this.level == null) return;
    this.level.sendBlockUpdated(worldPosition, state, state.setValue(BaseHousingBlock.ACTIVE, shouldBeActive), Block.UPDATE_IMMEDIATE);
    this.setChanged();
    this.isActive = shouldBeActive;
    this.shouldRenderParticles = shouldBeActive;
  }


  protected boolean canOutputSuccessfully(ItemStack output)
  {
    boolean canOutput = false;
    ItemStack out = output.copy();
    for (int i = 5; i < 12; i++)
    {
      if (inventory.insertItem(i, out.copy(), true) != out)
      {
        out = inventory.insertItem(i, out.copy(), true);
        if (out.isEmpty())
        {
          canOutput = true;
          break;
        }
      }
    }
    if (!canOutput) addError(HousingError.FULL_INVENTORY);
    return canOutput;
  }

  public int getModifiedOutputDuration()
  {
    Genome genome = inventory.getStackInSlot(0).get(DataComponentRegistrar.GENOME);
    if (genome == null) return 0;
    Speed speed = (Speed) genome.getSpeed(true).value();
    int outputDuration = Math.round(ApicuriousMainConfig.main_config.baseCycleTime.get() * (speed.getProductionModifier() == 0.0f ? 1.0f : speed.getProductionModifier()));
    for (int i = 2; i < 5; i++)
    {
      ItemStack stack = inventory.getStackInSlot(i);
      if (stack.isEmpty()) continue;
      if (stack.getItem() instanceof IFrameItem frame)
      {
        outputDuration = Math.round(outputDuration * frame.getProductionModifier());
      }
    }
    return Math.max(1, outputDuration);
  }

  public int getModifiedLifeSpan(Genome genome)
  {
    if (genome == null) return 0;
    Lifespan lifespanHolder = (Lifespan) genome.getLifespan(true).value();
    int lifespan = ApicuriousMainConfig.main_config.baseCycleTime.get() * lifespanHolder.getCycles();
    for (int i = 2; i < 5; i++)
    {
      ItemStack stack = inventory.getStackInSlot(i);
      if (stack.getItem() instanceof IFrameItem frame)
      {
        lifespan = Math.round(lifespan * frame.getLifespanModifier());
      }
    }
    return Math.max(1, lifespan);
  }

  @Override
  public void addError(HousingError error)
  {
    if (!errorList.contains(error)) this.errorList.add(error);
  }

  @Override
  public void removeError(HousingError error)
  {
    errorList.remove(error);
  }

  @Override
  public void clearErrors()
  {
    this.errorList.clear();
  }

  public List<HousingError> getErrorList()
  {
    return errorList;
  }

  public void validate(Level level, BlockPos pos, boolean isPreValidation)
  {
    if (isPreValidation)
    {
      hasPrincessAndDrone();
    } else
    {
      ItemStack queen = inventory.getStackInSlot(0);
      BlockState state = level.getBlockState(pos);
      if (state.getBlock() instanceof ApiaryBlock apiary)
      {
        List<ItemStack> frames = List.of(
                getInventory().getStackInSlot(2), getInventory().getStackInSlot(3), getInventory().getStackInSlot(4)
        );
        if (this.territory == null) this.territory = apiary.getTerritory(queen, pos, frames);
        validation.validate(queen, level, pos, this.territory);
      }
    }
  }

  public void hasPrincessAndDrone()
  {
    boolean hasPrincess = inventory.getStackInSlot(0).getItem() instanceof IBeeItem princess && princess.getBeeType() == EnumBeeType.PRINCESS;
    boolean hasDrone = inventory.getStackInSlot(1).getItem() instanceof IBeeItem drone && drone.getBeeType() == EnumBeeType.DRONE;
    if (!hasPrincess) addError(HousingError.MISSING_PRINCESS);
    else errorList.remove(HousingError.MISSING_PRINCESS);
    if (!hasDrone) addError(HousingError.MISSING_DRONE);
    else errorList.remove(HousingError.MISSING_DRONE);
  }

  private void handleInitialRunData(Genome genome)
  {
    if (this.currentWork == 0 && this.maxWork == 0)
    {
      this.currentWork = this.maxWork = ApicuriousMainConfig.main_config.getApiaryRunTime(getModifiedLifeSpan(genome));
    }
  }

  private void handlePollination(Level level, BaseHousingBlock housing, ItemStack stack)
  {
    if (Math.abs(this.currentWork - this.maxWork) % ApicuriousMainConfig.main_config.getPollinationRate() == 0 && housing.shouldPollinate(level.getRandom(), stack))
    {
      Predicate<BlockPos> filter = new LimitedFilter<>(filteredPos ->
              level.getBlockState(filteredPos).is(BlockTags.DIRT) &&
                      level.getBlockState(filteredPos.above()).isAir() &&
                      level.random.nextFloat() < 0.15f, 2);
      List<BlockPos> found = this.territory.stream().filter(filter).toList();
      if (!stack.has(DataComponentRegistrar.GENOME)) return;
      Genome genome = stack.get(DataComponentRegistrar.GENOME);
      if (genome == null) return;
      Registry<Block> blockRegistry = level.registryAccess().registry(Registries.BLOCK).orElseThrow();
      for (BlockPos f : found)
      {
        Optional<Holder<Block>> flower = blockRegistry.getRandomElementOf(BlockTags.SMALL_FLOWERS, level.random);
        flower.ifPresent(blockHolder -> level.setBlock(f.above(), blockHolder.value().defaultBlockState(), Block.UPDATE_ALL));
      }
    }
  }

  private boolean handleOutput(Genome genome)
  {
    if (Math.abs(this.currentWork - this.maxWork) % ApicuriousMainConfig.main_config.getOutputRate(getModifiedOutputDuration()) == 0)
    {
      List<ItemStack> outputs = ((BeeSpecies) genome.getSpecies(true).value()).getOutputData().generate(this);
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

  private void resetHousing(BlockState state)
  {
    changeActiveState(state, false);
    this.currentWork = 0;
    this.maxWork = 0;
    this.territory = null;
  }

  private void handleQueenLifecycleEnd(Genome genome)
  {
    getInventory().extractItem(0, 1, false);
    ItemStack princess = new ItemStack(ItemRegistration.PRINCESS.get(), 1);
    Fertility fertility = (Fertility) genome.getFertility(true).value();
    ItemStack drones = new ItemStack(ItemRegistration.DRONE.get(), fertility.getOffspring());
    princess.set(DataComponentRegistrar.GENOME, genome);
    drones.set(DataComponentRegistrar.GENOME, genome);
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

  private IMutation getPotentialMutation()
  {
    Level level = getLevel();
    if (level == null) return null;
    IGenome first = getInventory().getStackInSlot(0).get(DataComponentRegistrar.GENOME);
    IGenome second = getInventory().getStackInSlot(1).get(DataComponentRegistrar.GENOME);
    Optional<Registry<IMutation>> mutationRegistry = level.registryAccess().registry(ApicuriousRegistries.MUTATIONS);
    if (mutationRegistry.isPresent() && first != null && second != null)
    {
      Registry<IMutation> mutations = mutationRegistry.get();
      Optional<IMutation> mutation = mutations.stream().filter(mut -> mut.test(this)).findAny();
      if (mutation.isPresent())
      {
        return mutation.get();
      }
    }
    return null;
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
}
