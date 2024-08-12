package sandybay.apicurious.common.bee.genetic.mutation.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.api.bee.genetic.mutation.condition.IMutationCondition;
import sandybay.apicurious.api.bee.genetic.mutation.condition.MutationConditionType;
import sandybay.apicurious.api.register.MutationConditionTypeRegistrar;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

public record BiomeMutationCondition(HolderSet<Biome> biomes) implements IMutationCondition
{

  public static final MapCodec<BiomeMutationCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(BiomeMutationCondition::biomes)
          ).apply(instance, BiomeMutationCondition::new)
  );

  @Override
  public MutationConditionType getConditionType()
  {
    return MutationConditionTypeRegistrar.IS_CORRECT_BIOME.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    if (housing.getLevel() == null) return false;
    Holder<Biome> biome = housing.getLevel().getBiome(housing.getBlockPos());
    return biomes().contains(biome);
  }
}
