package sandybay.apicurious.api.housing;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
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
  private boolean isValid;
  private ClimateHelper helper;
  private ItemResource key;

  public HousingValidation(IApiaryErrorHandler errorHandler)
  {
    this.errorHandler = errorHandler;
    this.isValid = false;
  }

  public void validate(ItemResource key, Level level, BlockPos housingPosition, Set<BlockPos> territory,
                       boolean revalidate)
  {
    if (helper == null && level != null)
    {
      helper = new ClimateHelper(level, errorHandler);
    }
    if (!key.isEmpty() && !key.equals(this.key))
    {
      this.key = key;
    }
    if (revalidate)
    {
      this.isValid = revalidate(this.key, level, housingPosition, territory);
    }
  }

  public boolean isValid()
  {
    return isValid;
  }

  public ClimateHelper getHelper()
  {
    return helper;
  }

  private boolean revalidate(ItemResource queen, Level level, BlockPos housingPosition, Set<BlockPos> territory)
  {
    boolean flowersValid = validateFlowers(level, territory);
    boolean humidityValid = validateHumidity(housingPosition);
    boolean temperatureValid = validateTemperature(housingPosition);
    boolean weatherValid = validateWeather(queen, level, housingPosition);
    boolean skyValid = validateSky(queen, level, housingPosition);
    boolean timeValid = validateTime(queen, level);
    return flowersValid && humidityValid && temperatureValid && weatherValid && skyValid && timeValid;
  }

  private boolean validateFlowers(Level level, Set<BlockPos> territory)
  {
    if (!key.has(DataComponentRegistrar.GENOME))
    {
      return false;
    }
    Genome genome = key.get(DataComponentRegistrar.GENOME);
    if (genome == null)
    {
      return false;
    }
    Flowers flowers = (Flowers) genome.getFlowers(true).value();
    TagKey<Block> tagKey = flowers.getFlowers();

    boolean foundValid = false;
    for (BlockPos pos : territory)
    {
      if (level.isLoaded(pos))
      {
        BlockState state = level.getBlockState(pos);
        if (!state.isAir() && state.is(tagKey))
        {
          foundValid = true;
          break;
        }
      }
    }

    if (foundValid)
    {
      errorHandler.removeError(HousingError.MISSING_FLOWER);
    }
    else
    {
      errorHandler.addError(HousingError.MISSING_FLOWER);
    }
    return foundValid;
  }

  private boolean validateHumidity(BlockPos housingPosition)
  {
    return helper.isCorrectHumidity(key, housingPosition);
  }

  private boolean validateTemperature(BlockPos housingPosition)
  {
    return helper.isCorrectTemperature(key, housingPosition);
  }

  protected boolean validateTime(ItemResource queen, Level level)
  {
    Genome genome = queen.get(DataComponentRegistrar.GENOME);
    if (genome == null || level == null)
    {
      return false;
    }
    Workcycle speciesCycle = (Workcycle) genome.getWorkcycle(true).value();
    boolean isValidCycle = speciesCycle.isValidTime((int) (level.getOverworldClockTime() % 24000));
    if (isValidCycle)
    {
      errorHandler.removeError(HousingError.INVALID_TIME);
    }
    else
    {
      errorHandler.addError(HousingError.INVALID_TIME);
    }
    return isValidCycle;
  }

  protected boolean validateSky(ItemResource queen, Level level, BlockPos pos)
  {
    Genome genome = queen.get(DataComponentRegistrar.GENOME);
    if (genome == null || level == null)
    {
      return false;
    }
    boolean ignoresSky = ((BeeSpecies) genome.getSpecies(true).value()).getEnvironmentalData().ignoresSky();
    boolean canSeeSky = ignoresSky || level.canSeeSky(pos.above());
    if (canSeeSky)
    {
      errorHandler.removeError(HousingError.NO_SKY);
    }
    else
    {
      errorHandler.addError(HousingError.NO_SKY);
    }
    return canSeeSky;
  }

  protected boolean validateWeather(ItemResource queen, Level level, BlockPos pos)
  {
    Genome genome = queen.get(DataComponentRegistrar.GENOME);
    if (genome == null || level == null)
    {
      return false;
    }
    boolean ignoresRain = ((BeeSpecies) genome.getSpecies(true).value()).getEnvironmentalData().ignoresRain();
    boolean isClear = ignoresRain || !level.isRaining();
    if (isClear)
    {
      errorHandler.removeError(HousingError.IS_RAINING);
    }
    else
    {
      errorHandler.addError(HousingError.IS_RAINING);
    }
    return isClear;
  }
}