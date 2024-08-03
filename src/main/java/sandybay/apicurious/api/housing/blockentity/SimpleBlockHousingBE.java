package sandybay.apicurious.api.housing.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.api.EnumApiaryError;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.bee.IBeeItem;
import sandybay.apicurious.api.housing.BaseHousingBlock;
import sandybay.apicurious.api.housing.HousingValidation;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.api.item.IFrameItem;
import sandybay.apicurious.api.register.ApicuriousDataComponentRegistration;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.trait.WorkCycle;

import java.util.ArrayList;
import java.util.List;

//This does not need to be in the api
public abstract class SimpleBlockHousingBE extends BaseHousingBE
{

  public final HousingValidation validation;
  // Server-sided Data
  private final ConfigurableItemStackHandler inventory;
  public List<BlockPos> territory;

  public boolean isActive = false;
  public int currentWork;
  public int maxWork;

  public List<EnumApiaryError> errorList = new ArrayList<>();

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
            .setOnSlotChanged((stack, slot) -> this.setChanged());
    this.validation = new HousingValidation(this);
  }

  @Override
  public void saveData(CompoundTag tag, HolderLookup.Provider registries, boolean clientOnly, boolean alwaysSave)
  {
    CompoundTag apiaryData = new CompoundTag();
    apiaryData.putBoolean("isActive", isActive);
    if (clientOnly)
    {
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
  }

  public boolean isValidForStartup()
  {
    boolean hasPrincess = getInventory().getStackInSlot(0).getItem() instanceof IBeeItem princess && princess.getBeeType() == EnumBeeType.PRINCESS;
    boolean hasDrone = getInventory().getStackInSlot(1).getItem() instanceof IBeeItem drone && drone.getBeeType() == EnumBeeType.DRONE;
    if (!hasPrincess) errorList.add(EnumApiaryError.MISSING_PRINCESS);
    if (!hasDrone) errorList.add(EnumApiaryError.MISSING_DRONE);
    return hasPrincess && hasDrone && this.currentWork == 0 && this.maxWork == 0;
  }

  protected boolean checkWorkCycle()
  {
    ItemStack queen = getInventory().getStackInSlot(0);
    BeeSpecies species = queen.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null || getLevel() == null) return false;
    WorkCycle speciesCycle = species.getProductionData().getWorkCycle().value();
    boolean isValidCycle = speciesCycle.isValidTime((int) getLevel().getDayTime());
    if (!isValidCycle) addError(EnumApiaryError.INVALID_TIME);
    return isValidCycle;
  }

  protected boolean checkSky()
  {
    ItemStack queen = getInventory().getStackInSlot(0);
    BeeSpecies species = queen.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null || getLevel() == null) return false;
    boolean ignoresSky = species.getEnvironmentalData().ignoresSky();
    boolean canSeeSky = true;
    if (!ignoresSky) {
      canSeeSky = getLevel().canSeeSky(getBlockPos());
    }
    if (!canSeeSky) addError(EnumApiaryError.NO_SKY);
    return canSeeSky;
  }

  protected boolean checkRain()
  {
    ItemStack queen = getInventory().getStackInSlot(0);
    BeeSpecies species = queen.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null || getLevel() == null) return false;
    boolean ignoresRain = species.getEnvironmentalData().ignoresRain();
    boolean isClear = true;
    if (!ignoresRain) {
      isClear = getLevel().isRaining() && getLevel().getBiome(getBlockPos()).value().hasPrecipitation();
    }
    if (!isClear) addError(EnumApiaryError.IS_RAINING);
    return isClear;
  }

  @Override
  public void addError(EnumApiaryError error)
  {
    if (!errorList.contains(error)) this.errorList.add(error);
  }

  @Override
  public void clearErrors()
  {
    this.errorList.clear();
  }
}
