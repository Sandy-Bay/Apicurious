package sandybay.apicurious.common.bee.genetic.allele.groups;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public record HumidityData(Holder<IAllele<?>> preferenceHolder, Holder<IAllele<?>> toleranceHolder)
{
  public static final Codec<HumidityData> CODEC = RecordCodecBuilder.create(instance -> instance.group(RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("preferenceHolder").forGetter(HumidityData::preferenceHolder), RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("toleranceHolder").forGetter(HumidityData::toleranceHolder)).apply(instance, HumidityData::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, HumidityData> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), HumidityData::preferenceHolder, ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), HumidityData::toleranceHolder, HumidityData::new);

}
