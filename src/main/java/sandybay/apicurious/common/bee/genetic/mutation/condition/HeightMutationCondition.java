package sandybay.apicurious.common.bee.genetic.mutation.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import sandybay.apicurious.api.bee.genetic.mutation.condition.IMutationCondition;
import sandybay.apicurious.api.bee.genetic.mutation.condition.MutationConditionType;
import sandybay.apicurious.api.register.MutationConditionTypeRegistrar;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

public record HeightMutationCondition(int yHeight, boolean above) implements IMutationCondition
{
  public static final MapCodec<HeightMutationCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  Codec.intRange(-64, 320).fieldOf("yHeight").forGetter(HeightMutationCondition::yHeight),
                  Codec.BOOL.optionalFieldOf("above", true).forGetter(HeightMutationCondition::above)
          ).apply(instance, HeightMutationCondition::new)
  );

  @Override
  public MutationConditionType getConditionType()
  {
    return MutationConditionTypeRegistrar.IS_CORRECT_HEIGHT.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    return above() ? housing.getBlockPos().getY() >= yHeight() : housing.getBlockPos().getY() <= yHeight();
  }
}
