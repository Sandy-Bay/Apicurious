package sandybay.apicurious.common.bee.traits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.api.bee.species.IBeeSpecies;
import sandybay.apicurious.api.bee.traits.ITrait;

import java.util.HashSet;
import java.util.Set;

public class TemperaturePreference implements ITrait<TemperaturePreference> {
  // TODO: Add proper group tags.
  public static final TemperaturePreference INFERNAL = new TemperaturePreference(1, BiomeTags.HAS_IGLOO, "apicurious.preference.temperature.infernal");
  public static final TemperaturePreference HOT = new TemperaturePreference(2, BiomeTags.HAS_IGLOO, "apicurious.preference.temperature.hot");
  public static final TemperaturePreference NORMAL = new TemperaturePreference(3, BiomeTags.HAS_IGLOO, "apicurious.preference.temperature.normal");
  public static final TemperaturePreference COLD = new TemperaturePreference(4, BiomeTags.HAS_IGLOO, "apicurious.preference.temperature.cold");
  public static final TemperaturePreference FREEZING = new TemperaturePreference(5, BiomeTags.HAS_IGLOO, "apicurious.preference.temperature.freezing");

  public static final Codec<TemperaturePreference> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("temperature").forGetter(TemperaturePreference::getTemperature),
                  TagKey.codec(Registries.BIOME).fieldOf("groupTag").forGetter(TemperaturePreference::getGroupTag),
                  Codec.STRING.fieldOf("name").forGetter(TemperaturePreference::getName)
          ).apply(instance, TemperaturePreference::new)
  );

  public static final StreamCodec<ByteBuf, TemperaturePreference> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, TemperaturePreference::getTemperature,
          ByteBufCodecs.fromCodec(TagKey.codec(Registries.BIOME)), TemperaturePreference::getGroupTag,
          ByteBufCodecs.STRING_UTF8, TemperaturePreference::getName,
          TemperaturePreference::new
  );

  private final int temperature;
  private final TagKey<Biome> groupTag;
  private final String name;
  private Component readableName;

  TemperaturePreference(int temperature, TagKey<Biome> groupTag, String name) {
    this.temperature = temperature;
    this.groupTag = groupTag;
    this.name = name;
  }

  private int getTemperature() {
    return temperature;
  }

  private TagKey<Biome> getGroupTag() {
    return groupTag;
  }

  private String getName() {
    return name;
  }

  public Component getReadableName() {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public Codec<TemperaturePreference> getCodec() {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, TemperaturePreference> getStreamCodec() {
    return NETWORK_CODEC;
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