package sandybay.apicurious.common.bee.traits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.api.bee.species.IBeeSpecies;
import sandybay.apicurious.api.bee.traits.ITrait;

import java.util.HashSet;
import java.util.Set;

public class HumidityPreference implements ITrait<HumidityPreference> {
  // TODO: Add proper group tags.
  public static final HumidityPreference HELLISH = new HumidityPreference(1, null, "apicurious.preference.humidity.hellish");
  public static final HumidityPreference ARID = new HumidityPreference(2, null, "apicurious.preference.humidity.arid");
  public static final HumidityPreference NORMAL = new HumidityPreference(3, null, "apicurious.preference.humidity.normal");
  public static final HumidityPreference DAMP = new HumidityPreference(4, null, "apicurious.preference.humidity.damp");
  public static final HumidityPreference AQUATIC = new HumidityPreference(5, null, "apicurious.preference.humidity.aquatic");

  public static final Codec<HumidityPreference> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("humidity").forGetter(HumidityPreference::getHumidity),
                  TagKey.codec(Registries.BIOME).fieldOf("groupTag").forGetter(HumidityPreference::getGroupTag),
                  Codec.STRING.fieldOf("name").forGetter(HumidityPreference::getName)
          ).apply(instance, HumidityPreference::new)
  );

  private final int humidity;
  private final TagKey<Biome> groupTag;
  private final String name;
  private Component readableName;

  public HumidityPreference(int humidity, TagKey<Biome> groupTag, String name) {
    this.humidity = humidity;
    this.groupTag = groupTag;
    this.name = name;
  }

  private int getHumidity() {
    return humidity;
  }

  private TagKey<Biome> getGroupTag() {
    return groupTag;
  }

  private String getName() {
    return name;
  }

  public Component getReadableName() {
    return readableName;
  }

  @Override
  public Codec<HumidityPreference> getCodec() {
    return CODEC;
  }

  @Override
  public StreamCodec<FriendlyByteBuf, HumidityPreference> getStreamCodec() {
    return null;
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
