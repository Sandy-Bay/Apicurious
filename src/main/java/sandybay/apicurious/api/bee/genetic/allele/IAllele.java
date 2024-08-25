package sandybay.apicurious.api.bee.genetic.allele;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public interface IAllele<T extends IAllele<T>>
{
  Codec<IAllele<?>> TYPED_CODEC = ApicuriousRegistries.ALLELE_TYPE_REGISTRY.byNameCodec().dispatch("type", IAllele::getTraitKey, AlleleType::codec);

  StreamCodec<RegistryFriendlyByteBuf, IAllele<?>> NETWORK_TYPED_CODEC = ByteBufCodecs.registry(ApicuriousRegistries.ALLELE_TYPES).dispatch(IAllele::getTraitKey, AlleleType::streamCodec);

  AlleleType<T> getTraitKey();

  Component getReadableName();

  MapCodec<T> getCodec();

  StreamCodec<RegistryFriendlyByteBuf, T> getStreamCodec();

  boolean isDominantTrait();
}
