package sandybay.apicurious.data.defaults.branch;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.Fertility;
import sandybay.apicurious.common.bee.genetic.allele.Lifespan;
import sandybay.apicurious.common.bee.genetic.allele.Pollination;
import sandybay.apicurious.common.bee.genetic.allele.Speed;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.OutputTableDefaults;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

import static sandybay.apicurious.data.defaults.MutationDefaults.mutation;

public class HistoricBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.ANCIENT.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ANCIENT.species(), "ancient").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ANCIENT)).withProductionData(builder -> builder.withLifespan(Lifespan.ELONGATED).withPollination(Pollination.SLOW).withSpeed(Speed.SLOWER)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.ANCIENT.output())).build());
    bootstrap.register(ApicuriousSpecies.PRIMEVAL.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.PRIMEVAL.species(), "primeval").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.PRIMEVAL)).withProductionData(builder -> builder.withLifespan(Lifespan.LONG).withPollination(Pollination.SLOW).withSpeed(Speed.SLOWER)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.PRIMEVAL.output())).build());
    bootstrap.register(ApicuriousSpecies.PREHISTORIC.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.PREHISTORIC.species(), "prehistoric").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.PREHISTORIC)).withProductionData(builder -> builder.withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.LONGER).withPollination(Pollination.SLOW).withSpeed(Speed.SLOWER)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.PREHISTORIC.output())).build());
    bootstrap.register(ApicuriousSpecies.RELIC.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.RELIC.species(), "relic").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.RELIC)).withProductionData(builder -> builder.withLifespan(Lifespan.LONGEST).withPollination(Pollination.SLOW).withSpeed(Speed.SLOWER)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.RELIC.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.ANCIENT.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.NOBLE.species()).withSecond(ApicuriousSpecies.DILIGENT.species()).withChance(0.1f).withOutput(ApicuriousSpecies.ANCIENT.species()).build());
    bootstrap.register(ApicuriousSpecies.PRIMEVAL.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.ANCIENT.species()).withSecond(ApicuriousSpecies.SECLUDED.species()).withChance(0.08f).withOutput(ApicuriousSpecies.PRIMEVAL.species()).build());
    bootstrap.register(ApicuriousSpecies.PREHISTORIC.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.PRIMEVAL.species()).withSecond(ApicuriousSpecies.ANCIENT.species()).withChance(0.08f).withOutput(ApicuriousSpecies.PREHISTORIC.species()).build());
    bootstrap.register(ApicuriousSpecies.RELIC.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.PREHISTORIC.species()).withSecond(ApicuriousSpecies.IMPERIAL.species()).withChance(0.08f).withOutput(ApicuriousSpecies.RELIC.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.ANCIENT.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.ANCIENT_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.PRIMEVAL.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.ANCIENT_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.PREHISTORIC.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.ANCIENT_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.RELIC.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.ANCIENT_COMB, 0.3f));
  }
}
