package sandybay.apicurious.data.defaults.branch;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.OutputTableDefaults;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

import static sandybay.apicurious.data.defaults.MutationDefaults.mutation;

public class AustereBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.MODEST.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MODEST.species(), "modest").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MODEST)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.CACTI).withHumidityPreference(HumidityPreference.ARID).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.MODEST.output())).build());
    bootstrap.register(ApicuriousSpecies.FRUGAL.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.FRUGAL.species(), "frugal").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.FRUGAL)).withProductionData(builder -> builder.withLifespan(Lifespan.LONG).withPollination(Pollination.SLOWEST).withSpeed(Speed.AVERAGE).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.CACTI).withHumidityPreference(HumidityPreference.ARID).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.FRUGAL.output())).build());
    bootstrap.register(ApicuriousSpecies.AUSTERE.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.AUSTERE.species(), "austere").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.AUSTERE).hasEffect()).withProductionData(builder -> builder.withLifespan(Lifespan.LONGER).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWEST).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.CACTI).withHumidityPreference(HumidityPreference.ARID).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.AUSTERE.output())).build());
    bootstrap.register(ApicuriousSpecies.HAZARDOUS.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.HAZARDOUS.species(), "hazardous").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.HAZARDOUS)).withProductionData(builder -> builder.withLifespan(Lifespan.LONGER).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWEST).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.CACTI).withHumidityPreference(HumidityPreference.ARID).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.HAZARDOUS.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("frugal_sinister")), mutation(bootstrap).withFirst(ApicuriousSpecies.MODEST.species()).withSecond(ApicuriousSpecies.SINISTER.species()).withChance(0.16f).withOutput(ApicuriousSpecies.FRUGAL.species()).build());
    bootstrap.register(ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("frugal_fiendish")), mutation(bootstrap).withFirst(ApicuriousSpecies.MODEST.species()).withSecond(ApicuriousSpecies.FIENDISH.species()).withChance(0.1f).withOutput(ApicuriousSpecies.FRUGAL.species()).build());
    bootstrap.register(ApicuriousSpecies.AUSTERE.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.MODEST.species()).withSecond(ApicuriousSpecies.FRUGAL.species()).withChance(0.08f).withOutput(ApicuriousSpecies.AUSTERE.species()).build());
    bootstrap.register(ApicuriousSpecies.HAZARDOUS.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.AUSTERE.species()).withSecond(ApicuriousSpecies.DESOLATE.species()).withChance(0.05f).withOutput(ApicuriousSpecies.HAZARDOUS.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.MODEST.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.PARCHED_COMB.comb(), 0.2f));
    bootstrap.register(ApicuriousSpecies.FRUGAL.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.PARCHED_COMB.comb(), 0.3f));
    bootstrap.register(ApicuriousSpecies.AUSTERE.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(ItemRegistrar.PARCHED_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.5f)).withResult(result -> result.withResult(ItemRegistrar.POWDERY_COMB.comb().get()))).build());
    bootstrap.register(ApicuriousSpecies.HAZARDOUS.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.UNSTABLE_COMB.comb(), 0.12f));
  }
}
