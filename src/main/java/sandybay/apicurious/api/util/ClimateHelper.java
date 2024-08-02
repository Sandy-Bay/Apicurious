package sandybay.apicurious.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import sandybay.apicurious.api.EnumApiaryError;
import sandybay.apicurious.api.housing.blockentity.BaseHousingBE;
import sandybay.apicurious.api.housing.blockentity.IApiaryErrorHandler;
import sandybay.apicurious.api.register.ApicuriousDataComponentRegistration;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.trait.HumidityPreference;
import sandybay.apicurious.common.bee.species.trait.HumidityTolerance;
import sandybay.apicurious.common.bee.species.trait.TemperaturePreference;
import sandybay.apicurious.common.bee.species.trait.TemperatureTolerance;

import java.util.List;

public class ClimateHelper
{

  private final Level level;
  private final IApiaryErrorHandler handler;

  public ClimateHelper(BaseHousingBE housing)
  {
    this(housing.getLevel(), housing);
  }

  public ClimateHelper(Level level, IApiaryErrorHandler handler)
  {
    this.level = level;
    this.handler = handler;
  }

  public boolean isCorrectTemperature(ItemStack bee, BlockPos pos)
  {
    BeeSpecies species = bee.get(ApicuriousDataComponentRegistration.BEE_SPECIES); // Replace this with Genome stuff later
    if (species == null) return false;
    Holder<TemperaturePreference> preferenceHolder = species.getEnvironmentalData().getTemperatureData().preference();
    Holder<TemperatureTolerance> toleranceHolder = species.getEnvironmentalData().getTemperatureData().tolerance();
    if (!preferenceHolder.isBound() || !toleranceHolder.isBound())
      throw new IllegalArgumentException("Preference or Tolerance was not bound! REPORT THIS!");
    return isCorrectTemperature(preferenceHolder.value(), toleranceHolder.value(), pos);
  }

  public boolean isCorrectHumidity(ItemStack bee, BlockPos pos)
  {
    BeeSpecies species = bee.get(ApicuriousDataComponentRegistration.BEE_SPECIES); // Replace this with Genome stuff later
    if (species == null) return false;
    Holder<HumidityPreference> preferenceHolder = species.getEnvironmentalData().getHumidityData().preference();
    Holder<HumidityTolerance> toleranceHolder = species.getEnvironmentalData().getHumidityData().tolerance();
    if (!preferenceHolder.isBound() || !toleranceHolder.isBound())
      throw new IllegalArgumentException("Preference or Tolerance was not bound! REPORT THIS!");
    return isCorrectHumidity(preferenceHolder.value(), toleranceHolder.value(), pos);
  }

  private boolean isCorrectTemperature(TemperaturePreference preference, TemperatureTolerance tolerance, BlockPos pos)
  {
    TagKey<Biome> tempAtPos = getTemperatureAtPosition(pos);
    List<TagKey<Biome>> temperatures = preference.getTemperatureWithTolerance(tolerance);
    boolean found = false;

    for (TagKey<Biome> temperature : temperatures)
    {
      if (matchTemperaturePreference(temperature, pos)|| tempAtPos == temperature)
      {
        found = true;
        break;
      }
    }
    if (!found && handler != null) {
      boolean tooCold = false;
      boolean tooHot = false;
      switch (preference.getReadableName().getString()) {
        case "Hellish": tooCold = true;
        case "Hot": {
          if (tempAtPos.location().getPath().equals("hellish")) tooHot = true;
          else tooCold = true;
        };
        case "Average": {
          if (tempAtPos.location().getPath().equals("hellish") || tempAtPos.location().getPath().equals("hot")) tooHot = true;
          if (tempAtPos.location().getPath().equals("icy") || tempAtPos.location().getPath().equals("cold")) tooCold = true;
        }
        case "Cold": {
          if (tempAtPos.location().getPath().equals("icy")) tooCold = true;
          else tooHot = true;
        }
        case "Icy": tooHot = true;
      }
      if (tooCold) handler.addError(EnumApiaryError.TOO_COLD);
      if (tooHot) handler.addError(EnumApiaryError.TOO_HOT);
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
      if (matchHumidityPreference(humidity, pos) || getHumidityAtPosition(pos) == humidity)
      {
        found = true;
        break;
      }
    }
    if (!found && handler != null) {
      boolean tooHumid = false;
      boolean tooDry = false;
      switch (preference.getReadableName().getString()) {
        case "Hellish": tooHumid = true;
        case "Arid": {
          if (humidAtPos.location().getPath().equals("hellish")) tooDry = true;
          else tooHumid = true;
        }
        case "Average": {
          if (humidAtPos.location().getPath().equals("hellish") || humidAtPos.location().getPath().equals("arid")) tooDry = true;
          if (humidAtPos.location().getPath().equals("aquatic") || humidAtPos.location().getPath().equals("damp")) tooHumid = true;
        }
        case "Damp": {
          if (humidAtPos.location().getPath().equals("aquatic")) tooHumid = true;
          else tooDry = true;
        }
        case "Aquatic": tooDry = true;
      }
      if (tooHumid) handler.addError(EnumApiaryError.TOO_HUMID);
      if (tooDry) handler.addError(EnumApiaryError.TOO_DRY);
    }
    return found;
  }

  public TagKey<Biome> getTemperatureAtPosition(BlockPos pos)
  {
    Holder<Biome> biome = level.getBiome(pos);
    return biome.tags().filter(tag -> tag.location().getPath().contains("temperature")).findFirst().orElse(getTemperatureTagByBiome(biome));
  }

  public TagKey<Biome> getHumidityAtPosition(BlockPos pos)
  {
    Holder<Biome> biome = level.getBiome(pos);
    return biome.tags().filter(tag -> tag.location().getPath().contains("humidity")).findFirst().orElse(getHumidityTagByBiome(biome));
  }

  private boolean matchTemperaturePreference(TagKey<Biome> preference, BlockPos pos)
  {
    return level.getBiome(pos).is(preference);
  }

  private boolean matchHumidityPreference(TagKey<Biome> preference, BlockPos pos)
  {
    return level.getBiome(pos).is(preference);
  }

  private TagKey<Biome> getTemperatureTagByBiome(Holder<Biome> biome)
  {
    TagKey<Biome> defaultedTag = null;
    if (!biome.isBound())
      throw new IllegalArgumentException("Tried to get defaulted temperature tag from unbound biome! REPORT THIS!");
    float temperature = biome.value().getModifiedClimateSettings().temperature();
    if (temperature <= 0.0f) defaultedTag = ApicuriousTags.BiomeTags.ICY_TEMPERATURE;
    if (temperature > 0.0f && temperature <= 0.5f) defaultedTag = ApicuriousTags.BiomeTags.COLD_TEMPERATURE;
    if (temperature > 0.5f && temperature <= 0.8f) defaultedTag = ApicuriousTags.BiomeTags.AVERAGE_TEMPERATURE;
    if (temperature > 0.8f && temperature <= 1.2f) defaultedTag = ApicuriousTags.BiomeTags.HOT_TEMPERATURE;
    if (temperature > 1.2f) defaultedTag = ApicuriousTags.BiomeTags.HELLISH_TEMPERATURE;
    return defaultedTag;
  }

  private TagKey<Biome> getHumidityTagByBiome(Holder<Biome> biome)
  {
    TagKey<Biome> defaultedTag = null;
    if (!biome.isBound())
      throw new IllegalArgumentException("Tried to get defaulted humidity tag from unbound biome! REPORT THIS!");
    float humidity = biome.value().getModifiedClimateSettings().downfall();
    if (humidity <= 0.1f) defaultedTag = ApicuriousTags.BiomeTags.HELLISH_HUMIDITY;
    if (humidity > 0.1f && humidity <= 0.2f) defaultedTag = ApicuriousTags.BiomeTags.ARID_HUMIDITY;
    if (humidity > 0.2f && humidity <= 0.8f) defaultedTag = ApicuriousTags.BiomeTags.AVERAGE_HUMIDITY;
    if (humidity > 0.8f && humidity < 0.9f) defaultedTag = ApicuriousTags.BiomeTags.DAMP_HUMIDITY;
    if (humidity >= 0.9f) defaultedTag = ApicuriousTags.BiomeTags.AQUATIC_HUMIDITY;
    return defaultedTag;
  }
}
