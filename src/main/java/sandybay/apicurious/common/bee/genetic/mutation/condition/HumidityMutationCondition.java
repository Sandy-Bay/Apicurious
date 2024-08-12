package sandybay.apicurious.common.bee.genetic.mutation.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.api.bee.genetic.mutation.condition.IMutationCondition;
import sandybay.apicurious.api.bee.genetic.mutation.condition.MutationConditionType;
import sandybay.apicurious.api.register.MutationConditionTypeRegistrar;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

public record HumidityMutationCondition(HolderSet<Biome> humidity) implements IMutationCondition
{
  public static final MapCodec<HumidityMutationCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("humidity").forGetter(HumidityMutationCondition::humidity)
          ).apply(instance, HumidityMutationCondition::new)
  );

  @Override
  public MutationConditionType getConditionType()
  {
    return MutationConditionTypeRegistrar.IS_CORRECT_HUMIDITY.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    TagKey<Biome> humidityAtPosition = housing.validation.helper.getHumidityAtPosition(housing.getBlockPos());
    return humidity.stream().anyMatch(h -> h.is(humidityAtPosition));
  }
}
