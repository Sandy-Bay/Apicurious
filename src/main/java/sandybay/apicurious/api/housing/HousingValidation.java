package sandybay.apicurious.api.housing;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.api.housing.blockentity.IApiaryErrorHandler;
import sandybay.apicurious.api.register.DataComponentRegistration;
import sandybay.apicurious.api.util.ClimateHelper;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.genetic.allele.Flowers;
import sandybay.apicurious.common.bee.genetic.allele.Workcycle;

import java.util.Set;

public class HousingValidation
{

  private final IApiaryErrorHandler errorHandler;
  private final boolean isValid;
  private ItemStack key;
  private ClimateHelper helper;

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
    if (!key.has(DataComponentRegistration.GENOME)) return;
    Genome genome = key.get(DataComponentRegistration.GENOME);
    if (genome == null) return;
    Flowers flowers = genome.getFlowers(true);
    TagKey<Block> tagKey = flowers.getFlowers();
    for (BlockPos pos : territory)
    {
      BlockState state = level.getBlockState(pos);
      if (level.isLoaded(pos) && !state.isAir() && state.is(tagKey))
      {
        foundValid = true;
        break;
      }
    }
    if (!foundValid) errorHandler.addError(HousingError.MISSING_FLOWER);
    else errorHandler.removeError(HousingError.MISSING_FLOWER);
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
    Genome genome = queen.get(DataComponentRegistration.GENOME);
    if (genome == null || level == null) return;
    Workcycle speciesCycle = genome.getWorkcycle(true);
    boolean isValidCycle = speciesCycle.isValidTime((int) level.getDayTime());
    if (!isValidCycle) errorHandler.addError(HousingError.INVALID_TIME);
    else errorHandler.removeError(HousingError.INVALID_TIME);
  }

  protected void validateSky(ItemStack queen, Level level, BlockPos pos)
  {
    Genome genome = queen.get(DataComponentRegistration.GENOME);
    if (genome == null || level == null) return;
    boolean ignoresSky = genome.getSpecies(true).getEnvironmentalData().ignoresSky(); // TODO: Potentially implement alleles for this
    boolean canSeeSky = true;
    if (!ignoresSky)
    {
      canSeeSky = level.canSeeSky(pos.above());
    }
    if (!canSeeSky) errorHandler.addError(HousingError.NO_SKY);
    else errorHandler.removeError(HousingError.NO_SKY);
  }

  protected void validateWeather(ItemStack queen, Level level, BlockPos pos)
  {
    Genome genome = queen.get(DataComponentRegistration.GENOME);
    if (genome == null || level == null) return;
    boolean ignoresRain = genome.getSpecies(true).getEnvironmentalData().ignoresRain(); // TODO: Potentially implement alleles for this
    boolean isClear = true;
    if (!ignoresRain)
    {
      isClear = !level.isRainingAt(pos);
    }
    if (!isClear) errorHandler.addError(HousingError.IS_RAINING);
    else errorHandler.removeError(HousingError.IS_RAINING);
  }
}
