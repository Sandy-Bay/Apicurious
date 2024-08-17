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
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

public record NotCondition(Holder<ICondition> condition) implements ICondition
{
  public static final MapCodec<NotCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.CONDITIONS, ICondition.TYPED_CODEC)
                          .fieldOf("first")
                          .forGetter(NotCondition::condition)
          ).apply(instance, NotCondition::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, NotCondition> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holder(ApicuriousRegistries.CONDITIONS, ICondition.NETWORK_TYPED_CODEC), NotCondition::condition,
          NotCondition::new
  );

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.NOT.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    return !condition.value().test(housing);
  }
}
