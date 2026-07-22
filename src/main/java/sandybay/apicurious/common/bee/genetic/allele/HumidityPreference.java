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
import sandybay.apicurious.api.bee.genetic.allele.AbstractClimatePreference;
import sandybay.apicurious.api.bee.genetic.allele.AlleleType;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.AlleleTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class HumidityPreference extends AbstractClimatePreference<HumidityPreference>
{

  public static final ResourceKey<IAllele<?>> HELLISH = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("humidity/preference/hellish"));
  public static final ResourceKey<IAllele<?>> ARID = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("humidity/preference/arid"));
  public static final ResourceKey<IAllele<?>> AVERAGE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("humidity/preference/average"));
  public static final ResourceKey<IAllele<?>> DAMP = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("humidity/preference/damp"));
  public static final ResourceKey<IAllele<?>> AQUATIC = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("humidity/preference/aquatic"));

  public static final MapCodec<HumidityPreference> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.INT.fieldOf("humidity").forGetter(HumidityPreference::getValue), TagKey.codec(Registries.BIOME).fieldOf("groupTag").forGetter(HumidityPreference::getGroupTag), Codec.BOOL.fieldOf("isDominantTrait").forGetter(HumidityPreference::isDominantTrait), Codec.STRING.fieldOf("name").forGetter(HumidityPreference::getName)).apply(instance, HumidityPreference::new));

  public static final StreamCodec<RegistryFriendlyByteBuf, HumidityPreference> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.INT, HumidityPreference::getValue, ByteBufCodecs.fromCodec(TagKey.codec(Registries.BIOME)), HumidityPreference::getGroupTag, ByteBufCodecs.BOOL, HumidityPreference::isDominantTrait, ByteBufCodecs.STRING_UTF8, HumidityPreference::getName, HumidityPreference::new);

  public HumidityPreference(int humidity, TagKey<Biome> groupTag, boolean isDominantTrait, String name)
  {
    super(humidity, groupTag, isDominantTrait, name);
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

  @Override
  public AlleleType<HumidityPreference> getTraitKey()
  {
    return AlleleTypeRegistrar.HUMIDITY_PREFERENCE_TYPE.get();
  }
}