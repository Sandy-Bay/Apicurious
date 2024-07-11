package sandybay.apicurious.bee.characteristics;

import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.bee.species.IBeeSpecies;

import java.util.HashSet;
import java.util.Set;

public enum HumidityPreference {
  // TODO: Add proper group tags.
  HELLISH(1, null),
  ARID(2, null),
  NORMAL(3, null),
  DAMP(4, null),
  AQUATIC(5, null);

  private final int humidity;
  private final TagKey<Biome> groupTag;

  HumidityPreference(int humidity, TagKey<Biome> groupTag) {
    this.humidity = humidity;
    this.groupTag = groupTag;
  }

  public boolean isValidHumidity(IBeeSpecies bee, Holder<Biome> biome) {
    return getTagsWithTolerance(bee).stream().anyMatch(biome::is);
  }

  private Set<TagKey<Biome>> getTagsWithTolerance(IBeeSpecies bee) {
    int tolerance = bee.getHumidityTolerance().getToleranceModifier();
    int minHumidity = Math.max(1, this.humidity - tolerance);
    int maxHumidity = Math.min(5, this.humidity + tolerance);
    Set<TagKey<Biome>> validBiomes = new HashSet<>();
    for (int i = minHumidity; i <= maxHumidity; i++) {
      validBiomes.add(getByOrdinal(i).groupTag);
    }
    return validBiomes;
  }

  private HumidityPreference getByOrdinal(int ordinal) {
    HumidityPreference preference = null;
    switch (ordinal) {
      case 1:
        preference = HumidityPreference.HELLISH;
      case 2:
        preference = HumidityPreference.ARID;
      case 3:
        preference = HumidityPreference.NORMAL;
      case 4:
        preference = HumidityPreference.DAMP;
      case 5:
        preference = HumidityPreference.AQUATIC;
    }
    return preference;
  }

}
