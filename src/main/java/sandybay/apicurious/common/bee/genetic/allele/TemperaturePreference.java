package sandybay.apicurious.common.bee.genetic.allele;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.AbstractAllele;
import sandybay.apicurious.api.bee.genetic.allele.AlleleType;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.AlleleTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousTags;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TemperaturePreference extends AbstractAllele<TemperaturePreference>
{

  public static final ResourceKey<IAllele<?>> HELLISH = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("temperature/preference/hellish"));
  public static final ResourceKey<IAllele<?>> HOT = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("temperature/preference/hot"));
  public static final ResourceKey<IAllele<?>> AVERAGE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("temperature/preference/average"));
  public static final ResourceKey<IAllele<?>> COLD = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("temperature/preference/cold"));
  public static final ResourceKey<IAllele<?>> ICY = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("temperature/preference/icy"));

  public static final MapCodec<TemperaturePreference> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.INT.fieldOf("temperature").forGetter(TemperaturePreference::getTemperature), TagKey.codec(Registries.BIOME).fieldOf("groupTag").forGetter(TemperaturePreference::getGroupTag), Codec.BOOL.fieldOf("isDominantTrait").forGetter(TemperaturePreference::isDominantTrait), Codec.STRING.fieldOf("name").forGetter(TemperaturePreference::getName)).apply(instance, TemperaturePreference::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, TemperaturePreference> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.INT, TemperaturePreference::getTemperature, ByteBufCodecs.fromCodec(TagKey.codec(Registries.BIOME)), TemperaturePreference::getGroupTag, ByteBufCodecs.BOOL, TemperaturePreference::isDominantTrait, ByteBufCodecs.STRING_UTF8, TemperaturePreference::getName, TemperaturePreference::new);

  private final int temperature;
  private final TagKey<Biome> groupTag;

  public TemperaturePreference(int temperature, TagKey<Biome> groupTag, boolean isDominantTrait, String name)
  {
    super(isDominantTrait, name);
    this.temperature = temperature;
    this.groupTag = groupTag;
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
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!super.equals(o))
    {
      return false;
    }
    TemperaturePreference that = (TemperaturePreference) o;
    return temperature == that.temperature && groupTag.equals(that.groupTag);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(super.hashCode(), temperature, groupTag);
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
    int minValue = Math.max(temperature - tolerance.getToleranceModifier(), 1);
    int maxValue = Math.min(temperature + tolerance.getToleranceModifier(), 5);
    for (int i = minValue; i <= maxValue; i++)
    {
      temperatureTags.add(getTagByOrdinal(i));
    }
    return temperatureTags;
  }

  @Override
  public AlleleType<TemperaturePreference> getTraitKey()
  {
    return AlleleTypeRegistrar.TEMPERATURE_PREFERENCE_TYPE.get();
  }
}
