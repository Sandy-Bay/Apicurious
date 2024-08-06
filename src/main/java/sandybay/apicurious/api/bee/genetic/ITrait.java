package sandybay.apicurious.api.bee.genetic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public interface ITrait<T extends ITrait<T>>
{
  //Codec<T> TYPED_CODEC = ApicuriousRegistries.TRAIT_TYPES_REGISTRY
  //        .byNameCodec()
  //        .dispatch("codec", ITrait::getTraitKey, TraitType::codec);
  //Codec<ITrait<?>> DIRECT_CODEC = Codec.lazyInitialized(() -> TYPED_CODEC);
  //Codec<Holder<ITrait<?>> CODEC = RegistryFileCodec.create(ApicuriousRegistries.TRAIT_TYPES, DIRECT_CODEC);

  TraitType<T> getTraitKey();

  Component getReadableName();

  MapCodec<T> getCodec();

  StreamCodec<RegistryFriendlyByteBuf, T> getStreamCodec();

  boolean isDominantTrait();
}
