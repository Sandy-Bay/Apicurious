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
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
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
import sandybay.apicurious.client.renderer.particle.BeeParticleOption;
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
import sandybay.apicurious.common.registrar.ParticleTypeRegistrar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public abstract class SimpleBlockHousingBE extends BaseHousingBE
{
  // Slot layout constants — replaces the "magic numbers" scattered through the original class.
  private static final int SLOT_ROYAL = 0;
  private static final int SLOT_DRONE = 1;
  private static final int SLOT_FRAME_START = 2;
  private static final int SLOT_FRAME_END = 5; // exclusive
  private static final int SLOT_OUTPUT_START = 5;
  private static final int SLOT_OUTPUT_END = 12; // exclusive
  private static final int DEFAULT_BREEDING_TICKS = 75;

  // Particle constants
  private int particleSpawnCooldown;
  private static final int PARTICLE_SPAWN_INTERVAL = 100; // ~5s, tweak to taste
  private static final int FLOWER_SEARCH_RADIUS = 8;

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
  public boolean shouldRenderParticles;

  public SimpleBlockHousingBE(BlockEntityType<?> type, BlockPos pos, BlockState state)
  {
    super(type, pos, state);
    this.inventory = new ConfigurableItemStacksResourceHandler(12).setInputFilter((stack, slot) ->
    {
      if (slot == SLOT_ROYAL && (stack.getItem() instanceof IBeeItem beeItem && beeItem.getBeeType() != EnumBeeType.DRONE))
      {return true;}
      if (slot == SLOT_DRONE && stack.getItem() instanceof IBeeItem beeItem && beeItem.getBeeType() == EnumBeeType.DRONE)
      {return true;}
      if ((slot >= SLOT_FRAME_START && slot < SLOT_FRAME_END && stack.getItem() instanceof IFrameItem))
      {return true;}
      return true;
    }).setSlotLimit(SLOT_ROYAL, 1).setSlotLimit(2, 1).setSlotLimit(3, 1).setSlotLimit(4, 1).setOnSlotChanged((stack, slot) ->
    {
      if ((slot == 0 || slot == 1) && currentWork > 0 && maxWork > 0 && !territory.isEmpty()) {resetHousing(state);}
      if (stack.isEmpty() && slot >= SLOT_OUTPUT_START) {errorList.remove(HousingError.FULL_INVENTORY);}
    });
    // TODO: Make it so the queen outputs to a list, and if removed clear the list, if finished dump the contents of the list into the inventory.
    // .setOutputFilter((stack, slot) -> !(slot == SLOT_ROYAL && stack.getItem() instanceof IBeeItem beeItem && beeItem.getBeeType() == EnumBeeType.QUEEN));

    this.validation = new HousingValidation(this);
  }

  @Override
  public void serverTick(Level level, BlockPos pos, BlockState state)
  {
    if (!this.isActive)
    {
      ItemResource royal = getInventory().getResource(SLOT_ROYAL);
      if (!royal.isEmpty() && royal.is(ItemRegistrar.QUEEN.item()))
      {
        changeActiveState(state, true);
        this.maxWork = 0;
      }
      else
      {
        tickBreeding(level, pos, state);
      }
    }
    else
    {
      tickActiveQueen(level, pos, state);
      if (!this.shouldRenderParticles)
      {
        this.shouldRenderParticles = true;
        this.setChanged();
      }
    }
  }

  private void tickBreeding(Level level, BlockPos pos, BlockState state)
  {
    validate(level, pos, true);
    if (!getErrorList().isEmpty())
    {
      updateGuiData();
      return;
    }

    updateGuiData();

    if (currentWork == 0 && maxWork == 0)
    {
      this.currentWork = DEFAULT_BREEDING_TICKS;
      this.maxWork = currentWork;
      this.clearErrors();
    }

    if (this.currentWork != 0)
    {
      this.currentWork--;
      if (this.currentWork == 0)
      {
        completeBreeding(level, state);
      }
    }
  }

  private void completeBreeding(Level level, BlockState state)
  {
    ItemResource princess = getInventory().getResource(SLOT_ROYAL);
    ItemResource drone = getInventory().getResource(SLOT_DRONE);
    Genome princessGenome = princess.get(DataComponentRegistrar.GENOME);
    Genome droneGenome = drone.get(DataComponentRegistrar.GENOME);
    if (princessGenome == null || droneGenome == null) {return;}

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

    ItemResource queen = ItemResource.of(ItemRegistrar.QUEEN.item()).with(DataComponentRegistrar.GENOME, queenGenome);

    boolean success;
    try (Transaction tx = Transaction.openRoot())
    {
      success = getInventory().extract(SLOT_ROYAL, princess, 1, tx) > 0 && getInventory().extract(SLOT_DRONE, drone, 1, tx) > 0 && getInventory().insert(SLOT_ROYAL, queen, 1, tx) > 0;
      if (success) {tx.commit();}
    }

    if (!success)
    {
      addError(HousingError.FULL_INVENTORY);
      return;
    }

    changeActiveState(state, true);
    this.maxWork = 0;

    if (ApicuriousMainConfig.main_config.debug.get())
    {
      Apicurious.LOGGER.info("Successfully turned Princess of type %s, into Queen of type %s".formatted(princessGenome.getSpecies(true).value().getReadableName().getString(), queenGenome.getSpecies(true).value().getReadableName().getString()));
    }
  }

  private void tickActiveQueen(Level level, BlockPos pos, BlockState state)
  {
    validate(level, pos, false);

    ItemResource stack = getInventory().getResource(SLOT_ROYAL);
    if (stack.has(DataComponentRegistrar.GENOME) && this.maxWork == 0)
    {
      Genome genome = stack.get(DataComponentRegistrar.GENOME);
      handleInitialRunData(genome);
    }

    if (!getErrorList().isEmpty())
    {
      updateGuiData();
      return;
    }

    if ((!(stack.getItem() instanceof IBeeItem bee) || bee.getBeeType() != EnumBeeType.QUEEN) || !stack.has(DataComponentRegistrar.GENOME))
    {
      return;
    }

    Genome genome = stack.get(DataComponentRegistrar.GENOME);
    if (genome == null) {return;}

    handlePollination(level, (BaseHousingBlock) level.getBlockState(pos).getBlock(), stack);
    if (getBlockState().getBlock() instanceof ApiaryBlock)
    {
      handleOutput(genome);
    }

    this.currentWork--;
    damageFrames((ServerLevel) level, genome);

    if (this.currentWork == 0)
    {
      resetHousing(state);
      handleQueenLifecycleEnd(genome);
    }
  }

  /**
   * Damages held frames at fixed intervals tied to the queen's lifespan.
   * Guarded against a zero-cycle-length or zero-interval divisor, which
   * previously could throw ArithmeticException and crash the tick.
   */
  private void damageFrames(ServerLevel level, Genome genome)
  {
    int cycles = ((Lifespan) genome.getLifespan(true).value()).getCycles();
    int interval = cycles > 0 ? maxWork / cycles : 0;
    if (interval <= 0) {return;}

    if ((maxWork - currentWork) % interval == 0)
    {
      for (int i = SLOT_FRAME_START; i < SLOT_FRAME_END; i++)
      {
        ItemResource frame = getInventory().getResource(i);
        if (frame.isEmpty()) {continue;}
        frame.toStack().hurtAndBreak(1, level, null, _ -> {});
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

  @Override
  public void saveSyncData(CompoundTag tag, HolderLookup.Provider registries)
  {
    CompoundTag apiaryData = new CompoundTag();
    apiaryData.putBoolean("isActive", isActive);
    tag.putBoolean("shouldRenderParticles", shouldRenderParticles);
    tag.put("apiary_data", apiaryData);
  }

  @Override
  public void saveUpdateData(CompoundTag tag, HolderLookup.Provider registries)
  {
    saveSyncData(tag, registries);
  }

  @Override
  public void saveWorldData(ValueOutput output)
  {
    inventory.serialize(output);
    output.putBoolean("isActive", isActive);
    output.putInt("currentWork", currentWork);
    output.putInt("maxWork", maxWork);
    output.putBoolean("shouldRenderParticles", shouldRenderParticles);
  }

  @Override
  public void readSyncData(ValueInput input)
  {
    readCommon(input);
  }

  @Override
  public void readUpdateData(Connection net, ValueInput input)
  {
    readCommon(input);
  }

  @Override
  public void readWorldData(ValueInput input)
  {
    readCommon(input);
    this.currentWork = input.getIntOr("currentWork", 0);
    this.maxWork = input.getIntOr("maxWork", 0);
  }

  private void readCommon(ValueInput input)
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
    this.isActive = shouldBeActive;
    this.shouldRenderParticles = shouldBeActive;
    this.setChanged();
  }

  /**
   * Attempts to insert `count` of `resource` spread across the output slots.
   * Tracks the remaining amount explicitly instead of relying on the input
   * ItemResource mutating itself (it never does — ItemResource is immutable),
   * which previously caused the same full count to be re-offered to every
   * slot and could over-insert items.
   *
   * @return true if the full count was placed somewhere in the output range.
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

  protected boolean canOutputSuccessfully(ItemResource output, int count)
  {
    boolean canOutput;
    try (Transaction tx = Transaction.openRoot())
    {
      canOutput = tryDistributeToOutput(output, count, tx);
      if (canOutput) {tx.commit();}
    }
    if (!canOutput) {addError(HousingError.FULL_INVENTORY);}
    return canOutput;
  }

  public int getModifiedOutputDuration()
  {
    Genome genome = inventory.getResource(SLOT_ROYAL).get(DataComponentRegistrar.GENOME);
    if (genome == null) {return 0;}
    Speed speed = (Speed) genome.getSpeed(true).value();
    int outputDuration = Math.round(ApicuriousMainConfig.main_config.baseCycleTime.get() * (speed.getProductionModifier() == 0.0f ? 1.0f : speed.getProductionModifier()));
    for (int i = SLOT_FRAME_START; i < SLOT_FRAME_END; i++)
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
    for (int i = SLOT_FRAME_START; i < SLOT_FRAME_END; i++)
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
      errorList.clear();
      ItemResource queen = inventory.getResource(SLOT_ROYAL);
      BlockState state = level.getBlockState(pos);
      if (state.getBlock() instanceof ApiaryBlock apiary)
      {
        ensureTerritory(apiary, pos, queen);
        validation.validate(queen, level, pos, this.territory);
      }
    }
  }

  private void ensureTerritory(ApiaryBlock apiary, BlockPos pos, ItemResource queen)
  {
    if (this.territory == null)
    {
      this.territory = apiary.getTerritory(queen, pos, SimpleBlockHousingHelper.getFrames(this));
    }
  }

  public void hasPrincessAndDrone()
  {
    boolean hasPrincess = inventory.getResource(SLOT_ROYAL).getItem() instanceof IBeeItem princess && princess.getBeeType() == EnumBeeType.PRINCESS;
    boolean hasDrone = inventory.getResource(SLOT_DRONE).getItem() instanceof IBeeItem drone && drone.getBeeType() == EnumBeeType.DRONE;
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
    if (territory == null && housing instanceof ApiaryBlock apiary)
    {
      ensureTerritory(apiary, getBlockPos(), getInventory().getResource(SLOT_ROYAL));
    }
    if (territory == null) {return;}

    int pollinationRate = ApicuriousMainConfig.main_config.getPollinationRate();
    if (pollinationRate <= 0 || (maxWork - currentWork) % pollinationRate != 0) {return;}
    if (!housing.shouldPollinate(level.getRandom(), stack)) {return;}

    if (!stack.has(DataComponentRegistrar.GENOME)) {return;}
    Genome genome = stack.get(DataComponentRegistrar.GENOME);
    if (genome == null) {return;}

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
    int outputRate = ApicuriousMainConfig.main_config.getOutputRate(getModifiedOutputDuration());
    if (outputRate <= 0 || (maxWork - currentWork) % outputRate != 0) {return true;}

    List<ItemStack> outputs = ((BeeSpecies) genome.getSpecies(true).value()).getOutputData().generate(this);
    for (ItemStack output : outputs)
    {
      if (output.isEmpty()) {continue;}
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

  private void handleQueenLifecycleEnd(Genome genome)
  {
    try (Transaction tx = Transaction.openRoot())
    {
      if (getInventory().extract(SLOT_ROYAL, inventory.getResource(SLOT_ROYAL), 1, tx) > 0)
      {
        int princessCount = getLevel().getRandom().nextDouble() < getAdditionalPrincessChance() ? 2 : 1;
        ItemStack princess = new ItemStack(ItemRegistrar.PRINCESS.item().get(), princessCount);
        Fertility fertility = (Fertility) genome.getFertility(true).value();
        ItemStack drones = new ItemStack(ItemRegistrar.DRONE.item().get(), fertility.getOffspring());
        princess.set(DataComponentRegistrar.GENOME, genome);
        drones.set(DataComponentRegistrar.GENOME, genome);

        boolean princessPlaced = tryDistributeToOutput(ItemResource.of(princess), princess.getCount(), tx);
        boolean dronesPlaced = tryDistributeToOutput(ItemResource.of(drones), drones.getCount(), tx);
        if (princessPlaced && dronesPlaced)
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

  private float getAdditionalPrincessChance()
  {
    float chance = 0.05f;
    List<ItemResource> frames = SimpleBlockHousingHelper.getFrames(this);
    for (ItemResource frame : frames)
    {
      if (frame.isEmpty()) {continue;}
      FrameItem frameItem = (FrameItem) frame.getItem();
      chance *= frameItem.getAdditionalPrincessModifier();
    }
    return chance;
  }

  private IMutation getPotentialMutation()
  {
    Level level = getLevel();
    if (level == null) {return null;}
    IGenome first = getInventory().getResource(SLOT_ROYAL).get(DataComponentRegistrar.GENOME);
    IGenome second = getInventory().getResource(SLOT_DRONE).get(DataComponentRegistrar.GENOME);
    if (first == null || second == null) {return null;}

    Optional<Registry<IMutation>> mutationRegistry = level.registryAccess().lookup(ApicuriousRegistries.MUTATIONS);
    if (mutationRegistry.isEmpty()) {return null;}

    return mutationRegistry.get().stream().filter(mut -> mut.test(this)).findAny().orElse(null);
  }

  public void updateGuiData()
  {
    if (getLevel() == null) {return;}
    // Todo: this still broadcasts to every online player on every tick of every
    // housing block with no errors — a real cost at scale. Should be tracking
    // only players with this block's menu open (e.g. via ContainerLevelAccess /
    // a menu listener list) rather than iterating the whole player list here.
    for (ServerPlayer player : getLevel().getServer().getPlayerList().getPlayers())
    {
      PacketHandler.sendTo(new GuiDataPacket(getErrorList()), player);
    }
  }

  public ContainerData getContainerData()
  {
    return containerData;
  }

  private void spawnBeeParticle(Level level, BlockPos pos)
  {
    if (!(level instanceof ClientLevel clientLevel)) {return;}

    BlockPos flower = findNearbyFlower(level, pos, FLOWER_SEARCH_RADIUS);
    ItemResource queen = getInventory().getResource(SLOT_ROYAL);

    double spawnX = pos.getX() + 0.5;
    double spawnY = pos.getY() + 1.0;
    double spawnZ = pos.getZ() + 0.5;

    clientLevel.addParticle(new BeeParticleOption(ParticleTypeRegistrar.BEE.get(), new ItemStack(ItemRegistrar.DRONE.item(), 1, queen.getComponentsPatch()), flower, pos), spawnX, spawnY, spawnZ, 0d,0d,0d);
  }

  private BlockPos findNearbyFlower(Level level, BlockPos origin, int radius)
  {
    List<BlockPos> candidates = new ArrayList<>();
    BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
    for (int dx = -radius; dx <= radius; dx++)
    {
      for (int dy = -3; dy <= 3; dy++)
      {
        for (int dz = -radius; dz <= radius; dz++)
        {
          cursor.set(origin.getX() + dx, origin.getY() + dy, origin.getZ() + dz);
          if (level.getBlockState(cursor).is(BlockTags.SMALL_FLOWERS))
          {
            candidates.add(cursor.immutable());
          }
        }
      }
    }
    if (candidates.isEmpty()) {return null;}
    return candidates.get(level.getRandom().nextInt(candidates.size()));
  }

}