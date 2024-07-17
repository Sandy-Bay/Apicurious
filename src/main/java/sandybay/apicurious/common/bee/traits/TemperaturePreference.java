package sandybay.apicurious.common.bee.traits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.species.IBeeSpecies;
import sandybay.apicurious.api.bee.traits.ITrait;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousHolder;

import java.util.HashSet;
import java.util.Set;

public class TemperaturePreference implements ITrait<TemperaturePreference> {

  public static void load() {}

  // TODO: Add proper group tags.
  public static final ApicuriousHolder<TemperaturePreference> HELLISH = create(1, BiomeTags.HAS_IGLOO, "infernal");
  public static final ApicuriousHolder<TemperaturePreference> HOT = create(2, BiomeTags.HAS_IGLOO, "hot");
  public static final ApicuriousHolder<TemperaturePreference> WARM = create(3, BiomeTags.HAS_IGLOO, "warm");
  public static final ApicuriousHolder<TemperaturePreference> NORMAL = create(4, BiomeTags.HAS_IGLOO, "normal");
  public static final ApicuriousHolder<TemperaturePreference> CHILLY = create(5, BiomeTags.HAS_IGLOO, "chilly");
  public static final ApicuriousHolder<TemperaturePreference> COLD = create(6, BiomeTags.HAS_IGLOO, "cold");
  public static final ApicuriousHolder<TemperaturePreference> FREEZING = create(7, BiomeTags.HAS_IGLOO, "freezing");

  private static ApicuriousHolder<TemperaturePreference> create(int humidity, TagKey<Biome> groupTag, String name) {
    ResourceKey<TemperaturePreference> key = ResourceKey.create(ApicuriousRegistries.TEMPERATURE_PREFERENCES, Apicurious.createResourceLocation(name));
    TemperaturePreference area = new TemperaturePreference(humidity, groupTag, "apicurious.preference.temperature." + name);
    return new ApicuriousHolder<>(key, area);
  }

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
        preference = TemperaturePreference.HELLISH.value();
      case 2:
        preference = TemperaturePreference.HOT.value();
      case 3:
        preference = TemperaturePreference.NORMAL.value();
      case 4:
        preference = TemperaturePreference.COLD.value();
      case 5:
        preference = TemperaturePreference.FREEZING.value();
    }
    return preference;
  }


}
