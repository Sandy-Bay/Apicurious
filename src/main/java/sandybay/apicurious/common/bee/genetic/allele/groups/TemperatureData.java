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

import java.util.Objects;

public record TemperatureData(Holder<IAllele<?>> preferenceHolder, Holder<IAllele<?>> toleranceHolder)
{
  public static final Codec<TemperatureData> CODEC = RecordCodecBuilder.create(instance -> instance.group(RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("preferenceHolder").forGetter(TemperatureData::preferenceHolder), RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("toleranceHolder").forGetter(TemperatureData::toleranceHolder)).apply(instance, TemperatureData::new));

  public static final StreamCodec<RegistryFriendlyByteBuf, TemperatureData> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), TemperatureData::preferenceHolder, ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), TemperatureData::toleranceHolder, TemperatureData::new);

  @Override
  public boolean equals(Object o)
  {
    if (this == o) {return true;}
    if (o == null || getClass() != o.getClass()) {return false;}
    TemperatureData that = (TemperatureData) o;
    return Objects.equals(preferenceHolder.value(), that.preferenceHolder.value()) && Objects.equals(toleranceHolder.value(), that.toleranceHolder.value());
  }

}
