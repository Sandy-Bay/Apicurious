package sandybay.apicurious.api.bee.genetic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public record AlleleType<T extends IAllele<T>>(MapCodec<T> codec,
                                               StreamCodec<RegistryFriendlyByteBuf, ? extends IAllele<?>> streamCodec)
{
  public static Codec<AlleleType<?>> CODEC = ApicuriousRegistries.TRAIT_TYPES_REGISTRY.byNameCodec();
  public static StreamCodec<RegistryFriendlyByteBuf, AlleleType<?>> NETWORK_CODEC = ByteBufCodecs.registry(ApicuriousRegistries.TRAIT_TYPES);

}
