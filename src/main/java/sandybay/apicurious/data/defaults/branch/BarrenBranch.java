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

public class BarrenBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.ARID.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ARID.species(), "arid").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ARID)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.DEAD_BUSH).withHumidityPreference(HumidityPreference.ARID).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.ARID.output())).build());
    bootstrap.register(ApicuriousSpecies.BARREN.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.BARREN.species(), "barren").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.BARREN)).withProductionData(builder -> builder.withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.DEAD_BUSH).withHumidityPreference(HumidityPreference.ARID).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.BARREN.output())).build());
    bootstrap.register(ApicuriousSpecies.DESOLATE.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.DESOLATE.species(), "desolate").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.DESOLATE)).withProductionData(builder -> builder.withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.DEAD_BUSH).withHumidityPreference(HumidityPreference.ARID).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.DESOLATE.output())).build());
    bootstrap.register(ApicuriousSpecies.GNAWING.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.GNAWING.species(), "gnawing").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.GNAWING)).withProductionData(builder -> builder.withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.WOOD).withHumidityPreference(HumidityPreference.ARID).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.GNAWING.output())).build());
    bootstrap.register(ApicuriousSpecies.DECOMPOSING.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.DECOMPOSING.species(), "decomposing").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.DECOMPOSING)).withProductionData(builder -> builder.withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.DEAD_BUSH).withHumidityPreference(HumidityPreference.ARID).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.DECOMPOSING.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.ARID.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.MEADOW.species()).withSecond(ApicuriousSpecies.FRUGAL.species()).withChance(0.1f).withOutput(ApicuriousSpecies.ARID.species()).build());
    bootstrap.register(ApicuriousSpecies.BARREN.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.ARID.species()).withSecond(ApicuriousSpecies.COMMON.species()).withChance(0.08f).withOutput(ApicuriousSpecies.BARREN.species()).build());
    bootstrap.register(ApicuriousSpecies.DESOLATE.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.ARID.species()).withSecond(ApicuriousSpecies.BARREN.species()).withChance(0.08f).withOutput(ApicuriousSpecies.DESOLATE.species()).build());
    bootstrap.register(ApicuriousSpecies.GNAWING.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.FOREST.species()).withSecond(ApicuriousSpecies.BARREN.species()).withChance(0.15f).withOutput(ApicuriousSpecies.GNAWING.species()).build());
    bootstrap.register(ApicuriousSpecies.DECOMPOSING.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.BARREN.species()).withSecond(ApicuriousSpecies.MARSHY.species()).withChance(0.15f).withOutput(ApicuriousSpecies.DECOMPOSING.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.ARID.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.BARREN_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.BARREN.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.BARREN_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.DESOLATE.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.BARREN_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.GNAWING.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.25f)).withResult(result -> result.withResult(ItemRegistrar.BARREN_COMB.get()))).withPool(pool -> pool.when(new ChanceCondition(0.25f)).withResult(result -> result.withResult(ItemRegistrar.DUSTY_COMB.get()))).build());
    bootstrap.register(ApicuriousSpecies.DECOMPOSING.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.3f)).withResult(result -> result.withResult(ItemRegistrar.BARREN_COMB.get()))).withPool(pool -> pool.when(new ChanceCondition(0.08f)).withResult(result -> result.withResult(ItemRegistrar.DECOMPOSED_COMB.get()))).build());
  }
}
