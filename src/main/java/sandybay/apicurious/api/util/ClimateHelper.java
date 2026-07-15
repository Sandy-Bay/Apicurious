package sandybay.apicurious.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.transfer.item.ItemResource;
import sandybay.apicurious.api.housing.HousingError;
import sandybay.apicurious.api.housing.blockentity.IApiaryErrorHandler;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.genetic.allele.HumidityPreference;
import sandybay.apicurious.common.bee.genetic.allele.HumidityTolerance;
import sandybay.apicurious.common.bee.genetic.allele.TemperaturePreference;
import sandybay.apicurious.common.bee.genetic.allele.TemperatureTolerance;

import java.util.List;

public class ClimateHelper
{

  private final Level level;
  private final IApiaryErrorHandler handler;

  public ClimateHelper(Level level, IApiaryErrorHandler handler)
  {
    this.level = level;
    this.handler = handler;
  }

  public boolean isCorrectTemperature(ItemResource bee, BlockPos pos)
  {
    Genome genome = bee.get(DataComponentRegistrar.GENOME);
    if (genome == null)
    {
      return false;
    }
    TemperaturePreference preference = (TemperaturePreference) genome.getTemperaturePreference(true).value();
    TemperatureTolerance tolerance = (TemperatureTolerance) genome.getTemperatureTolerance(true).value();
    return isCorrectTemperature(preference, tolerance, pos);
  }

  public boolean isCorrectHumidity(ItemResource bee, BlockPos pos)
  {
    Genome genome = bee.get(DataComponentRegistrar.GENOME);
    if (genome == null)
    {
      return false;
    }
    HumidityPreference preference = (HumidityPreference) genome.getHumidityPreference(true).value();
    HumidityTolerance tolerance = (HumidityTolerance) genome.getHumidityTolerance(true).value();
    return isCorrectHumidity(preference, tolerance, pos);
  }

  public TagKey<Biome> getTemperatureAtPosition(BlockPos pos)
  {
    Holder<Biome> biome = level.getBiome(pos);
    return biome.tags().filter(tag -> tag.location().getPath().contains("temperature")).findFirst().orElseGet(() -> getTemperatureTagByBiome(biome));
  }

  public TagKey<Biome> getHumidityAtPosition(BlockPos pos)
  {
    Holder<Biome> biome = level.getBiome(pos);
    return biome.tags().filter(tag -> tag.location().getPath().contains("humidity")).findFirst().orElseGet(() -> getHumidityTagByBiome(biome));
  }

  private boolean isCorrectTemperature(TemperaturePreference preference, TemperatureTolerance tolerance, BlockPos pos)
  {
    TagKey<Biome> tempAtPos = getTemperatureAtPosition(pos);
    List<TagKey<Biome>> temperatures = preference.getTemperatureWithTolerance(tolerance);
    boolean found = false;

    for (TagKey<Biome> temperature : temperatures)
    {
      if (level.getBiome(pos).is(temperature) || tempAtPos.equals(temperature))
      {
        found = true;
        break;
      }
    }

    if (handler != null)
    {
      if (!found)
      {
        boolean tooCold = false;
        boolean tooHot = false;
        switch (preference.getReadableName().getString())
        {
          case "Hellish" -> tooCold = true;
          case "Hot" ->
          {
            if (pathEquals(tempAtPos, "hellish"))
            {
              tooHot = true;
            }
            else
            {
              tooCold = true;
            }
          }
          case "Average" ->
          {
            if (pathEquals(tempAtPos, "hellish") || pathEquals(tempAtPos, "hot"))
            {
              tooHot = true;
            }
            if (pathEquals(tempAtPos, "icy") || pathEquals(tempAtPos, "cold"))
            {
              tooCold = true;
            }
          }
          case "Cold" ->
          {
            if (pathEquals(tempAtPos, "icy"))
            {
              tooCold = true;
            }
            else
            {
              tooHot = true;
            }
          }
          case "Icy" -> tooHot = true;
          default ->
          {
          }
        }
        if (tooCold)
        {
          handler.addError(HousingError.TOO_COLD);
        }
        if (tooHot)
        {
          handler.addError(HousingError.TOO_HOT);
        }
      }
      else
      {
        handler.removeError(HousingError.TOO_COLD);
        handler.removeError(HousingError.TOO_HOT);
      }
    }
    return found;
  }

  private boolean isCorrectHumidity(HumidityPreference preference, HumidityTolerance tolerance, BlockPos pos)
  {
    TagKey<Biome> humidAtPos = getHumidityAtPosition(pos);
    List<TagKey<Biome>> humidities = preference.getHumidityWithTolerance(tolerance);
    boolean found = false;

    for (TagKey<Biome> humidity : humidities)
    {
      if (level.getBiome(pos).is(humidity) || humidAtPos.equals(humidity))
      {
        found = true;
        break;
      }
    }

    if (handler != null)
    {
      if (!found)
      {
        boolean tooHumid = false;
        boolean tooDry = false;
        switch (preference.getReadableName().getString())
        {
          case "Hellish" -> tooHumid = true;
          case "Arid" ->
          {
            if (pathEquals(humidAtPos, "hellish"))
            {
              tooDry = true;
            }
            else
            {
              tooHumid = true;
            }
          }
          case "Average" ->
          {
            if (pathEquals(humidAtPos, "hellish") || pathEquals(humidAtPos, "arid"))
            {
              tooDry = true;
            }
            if (pathEquals(humidAtPos, "aquatic") || pathEquals(humidAtPos, "damp"))
            {
              tooHumid = true;
            }
          }
          case "Damp" ->
          {
            if (pathEquals(humidAtPos, "aquatic"))
            {
              tooHumid = true;
            }
            else
            {
              tooDry = true;
            }
          }
          case "Aquatic" -> tooDry = true;
          default ->
          {
          }
        }
        if (tooHumid)
        {
          handler.addError(HousingError.TOO_HUMID);
        }
        if (tooDry)
        {
          handler.addError(HousingError.TOO_DRY);
        }
      }
      else
      {
        handler.removeError(HousingError.TOO_HUMID);
        handler.removeError(HousingError.TOO_DRY);
      }
    }
    return found;
  }

  private boolean pathEquals(TagKey<Biome> tag, String path)
  {
    return tag.location().getPath().equals(path);
  }

  private TagKey<Biome> getTemperatureTagByBiome(Holder<Biome> biome)
  {
    if (!biome.isBound())
    {
      throw new IllegalArgumentException("Tried to get defaulted temperature tag from unbound biome! REPORT THIS!");
    }
    float temperature = biome.value().getModifiedClimateSettings().temperature();
    if (temperature <= 0.0f)
    {
      return ApicuriousTags.BiomeTags.ICY_TEMPERATURE;
    }
    if (temperature <= 0.5f)
    {
      return ApicuriousTags.BiomeTags.COLD_TEMPERATURE;
    }
    if (temperature <= 0.8f)
    {
      return ApicuriousTags.BiomeTags.AVERAGE_TEMPERATURE;
    }
    if (temperature <= 1.2f)
    {
      return ApicuriousTags.BiomeTags.HOT_TEMPERATURE;
    }
    return ApicuriousTags.BiomeTags.HELLISH_TEMPERATURE;
  }

  private TagKey<Biome> getHumidityTagByBiome(Holder<Biome> biome)
  {
    if (!biome.isBound())
    {
      throw new IllegalArgumentException("Tried to get defaulted humidity tag from unbound biome! REPORT THIS!");
    }
    float humidity = biome.value().getModifiedClimateSettings().downfall();
    if (humidity <= 0.1f)
    {
      return ApicuriousTags.BiomeTags.HELLISH_HUMIDITY;
    }
    if (humidity <= 0.2f)
    {
      return ApicuriousTags.BiomeTags.ARID_HUMIDITY;
    }
    if (humidity <= 0.8f)
    {
      return ApicuriousTags.BiomeTags.AVERAGE_HUMIDITY;
    }
    if (humidity < 0.9f)
    {
      return ApicuriousTags.BiomeTags.DAMP_HUMIDITY;
    }
    return ApicuriousTags.BiomeTags.AQUATIC_HUMIDITY;
  }
}