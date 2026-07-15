package sandybay.apicurious.common.bee.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.Level;
import sandybay.apicurious.api.condition.ConditionType;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

public record MoonPhaseCondition(int moonPhase) implements ICondition
{
  public static final MapCodec<MoonPhaseCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.intRange(0, 7).fieldOf("moonPhase").forGetter(MoonPhaseCondition::moonPhase)).apply(instance, MoonPhaseCondition::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, MoonPhaseCondition> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.INT, MoonPhaseCondition::moonPhase, MoonPhaseCondition::new);

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.IS_CORRECT_MOON_PHASE.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    Level level = housing.getLevel();
    if (level == null)
    {
      return false;
    }
    return level.environmentAttributes().getDimensionValue(EnvironmentAttributes.MOON_PHASE).index() == moonPhase();
  }

  @Override
  public Component getDisplayText()
  {
    String moonPhaseName = switch (moonPhase)
    {
      case 0:
        yield "Full Moon";
      case 1:
        yield "Waning Gibbous";
      case 2:
        yield "Last Quarter";
      case 3:
        yield "Waning Crescent";
      case 4:
        yield "New Moon";
      case 5:
        yield "Waxing Crescent";
      case 6:
        yield "First Quarter";
      case 7:
        yield "Waxing Gibbous";
      default:
        yield "Unknown";
    };
    return Component.literal("Moon Phase: " + moonPhaseName);
  }
}