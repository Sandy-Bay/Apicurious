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

public class ViscousBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.VISCOUS.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.VISCOUS.species(), "viscous").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.VISCOUS)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOW)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.JUNGLE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.VISCOUS.output())).build());
    bootstrap.register(ApicuriousSpecies.GLUTINOUS.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.GLUTINOUS.species(), "glutinous").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.GLUTINOUS)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.JUNGLE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.GLUTINOUS.output())).build());
    bootstrap.register(ApicuriousSpecies.STICKY.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.STICKY.species(), "sticky").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.STICKY)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.FAST)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.JUNGLE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.STICKY.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.VISCOUS.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.RIVER.species()).withSecond(ApicuriousSpecies.EXOTIC.species()).withChance(0.1f).withOutput(ApicuriousSpecies.VISCOUS.species()).build());
    bootstrap.register(ApicuriousSpecies.GLUTINOUS.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.VISCOUS.species()).withSecond(ApicuriousSpecies.EXOTIC.species()).withChance(0.08f).withOutput(ApicuriousSpecies.GLUTINOUS.species()).build());
    bootstrap.register(ApicuriousSpecies.STICKY.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.VISCOUS.species()).withSecond(ApicuriousSpecies.GLUTINOUS.species()).withChance(0.08f).withOutput(ApicuriousSpecies.STICKY.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.VISCOUS.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.SILKY_COMB, 0.25f));
    bootstrap.register(ApicuriousSpecies.GLUTINOUS.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.SILKY_COMB, 0.25f));
    bootstrap.register(ApicuriousSpecies.STICKY.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.25f)).withResult(result -> result.withResult(ItemRegistrar.SILKY_COMB.get()))).withPool(pool -> pool.when(new ChanceCondition(0.25f)).withResult(result -> result.withResult(ItemRegistrar.MUCOUS_COMB.get()))).build());
  }
}
