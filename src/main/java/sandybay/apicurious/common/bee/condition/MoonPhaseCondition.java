package sandybay.apicurious.common.bee.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import sandybay.apicurious.api.bee.condition.ICondition;
import sandybay.apicurious.api.bee.condition.ConditionType;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

public record MoonPhaseCondition(int moonPhase) implements ICondition
{
  public static final MapCodec<MoonPhaseCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  Codec.INT.fieldOf("moonPhase").forGetter(MoonPhaseCondition::moonPhase)
          ).apply(instance, MoonPhaseCondition::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, MoonPhaseCondition> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, MoonPhaseCondition::moonPhase,
          MoonPhaseCondition::new
  );

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.IS_CORRECT_MOON_PHASE.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    Level level = housing.getLevel();
    if (level == null) return false;
    return level.getMoonPhase() == moonPhase();
  }
}
