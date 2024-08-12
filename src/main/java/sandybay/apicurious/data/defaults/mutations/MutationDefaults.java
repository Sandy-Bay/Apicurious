package sandybay.apicurious.data.defaults.mutations;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biomes;
import org.apache.commons.compress.utils.Lists;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.genetic.mutation.condition.IMutationCondition;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.common.bee.ApicuriousMutations;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.mutation.ConditionalMutation;
import sandybay.apicurious.common.bee.genetic.mutation.Mutation;
import sandybay.apicurious.common.bee.genetic.mutation.condition.BiomeMutationCondition;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
            .withCondition(new BiomeMutationCondition(HolderSet.direct(bootstrap.lookup(Registries.BIOME).get(Biomes.BEACH).get())))
            .build()
    );
    bootstrap.register(ApicuriousMutations.MANY_CONDITIONAL_EXAMPLE, mutation(bootstrap)
            .withFirst(ApicuriousSpecies.FOREST)
            .withSecond(ApicuriousSpecies.MEADOW)
            .withChance(1.0f)
            .withOutput(ApicuriousSpecies.DEBUG)
            .withConditions(
                    new BiomeMutationCondition(bootstrap.lookup(Registries.BIOME).get(ApicuriousTags.BiomeTags.HELLISH_TEMPERATURE).get()),
                    new BiomeMutationCondition(bootstrap.lookup(Registries.BIOME).get(ApicuriousTags.BiomeTags.ICY_TEMPERATURE).get())
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
    private final HolderGetter<IMutationCondition> conditionGetter;
    private final List<Holder<IMutationCondition>> conditions;
    private HolderSet<IAllele<?>> first;
    private HolderSet<IAllele<?>> second;
    private float chance;
    private Holder<IAllele<?>> output;

    private Builder(BootstrapContext<IMutation> context)
    {
      this.context = context;
      this.alleleGetter = context.lookup(ApicuriousRegistries.ALLELES);
      this.conditionGetter = context.lookup(ApicuriousRegistries.MUTATION_CONDITIONS);
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

    public Builder withCondition(IMutationCondition mutationCondition)
    {
      this.conditions.add(Holder.direct(mutationCondition));
      return this;
    }

    public Builder withCondition(ResourceKey<IMutationCondition> mutationCondition)
    {
      Optional<Holder.Reference<IMutationCondition>> condition = conditionGetter.get(mutationCondition);
      condition.ifPresent(conditions::add);
      return this;
    }

    public Builder withConditions(IMutationCondition... mutationConditions)
    {
      Arrays.stream(mutationConditions).forEach(c -> conditions.add(Holder.direct(c)));
      return this;
    }

    public Builder withConditions(ResourceKey<IMutationCondition>... mutationConditions)
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
