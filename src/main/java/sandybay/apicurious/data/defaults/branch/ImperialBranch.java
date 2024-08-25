package sandybay.apicurious.data.defaults.branch;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.bee.genetic.allele.Fertility;
import sandybay.apicurious.common.bee.genetic.allele.Lifespan;
import sandybay.apicurious.common.bee.genetic.allele.Pollination;
import sandybay.apicurious.common.bee.genetic.allele.Speed;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.OutputTableDefaults;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

import static sandybay.apicurious.data.defaults.MutationDefaults.mutation;

public class ImperialBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.NOBLE.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.NOBLE.species(), "noble").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.NOBLE)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOW).withSpeed(Speed.SLOWER)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.NOBLE.output())).build());
    bootstrap.register(ApicuriousSpecies.MAJESTIC.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MAJESTIC.species(), "majestic").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MAJESTIC)).withProductionData(builder -> builder.withFertility(Fertility.MAXIMUM_FERTILITY).withLifespan(Lifespan.SHORTENED).withPollination(Pollination.SLOWEST).withSpeed(Speed.AVERAGE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.MAJESTIC.output())).build());
    bootstrap.register(ApicuriousSpecies.IMPERIAL.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.IMPERIAL.species(), "imperial").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.IMPERIAL).hasEffect()).withProductionData(builder -> builder.withLifespan(Lifespan.AVERAGE).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.IMPERIAL.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.NOBLE.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.COMMON.species()).withSecond(ApicuriousSpecies.CULTIVATED.species()).withChance(0.1f).withOutput(ApicuriousSpecies.NOBLE.species()).build());
    bootstrap.register(ApicuriousSpecies.MAJESTIC.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.NOBLE.species()).withSecond(ApicuriousSpecies.CULTIVATED.species()).withChance(0.08f).withOutput(ApicuriousSpecies.MAJESTIC.species()).build());
    bootstrap.register(ApicuriousSpecies.IMPERIAL.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.NOBLE.species()).withSecond(ApicuriousSpecies.MAJESTIC.species()).withChance(0.08f).withOutput(ApicuriousSpecies.IMPERIAL.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.NOBLE.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.DRIPPING_COMB, 0.2f));
    bootstrap.register(ApicuriousSpecies.MAJESTIC.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.DRIPPING_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.IMPERIAL.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(ItemRegistrar.DRIPPING_COMB.get()))).withPool(pool -> pool.when(new ChanceCondition(0.15f)).withResult(result -> result.withResult(ItemRegistrar.ROYAL_JELLY.get()))).build());
  }
}
