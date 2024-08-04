package sandybay.apicurious.api.housing;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.api.ApiaryError;
import sandybay.apicurious.api.housing.blockentity.IApiaryErrorHandler;
import sandybay.apicurious.api.register.ApicuriousDataComponentRegistration;
import sandybay.apicurious.api.util.ClimateHelper;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.trait.Flowers;
import sandybay.apicurious.common.bee.species.trait.WorkCycle;

import java.util.List;
import java.util.Set;

public class HousingValidation
{

  private final IApiaryErrorHandler errorHandler;

  private ItemStack key;
  private ClimateHelper helper;
  private final boolean isValid;

  public HousingValidation(IApiaryErrorHandler errorHandler)
  {
    this.errorHandler = errorHandler;
    this.isValid = false;
  }

  public boolean validate(ItemStack key, Level level, BlockPos housingPosition, Set<BlockPos> territory)
  {
    if (helper == null && level != null) helper = new ClimateHelper(level, errorHandler);
    if (key.isEmpty()) return false;
    if (!this.isValid || this.key != key)
    {
      this.key = key;
      revalidate(this.key, level, housingPosition, territory);
    }
    return isValid;
  }

  private void revalidate(ItemStack queen, Level level, BlockPos housingPosition, Set<BlockPos> territory)
  {
    validateFlowers(level, territory);
    validateHumidity(housingPosition);
    validateTemperature(housingPosition);
    validateWeather(queen, level, housingPosition);
    validateSky(queen, level, housingPosition);
    validateTime(queen, level);
  }

  private void validateFlowers(Level level, Set<BlockPos> territory)
  {
    boolean foundValid = false;
    if (!key.has(ApicuriousDataComponentRegistration.BEE_SPECIES)) return;
    BeeSpecies species = key.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null) return;
    Holder<Flowers> flowersHolder = species.getEnvironmentalData().getFlowers();
    if (!flowersHolder.isBound()) throw new IllegalArgumentException("BeeSpecies flowers were unbound! REPORT THIS!");
    TagKey<Block> flowers = flowersHolder.value().getFlowers();
    for (BlockPos pos : territory)
    {
      BlockState state = level.getBlockState(pos);
      if (level.isLoaded(pos) && !state.isAir() && state.is(flowers))
      {
        foundValid = true;
        break;
      }
    }
    if (!foundValid) errorHandler.addError(ApiaryError.MISSING_FLOWER);
    else errorHandler.removeError(ApiaryError.MISSING_FLOWER);
  }

  private void validateHumidity(BlockPos housingPosition)
  {
    helper.isCorrectHumidity(key, housingPosition);
  }

  private void validateTemperature(BlockPos housingPosition)
  {
    helper.isCorrectTemperature(key, housingPosition);
  }

  protected void validateTime(ItemStack queen, Level level)
  {
    BeeSpecies species = queen.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null || level == null) return;
    WorkCycle speciesCycle = species.getProductionData().getWorkCycle().value();
    boolean isValidCycle = speciesCycle.isValidTime((int) level.getDayTime());
    if (!isValidCycle) errorHandler.addError(ApiaryError.INVALID_TIME);
    else errorHandler.removeError(ApiaryError.INVALID_TIME);
  }

  protected void validateSky(ItemStack queen, Level level, BlockPos pos)
  {
    BeeSpecies species = queen.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null || level == null) return;
    boolean ignoresSky = species.getEnvironmentalData().ignoresSky();
    boolean canSeeSky = true;
    if (!ignoresSky)
    {
      canSeeSky = level.canSeeSky(pos.above());
    }
    if (!canSeeSky) errorHandler.addError(ApiaryError.NO_SKY);
    else errorHandler.removeError(ApiaryError.NO_SKY);
  }

  protected void validateWeather(ItemStack queen, Level level, BlockPos pos)
  {
    BeeSpecies species = queen.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null || level == null) return;
    boolean ignoresRain = species.getEnvironmentalData().ignoresRain();
    boolean isClear = true;
    if (!ignoresRain)
    {
      isClear = !level.isRainingAt(pos);
    }
    if (!isClear) errorHandler.addError(ApiaryError.IS_RAINING);
    else errorHandler.removeError(ApiaryError.IS_RAINING);
  }
}
