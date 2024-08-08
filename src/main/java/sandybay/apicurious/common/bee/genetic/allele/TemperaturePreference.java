package sandybay.apicurious.common.bee.genetic.allele;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.AlleleType;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.register.AlleleTypeRegistration;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousTags;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TemperaturePreference implements IAllele<TemperaturePreference>
{

  public static final ResourceKey<IAllele<?>> HELLISH = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("temperature/preference/hellish"));
  public static final ResourceKey<IAllele<?>> HOT = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("temperature/preference/hot"));
  public static final ResourceKey<IAllele<?>> AVERAGE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("temperature/preference/average"));
  public static final ResourceKey<IAllele<?>> COLD = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("temperature/preference/cold"));
  public static final ResourceKey<IAllele<?>> ICY = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("temperature/preference/icy"));

  public static final MapCodec<TemperaturePreference> CODEC = RecordCodecBuilder.mapCodec(
          instance -> instance.group(
                  Codec.INT.fieldOf("temperature").forGetter(TemperaturePreference::getTemperature),
                  TagKey.codec(Registries.BIOME).fieldOf("groupTag").forGetter(TemperaturePreference::getGroupTag),
                  Codec.BOOL.fieldOf("isDominantTrait").forGetter(TemperaturePreference::isDominantTrait),
                  Codec.STRING.fieldOf("name").forGetter(TemperaturePreference::getName)
          ).apply(instance, TemperaturePreference::new)
  );
  public static final StreamCodec<RegistryFriendlyByteBuf, TemperaturePreference> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, TemperaturePreference::getTemperature,
          ByteBufCodecs.fromCodec(TagKey.codec(Registries.BIOME)), TemperaturePreference::getGroupTag,
          ByteBufCodecs.BOOL, TemperaturePreference::isDominantTrait,
          ByteBufCodecs.STRING_UTF8, TemperaturePreference::getName,
          TemperaturePreference::new
  );

  private final int temperature;
  private final TagKey<Biome> groupTag;
  private final boolean isDominantTrait;
  private final String name;
  private Component readableName;

  public TemperaturePreference(int temperature, TagKey<Biome> groupTag, boolean isDominantTrait, String name)
  {
    this.temperature = temperature;
    this.groupTag = groupTag;
    this.isDominantTrait = isDominantTrait;
    this.name = name;
  }

  private int getTemperature()
  {
    return temperature;
  }

  private TagKey<Biome> getGroupTag()
  {
    return groupTag;
  }

  @Override
  public boolean isDominantTrait()
  {
    return isDominantTrait;
  }

  private String getName()
  {
    return name;
  }

  public Component getReadableName()
  {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TemperaturePreference that = (TemperaturePreference) o;
    return temperature == that.temperature && Objects.equals(groupTag, that.groupTag) && isDominantTrait == that.isDominantTrait && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(temperature, groupTag, isDominantTrait, name);
  }

  @Override
  public MapCodec<TemperaturePreference> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, TemperaturePreference> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  private TagKey<Biome> getTagByOrdinal(int ordinal)
  {
    return switch (ordinal)
    {
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

  public List<TagKey<Biome>> getTemperatureWithTolerance(TemperatureTolerance tolerance)
  {
    List<TagKey<Biome>> temperatureTags = new ArrayList<>();
    int minValue = Math.max(temperature - tolerance.getToleranceModifier(), 0);
    int maxValue = Math.min(temperature + tolerance.getToleranceModifier(), 4);
    for (int i = minValue; i <= maxValue; i++)
    {
      temperatureTags.add(getTagByOrdinal(i));
    }
    return temperatureTags;
  }

  @Override
  public AlleleType<TemperaturePreference> getTraitKey()
  {
    return AlleleTypeRegistration.TEMPERATURE_PREFERENCE_TYPE.get();
  }
}
