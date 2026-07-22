package sandybay.apicurious.common.block.housing.blockentity;

import net.minecraft.client.multiplayer.ClientLevel;
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
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.bee.IBeeItem;
import sandybay.apicurious.api.bee.genetic.Genotype;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.genetic.mutation.MutationResolver;
import sandybay.apicurious.api.housing.BaseHousingBlock;
import sandybay.apicurious.api.housing.HousingError;
import sandybay.apicurious.api.housing.HousingValidation;
import sandybay.apicurious.api.housing.blockentity.BaseHousingBE;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStacksResourceHandler;
import sandybay.apicurious.api.item.IFrameItem;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.util.LimitedFilter;
import sandybay.apicurious.api.util.SimpleBlockHousingHelper;
import sandybay.apicurious.client.renderer.particle.BeeParticleOption;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.genetic.allele.Fertility;
import sandybay.apicurious.common.bee.genetic.allele.Lifespan;
import sandybay.apicurious.common.bee.genetic.allele.Speed;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.block.housing.ApiaryBlock;
import sandybay.apicurious.common.config.ApicuriousMainConfig;
import sandybay.apicurious.common.item.BeeItem;
import sandybay.apicurious.common.item.frame.FrameItem;
import sandybay.apicurious.common.menu.ApiaryMenu;
import sandybay.apicurious.common.network.PacketHandler;
import sandybay.apicurious.common.network.packets.GuiDataPacket;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.common.registrar.ParticleTypeRegistrar;

import java.util.*;
import java.util.function.Predicate;

public abstract class SimpleBlockHousingBE extends BaseHousingBE
{
  // Slot layout constants — replaces the "magic numbers" scattered through the original class.
  public static final int SLOT_ROYAL = 0;
  public static final int SLOT_DRONE = 1;
  public static final int SLOT_FRAME_START = 2;
  public static final int SLOT_FRAME_END = 5;
  public static final int SLOT_OUTPUT_START = 5;
  public static final int SLOT_OUTPUT_END = 12;
  private static final int DEFAULT_BREEDING_TICKS = 75;
  private static final int PARTICLE_SPAWN_INTERVAL = 50; // ~5s, tweak to taste
  private static final int FLOWER_SEARCH_RADIUS = 8;
  public final HousingValidation validation;

  // Server-sided Data
  private final ConfigurableItemStacksResourceHandler inventory;
  private final List<HousingError> errorList = new ArrayList<>();
  private final Set<ServerPlayer> viewingPlayers = new HashSet<>();
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
  public boolean shouldRenderParticles;
  // Particle constants
  private int particleSpawnCooldown;
  private Genome pendingOffspringGenome;
  private List<HousingError> lastSentErrors = List.of();

  public SimpleBlockHousingBE(BlockEntityType<?> type, BlockPos pos, BlockState state)
  {
    super(type, pos, state);
    this.inventory = new ConfigurableItemStacksResourceHandler(12).setInputFilter((stack, slot) ->
    {
      if (slot == SLOT_ROYAL)
      {
        return stack.getItem() instanceof IBeeItem beeItem && beeItem.getBeeType() != EnumBeeType.DRONE;
      }
      if (slot == SLOT_DRONE)
      {
        return stack.getItem() instanceof IBeeItem beeItem && beeItem.getBeeType() == EnumBeeType.DRONE;
      }
      if (slot >= SLOT_FRAME_START && slot < SLOT_FRAME_END)
      {
        return stack.getItem() instanceof IFrameItem;
      }
      return true;
    }).setSlotLimit(SLOT_ROYAL, 1).setSlotLimit(2, 1).setSlotLimit(3, 1).setSlotLimit(4, 1)
      .setOnSlotChanged((stack, slot) ->
    {
      if (slot == SLOT_ROYAL && stack.is(ItemRegistrar.QUEEN.item()))
      {
        removeError(HousingError.MISSING_PRINCESS);
        removeError(HousingError.MISSING_DRONE);
        updateGuiData();
      }
      if (slot == SLOT_ROYAL && stack.is(ItemRegistrar.PRINCESS.item()))
      {
        removeError(HousingError.MISSING_PRINCESS);
        updateGuiData();
      }
      if (slot == SLOT_DRONE && stack.is(ItemRegistrar.DRONE.item())) {
        removeError(HousingError.MISSING_DRONE);
        removeError(HousingError.MISSING_DRONE);
      }
      if (slot == SLOT_ROYAL && stack.isEmpty())
      {
        resetHousing(state);
      }
      if (stack.isEmpty() && slot >= SLOT_OUTPUT_START)
      {
        removeError(HousingError.FULL_INVENTORY);
      }
    }).setOutputFilter((stack, slot) -> {
      if (slot == SLOT_ROYAL && stack.is(ItemRegistrar.QUEEN.item()))
      {
        return !isActive;
      }
      return true;
    });
    this.validation = new HousingValidation(this);
  }

  @Override
  public void saveSyncData(CompoundTag tag, HolderLookup.Provider registries)
  {
    try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), Apicurious.LOGGER))
    {
      TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
      this.inventory.serialize(output);
      output.putBoolean("isActive", isActive);
      output.putBoolean("shouldRenderParticles", shouldRenderParticles);
      tag.put("housingData", output.buildResult());
    }
  }

  @Override
  public void readSyncData(ValueInput input)
  {
    ValueInput housingInput = input.childOrEmpty("housingData");
    inventory.deserialize(housingInput);
    this.isActive = housingInput.getBooleanOr("isActive", false);
    this.shouldRenderParticles = housingInput.getBooleanOr("shouldRenderParticles", false);
  }

  @Override
  public void saveWorldData(ValueOutput output)
  {
    inventory.serialize(output);
    output.putBoolean("isActive", isActive);
    output.putBoolean("shouldRenderParticles", shouldRenderParticles);
    if (pendingOffspringGenome != null)
    {
      output.store("pendingOffspringGenome", Genome.CODEC, pendingOffspringGenome);
    }
  }

  @Override
  public void readWorldData(ValueInput input)
  {
    inventory.deserialize(input);
    this.isActive = input.getBooleanOr("isActive", false);
    this.shouldRenderParticles = input.getBooleanOr("shouldRenderParticles", false);
    this.pendingOffspringGenome = input.read("pendingOffspringGenome", Genome.CODEC).orElse(null);
  }

  @Override
  public void saveUpdateData(CompoundTag tag, HolderLookup.Provider registries)
  {
    saveSyncData(tag, registries);
  }

  @Override
  public void readUpdateData(Connection net, ValueInput input)
  {
    readSyncData(input);
  }

  @Override
  public void serverTick(Level level, BlockPos pos, BlockState state)
  {
    ItemResource royal = getInventory().getResource(SLOT_ROYAL);
    boolean holdsQueen = !royal.isEmpty() && royal.is(ItemRegistrar.QUEEN.item());
    // If the "Royal Slot" holds a queen,
    if (holdsQueen)
    {
      if (errorList.contains(HousingError.MISSING_PRINCESS) || errorList.contains(HousingError.MISSING_DRONE))
      {
        removeError(HousingError.MISSING_PRINCESS);
        removeError(HousingError.MISSING_DRONE);
      }
      validateEnvironment(level, pos);
      // If there are no environment errors then "tick" the active queen.
      if (errorList.isEmpty())
      {
        tickActiveQueen(level, pos, state);
      }
    }
    else
    // If the "Royal Slot" does not contain a queen.
    {
      validatePrincessAndDrone();
      // If there are no bee-related errors then "tick" the princess/drone combo for breeding.
      if (errorList.isEmpty())
      {
        tickBreeding(level, pos, state);
      }
    }
  }

  private void tickBreeding(Level level, BlockPos pos, BlockState state)
  {
    // If currentWork is finished, and maxWork was not defaulted to 0, complete the breeding cycle and return early.
    if (currentWork == 0 && maxWork > 0)
    {
      completeBreeding(level, state);
      return;
    }

    // If currentWork is 0 and maxWork is 0, then start the breeding cycle.
    if (currentWork == 0 && maxWork == 0)
    {
      this.currentWork = DEFAULT_BREEDING_TICKS;
      this.maxWork = currentWork;
    }

    // If currentWork isn't 0 && maxWork is not defaulted 0
    if (this.currentWork != 0 && maxWork > 0)
    {
      // lower currentWork
      this.currentWork--;
    }
  }

  private void completeBreeding(Level level, BlockState state)
  {
    ItemResource princess = getInventory().getResource(SLOT_ROYAL);
    ItemResource drone = getInventory().getResource(SLOT_DRONE);
    Genome princessGenome = princess.get(DataComponentRegistrar.GENOME);
    Genome droneGenome = drone.get(DataComponentRegistrar.GENOME);
    // If either Princess or Drone Genomes are null, then return early.
    if (princessGenome == null) {
      Apicurious.LOGGER.error("Princess Genome was null, this shouldn't happen!");
      return;
    }
    if (droneGenome == null)
    {
      Apicurious.LOGGER.error("Drone Genome was null, this shouldn't happen!");
      return;
    }

    Genome offspringGenome;

    // Attempt to resolve if a mutation occurs.
    IMutation mutation = MutationResolver.resolve(level, this);
    if (mutation != null)
    {
      // If a mutation occurs, then set the offspringGenome to the defaulted genome of the new mutated species.
      offspringGenome = ((BeeSpecies) mutation.output().value()).getSpeciesDefaultGenome(level);
    }
    else
    {
      // Otherwise, create a new offspring Genome using the combined genomes of the princess and the drone.
      offspringGenome = (Genome) princessGenome.combineGenomes(droneGenome, level.getRandom());
    }

    // Create a queen that uses the genome of the princess for the purposes of the current run.
    ItemResource queen = ItemResource.of(ItemRegistrar.QUEEN.item()).with(DataComponentRegistrar.GENOME, princessGenome.copy());

    // Attempts to extract the princess and one drone, as well as try to insert the queen.
    try (Transaction tx = Transaction.openRoot())
    {
      if (
              getInventory().extract(SLOT_ROYAL, princess, 1, tx) > 0 && // Extract princess
              getInventory().extract(SLOT_DRONE, drone, 1, tx) > 0 &&    // Extract drone
              getInventory().insert(SLOT_ROYAL, queen, 1, tx) > 0)       // Insert queen
      {
        tx.commit();
      }
    }

    this.maxWork = 0;
    this.pendingOffspringGenome = offspringGenome;
    this.territory = null;
    if (ApicuriousMainConfig.getDebug())
    {
      Apicurious.LOGGER.info("Successfully turned Princess of type %s, into Queen of type %s".formatted(princessGenome.getSpecies(true).value().getReadableName().getString(), offspringGenome.getSpecies(true).value().getReadableName().getString()));
    }
  }

  private void tickActiveQueen(Level level, BlockPos pos, BlockState state)
  {
    // Grab the Queen and grab the genome
    ItemResource queen = getInventory().getResource(SLOT_ROYAL);
    Genome genome = queen.getItem() instanceof IBeeItem bee && bee.getBeeType() == EnumBeeType.QUEEN ? queen.get(DataComponentRegistrar.GENOME) : null;

    // If the queen genome is null or there are errors then set the active state to false and return early.
    if (genome == null || !errorList.isEmpty())
    {
      changeActiveState(state, false);
      return;
    }

    // If the queens genome isn't null and maxWork is defaulted 0
    if (this.maxWork == 0)
    {
      // Set the initial run information
      handleInitialRunData(genome);
    }

    // If the active state is set as false I.E. not running, then set it to true and running if we hit this point.
    if (!isActive)
    {
      changeActiveState(state, true);
    }

    // Handle pollination of flowers.
    handlePollination(level, (BaseHousingBlock) level.getBlockState(pos).getBlock(), queen);

    // If this is an Apiary block, then we need to handle output.
    if (getBlockState().getBlock() instanceof ApiaryBlock)
    {
      // If we fail to output as planned.
      if (!handleOutput(genome))
      {
        // Then add the error to the error list and send an update packet to affected players.
        addError(HousingError.FULL_INVENTORY);
        updateGuiData();
        return;
      }
    }

    // Decrement the work timer, also damage the frames.
    this.currentWork--;
    damageFrames((ServerLevel) level, ((Lifespan) genome.getLifespan(true).value()));

    // Finally if we've reached 0 for the current work, then reset the housing, and handle the Queen's end of lifecycle.
    if (this.currentWork == 0)
    {
      Genome offspringGenome = this.pendingOffspringGenome;
      resetHousing(state);
      handleQueenLifecycleEnd(offspringGenome);
    }
  }

  /**
   * Attempts to damage the frames in all the slots.
   * It attempts to do this by attempting to checking if a set interval has occured.
   * If it hasn't then it just returns early, otherwise it calls {@link #damageFrame(ServerLevel, int)}.
   */
  private void damageFrames(ServerLevel level, Lifespan lifespan)
  {
    int cycles = lifespan.getCycles();
    int interval = cycles > 0 ? maxWork / cycles : 0;
    if (interval <= 0)
    {
      return;
    }
    if ((maxWork - currentWork) % interval != 0)
    {
      return;
    }

    for (int i = SLOT_FRAME_START; i < SLOT_FRAME_END; i++)
    {
      damageFrame(level, i);
    }
  }

  /**
   * Damages the frame in the given slot by one point. {@link ItemResource} is immutable,
   * so the damaged stack is written back via extract-then-insert rather than mutating the resource directly.
   */
  private void damageFrame(ServerLevel level, int slot)
  {
    ItemResource frame = getInventory().getResource(slot);
    if (frame.isEmpty())
    {
      return;
    }

    ItemStack damagedStack = frame.toStack();
    damagedStack.hurtAndBreak(1, level, null, _ -> {});

    // Replace the stack with a new damaged stack.
    try (Transaction tx = Transaction.openRoot())
    {
      if (getInventory().extract(slot, frame, 1, tx) > 0)
      {
        if (!damagedStack.isEmpty())
        {
          getInventory().insert(slot, ItemResource.of(damagedStack), 1, tx);
        }
        tx.commit();
      }
    }
  }

  @Override
  public void clientTick(Level level, BlockPos pos, BlockState state)
  {
    if (shouldRenderParticles)
    {
      if (particleSpawnCooldown > 0)
      {
        particleSpawnCooldown--;
      }
      else
      {
        particleSpawnCooldown = PARTICLE_SPAWN_INTERVAL + level.getRandom().nextInt(PARTICLE_SPAWN_INTERVAL);
        spawnBeeParticle(level, pos);
      }
    }
  }

  public ConfigurableItemStacksResourceHandler getInventory()
  {
    return inventory;
  }

  public void changeActiveState(BlockState state, boolean shouldBeActive)
  {
    if (this.level == null) return;
    this.level.sendBlockUpdated(worldPosition, state, state.setValue(BaseHousingBlock.ACTIVE, shouldBeActive), Block.UPDATE_IMMEDIATE);
    this.isActive = shouldBeActive;
    this.shouldRenderParticles = shouldBeActive;
    this.setChanged();
  }


  /**
   * Attempts to distribute an {@link ItemResource} over all the output slots between SLOT_OUTPUT_START and SLOT_OUTPUT_END. <br>
   *
   *
   * @param resource The {@link ItemResource} to insert.
   * @param count The initial size of the resource to insert.
   * @param tx The transaction context.
   * @return Returns where it was successfully able to insert the entire stack.
   */
  private boolean tryDistributeToOutput(ItemResource resource, int count, Transaction tx)
  {
    int remaining = count;
    for (int i = SLOT_OUTPUT_START; i < SLOT_OUTPUT_END && remaining > 0; i++)
    {
      remaining -= getInventory().insert(i, resource, remaining, tx);
    }
    return remaining == 0;
  }

  /**
   * Checks whether an {@link ItemResource} can be inserted for a certain count into the output slots.
   *
   * @param output The ItemResource equivalent output stack.
   * @param count The original count size of the output.
   * @return Returns whether the output could be inserted into the output inventory.
   */
  protected boolean canOutputSuccessfully(ItemResource output, int count)
  {
    boolean canOutput;
    try (Transaction tx = Transaction.openRoot())
    {
      canOutput = tryDistributeToOutput(output, count, tx);
      if (canOutput)
      {
        tx.commit();
      }
    }
    if (!canOutput)
    {
      addError(HousingError.FULL_INVENTORY);
    }
    return canOutput;
  }

  /**
   * Computes how many ticks must pass between output attempts, after applying the
   * queen's Speed allele and any held frame production modifiers.
   */
  public int getModifiedOutputDuration()
  {
    Genome genome = inventory.getResource(SLOT_ROYAL).get(DataComponentRegistrar.GENOME);
    if (genome == null)
    {
      return 0;
    }
    Speed speed = (Speed) genome.getSpeed(true).value();
    float speedModifier = speed.getProductionModifier();
    if (speedModifier == 0.0f)
    {
      BeeSpecies species = (BeeSpecies) genome.getSpecies(true).value();
      Apicurious.LOGGER.warn("Bee species '{}' resolved a Speed allele '{}' with a productionModifier of 0.0 - falling back to 1.0. This is likely a datapack error.", species.getReadableName().getString(), speed.getName());
      speedModifier = 1.0f;
    }
    int outputDuration = Math.round(ApicuriousMainConfig.getBaseCycleTime() * speedModifier);
    for (int i = SLOT_FRAME_START; i < SLOT_FRAME_END; i++)
    {
      ItemResource stack = inventory.getResource(i);
      if (!stack.isEmpty() && stack.getItem() instanceof IFrameItem frame)
      {
        outputDuration = Math.round(outputDuration * frame.getProductionModifier());
      }
    }
    return Math.max(ApicuriousMainConfig.getMinOutputInterval(), outputDuration);
  }

  /**
   * Computes the queen's total lifespan in ticks, after applying frame lifespan modifiers.
   */
  public int getModifiedLifeSpan(Genome genome)
  {
    if (genome == null)
    {
      return 0;
    }
    Lifespan lifespanHolder = (Lifespan) genome.getLifespan(true).value();
    int cycles = lifespanHolder.getCycles();
    if (cycles <= 0)
    {
      BeeSpecies species = (BeeSpecies) genome.getSpecies(true).value();
      Apicurious.LOGGER.warn("Bee species '{}' resolved a Lifespan allele '{}' with {} cycles - falling back to 1 cycle. This is likely a datapack error.", species.getReadableName().getString(), lifespanHolder.getName(), cycles);
      cycles = 1;
    }
    int lifespan = ApicuriousMainConfig.getBaseCycleTime() * cycles;
    for (int i = SLOT_FRAME_START; i < SLOT_FRAME_END; i++)
    {
      ItemResource stack = inventory.getResource(i);
      if (!stack.isEmpty() && stack.getItem() instanceof IFrameItem frame)
      {
        lifespan = Math.round(lifespan * frame.getLifespanModifier());
      }
    }
    return Math.max(ApicuriousMainConfig.getMinLifespanTicks(), lifespan);
  }

  @Override
  public void addError(HousingError error)
  {
    if (!errorList.contains(error))
    {
      errorList.add(error);
      updateGuiData();
    }
  }

  @Override
  public void removeError(HousingError error)
  {
    errorList.remove(error);
    updateGuiData();
  }

  @Override
  public void clearErrors()
  {
    this.errorList.clear();
    updateGuiData();
  }

  public List<HousingError> getErrorList()
  {
    return errorList;
  }

  public void validateEnvironment(Level level, BlockPos pos)
  {
    ItemResource queen = inventory.getResource(SLOT_ROYAL);
    BlockState state = level.getBlockState(pos);
    if (state.getBlock() instanceof ApiaryBlock apiary)
    {
      ensureTerritory(apiary, pos, queen);
      validation.validate(queen, level, pos, this.territory, true);
    }
    if (!errorList.isEmpty()) changeActiveState(state, false);
  }

  private void ensureTerritory(ApiaryBlock apiary, BlockPos pos, ItemResource queen)
  {
    if (this.territory == null)
    {
      this.territory = apiary.getTerritory(queen, pos, SimpleBlockHousingHelper.getFrames(this));
    }
  }

  public void validatePrincessAndDrone()
  {
    boolean hasPrincess = inventory.getResource(SLOT_ROYAL).getItem() instanceof IBeeItem princess && princess.getBeeType() == EnumBeeType.PRINCESS;
    boolean hasDrone = inventory.getResource(SLOT_DRONE).getItem() instanceof IBeeItem drone && drone.getBeeType() == EnumBeeType.DRONE;
    if (!hasPrincess)
    {
      addError(HousingError.MISSING_PRINCESS);
    }
    else
    {
      removeError(HousingError.MISSING_PRINCESS);
    }
    if (!hasDrone)
    {
      addError(HousingError.MISSING_DRONE);
    }
    else
    {
      removeError(HousingError.MISSING_DRONE);
    }
  }

  private void handleInitialRunData(Genome genome)
  {
    if (this.currentWork == 0 && this.maxWork == 0)
    {
      this.currentWork = this.maxWork = getModifiedLifeSpan(genome);
    }
  }

  private void handlePollination(Level level, BaseHousingBlock housing, ItemResource stack)
  {
    if (territory == null && housing instanceof ApiaryBlock apiary)
    {
      ensureTerritory(apiary, getBlockPos(), getInventory().getResource(SLOT_ROYAL));
    }
    if (territory == null)
    {
      return;
    }

    int pollinationRate = ApicuriousMainConfig.main_config.getPollinationRate();
    if (pollinationRate <= 0 || (maxWork - currentWork) % pollinationRate != 0)
    {
      return;
    }
    if (!housing.shouldPollinate(level.getRandom(), stack))
    {
      return;
    }

    if (!stack.has(DataComponentRegistrar.GENOME))
    {
      return;
    }
    Genome genome = stack.get(DataComponentRegistrar.GENOME);
    if (genome == null)
    {
      return;
    }

    Predicate<BlockPos> filter = new LimitedFilter<>(filteredPos -> level.getBlockState(filteredPos).is(BlockTags.DIRT) && level.getBlockState(filteredPos.above()).isAir() && level.getRandom().nextFloat() < 0.15f, 2);
    List<BlockPos> found = this.territory.stream().filter(filter).toList();

    Registry<Block> blockRegistry = level.registryAccess().lookupOrThrow(Registries.BLOCK);
    for (BlockPos f : found)
    {
      Optional<Holder<Block>> flower = blockRegistry.getRandomElementOf(BlockTags.SMALL_FLOWERS, level.getRandom());
      flower.ifPresent(blockHolder -> level.setBlock(f.above(), blockHolder.value().defaultBlockState(), Block.UPDATE_ALL));
    }
  }

  /**
   * @return true if output was successfully produced (or none was due), false if
   * an output couldn't be placed (inventory full) and the caller should back off.
   */
  private boolean handleOutput(Genome genome)
  {
    int outputRate = getModifiedOutputDuration();
    if (outputRate <= 0 || (maxWork - currentWork) % outputRate != 0)
    {
      return true;
    }

    List<ItemStack> outputs = ((BeeSpecies) genome.getSpecies(true).value()).getOutputData().generate(this);
    for (ItemStack output : outputs)
    {
      if (output.isEmpty())
      {
        continue;
      }
      if (!canOutputSuccessfully(ItemResource.of(output), output.getCount()))
      {
        return false;
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
    clearErrors();
    this.setChanged();
  }

  private void handleQueenLifecycleEnd(Genome offspringGenome)
  {
    if (offspringGenome == null)
    {
      return;
    }

    try (Transaction tx = Transaction.openRoot())
    {
      if (getInventory().extract(SLOT_ROYAL, inventory.getResource(SLOT_ROYAL), 1, tx) > 0)
      {
        if (getLevel() == null)
        {
          return;
        }
        if (spawnOffspring(offspringGenome, tx))
        {
          tx.commit();
        }
        else
        {
          addError(HousingError.FULL_INVENTORY);
        }
      }
    }
  }

  /**
   * Produces a princess and her drones from the given genome and attempts to
   * place them in the output slots within the given transaction.
   */
  private boolean spawnOffspring(Genome genome, Transaction tx)
  {
    if (getLevel() == null)
    {
      return false;
    }
    int princessCount = getLevel().getRandom().nextDouble() < getAdditionalPrincessChance() ? 2 : 1;
    ItemStack princess = new ItemStack(ItemRegistrar.PRINCESS.item().get(), princessCount);
    Fertility fertility = (Fertility) genome.getFertility(true).value();
    ItemStack drones = new ItemStack(ItemRegistrar.DRONE.item(), getModifiedOffspringCount(fertility));
    princess.set(DataComponentRegistrar.GENOME, genome);
    drones.set(DataComponentRegistrar.GENOME, genome);

    boolean princessPlaced = tryDistributeToOutput(ItemResource.of(princess), princess.getCount(), tx);
    boolean dronesPlaced = tryDistributeToOutput(ItemResource.of(drones), drones.getCount(), tx);
    return princessPlaced && dronesPlaced;
  }

  private int getModifiedOffspringCount(Fertility fertility)
  {
    int offspring = fertility.getOffspring();
    if (offspring <= 0)
    {
      Apicurious.LOGGER.warn("Bee fertility allele '{}' resolved a non-positive offspring count of {} - falling back to 1. This is likely a datapack error.", fertility.getName(), offspring);
      return 1;
    }
    return offspring;
  }

  private float getAdditionalPrincessChance()
  {
    float chance = 0.005f;
    List<ItemResource> frames = SimpleBlockHousingHelper.getFrames(this);
    for (ItemResource frame : frames)
    {
      if (frame.isEmpty())
      {
        continue;
      }
      FrameItem frameItem = (FrameItem) frame.getItem();
      chance *= frameItem.getAdditionalPrincessModifier();
    }
    return Math.clamp(chance, 0.0f, 1.0f);
  }

  public void updateGuiData()
  {
    if (getLevel() == null || viewingPlayers.isEmpty())
    {
      return;
    }
    viewingPlayers.removeIf(player -> !(player.containerMenu instanceof ApiaryMenu menu) || menu.getApiary() != this);
    if (viewingPlayers.isEmpty() || getErrorList().equals(lastSentErrors))
    {
      return;
    }
    lastSentErrors = List.copyOf(getErrorList());
    GuiDataPacket packet = new GuiDataPacket(lastSentErrors);
    for (ServerPlayer player : viewingPlayers)
    {
      PacketHandler.sendTo(packet, player);
    }
  }

  public void addViewer(ServerPlayer player)
  {
    viewingPlayers.add(player);
    lastSentErrors = List.of();
  }

  public void removeViewer(ServerPlayer player)
  {
    viewingPlayers.remove(player);
  }

  public ContainerData getContainerData()
  {
    return containerData;
  }

  private void spawnBeeParticle(Level level, BlockPos pos)
  {
    if (!(level instanceof ClientLevel clientLevel))
    {
      return;
    }

    BlockPos flower = findNearbyFlower(level, pos);
    ItemResource queen = getInventory().getResource(SLOT_ROYAL);

    double spawnX = pos.getX() + 0.5;
    double spawnY = pos.getY() + 0.5;
    double spawnZ = pos.getZ() + 0.5;

    clientLevel.addParticle(new BeeParticleOption(ParticleTypeRegistrar.BEE.get(), new ItemStack(ItemRegistrar.DRONE.item(), 1, queen.getComponentsPatch()), pos, flower), spawnX, spawnY, spawnZ, 0d, 0d, 0d);
  }

  private BlockPos findNearbyFlower(Level level, BlockPos origin)
  {
    List<BlockPos> candidates = new ArrayList<>();
    BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
    for (int dx = -SimpleBlockHousingBE.FLOWER_SEARCH_RADIUS; dx <= SimpleBlockHousingBE.FLOWER_SEARCH_RADIUS; dx++)
    {
      for (int dy = -3; dy <= 3; dy++)
      {
        for (int dz = -SimpleBlockHousingBE.FLOWER_SEARCH_RADIUS; dz <= SimpleBlockHousingBE.FLOWER_SEARCH_RADIUS; dz++)
        {
          cursor.set(origin.getX() + dx, origin.getY() + dy, origin.getZ() + dz);
          if (level.getBlockState(cursor).is(BlockTags.SMALL_FLOWERS))
          {
            candidates.add(cursor.immutable());
          }
        }
      }
    }
    if (candidates.isEmpty())
    {
      return null;
    }
    return candidates.get(level.getRandom().nextInt(candidates.size()));
  }

}