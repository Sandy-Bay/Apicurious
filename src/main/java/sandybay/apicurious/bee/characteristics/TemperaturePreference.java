package sandybay.apicurious.bee.characteristics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.bee.species.IBeeSpecies;

import java.util.HashSet;
import java.util.Set;

public class TemperaturePreference {
  // TODO: Add proper group tags.
  public static final TemperaturePreference INFERNAL = new TemperaturePreference(1, null);
  public static final TemperaturePreference HOT = new TemperaturePreference(2, null);
  public static final TemperaturePreference NORMAL = new TemperaturePreference(3, null);
  public static final TemperaturePreference COLD = new TemperaturePreference(4, null);
  public static final TemperaturePreference FREEZING = new TemperaturePreference(5, null);

  public static final Codec<TemperaturePreference> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("temperature").forGetter(TemperaturePreference::getTemperature),
                  TagKey.codec(Registries.BIOME).fieldOf("groupTag").forGetter(TemperaturePreference::getGroupTag)
          ).apply(instance, TemperaturePreference::new)
  );

  private final int temperature;
  private final TagKey<Biome> groupTag;

  TemperaturePreference(int temperature, TagKey<Biome> groupTag) {
    this.temperature = temperature;
    this.groupTag = groupTag;
  }

  private int getTemperature() {
    return temperature;
  }

  private TagKey<Biome> getGroupTag() {
    return groupTag;
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
