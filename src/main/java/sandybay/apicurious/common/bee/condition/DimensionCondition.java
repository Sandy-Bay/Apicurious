package sandybay.apicurious.common.bee.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import sandybay.apicurious.api.bee.condition.ICondition;
import sandybay.apicurious.api.bee.condition.ConditionType;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

public record DimensionCondition(HolderSet<Level> dimensions) implements ICondition
{
  public static final MapCodec<DimensionCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryCodecs.homogeneousList(Registries.DIMENSION).fieldOf("dimensions").forGetter(DimensionCondition::dimensions)
          ).apply(instance, DimensionCondition::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, DimensionCondition> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.fromCodecWithRegistries(RegistryCodecs.homogeneousList(Registries.DIMENSION)), DimensionCondition::dimensions,
          DimensionCondition::new
  );

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.IS_CORRECT_DIMENSION.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    Level level = housing.getLevel();
    if (level == null) return false;
    return dimensions().stream().anyMatch(h -> h.is(level.dimension()));
  }
}
