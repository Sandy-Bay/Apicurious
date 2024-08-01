package sandybay.apicurious.common.bee.species.trait;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.ITrait;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousTags;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TemperaturePreference implements ITrait<TemperaturePreference> {

  public static final ResourceKey<TemperaturePreference> INFERNAL = ResourceKey.create(ApicuriousRegistries.TEMPERATURE_PREFERENCES, Apicurious.createResourceLocation("infernal"));
  public static final ResourceKey<TemperaturePreference> HOT = ResourceKey.create(ApicuriousRegistries.TEMPERATURE_PREFERENCES, Apicurious.createResourceLocation("hot"));
  public static final ResourceKey<TemperaturePreference> AVERAGE = ResourceKey.create(ApicuriousRegistries.TEMPERATURE_PREFERENCES, Apicurious.createResourceLocation("average"));
  public static final ResourceKey<TemperaturePreference> COLD = ResourceKey.create(ApicuriousRegistries.TEMPERATURE_PREFERENCES, Apicurious.createResourceLocation("cold"));
  public static final ResourceKey<TemperaturePreference> FREEZING = ResourceKey.create(ApicuriousRegistries.TEMPERATURE_PREFERENCES, Apicurious.createResourceLocation("freezing"));

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

  public TemperaturePreference(int temperature, TagKey<Biome> groupTag, String name) {
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TemperaturePreference that = (TemperaturePreference) o;
    return temperature == that.temperature && Objects.equals(groupTag, that.groupTag) && Objects.equals(name, that.name) && Objects.equals(readableName, that.readableName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(temperature, groupTag, name, readableName);
  }

  @Override
  public Codec<TemperaturePreference> getCodec() {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, TemperaturePreference> getStreamCodec() {
    return NETWORK_CODEC;
  }

  private TagKey<Biome> getTagByOrdinal(int ordinal) {
    return switch (ordinal) {
      case 1:
        yield ApicuriousTags.BiomeTags.HELLISH_TEMPERATURE;
      case 2:
        yield ApicuriousTags.BiomeTags.HOT_TEMPERATURE;
      case 3:
        yield ApicuriousTags.BiomeTags.AVERAGE_TEMPERATURE;
      case 4:
        yield ApicuriousTags.BiomeTags.COLD_TEMPERATURE;
      case 5:
        yield ApicuriousTags.BiomeTags.ICY_TEMPERATURE;
      default:
        yield null;
    };
  }

  public List<TagKey<Biome>> getTemperatureWithTolerance(TemperatureTolerance tolerance) {
    List<TagKey<Biome>> temperatureTags = new ArrayList<>();
    int minValue = Math.max(temperature - tolerance.getToleranceModifier(), 0);
    int maxValue = Math.min(temperature + tolerance.getToleranceModifier(), 4);
    for (int i = minValue; i <= maxValue; i++) {
      temperatureTags.add(getTagByOrdinal(i));
    }
    return temperatureTags;
  }
}
