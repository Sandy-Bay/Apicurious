package sandybay.apicurious.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ClimateHelper {

  private final Level level;

  public ClimateHelper(BlockEntity blockEntity) {
    this(blockEntity.getLevel());
  }

  public ClimateHelper(Level level) {
    this.level = level;
  }

  public TagKey<Biome> getTemperatureAtPosition(BlockPos pos) {
    Holder<Biome> biome = level.getBiome(pos);
    return biome.tags().filter(tag -> tag.location().getPath().contains("temperature")).findFirst().orElse(getTemperatureTagByBiome(biome));
  }

  public TagKey<Biome> getHumidityAtPosition(BlockPos pos) {
    Holder<Biome> biome = level.getBiome(pos);
    return biome.tags().filter(tag -> tag.location().getPath().contains("humidity")).findFirst().orElse(getHumidityTagByBiome(biome));
  }

  private TagKey<Biome> getTemperatureTagByBiome(Holder<Biome> biome) {
    TagKey<Biome> defaultedTag = null;
    if (!biome.isBound()) throw new IllegalArgumentException("Tried to get defaulted temperature tag from unbound biome!");
    float temperature = biome.value().getModifiedClimateSettings().temperature();
    if (temperature <= 0.0f) defaultedTag = ApicuriousTags.BiomeTags.ICY_TEMPERATURE;
    if (temperature > 0.0f && temperature <= 0.5f) defaultedTag = ApicuriousTags.BiomeTags.COLD_TEMPERATURE;
    if (temperature > 0.5f && temperature <= 0.8f) defaultedTag = ApicuriousTags.BiomeTags.AVERAGE_TEMPERATURE;
    if (temperature > 0.8f && temperature <= 1.2f) defaultedTag = ApicuriousTags.BiomeTags.HOT_TEMPERATURE;
    if (temperature > 1.2f) defaultedTag = ApicuriousTags.BiomeTags.HELLISH_TEMPERATURE;
    return defaultedTag;
  }

  private TagKey<Biome> getHumidityTagByBiome(Holder<Biome> biome) {
    TagKey<Biome> defaultedTag = null;
    if (!biome.isBound()) throw new IllegalArgumentException("Tried to get defaulted humidity tag from unbound biome!");
    float humidity = biome.value().getModifiedClimateSettings().downfall();
    if (humidity <= 0.1f) defaultedTag = ApicuriousTags.BiomeTags.HELLISH_HUMIDITY;
    if (humidity > 0.1f && humidity <= 0.2f) defaultedTag = ApicuriousTags.BiomeTags.ARID_HUMIDITY;
    if (humidity > 0.2f && humidity <= 0.8f) defaultedTag = ApicuriousTags.BiomeTags.AVERAGE_HUMIDITY;
    if (humidity > 0.8f && humidity < 0.9f) defaultedTag = ApicuriousTags.BiomeTags.DAMP_HUMIDITY;
    if (humidity >= 0.9f) defaultedTag = ApicuriousTags.BiomeTags.AQUATIC_HUMIDITY;
    return defaultedTag;
  }
}
