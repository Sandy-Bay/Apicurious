package sandybay.apicurious.api.housing;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.item.ItemResource;
import sandybay.apicurious.api.housing.blockentity.IApiaryErrorHandler;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.util.ClimateHelper;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.genetic.allele.Flowers;
import sandybay.apicurious.common.bee.genetic.allele.Workcycle;
import sandybay.apicurious.common.bee.species.BeeSpecies;

import java.util.Set;

public class HousingValidation
{

  private final IApiaryErrorHandler errorHandler;
  private final boolean isValid;
  public ClimateHelper helper;
  private ItemResource key;

  public HousingValidation(IApiaryErrorHandler errorHandler)
  {
    this.errorHandler = errorHandler;
    this.isValid = false;
  }

  public boolean validate(ItemResource key, Level level, BlockPos housingPosition, Set<BlockPos> territory)
  {
    if (helper == null && level != null) {helper = new ClimateHelper(level, errorHandler);}
    if (key.isEmpty()) {return false;}
    if (!this.isValid || this.key != key)
    {
      this.key = key;
      revalidate(this.key, level, housingPosition, territory);
    }
    return isValid;
  }

  private void revalidate(ItemResource queen, Level level, BlockPos housingPosition, Set<BlockPos> territory)
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
    if (!key.has(DataComponentRegistrar.GENOME)) {return;}
    Genome genome = key.get(DataComponentRegistrar.GENOME);
    if (genome == null) {return;}
    Flowers flowers = (Flowers) genome.getFlowers(true).value();
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
    if (!foundValid) {errorHandler.addError(HousingError.MISSING_FLOWER);}
    else {errorHandler.removeError(HousingError.MISSING_FLOWER);}
  }

  private void validateHumidity(BlockPos housingPosition)
  {
    helper.isCorrectHumidity(key, housingPosition);
  }

  private void validateTemperature(BlockPos housingPosition)
  {
    helper.isCorrectTemperature(key, housingPosition);
  }

  protected void validateTime(ItemResource queen, Level level)
  {
    Genome genome = queen.get(DataComponentRegistrar.GENOME);
    if (genome == null || level == null) {return;}
    Workcycle speciesCycle = (Workcycle) genome.getWorkcycle(true).value();
    boolean isValidCycle = speciesCycle.isValidTime((int) level.getDayTime());
    if (!isValidCycle) {errorHandler.addError(HousingError.INVALID_TIME);}
    else {errorHandler.removeError(HousingError.INVALID_TIME);}
  }

  protected void validateSky(ItemResource queen, Level level, BlockPos pos)
  {
    Genome genome = queen.get(DataComponentRegistrar.GENOME);
    if (genome == null || level == null) {return;}
    boolean ignoresSky = ((BeeSpecies) genome.getSpecies(true).value()).getEnvironmentalData().ignoresSky();
    boolean canSeeSky = true;
    if (!ignoresSky)
    {
      canSeeSky = level.canSeeSky(pos.above());
    }
    if (!canSeeSky) {errorHandler.addError(HousingError.NO_SKY);}
    else {errorHandler.removeError(HousingError.NO_SKY);}
  }

  protected void validateWeather(ItemResource queen, Level level, BlockPos pos)
  {
    Genome genome = queen.get(DataComponentRegistrar.GENOME);
    if (genome == null || level == null) {return;}
    boolean ignoresRain = ((BeeSpecies) genome.getSpecies(true).value()).getEnvironmentalData().ignoresRain();
    boolean isClear = true;
    if (!ignoresRain)
    {
      isClear = !level.isRainingAt(pos);
    }
    if (!isClear) {errorHandler.addError(HousingError.IS_RAINING);}
    else {errorHandler.removeError(HousingError.IS_RAINING);}
  }
}
