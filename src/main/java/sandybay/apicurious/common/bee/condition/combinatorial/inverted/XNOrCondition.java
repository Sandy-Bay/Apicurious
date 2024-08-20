package sandybay.apicurious.common.bee.condition.combinatorial.inverted;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import sandybay.apicurious.api.condition.ConditionType;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

public record XNOrCondition(Holder<ICondition> first, Holder<ICondition> second) implements ICondition
{
  public static final MapCodec<XNOrCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.CONDITIONS, ICondition.TYPED_CODEC)
                          .fieldOf("first")
                          .forGetter(XNOrCondition::first),
                  RegistryFileCodec.create(ApicuriousRegistries.CONDITIONS, ICondition.TYPED_CODEC)
                          .fieldOf("second")
                          .forGetter(XNOrCondition::first)
          ).apply(instance, XNOrCondition::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, XNOrCondition> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holder(ApicuriousRegistries.CONDITIONS, ICondition.NETWORK_TYPED_CODEC), XNOrCondition::first,
          ByteBufCodecs.holder(ApicuriousRegistries.CONDITIONS, ICondition.NETWORK_TYPED_CODEC), XNOrCondition::second,
          XNOrCondition::new
  );

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.XNOR.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    ICondition first = first().value();
    ICondition second = second().value();
    return (first.test(housing) && second.test(housing)) || (!first.test(housing) && !second.test(housing));
  }
}
