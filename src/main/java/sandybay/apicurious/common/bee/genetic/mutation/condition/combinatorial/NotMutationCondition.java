package sandybay.apicurious.common.bee.genetic.mutation.condition.combinatorial;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import sandybay.apicurious.api.bee.genetic.mutation.condition.IMutationCondition;
import sandybay.apicurious.api.bee.genetic.mutation.condition.MutationConditionType;
import sandybay.apicurious.api.register.MutationConditionTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

public record NotMutationCondition(Holder<IMutationCondition> condition) implements IMutationCondition
{
  public static final MapCodec<NotMutationCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.MUTATION_CONDITIONS, IMutationCondition.TYPED_CODEC)
                          .fieldOf("first")
                          .forGetter(NotMutationCondition::condition)
          ).apply(instance, NotMutationCondition::new)
  );

  @Override
  public MutationConditionType getConditionType()
  {
    return MutationConditionTypeRegistrar.NOT.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    return !condition.value().test(housing);
  }
}
