package sandybay.apicurious.api.bee.genetic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public interface IAllele<T extends IAllele<T>>
{
  Codec<?> TYPED_CODEC = ApicuriousRegistries.TRAIT_TYPES_REGISTRY
          .byNameCodec()
          .dispatch("type", IAllele::getTraitKey, AlleleType::codec);



  AlleleType<T> getTraitKey();

  Component getReadableName();

  MapCodec<T> getCodec();

  StreamCodec<RegistryFriendlyByteBuf, T> getStreamCodec();

  boolean isDominantTrait();
}
