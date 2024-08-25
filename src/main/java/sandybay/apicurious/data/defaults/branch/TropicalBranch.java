package sandybay.apicurious.data.defaults.branch;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.OutputTableDefaults;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

import static sandybay.apicurious.data.defaults.MutationDefaults.mutation;

public class TropicalBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.TROPICAL.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.TROPICAL.species(), "tropical").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.TROPICAL)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.JUNGLE).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.TROPICAL.output())).build());
    bootstrap.register(ApicuriousSpecies.EXOTIC.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.EXOTIC.species(), "exotic").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.EXOTIC)).withProductionData(builder -> builder.withLifespan(Lifespan.LONG).withPollination(Pollination.SLOWEST)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.JUNGLE).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.EXOTIC.output())).build());
    bootstrap.register(ApicuriousSpecies.EDENIC.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.EDENIC.species(), "edenic").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.EDENIC)).withProductionData(builder -> builder.withLifespan(Lifespan.LONGER).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWEST)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.JUNGLE).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.EDENIC.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.EXOTIC.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.AUSTERE.species()).withSecond(ApicuriousSpecies.TROPICAL.species()).withChance(0.12f).withOutput(ApicuriousSpecies.EXOTIC.species()).build());
    bootstrap.register(ApicuriousSpecies.EDENIC.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.EXOTIC.species()).withSecond(ApicuriousSpecies.TROPICAL.species()).withChance(0.08f).withOutput(ApicuriousSpecies.EDENIC.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.TROPICAL.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.SILKY_COMB, 0.2f));
    bootstrap.register(ApicuriousSpecies.EXOTIC.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.SILKY_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.EDENIC.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.SILKY_COMB, 0.2f));
  }
}
