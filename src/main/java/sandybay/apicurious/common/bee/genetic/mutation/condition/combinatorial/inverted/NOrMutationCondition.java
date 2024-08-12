package sandybay.apicurious.common.bee.genetic.mutation.condition.combinatorial.inverted;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import sandybay.apicurious.api.bee.genetic.mutation.condition.IMutationCondition;
import sandybay.apicurious.api.bee.genetic.mutation.condition.MutationConditionType;
import sandybay.apicurious.api.register.MutationConditionTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

public record NOrMutationCondition(Holder<IMutationCondition> first, Holder<IMutationCondition> second) implements IMutationCondition
{
  public static final MapCodec<NOrMutationCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.MUTATION_CONDITIONS, IMutationCondition.TYPED_CODEC)
                          .fieldOf("first")
                          .forGetter(NOrMutationCondition::first),
                  RegistryFileCodec.create(ApicuriousRegistries.MUTATION_CONDITIONS, IMutationCondition.TYPED_CODEC)
                          .fieldOf("second")
                          .forGetter(NOrMutationCondition::first)
          ).apply(instance, NOrMutationCondition::new)
  );

  @Override
  public MutationConditionType getConditionType()
  {
    return MutationConditionTypeRegistrar.NOR.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    IMutationCondition first = first().value();
    IMutationCondition second = second().value();
    return !first.test(housing) && !second.test(housing);
  }
}
