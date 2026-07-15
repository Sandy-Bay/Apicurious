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
import sandybay.apicurious.api.bee.genetic.allele.AbstractAllele;
import sandybay.apicurious.api.bee.genetic.allele.AlleleType;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.AlleleTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousTags;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HumidityPreference extends AbstractAllele<HumidityPreference>
{

  public static final ResourceKey<IAllele<?>> HELLISH = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("humidity/preference/hellish"));
  public static final ResourceKey<IAllele<?>> ARID = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("humidity/preference/arid"));
  public static final ResourceKey<IAllele<?>> AVERAGE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("humidity/preference/average"));
  public static final ResourceKey<IAllele<?>> DAMP = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("humidity/preference/damp"));
  public static final ResourceKey<IAllele<?>> AQUATIC = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("humidity/preference/aquatic"));


  public static final MapCodec<HumidityPreference> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.INT.fieldOf("humidity").forGetter(HumidityPreference::getHumidity), TagKey.codec(Registries.BIOME).fieldOf("groupTag").forGetter(HumidityPreference::getGroupTag), Codec.BOOL.fieldOf("isDominantTrait").forGetter(HumidityPreference::isDominantTrait), Codec.STRING.fieldOf("name").forGetter(HumidityPreference::getName)).apply(instance, HumidityPreference::new));

  public static final StreamCodec<RegistryFriendlyByteBuf, HumidityPreference> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.INT, HumidityPreference::getHumidity, ByteBufCodecs.fromCodec(TagKey.codec(Registries.BIOME)), HumidityPreference::getGroupTag, ByteBufCodecs.BOOL, HumidityPreference::isDominantTrait, ByteBufCodecs.STRING_UTF8, HumidityPreference::getName, HumidityPreference::new);

  private final int humidity;
  private final TagKey<Biome> groupTag;
  private Component readableName;

  public HumidityPreference(int humidity, TagKey<Biome> groupTag, boolean isDominantTrait, String name)
  {
    super(isDominantTrait, name);
    this.humidity = humidity;
    this.groupTag = groupTag;
  }

  private int getHumidity()
  {
    return humidity;
  }

  private TagKey<Biome> getGroupTag()
  {
    return groupTag;
  }

  @Override
  public MapCodec<HumidityPreference> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, HumidityPreference> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  private TagKey<Biome> getTagByOrdinal(int ordinal)
  {
    return switch (ordinal)
    {
      case 1:
        yield ApicuriousTags.BiomeTags.HELLISH_HUMIDITY;
      case 2:
        yield ApicuriousTags.BiomeTags.ARID_HUMIDITY;
      case 3:
        yield ApicuriousTags.BiomeTags.AVERAGE_HUMIDITY;
      case 4:
        yield ApicuriousTags.BiomeTags.DAMP_HUMIDITY;
      case 5:
        yield ApicuriousTags.BiomeTags.AQUATIC_HUMIDITY;
      default:
        yield null;
    };
  }

  public List<TagKey<Biome>> getHumidityWithTolerance(HumidityTolerance tolerance)
  {
    List<TagKey<Biome>> humidityTags = new ArrayList<>();
    int minValue = Math.max(humidity - tolerance.getToleranceModifier(), 0);
    int maxValue = Math.min(humidity + tolerance.getToleranceModifier(), 4);
    for (int i = minValue; i <= maxValue; i++)
    {
      humidityTags.add(getTagByOrdinal(i));
    }
    return humidityTags;
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
    HumidityPreference otherPreference = (HumidityPreference) o;
    return humidity == otherPreference.humidity && Objects.equals(groupTag, otherPreference.groupTag);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(super.hashCode(), humidity, groupTag);
  }

  @Override
  public AlleleType<HumidityPreference> getTraitKey()
  {
    return AlleleTypeRegistrar.HUMIDITY_PREFERENCE_TYPE.get();
  }
}
