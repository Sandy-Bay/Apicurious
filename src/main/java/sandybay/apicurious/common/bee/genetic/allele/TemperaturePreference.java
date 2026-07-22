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
import sandybay.apicurious.api.bee.genetic.allele.AbstractClimatePreference;
import sandybay.apicurious.api.bee.genetic.allele.AlleleType;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.AlleleTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousTags;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TemperaturePreference extends AbstractClimatePreference<TemperaturePreference>
{

  public static final ResourceKey<IAllele<?>> HELLISH = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("temperature/preference/hellish"));
  public static final ResourceKey<IAllele<?>> HOT = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("temperature/preference/hot"));
  public static final ResourceKey<IAllele<?>> AVERAGE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("temperature/preference/average"));
  public static final ResourceKey<IAllele<?>> COLD = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("temperature/preference/cold"));
  public static final ResourceKey<IAllele<?>> ICY = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("temperature/preference/icy"));

  public static final MapCodec<TemperaturePreference> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.INT.fieldOf("temperature").forGetter(TemperaturePreference::getValue), TagKey.codec(Registries.BIOME).fieldOf("groupTag").forGetter(TemperaturePreference::getGroupTag), Codec.BOOL.fieldOf("isDominantTrait").forGetter(TemperaturePreference::isDominantTrait), Codec.STRING.fieldOf("name").forGetter(TemperaturePreference::getName)).apply(instance, TemperaturePreference::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, TemperaturePreference> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.INT, TemperaturePreference::getValue, ByteBufCodecs.fromCodec(TagKey.codec(Registries.BIOME)), TemperaturePreference::getGroupTag, ByteBufCodecs.BOOL, TemperaturePreference::isDominantTrait, ByteBufCodecs.STRING_UTF8, TemperaturePreference::getName, TemperaturePreference::new);

  public TemperaturePreference(int temperature, TagKey<Biome> groupTag, boolean isDominantTrait, String name)
  {
    super(temperature, groupTag, isDominantTrait, name);
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

  @Override
  public AlleleType<TemperaturePreference> getTraitKey()
  {
    return AlleleTypeRegistrar.TEMPERATURE_PREFERENCE_TYPE.get();
  }
}
