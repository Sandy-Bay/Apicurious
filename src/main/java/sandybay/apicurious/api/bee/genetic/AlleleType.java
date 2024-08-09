package sandybay.apicurious.api.bee.genetic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import sandybay.apicurious.api.register.AlleleTypeRegistration;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

import java.lang.reflect.Type;

public record AlleleType<T extends IAllele<T>>(MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, ? extends IAllele<?>> streamCodec)
{
  public static Codec<AlleleType<?>> CODEC = ApicuriousRegistries.TRAIT_TYPES_REGISTRY.byNameCodec();
  public static StreamCodec<RegistryFriendlyByteBuf, AlleleType<?>> NETWORK_CODEC = ByteBufCodecs.registry(ApicuriousRegistries.TRAIT_TYPES);

}
