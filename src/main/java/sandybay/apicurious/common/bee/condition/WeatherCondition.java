package sandybay.apicurious.common.bee.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.api.condition.ConditionType;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

import java.util.Optional;

public record WeatherCondition(Biome.Precipitation precipitation, boolean isRaining,
                               Optional<Boolean> isThundering) implements ICondition
{
  public static final MapCodec<WeatherCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  Biome.Precipitation.CODEC.fieldOf("precipitation").forGetter(WeatherCondition::precipitation),
                  Codec.BOOL.fieldOf("isRaining").forGetter(WeatherCondition::isRaining),
                  Codec.BOOL.optionalFieldOf("isThundering").forGetter(WeatherCondition::isThundering)
          ).apply(instance, WeatherCondition::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, WeatherCondition> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.fromCodec(Biome.Precipitation.CODEC), WeatherCondition::precipitation,
          ByteBufCodecs.BOOL, WeatherCondition::isRaining,
          ByteBufCodecs.optional(ByteBufCodecs.BOOL), WeatherCondition::isThundering,
          WeatherCondition::new
  );

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.IS_CORRECT_WEATHER.get();
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
