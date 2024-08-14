package sandybay.apicurious.api.function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public record FunctionType(MapCodec<? extends IFunction> codec,
                           StreamCodec<RegistryFriendlyByteBuf, ? extends IFunction> streamCodec)
{
  public static Codec<FunctionType> CODEC = ApicuriousRegistries.FUNCTION_TYPE_REGISTRY.byNameCodec();
}
