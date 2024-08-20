package sandybay.apicurious.common.bee.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.condition.ConditionType;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

public record HeightCondition(int yHeight, boolean above) implements ICondition
{
  public static final MapCodec<HeightCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  Codec.intRange(-64, 320).fieldOf("yHeight").forGetter(HeightCondition::yHeight),
                  Codec.BOOL.optionalFieldOf("above", true).forGetter(HeightCondition::above)
          ).apply(instance, HeightCondition::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, HeightCondition> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, HeightCondition::yHeight,
          ByteBufCodecs.BOOL, HeightCondition::above,
          HeightCondition::new
  );

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.IS_CORRECT_HEIGHT.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    return above() ? housing.getBlockPos().getY() >= yHeight() : housing.getBlockPos().getY() <= yHeight();
  }
}
