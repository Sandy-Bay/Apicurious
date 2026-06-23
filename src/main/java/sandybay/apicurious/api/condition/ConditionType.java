package sandybay.apicurious.api.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public record ConditionType(MapCodec<? extends ICondition> codec,
                            StreamCodec<RegistryFriendlyByteBuf, ? extends ICondition> streamCodec)
{
  public static Codec<ConditionType> CODEC = ApicuriousRegistries.CONDITION_TYPE_REGISTRY.byNameCodec();
}
