package sandybay.apicurious.common.bee.genetic.mutation.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.Level;
import sandybay.apicurious.api.bee.genetic.mutation.condition.IMutationCondition;
import sandybay.apicurious.api.bee.genetic.mutation.condition.MutationConditionType;
import sandybay.apicurious.api.register.MutationConditionTypeRegistrar;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

public record MoonPhaseCondition(int moonPhase) implements IMutationCondition
{
  public static final MapCodec<MoonPhaseCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  Codec.INT.fieldOf("moonPhase").forGetter(MoonPhaseCondition::moonPhase)
          ).apply(instance, MoonPhaseCondition::new)
  );

  @Override
  public MutationConditionType getConditionType()
  {
    return MutationConditionTypeRegistrar.IS_CORRECT_MOON_PHASE.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    Level level = housing.getLevel();
    if (level == null) return false;
    return level.getMoonPhase() == moonPhase();
  }
}
