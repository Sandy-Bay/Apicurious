package sandybay.apicurious.common.bee.genetic.mutation.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.Level;
import sandybay.apicurious.api.bee.genetic.mutation.condition.IMutationCondition;
import sandybay.apicurious.api.bee.genetic.mutation.condition.MutationConditionType;
import sandybay.apicurious.api.register.MutationConditionTypeRegistrar;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

public record DimensionMutationCondition(HolderSet<Level> dimensions) implements IMutationCondition
{
  public static final MapCodec<DimensionMutationCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryCodecs.homogeneousList(Registries.DIMENSION).fieldOf("dimensions").forGetter(DimensionMutationCondition::dimensions)
          ).apply(instance, DimensionMutationCondition::new)
  );

  @Override
  public MutationConditionType getConditionType()
  {
    return MutationConditionTypeRegistrar.IS_CORRECT_DIMENSION.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    Level level = housing.getLevel();
    if (level == null) return false;
    return dimensions().stream().anyMatch(h -> h.is(level.dimension()));
  }
}
