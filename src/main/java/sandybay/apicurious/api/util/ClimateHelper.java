package sandybay.apicurious.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
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

  public ClimateHelper(BlockEntity blockEntity)
  {
    this(blockEntity.getLevel());
  }

  public ClimateHelper(Level level)
  {
    this.level = level;
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
    List<TagKey<Biome>> temperatures = preference.getTemperatureWithTolerance(tolerance);
    boolean found = false;
    for (TagKey<Biome> temperature : temperatures)
    {
      if (matchTemperaturePreference(temperature, pos) || getTemperatureAtPosition(pos) == temperature)
      {
        found = true;
        break;
      }
    }
    return found;
  }

  private boolean isCorrectHumidity(HumidityPreference preference, HumidityTolerance tolerance, BlockPos pos)
  {
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
