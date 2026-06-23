package sandybay.apicurious.data.defaults.branch;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.OutputTableDefaults;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

import static sandybay.apicurious.data.defaults.MutationDefaults.mutation;

public class MonasticBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.MONASTIC.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MONASTIC.species(), "monastic").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MONASTIC)).withProductionData(builder -> builder.withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.LONG).withPollination(Pollination.FASTER).withSpeed(Speed.SLOWER)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.WHEAT).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.MONASTIC.output())).build());
    bootstrap.register(ApicuriousSpecies.SECLUDED.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.SECLUDED.species(), "secluded").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.SECLUDED)).withProductionData(builder -> builder.withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.LONG).withPollination(Pollination.FASTEST).withSpeed(Speed.SLOWER)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.WHEAT).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.SECLUDED.output())).build());
    bootstrap.register(ApicuriousSpecies.HERMITIC.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.HERMITIC.species(), "hermitic").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.HERMITIC)).withProductionData(builder -> builder.withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.LONG).withPollination(Pollination.FASTEST).withSpeed(Speed.SLOWER)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.WHEAT).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.HERMITIC.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.SECLUDED.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.MONASTIC.species()).withSecond(ApicuriousSpecies.AUSTERE.species()).withChance(0.12f).withOutput(ApicuriousSpecies.SECLUDED.species()).build());
    bootstrap.register(ApicuriousSpecies.HERMITIC.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.MONASTIC.species()).withSecond(ApicuriousSpecies.SECLUDED.species()).withChance(0.08f).withOutput(ApicuriousSpecies.HERMITIC.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.MONASTIC.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.3f)).withResult(result -> result.withResult(ItemRegistrar.WHEATEN_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.1f)).withResult(result -> result.withResult(ItemRegistrar.MELLOW_COMB.comb().get()))).build());
    bootstrap.register(ApicuriousSpecies.SECLUDED.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.MELLOW_COMB.comb(), 0.2f));
    bootstrap.register(ApicuriousSpecies.HERMITIC.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.MELLOW_COMB.comb(), 0.2f));
  }
}
