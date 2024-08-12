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

public record TemperatureMutationCondition(HolderSet<Biome> temperature) implements IMutationCondition
{
  public static final MapCodec<TemperatureMutationCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("temperature").forGetter(TemperatureMutationCondition::temperature)
          ).apply(instance, TemperatureMutationCondition::new)
  );

  @Override
  public MutationConditionType getConditionType()
  {
    return MutationConditionTypeRegistrar.IS_CORRECT_TEMPERATURE.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    TagKey<Biome> temperatureAtPosition = housing.validation.helper.getTemperatureAtPosition(housing.getBlockPos());
    return temperature.stream().anyMatch(h -> h.is(temperatureAtPosition));
  }
}
