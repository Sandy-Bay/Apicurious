package sandybay.apicurious.common.bee.genetic.mutation.condition.combinatorial;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import sandybay.apicurious.api.bee.genetic.mutation.condition.IMutationCondition;
import sandybay.apicurious.api.bee.genetic.mutation.condition.MutationConditionType;
import sandybay.apicurious.api.register.MutationConditionTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.genetic.mutation.condition.combinatorial.inverted.NAndMutationCondition;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

public record AndMutationCondition(Holder<IMutationCondition> first, Holder<IMutationCondition> second) implements IMutationCondition
{
  public static final MapCodec<AndMutationCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.MUTATION_CONDITIONS, IMutationCondition.TYPED_CODEC)
                          .fieldOf("first")
                          .forGetter(AndMutationCondition::first),
                  RegistryFileCodec.create(ApicuriousRegistries.MUTATION_CONDITIONS, IMutationCondition.TYPED_CODEC)
                          .fieldOf("second")
                          .forGetter(AndMutationCondition::first)
          ).apply(instance, AndMutationCondition::new)
  );

  @Override
  public MutationConditionType getConditionType()
  {
    return MutationConditionTypeRegistrar.AND.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    return first().value().test(housing) && second().value().test(housing);
  }
}
