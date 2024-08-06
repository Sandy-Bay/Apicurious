package sandybay.apicurious.api.bee.genetic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record AlleleType<T extends IAllele<T>>(MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, ? extends IAllele<?>> streamCodec) { }
