package sandybay.apicurious.api.function;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record FunctionType(MapCodec<? extends IFunction> codec,
                           StreamCodec<RegistryFriendlyByteBuf, ? extends IFunction> streamCodec)
{
}
