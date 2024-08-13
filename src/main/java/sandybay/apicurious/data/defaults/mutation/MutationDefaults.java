package sandybay.apicurious.data.defaults.mutation;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.neoforged.neoforge.common.Tags;
import org.apache.commons.compress.utils.Lists;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.condition.ICondition;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.common.bee.ApicuriousMutations;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.*;
import sandybay.apicurious.common.bee.condition.combinatorial.AndCondition;
import sandybay.apicurious.common.bee.genetic.mutation.ConditionalMutation;
import sandybay.apicurious.common.bee.genetic.mutation.Mutation;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// TODO: Implement some sort of condition builder system to make it easier to specify complex conditional behaviour
public class MutationDefaults
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
  {

    bootstrap.register(ApicuriousMutations.FIRST_EXAMPLE, mutation(bootstrap)
            .withFirst(ApicuriousSpecies.FOREST)
            .withSecond(ApicuriousSpecies.MEADOW)
            .withChance(1.0f)
            .withOutput(ApicuriousSpecies.DEBUG)
            .build()
    );
    bootstrap.register(ApicuriousMutations.SECOND_EXAMPLE, mutation(bootstrap)
            .withFirst(ApicuriousSpecies.FOREST)
            .withSecond(ApicuriousTags.AlleleTags.BASELINE_BEE)
            .withChance(1.0f)
            .withOutput(ApicuriousSpecies.DEBUG)
            .build()
    );
    bootstrap.register(ApicuriousMutations.THIRD_EXAMPLE, mutation(bootstrap)
            .withFirst(ApicuriousTags.AlleleTags.BASELINE_BEE)
            .withSecond(ApicuriousTags.AlleleTags.BASELINE_BEE)
            .withChance(1.0f)
            .withOutput(ApicuriousSpecies.DEBUG)
            .build()
    );

    bootstrap.register(ApicuriousMutations.SINGLE_CONDITIONAL_EXAMPLE, mutation(bootstrap)
            .withFirst(ApicuriousSpecies.FOREST)
            .withSecond(ApicuriousSpecies.MEADOW)
            .withChance(1.0f)
            .withOutput(ApicuriousSpecies.DEBUG)
            .withCondition(new BiomeCondition(HolderSet.direct(bootstrap.lookup(Registries.BIOME).get(Biomes.BEACH).get())))
            .build()
    );
    bootstrap.register(ApicuriousMutations.MANY_CONDITIONAL_EXAMPLE, mutation(bootstrap)
            .withFirst(ApicuriousSpecies.FOREST)
            .withSecond(ApicuriousSpecies.MEADOW)
            .withChance(1.0f)
            .withOutput(ApicuriousSpecies.DEBUG)
            .withConditions(
                    new BiomeCondition(bootstrap.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_BEACH)),
                    new BlockInAreaCondition(bootstrap.lookup(Registries.BLOCK).getOrThrow(Tags.Blocks.BOOKSHELVES)),
                    new DateCondition(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31)),
                    new DimensionCondition(HolderSet.direct(bootstrap.lookup(Registries.DIMENSION_TYPE).getOrThrow(BuiltinDimensionTypes.OVERWORLD))),
                    new HeightCondition(64, false),
                    new HumidityCondition(bootstrap.lookup(Registries.BIOME).getOrThrow(ApicuriousTags.BiomeTags.AQUATIC_HUMIDITY)),
                    new MoonPhaseCondition(4),
                    new TemperatureCondition(bootstrap.lookup(Registries.BIOME).getOrThrow(ApicuriousTags.BiomeTags.HELLISH_TEMPERATURE)),
                    new WeatherCondition(Biome.Precipitation.RAIN, true, Optional.of(true)),
                    new AndCondition(
                            Holder.direct(new TemperatureCondition(bootstrap.lookup(Registries.BIOME).getOrThrow(ApicuriousTags.BiomeTags.ICY_TEMPERATURE))),
                            Holder.direct(new TemperatureCondition(bootstrap.lookup(Registries.BIOME).getOrThrow(ApicuriousTags.BiomeTags.HELLISH_TEMPERATURE)))
                    )
            )
            .build()
    );
  }

  public static Builder mutation(BootstrapContext<IMutation> bootstrap)
  {
    return new Builder(bootstrap);
  }

  public static class Builder
  {
    private final BootstrapContext<IMutation> context;
    private final HolderGetter<IAllele<?>> alleleGetter;
    private final HolderGetter<ICondition> conditionGetter;
    private final List<Holder<ICondition>> conditions;
    private HolderSet<IAllele<?>> first;
    private HolderSet<IAllele<?>> second;
    private float chance;
    private Holder<IAllele<?>> output;

    private Builder(BootstrapContext<IMutation> context)
    {
      this.context = context;
      this.alleleGetter = context.lookup(ApicuriousRegistries.ALLELES);
      this.conditionGetter = context.lookup(ApicuriousRegistries.CONDITIONS);
      this.conditions = Lists.newArrayList();
    }

    public Builder withFirst(ResourceKey<IAllele<?>> first)
    {
      Optional<Holder.Reference<IAllele<?>>> firstSpecies = alleleGetter.get(first);
      firstSpecies.ifPresent(ref -> this.first = HolderSet.direct(ref));
      return this;
    }

    public Builder withFirst(TagKey<IAllele<?>> first)
    {
      this.first = alleleGetter.getOrThrow(first);
      return this;
    }

    public Builder withSecond(ResourceKey<IAllele<?>> second)
    {
      Optional<Holder.Reference<IAllele<?>>> secondSpecies = alleleGetter.get(second);
      secondSpecies.ifPresent(ref -> this.second = HolderSet.direct(ref));
      return this;
    }

    public Builder withSecond(TagKey<IAllele<?>> second)
    {
      this.second = alleleGetter.getOrThrow(second);
      return this;
    }

    public Builder withChance(float chance)
    {
      this.chance = chance;
      return this;
    }

    public Builder withOutput(ResourceKey<IAllele<?>> output)
    {
      Optional<Holder.Reference<IAllele<?>>> outputSpecies = alleleGetter.get(output);
      outputSpecies.ifPresent(ref -> this.output = ref);
      return this;
    }

    public Builder withCondition(ICondition mutationCondition)
    {
      this.conditions.add(Holder.direct(mutationCondition));
      return this;
    }

    public Builder withCondition(ResourceKey<ICondition> mutationCondition)
    {
      Optional<Holder.Reference<ICondition>> condition = conditionGetter.get(mutationCondition);
      condition.ifPresent(conditions::add);
      return this;
    }

    public Builder withConditions(ICondition... mutationConditions)
    {
      Arrays.stream(mutationConditions).forEach(c -> conditions.add(Holder.direct(c)));
      return this;
    }

    public Builder withConditions(ResourceKey<ICondition>... mutationConditions)
    {
      Arrays.stream(mutationConditions).forEach(k ->
      {
        conditionGetter.get(k).ifPresent(conditions::add);
      });
      return this;
    }

    public IMutation build()
    {
      return conditions.isEmpty() ? new Mutation(first, second, chance, output) : new ConditionalMutation(first, second, chance, output, conditions);
    }
  }
}
