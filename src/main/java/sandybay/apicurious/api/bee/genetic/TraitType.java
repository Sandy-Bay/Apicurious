package sandybay.apicurious.api.bee.genetic;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record TraitType<T extends ITrait<T>>(MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, ? extends ITrait<?>> streamCodec) { }
