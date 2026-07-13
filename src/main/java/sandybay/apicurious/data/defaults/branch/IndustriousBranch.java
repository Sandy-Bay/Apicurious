package sandybay.apicurious.data.defaults.branch;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.bee.genetic.allele.Lifespan;
import sandybay.apicurious.common.bee.genetic.allele.Pollination;
import sandybay.apicurious.common.bee.genetic.allele.Speed;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.OutputTableDefaults;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

import static sandybay.apicurious.data.defaults.MutationDefaults.mutation;

public class IndustriousBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.DILIGENT.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.DILIGENT.species(), "diligent").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.DILIGENT)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOW).withSpeed(Speed.SLOWER)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.DILIGENT.output())).build());
    bootstrap.register(ApicuriousSpecies.UNWEARY.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.UNWEARY.species(), "unweary").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.UNWEARY)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORTENED).withPollination(Pollination.SLOWEST).withSpeed(Speed.AVERAGE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.UNWEARY.output())).build());
    bootstrap.register(ApicuriousSpecies.INDUSTRIOUS.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.INDUSTRIOUS.species(), "industrious").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.INDUSTRIOUS).hasEffect()).withProductionData(builder -> builder.withLifespan(Lifespan.AVERAGE).withPollination(Pollination.FAST).withSpeed(Speed.SLOWER)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.INDUSTRIOUS.output())).recessive().build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.DILIGENT.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.COMMON.species()).withSecond(ApicuriousSpecies.CULTIVATED.species()).withChance(0.1f).withOutput(ApicuriousSpecies.DILIGENT.species()).build());
    bootstrap.register(ApicuriousSpecies.UNWEARY.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.DILIGENT.species()).withSecond(ApicuriousSpecies.CULTIVATED.species()).withChance(0.08f).withOutput(ApicuriousSpecies.UNWEARY.species()).build());
    bootstrap.register(ApicuriousSpecies.INDUSTRIOUS.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.DILIGENT.species()).withSecond(ApicuriousSpecies.UNWEARY.species()).withChance(0.08f).withOutput(ApicuriousSpecies.INDUSTRIOUS.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.DILIGENT.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.STRINGY_COMB.comb(), 0.2f));
    bootstrap.register(ApicuriousSpecies.UNWEARY.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.STRINGY_COMB.comb(), 0.3f));
    bootstrap.register(ApicuriousSpecies.INDUSTRIOUS.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(ItemRegistrar.STRINGY_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.15f)).withResult(result -> result.withResult(ItemRegistrar.POLLEN.pollen().get()))).build());
  }
}
