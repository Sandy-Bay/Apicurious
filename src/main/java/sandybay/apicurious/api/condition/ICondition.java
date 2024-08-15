package sandybay.apicurious.api.condition;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public interface ICondition
{
  Codec<ICondition> TYPED_CODEC = ApicuriousRegistries.CONDITION_TYPE_REGISTRY
          .byNameCodec()
          .dispatch("condition", ICondition::getConditionType, ConditionType::codec);

  StreamCodec<RegistryFriendlyByteBuf, ICondition> NETWORK_TYPED_CODEC = ByteBufCodecs
          .registry(ApicuriousRegistries.CONDITION_TYPES)
          .dispatch(ICondition::getConditionType, ConditionType::streamCodec);

  ConditionType getConditionType();

  boolean test(SimpleBlockHousingBE housing);

}
