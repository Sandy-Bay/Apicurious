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

public record NOrCondition(Holder<ICondition> first, Holder<ICondition> second) implements ICondition
{
  public static final MapCodec<NOrCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.CONDITIONS, ICondition.TYPED_CODEC)
                          .fieldOf("first")
                          .forGetter(NOrCondition::first),
                  RegistryFileCodec.create(ApicuriousRegistries.CONDITIONS, ICondition.TYPED_CODEC)
                          .fieldOf("second")
                          .forGetter(NOrCondition::first)
          ).apply(instance, NOrCondition::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, NOrCondition> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holder(ApicuriousRegistries.CONDITIONS, ICondition.NETWORK_TYPED_CODEC), NOrCondition::first,
          ByteBufCodecs.holder(ApicuriousRegistries.CONDITIONS, ICondition.NETWORK_TYPED_CODEC), NOrCondition::second,
          NOrCondition::new
  );


  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.NOR.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    ICondition first = first().value();
    ICondition second = second().value();
    return !first.test(housing) && !second.test(housing);
  }
}
