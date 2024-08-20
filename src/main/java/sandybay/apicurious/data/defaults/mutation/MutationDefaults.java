package sandybay.apicurious.data.defaults.mutation;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import org.apache.commons.compress.utils.Lists;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.genetic.mutation.ConditionalMutation;
import sandybay.apicurious.common.bee.genetic.mutation.Mutation;
import sandybay.apicurious.data.defaults.branch.*;
import sandybay.apicurious.data.defaults.mutation.line.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// TODO: Implement some sort of condition builder system to make it easier to specify complex conditional behaviour
public class MutationDefaults
{
  public static void registerDefaults(BootstrapContext<IMutation> bootstrap)
  {
    AgrarianBranch.mutationsDefaults(bootstrap);
    ApisBranch.mutationsDefaults(bootstrap);
    AquaticBranch.mutationsDefaults(bootstrap);
    AustereBranch.mutationsDefaults(bootstrap);
    BarrenBranch.mutationsDefaults(bootstrap);
  }

  public static Builder mutation(BootstrapContext<IMutation> bootstrap)
  {
    return new Builder(bootstrap);
  }

  public static class Builder
  {
    private final HolderGetter<IAllele<?>> alleleGetter;
    private final HolderGetter<ICondition> conditionGetter;
    private final List<Holder<ICondition>> conditions;
    private HolderSet<IAllele<?>> first;
    private HolderSet<IAllele<?>> second;
    private float chance;
    private Holder<IAllele<?>> output;

    private Builder(BootstrapContext<IMutation> context)
    {
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
