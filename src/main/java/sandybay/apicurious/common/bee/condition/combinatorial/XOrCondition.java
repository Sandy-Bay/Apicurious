package sandybay.apicurious.common.bee.condition.combinatorial;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import sandybay.apicurious.api.condition.ConditionType;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

public record XOrCondition(Holder<ICondition> first, Holder<ICondition> second) implements ICondition
{
  public static final MapCodec<XOrCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(RegistryFileCodec.create(ApicuriousRegistries.CONDITIONS, ICondition.TYPED_CODEC).fieldOf("first").forGetter(XOrCondition::first), RegistryFileCodec.create(ApicuriousRegistries.CONDITIONS, ICondition.TYPED_CODEC).fieldOf("second").forGetter(XOrCondition::second)).apply(instance, XOrCondition::new));

  public static final StreamCodec<RegistryFriendlyByteBuf, XOrCondition> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.holder(ApicuriousRegistries.CONDITIONS, ICondition.NETWORK_TYPED_CODEC), XOrCondition::first, ByteBufCodecs.holder(ApicuriousRegistries.CONDITIONS, ICondition.NETWORK_TYPED_CODEC), XOrCondition::second, XOrCondition::new);

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.XOR.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    ICondition first = first().value();
    ICondition second = second().value();
    return (first.test(housing) && second.test(housing));
  }

  @Override
  public Component getDisplayText()
  {
    return first.value().getDisplayText().copy().append(" XOR ").append(second.value().getDisplayText().copy());
  }
}
