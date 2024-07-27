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

import java.util.ArrayList;
import java.util.List;

public class HumidityPreference implements ITrait<HumidityPreference> {

  public static final ResourceKey<HumidityPreference> HELLISH = ResourceKey.create(ApicuriousRegistries.HUMIDITY_PREFERENCES, Apicurious.createResourceLocation("hellish"));
  public static final ResourceKey<HumidityPreference> ARID = ResourceKey.create(ApicuriousRegistries.HUMIDITY_PREFERENCES, Apicurious.createResourceLocation("arid"));
  public static final ResourceKey<HumidityPreference> AVERAGE = ResourceKey.create(ApicuriousRegistries.HUMIDITY_PREFERENCES, Apicurious.createResourceLocation("normal"));
  public static final ResourceKey<HumidityPreference> DAMP = ResourceKey.create(ApicuriousRegistries.HUMIDITY_PREFERENCES, Apicurious.createResourceLocation("damp"));
  public static final ResourceKey<HumidityPreference> AQUATIC = ResourceKey.create(ApicuriousRegistries.HUMIDITY_PREFERENCES, Apicurious.createResourceLocation("aquatic"));


  public static final Codec<HumidityPreference> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("humidity").forGetter(HumidityPreference::getHumidity),
                  TagKey.codec(Registries.BIOME).fieldOf("groupTag").forGetter(HumidityPreference::getGroupTag),
                  Codec.STRING.fieldOf("name").forGetter(HumidityPreference::getName)
          ).apply(instance, HumidityPreference::new)
  );

  public static final StreamCodec<ByteBuf, HumidityPreference> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, HumidityPreference::getHumidity,
          ByteBufCodecs.fromCodec(TagKey.codec(Registries.BIOME)), HumidityPreference::getGroupTag,
          ByteBufCodecs.STRING_UTF8, HumidityPreference::getName,
          HumidityPreference::new
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
  public StreamCodec<ByteBuf, HumidityPreference> getStreamCodec() {
    return NETWORK_CODEC;
  }

  private TagKey<Biome> getTagByOrdinal(int ordinal) {
    // TODO: Replace with proper tags.
    // TODO: Figure out a more dynamic way to do this...
    return switch (ordinal) {
      case 0:
        yield BiomeTags.HAS_IGLOO;
      case 1:
        yield BiomeTags.HAS_IGLOO;
      case 2:
        yield BiomeTags.HAS_IGLOO;
      case 3:
        yield BiomeTags.HAS_IGLOO;
      case 4:
        yield BiomeTags.HAS_IGLOO;
      default:
        yield null;
    };
  }

  public List<TagKey<Biome>> getHumidityWithTolerance(HumidityTolerance tolerance) {
    List<TagKey<Biome>> humidityTags = new ArrayList<>();
    int minValue = Math.max(humidity - tolerance.getToleranceModifier(), 0);
    int maxValue = Math.min(humidity + tolerance.getToleranceModifier(), 4);
    for (int i = minValue; i <= maxValue; i++) {
      humidityTags.add(getTagByOrdinal(i));
    }
    return humidityTags;
  }
}
