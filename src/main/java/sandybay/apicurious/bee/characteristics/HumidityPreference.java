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

public class HumidityPreference {
  // TODO: Add proper group tags.
  public static final HumidityPreference HELLISH = new HumidityPreference(1, null);
  public static final HumidityPreference ARID = new HumidityPreference(2, null);
  public static final HumidityPreference NORMAL = new HumidityPreference(3, null);
  public static final HumidityPreference DAMP = new HumidityPreference(4, null);
  public static final HumidityPreference AQUATIC = new HumidityPreference(5, null);

  public static final Codec<HumidityPreference> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("humidity").forGetter(HumidityPreference::getHumidity),
                  TagKey.codec(Registries.BIOME).fieldOf("groupTag").forGetter(HumidityPreference::getGroupTag)
          ).apply(instance, HumidityPreference::new)
  );

  private final int humidity;
  private final TagKey<Biome> groupTag;

  public HumidityPreference(int humidity, TagKey<Biome> groupTag) {
    this.humidity = humidity;
    this.groupTag = groupTag;
  }

  private int getHumidity() {
    return humidity;
  }

  private TagKey<Biome> getGroupTag() {
    return groupTag;
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
