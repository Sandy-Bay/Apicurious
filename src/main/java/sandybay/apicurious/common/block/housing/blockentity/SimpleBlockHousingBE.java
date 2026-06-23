package sandybay.apicurious.common.block.housing.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.bee.IBeeItem;
import sandybay.apicurious.api.bee.genetic.IGenome;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.housing.BaseHousingBlock;
import sandybay.apicurious.api.housing.HousingError;
import sandybay.apicurious.api.housing.HousingValidation;
import sandybay.apicurious.api.housing.blockentity.BaseHousingBE;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStacksResourceHandler;
import sandybay.apicurious.api.item.IFrameItem;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.LimitedFilter;
import sandybay.apicurious.api.util.SimpleBlockHousingHelper;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.genetic.allele.Fertility;
import sandybay.apicurious.common.bee.genetic.allele.Lifespan;
import sandybay.apicurious.common.bee.genetic.allele.Speed;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.block.housing.ApiaryBlock;
import sandybay.apicurious.common.config.ApicuriousMainConfig;
import sandybay.apicurious.common.item.frame.FrameItem;
import sandybay.apicurious.common.network.PacketHandler;
import sandybay.apicurious.common.network.packets.GuiDataPacket;
import sandybay.apicurious.common.registrar.ItemRegistrar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public abstract class SimpleBlockHousingBE extends BaseHousingBE
{

  public final HousingValidation validation;
  // Server-sided Data
  private final ConfigurableItemStacksResourceHandler inventory;
  private final List<HousingError> errorList = new ArrayList<>();
  public Set<BlockPos> territory;
  public boolean isActive = false;
  public int currentWork;
  public int maxWork;
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
  public int outputTimer;
  public boolean shouldRenderParticles;

  public SimpleBlockHousingBE(BlockEntityType<?> type, BlockPos pos, BlockState state)
  {
    super(type, pos, state);
    this.inventory = new ConfigurableItemStacksResourceHandler(12).setInputFilter((stack, slot) ->
    {
      if (slot == 0 && (stack.getItem() instanceof IBeeItem beeItem && beeItem.getBeeType() != EnumBeeType.DRONE))
      {return true;}
      if (slot == 1 && stack.getItem() instanceof IBeeItem beeItem && beeItem.getBeeType() == EnumBeeType.DRONE)
      {return true;}
      return (slot >= 2 && slot <= 4) && stack.getItem() instanceof IFrameItem;
    }).setSlotLimit(0, 1).setSlotLimit(2, 1).setSlotLimit(3, 1).setSlotLimit(4, 1).setOnSlotChanged((stack, slot) ->
    {
      this.setChanged();
      if (stack.isEmpty() && slot >= 5) {errorList.remove(HousingError.FULL_INVENTORY);}
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
        updateGuiData();
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
            ItemResource princess = getInventory().getResource(0);
            ItemResource drone = getInventory().getResource(1);
            Genome princessGenome = princess.get(DataComponentRegistrar.GENOME);
            Genome droneGenome = drone.get(DataComponentRegistrar.GENOME);
            ItemResource queen = ItemResource.of(ItemRegistrar.QUEEN.item());
            if (princessGenome != null && droneGenome != null)
            {
              Genome queenGenome;
              IMutation mutation = getPotentialMutation();
              if (mutation == null)
              {
                queenGenome = (Genome) princessGenome.combineGenomes(droneGenome, level.getRandom());
              }
              else
              {
                BeeSpecies mutatedSpecies = (BeeSpecies) mutation.getOutput().value();
                queenGenome = mutatedSpecies.getSpeciesDefaultGenome(level);
              }
              queen.with(DataComponentRegistrar.GENOME, queenGenome);
              try (Transaction tx = Transaction.openRoot())
              {
                if (getInventory().extract(0, princess, 1, tx) > 0 && getInventory().extract(1, drone, 1, tx) > 0)
                {
                  tx.commit();
                }
              }

              getInventory().set(0, queen, 1);
              changeActiveState(state, true);
              this.maxWork = 0;
              if (ApicuriousMainConfig.main_config.debug.get())
              {Apicurious.LOGGER.info("Successfully turned Princess of type %s, into Queen of type %s".formatted(princessGenome.getSpecies(true).value().getReadableName().getString(), queenGenome.getSpecies(true).value().getReadableName().getString()));}
            }
          }
        }
      }
      else
      {
        updateGuiData();
      }
    }
    else
    {
      validate(level, pos, false);
      ItemResource stack = getInventory().getResource(0);
      if (stack.has(DataComponentRegistrar.GENOME))
      {
        Genome genome = stack.get(DataComponentRegistrar.GENOME);
        handleInitialRunData(genome);
      }
      if (getErrorList().isEmpty())
      {
        updateGuiData();

        if (stack.getItem() instanceof IBeeItem bee && bee.getBeeType() == EnumBeeType.QUEEN)
        {
          if (stack.has(DataComponentRegistrar.GENOME))
          {
            Genome genome = stack.get(DataComponentRegistrar.GENOME);
            if (genome == null) {return;}
            handlePollination(level, (BaseHousingBlock) level.getBlockState(pos).getBlock(), stack);
            // Beta: Implement effect occurrences here.
            if (getBlockState().getBlock() instanceof ApiaryBlock && !handleOutput(genome)) {updateGuiData();}
            this.currentWork--;
            damageFrames((ServerLevel) level, genome);
            if (this.currentWork == 0)
            {
              resetHousing(state);
              handleQueenLifecycleEnd(genome);
            }
          }
        }
      }
      else
      {
        updateGuiData();
      }
    }
  }

  private void damageFrames(ServerLevel level, Genome genome)
  {
    if (Math.abs(currentWork - maxWork) % (maxWork / ((Lifespan) genome.getLifespan(true).value()).getCycles()) == 0)
    {
      for (int i = 2; i < 5; i++)
      {
        ItemResource frame = getInventory().getResource(i);
        frame.toStack().hurtAndBreak(1, level, null, item -> {});
      }
    }
  }

  @Override
  public void clientTick(Level level, BlockPos pos, BlockState state)
  {
    if (shouldRenderParticles)
    {
      // Todo: Render Particles
    }
  }

  @Override
  public void saveSyncData(CompoundTag tag, HolderLookup.Provider registries)
  {
    CompoundTag apiaryData = new CompoundTag();
    apiaryData.putBoolean("isActive", isActive);
    tag.put("apiary_data", apiaryData);
  }

  @Override
  public void saveUpdateData(CompoundTag tag, HolderLookup.Provider registries)
  {
    CompoundTag apiaryData = new CompoundTag();
    apiaryData.putBoolean("isActive", isActive);
    tag.put("apiary_data", apiaryData);
  }

  @Override
  public void saveWorldData(ValueOutput output)
  {
    inventory.serialize(output);
  }

  @Override
  public void readSyncData(ValueInput input)
  {
    this.shouldRenderParticles = input.getBooleanOr("shouldRenderParticles", false);
    this.isActive = input.getBooleanOr("isActive", false);
    inventory.deserialize(input);
  }

  @Override
  public void readUpdateData(Connection net, ValueInput input)
  {
    this.shouldRenderParticles = input.getBooleanOr("shouldRenderParticles", false);
    this.isActive = input.getBooleanOr("isActive", false);
    inventory.deserialize(input);
  }

  @Override
  public void readWorldData(ValueInput input)
  {
    this.shouldRenderParticles = input.getBooleanOr("shouldRenderParticles", false);
    this.isActive = input.getBooleanOr("isActive", false);
    inventory.deserialize(input);
  }

  public ConfigurableItemStacksResourceHandler getInventory()
  {
    return inventory;
  }

  public void changeActiveState(BlockState state, boolean shouldBeActive)
  {
    if (this.level == null) {return;}
    this.level.sendBlockUpdated(worldPosition, state, state.setValue(BaseHousingBlock.ACTIVE, shouldBeActive), Block.UPDATE_IMMEDIATE);
    this.setChanged();
    this.isActive = shouldBeActive;
    this.shouldRenderParticles = shouldBeActive;
  }


  protected boolean canOutputSuccessfully(ItemResource output)
  {
    boolean canOutput = false;
    for (int i = 5; i < 12; i++)
    {
      try (Transaction tx = Transaction.openRoot())
      {
        if (inventory.insert(i, output, output.toStack().getCount(), tx) != output.toStack().getCount())
        {
          tx.commit();
          if (output.isEmpty())
          {
            canOutput = true;
            break;
          }
        }
      }
    }
    if (!canOutput) {addError(HousingError.FULL_INVENTORY);}
    return canOutput;
  }

  public int getModifiedOutputDuration()
  {
    Genome genome = inventory.getResource(0).get(DataComponentRegistrar.GENOME);
    if (genome == null) {return 0;}
    Speed speed = (Speed) genome.getSpeed(true).value();
    int outputDuration = Math.round(ApicuriousMainConfig.main_config.baseCycleTime.get() * (speed.getProductionModifier() == 0.0f ? 1.0f : speed.getProductionModifier()));
    for (int i = 2; i < 5; i++)
    {
      ItemResource stack = inventory.getResource(i);
      if (stack.isEmpty()) {continue;}
      if (stack.getItem() instanceof IFrameItem frame)
      {
        outputDuration = Math.round(outputDuration * frame.getProductionModifier());
      }
    }
    return Math.max(1, outputDuration);
  }

  public int getModifiedLifeSpan(Genome genome)
  {
    if (genome == null) {return 0;}
    Lifespan lifespanHolder = (Lifespan) genome.getLifespan(true).value();
    int lifespan = ApicuriousMainConfig.main_config.baseCycleTime.get() * lifespanHolder.getCycles();
    for (int i = 2; i < 5; i++)
    {
      ItemResource stack = inventory.getResource(i);
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
    if (!errorList.contains(error)) {this.errorList.add(error);}
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
    }
    else
    {
      ItemResource queen = inventory.getResource(0);
      BlockState state = level.getBlockState(pos);
      if (state.getBlock() instanceof ApiaryBlock apiary)
      {
        List<ItemResource> frames = List.of(getInventory().getResource(2), getInventory().getResource(3), getInventory().getResource(4));
        if (this.territory == null) {this.territory = apiary.getTerritory(queen, pos, frames);}
        validation.validate(queen, level, pos, this.territory);
      }
    }
  }

  public void hasPrincessAndDrone()
  {
    boolean hasPrincess = inventory.getResource(0).getItem() instanceof IBeeItem princess && princess.getBeeType() == EnumBeeType.PRINCESS;
    boolean hasDrone = inventory.getResource(1).getItem() instanceof IBeeItem drone && drone.getBeeType() == EnumBeeType.DRONE;
    if (!hasPrincess) {addError(HousingError.MISSING_PRINCESS);}
    else {errorList.remove(HousingError.MISSING_PRINCESS);}
    if (!hasDrone) {addError(HousingError.MISSING_DRONE);}
    else {errorList.remove(HousingError.MISSING_DRONE);}
  }

  private void handleInitialRunData(Genome genome)
  {
    if (this.currentWork == 0 && this.maxWork == 0)
    {
      this.currentWork = this.maxWork = ApicuriousMainConfig.main_config.getApiaryRunTime(getModifiedLifeSpan(genome));
    }
  }

  private void handlePollination(Level level, BaseHousingBlock housing, ItemResource stack)
  {
    if (territory == null)
    {territory = housing.getTerritory(getInventory().getResource(0), getBlockPos(), SimpleBlockHousingHelper.getFrames(this));}
    if (Math.abs(this.currentWork - this.maxWork) % ApicuriousMainConfig.main_config.getPollinationRate() == 0 && housing.shouldPollinate(level.getRandom(), stack))
    {
      Predicate<BlockPos> filter = new LimitedFilter<>(filteredPos -> level.getBlockState(filteredPos).is(BlockTags.DIRT) && level.getBlockState(filteredPos.above()).isAir() && level.getRandom().nextFloat() < 0.15f, 2);
      List<BlockPos> found = this.territory.stream().filter(filter).toList();
      if (!stack.has(DataComponentRegistrar.GENOME)) {return;}
      Genome genome = stack.get(DataComponentRegistrar.GENOME);
      if (genome == null) {return;}
      Registry<Block> blockRegistry = level.registryAccess().get(Registries.BLOCK).orElseThrow().value();
      for (BlockPos f : found)
      {
        Optional<Holder<Block>> flower = blockRegistry.getRandomElementOf(BlockTags.SMALL_FLOWERS, level.getRandom());
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
        if (!canOutputSuccessfully(ItemResource.of(output))) {return false;}
        ItemStack out = output.copy();
        for (int i = 5; i < 12; i++)
        {
          try (Transaction tx = Transaction.openRoot())
          {
            if (getInventory().insert(i, ItemResource.of(out.copy()), out.getCount(), tx) != out.getCount())
            {
              tx.commit();
              if (out.isEmpty())
              {
                break;
              }
            }
          }
        }
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
    this.outputTimer = -1;
  }

  private void handleQueenLifecycleEnd(Genome genome)
  {
    try (Transaction tx = Transaction.openRoot())
    {
      if (getInventory().extract(0, inventory.getResource(0), 1, tx) > 0) tx.commit();
    }
    ItemStack princess = new ItemStack(ItemRegistrar.PRINCESS.item().get(), getLevel().getRandom().nextDouble() < getAdditionalPrincessChance() ? 2 : 1);
    Fertility fertility = (Fertility) genome.getFertility(true).value();
    ItemStack drones = new ItemStack(ItemRegistrar.DRONE.item().get(), fertility.getOffspring());
    princess.set(DataComponentRegistrar.GENOME, genome);
    drones.set(DataComponentRegistrar.GENOME, genome);
    for (int i = 5; i < 12; i++)
    {
      try (Transaction tx = Transaction.openRoot())
      {
        if (getInventory().insert(i, ItemResource.of(princess), princess.getCount(), tx) > 0)
        {
          tx.commit();
          break;
        }
      }
    }
    for (int i = 5; i < 12; i++)
    {
      try (Transaction tx = Transaction.openRoot()) {
        if (getInventory().insert(i, ItemResource.of(drones), drones.getCount(), tx) > 0)
        {
          tx.commit();
          break;
        }
      }
    }
  }

  private float getAdditionalPrincessChance()
  {
    float chance = 0.05f;
    List<ItemResource> frames = SimpleBlockHousingHelper.getFrames(this);
    for (ItemResource frame : frames)
    {
      FrameItem frameItem = (FrameItem) frame.getItem();
      chance *= frameItem.getAdditionalPrincessModifier();
    }
    return chance;
  }

  private IMutation getPotentialMutation()
  {
    Level level = getLevel();
    if (level == null) {return null;}
    IGenome first = getInventory().getResource(0).get(DataComponentRegistrar.GENOME);
    IGenome second = getInventory().getResource(1).get(DataComponentRegistrar.GENOME);
    Optional<Registry<IMutation>> mutationRegistry = level.registryAccess().lookup(ApicuriousRegistries.MUTATIONS);
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
    if (getLevel() == null) {return;}
    //Todo change this, this is just for debug
    for (ServerPlayer player : getLevel().getServer().getPlayerList().getPlayers())
    {
      if (player instanceof ServerPlayer serverPlayer)
      {PacketHandler.sendTo(new GuiDataPacket(getErrorList()), serverPlayer);}
    }
  }

  public ContainerData getContainerData()
  {
    return containerData;
  }

}
