package sandybay.apicurious.common.bee.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import sandybay.apicurious.api.condition.ConditionType;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

import java.text.DecimalFormat;


public record ChanceCondition(float chance) implements ICondition
{
  public static final MapCodec<ChanceCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter(ChanceCondition::chance)).apply(instance, ChanceCondition::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, ChanceCondition> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.FLOAT, ChanceCondition::chance, ChanceCondition::new);

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.CHANCE.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    Level level = housing.getLevel();
    if (level == null)
    {
      return false;
    }
    return level.getRandom().nextFloat() < chance();
  }

  @Override
  public Component getDisplayText()
  {
    return Component.translatable("apicurious.condition.chance").append(DecimalFormat.getPercentInstance().format(chance));
  }
}