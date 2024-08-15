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
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public record NAndCondition(Holder<ICondition> first, Holder<ICondition> second) implements ICondition
{
  public static final MapCodec<NAndCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.CONDITIONS, ICondition.TYPED_CODEC)
                          .fieldOf("first")
                          .forGetter(NAndCondition::first),
                  RegistryFileCodec.create(ApicuriousRegistries.CONDITIONS, ICondition.TYPED_CODEC)
                          .fieldOf("second")
                          .forGetter(NAndCondition::first)
          ).apply(instance, NAndCondition::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, NAndCondition> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holder(ApicuriousRegistries.CONDITIONS, ICondition.NETWORK_TYPED_CODEC), NAndCondition::first,
          ByteBufCodecs.holder(ApicuriousRegistries.CONDITIONS, ICondition.NETWORK_TYPED_CODEC), NAndCondition::second,
          NAndCondition::new
  );

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.NAND.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    return !(first().value().test(housing) && !second().value().test(housing));
  }
}
