package sandybay.apicurious.api.bee.genetic.mutation.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public record MutationConditionType(MapCodec<? extends IMutationCondition> codec)
{
  public static Codec<MutationConditionType> CODEC = ApicuriousRegistries.MUTATION_CONDITION_TYPE_REGISTRY.byNameCodec();
}
