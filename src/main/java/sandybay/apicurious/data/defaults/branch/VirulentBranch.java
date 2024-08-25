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

public class VirulentBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.MALICIOUS.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MALICIOUS.species(), "malicious").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MALICIOUS)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.JUNGLE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.MALICIOUS.output())).build());
    bootstrap.register(ApicuriousSpecies.INFECTIOUS.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.INFECTIOUS.species(), "infectious").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.INFECTIOUS)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOW).withSpeed(Speed.SLOWER)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.JUNGLE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.INFECTIOUS.output())).build());
    bootstrap.register(ApicuriousSpecies.VIRULENT.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.VIRULENT.species(), "virulent").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.VIRULENT)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withSpeed(Speed.SLOWER)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.JUNGLE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.VIRULENT.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.MALICIOUS.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.SINISTER.species()).withSecond(ApicuriousSpecies.TROPICAL.species()).withChance(0.1f).withOutput(ApicuriousSpecies.MALICIOUS.species()).build());
    bootstrap.register(ApicuriousSpecies.INFECTIOUS.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.MALICIOUS.species()).withSecond(ApicuriousSpecies.TROPICAL.species()).withChance(0.08f).withOutput(ApicuriousSpecies.INFECTIOUS.species()).build());
    bootstrap.register(ApicuriousSpecies.VIRULENT.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.MALICIOUS.species()).withSecond(ApicuriousSpecies.MALICIOUS.species()).withChance(0.08f).withOutput(ApicuriousSpecies.VIRULENT.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.MALICIOUS.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.SILKY_COMB, 0.25f));
    bootstrap.register(ApicuriousSpecies.INFECTIOUS.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.SILKY_COMB, 0.25f));
    bootstrap.register(ApicuriousSpecies.VIRULENT.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.25f)).withResult(result -> result.withResult(ItemRegistrar.SILKY_COMB.get()))).withPool(pool -> pool.when(new ChanceCondition(0.12f)).withResult(result -> result.withResult(ItemRegistrar.VENOMOUS_COMB.get()))).build());
  }
}
