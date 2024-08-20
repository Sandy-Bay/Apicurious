package sandybay.apicurious.common.bee.condition.combinatorial;

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

public record OrCondition(Holder<ICondition> first, Holder<ICondition> second) implements ICondition
{
  public static final MapCodec<OrCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.CONDITIONS, ICondition.TYPED_CODEC)
                          .fieldOf("first")
                          .forGetter(OrCondition::first),
                  RegistryFileCodec.create(ApicuriousRegistries.CONDITIONS, ICondition.TYPED_CODEC)
                          .fieldOf("second")
                          .forGetter(OrCondition::first)
          ).apply(instance, OrCondition::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, OrCondition> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holder(ApicuriousRegistries.CONDITIONS, ICondition.NETWORK_TYPED_CODEC), OrCondition::first,
          ByteBufCodecs.holder(ApicuriousRegistries.CONDITIONS, ICondition.NETWORK_TYPED_CODEC), OrCondition::second,
          OrCondition::new
  );

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.OR.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    return first().value().test(housing) || second().value().test(housing);
  }
}
