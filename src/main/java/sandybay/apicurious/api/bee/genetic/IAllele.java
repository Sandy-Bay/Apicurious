package sandybay.apicurious.api.bee.genetic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public interface IAllele<T extends IAllele<T>>
{
  Codec<IAllele<?>> TYPED_CODEC = ApicuriousRegistries.TRAIT_TYPES_REGISTRY
          .byNameCodec()
          .dispatch("type", IAllele::getTraitKey, AlleleType::codec);

  StreamCodec<RegistryFriendlyByteBuf, IAllele<?>> NETWORK_TYPED_CODEC = ByteBufCodecs
          .registry(ApicuriousRegistries.TRAIT_TYPES)
          .dispatch(IAllele::getTraitKey, AlleleType::streamCodec);

  AlleleType<T> getTraitKey();

  Component getReadableName();

  MapCodec<T> getCodec();

  StreamCodec<RegistryFriendlyByteBuf, T> getStreamCodec();

  boolean isDominantTrait();

  default T cast()
  {
    return (T) this;
  }
}
