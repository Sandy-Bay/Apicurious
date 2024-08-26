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

public class BoggyBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.MARSHY.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MARSHY.species(), "marshy").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MARSHY)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORTER).withPollination(Pollination.SLOWER).withSpeed(Speed.SLOWEST)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.MUSHROOM).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.MARSHY.output())).build());
    bootstrap.register(ApicuriousSpecies.DAMP.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.DAMP.species(), "damp").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.DAMP)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORTER).withPollination(Pollination.SLOWER).withSpeed(Speed.SLOWEST)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.MUSHROOM).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.DAMP.output())).build());
    bootstrap.register(ApicuriousSpecies.BOGGY.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.BOGGY.species(), "boggy").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.BOGGY)).withProductionData(builder -> builder.withArea(Area.LARGER).withLifespan(Lifespan.SHORTER).withPollination(Pollination.SLOWER).withSpeed(Speed.SLOWEST).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.MUSHROOM).withHumidityPreference(HumidityPreference.DAMP).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE).ignoresRain()).withOutputData(builder -> builder.withTable(ApicuriousSpecies.BOGGY.output())).build());
    bootstrap.register(ApicuriousSpecies.FUNGAL.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.FUNGAL.species(), "fungal").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.FUNGAL)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORTER).withPollination(Pollination.SLOWER).withSpeed(Speed.SLOWEST)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.MUSHROOM).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.FUNGAL.output())).build());
    bootstrap.register(ApicuriousSpecies.MIRY.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MIRY.species(), "miry").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MIRY)).withProductionData(builder -> builder.withFertility(Fertility.MAXIMUM_FERTILITY).withLifespan(Lifespan.SHORTER).withPollination(Pollination.SLOWER).withSpeed(Speed.SLOWEST)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.MUSHROOM).withHumidityPreference(HumidityPreference.DAMP).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.MIRY.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.DAMP.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.MARSHY.species()).withSecond(ApicuriousSpecies.NOBLE.species()).withChance(0.1f).withOutput(ApicuriousSpecies.DAMP.species()).build());
    bootstrap.register(ApicuriousSpecies.BOGGY.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.DAMP.species()).withSecond(ApicuriousSpecies.MAJESTIC.species()).withChance(0.1f).withOutput(ApicuriousSpecies.BOGGY.species()).build());
    bootstrap.register(ApicuriousSpecies.FUNGAL.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.BOGGY.species()).withSecond(ApicuriousSpecies.IMPERIAL.species()).withChance(0.08f).withOutput(ApicuriousSpecies.FUNGAL.species()).build());
    bootstrap.register(ApicuriousSpecies.MIRY.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.MARSHY.species()).withSecond(ApicuriousSpecies.DILIGENT.species()).withChance(0.15f).withOutput(ApicuriousSpecies.MIRY.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.MARSHY.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.MOSSY_COMB.comb(), 0.3f));
    bootstrap.register(ApicuriousSpecies.DAMP.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.MOSSY_COMB.comb(), 0.3f));
    bootstrap.register(ApicuriousSpecies.BOGGY.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.39f)).withResult(result -> result.withResult(ItemRegistrar.MOSSY_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.08f)).withResult(result -> result.withResult(ItemRegistrar.PEAT.get()))).build());
    bootstrap.register(ApicuriousSpecies.FUNGAL.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.3f)).withResult(result -> result.withResult(ItemRegistrar.MOSSY_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.15f)).withResult(result -> result.withResult(ItemRegistrar.FUNGAL_COMB.comb().get()))).build());
    bootstrap.register(ApicuriousSpecies.MIRY.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.MOSSY_COMB.comb(), 0.1f));
  }
}
