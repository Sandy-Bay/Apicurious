package sandybay.apicurious.bee.characteristics;

import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.bee.species.IBeeSpecies;

import java.util.HashSet;
import java.util.Set;

public enum TemperaturePreference {
  // TODO: Add proper group tags.
  INFERNAL(1, null),
  HOT(2, null),
  NORMAL(3, null),
  COLD(4, null),
  FREEZING(5, null);

  private final int temperature;
  private final TagKey<Biome> groupTag;

  TemperaturePreference(int temperature, TagKey<Biome> groupTag) {
    this.temperature = temperature;
    this.groupTag = groupTag;
  }

  public boolean isValidTemperature(IBeeSpecies bee, Holder<Biome> biome) {
    return getTagsWithTolerance(bee).stream().anyMatch(biome::is);
  }

  private Set<TagKey<Biome>> getTagsWithTolerance(IBeeSpecies bee) {
    int tolerance = bee.getTemperatureTolerance().getToleranceModifier();
    int minHumidity = Math.max(1, this.temperature - tolerance);
    int maxHumidity = Math.min(5, this.temperature + tolerance);
    Set<TagKey<Biome>> validBiomes = new HashSet<>();
    for (int i = minHumidity; i <= maxHumidity; i++) {
      validBiomes.add(getByOrdinal(i).groupTag);
    }
    return validBiomes;
  }

  private TemperaturePreference getByOrdinal(int ordinal) {
    TemperaturePreference preference = null;
    switch (ordinal) {
      case 1:
        preference = TemperaturePreference.INFERNAL;
      case 2:
        preference = TemperaturePreference.HOT;
      case 3:
        preference = TemperaturePreference.NORMAL;
      case 4:
        preference = TemperaturePreference.COLD;
      case 5:
        preference = TemperaturePreference.FREEZING;
    }
    return preference;
  }


}
