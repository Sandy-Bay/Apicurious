package sandybay.apicurious.common.bee.genetic.mutation.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.api.bee.genetic.mutation.condition.IMutationCondition;
import sandybay.apicurious.api.bee.genetic.mutation.condition.MutationConditionType;
import sandybay.apicurious.api.register.MutationConditionTypeRegistrar;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

import java.util.Optional;

public record WeatherMutationCondition(Biome.Precipitation precipitation, boolean isRaining, Optional<Boolean> isThundering) implements IMutationCondition
{
  public static final MapCodec<WeatherMutationCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  Biome.Precipitation.CODEC.fieldOf("precipitation").forGetter(WeatherMutationCondition::precipitation),
                  Codec.BOOL.fieldOf("isRaining").forGetter(WeatherMutationCondition::isRaining),
                  Codec.BOOL.optionalFieldOf("isThundering").forGetter(WeatherMutationCondition::isThundering)
          ).apply(instance, WeatherMutationCondition::new)
  );

  @Override
  public MutationConditionType getConditionType()
  {
    return MutationConditionTypeRegistrar.IS_CORRECT_WEATHER.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    Level level = housing.getLevel();
    if (level == null) return false;
    Biome biome = level.getBiome(housing.getBlockPos()).value();
    if (precipitation() == Biome.Precipitation.NONE) return !biome.hasPrecipitation();
    return biome.getPrecipitationAt(housing.getBlockPos()) == precipitation() &&
            level.isRainingAt(housing.getBlockPos()) == isRaining() &&
            (isThundering().isEmpty() || level.isThundering() == isThundering().get());
  }
}
