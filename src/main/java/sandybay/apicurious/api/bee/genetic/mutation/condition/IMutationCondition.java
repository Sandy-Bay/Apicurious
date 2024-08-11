package sandybay.apicurious.api.bee.genetic.mutation.condition;

import com.mojang.serialization.Codec;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

public interface IMutationCondition
{
  Codec<IMutationCondition> TYPED_CODEC = ApicuriousRegistries.MUTATION_CONDITION_TYPE_REGISTRY
          .byNameCodec()
          .dispatch("condition", IMutationCondition::getConditionType, MutationConditionType::codec);

  MutationConditionType getConditionType();

  boolean test(SimpleBlockHousingBE housing);

}
