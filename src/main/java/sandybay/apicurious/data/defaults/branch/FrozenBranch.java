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

public class FrozenBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.WINTRY.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.WINTRY.species(), "wintry").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.WINTRY)).withProductionData(builder -> builder.withFertility(Fertility.MAXIMUM_FERTILITY).withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.SNOW).withTemperaturePreference(TemperaturePreference.ICY).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.WINTRY.output())).build());
    bootstrap.register(ApicuriousSpecies.ICY.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ICY.species(), "icy").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ICY)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOW)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.SNOW).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.ICY).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.ICY.output())).build());
    bootstrap.register(ApicuriousSpecies.GLACIAL.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.GLACIAL.species(), "glacial").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.GLACIAL)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER)).withEnvironmentalData(builder -> builder.withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.ICY).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.GLACIAL.output())).build());
    bootstrap.register(ApicuriousSpecies.FRIGID.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.FRIGID.species(), "frigid").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.FRIGID)).withProductionData(builder -> builder.withFertility(Fertility.MAXIMUM_FERTILITY).withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER)).withEnvironmentalData(builder -> builder.withTemperaturePreference(TemperaturePreference.ICY).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.FRIGID.output())).build());
    bootstrap.register(ApicuriousSpecies.ABSOLUTE.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ABSOLUTE.species(), "absolute").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ABSOLUTE)).withProductionData(builder -> builder.withFertility(Fertility.MAXIMUM_FERTILITY).withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER)).withEnvironmentalData(builder -> builder.withTemperaturePreference(TemperaturePreference.ICY).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.ABSOLUTE.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.ICY.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.MODEST.species()).withSecond(ApicuriousSpecies.SINISTER.species()).withChance(0.12f).withOutput(ApicuriousSpecies.ICY.species()).build());
    bootstrap.register(ApicuriousSpecies.GLACIAL.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.MODEST.species()).withSecond(ApicuriousSpecies.SINISTER.species()).withChance(0.08f).withOutput(ApicuriousSpecies.GLACIAL.species()).build());
    bootstrap.register(ApicuriousSpecies.FRIGID.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.WINTRY.species()).withSecond(ApicuriousSpecies.DILIGENT.species()).withChance(0.1f).withOutput(ApicuriousSpecies.FRIGID.species()).build());
    bootstrap.register(ApicuriousSpecies.ABSOLUTE.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.FRIGID.species()).withSecond(ApicuriousSpecies.OCEAN.species()).withChance(0.1f).withOutput(ApicuriousSpecies.ABSOLUTE.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.WINTRY.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.FROZEN_COMB.comb(), 0.3f));
    bootstrap.register(ApicuriousSpecies.ICY.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(ItemRegistrar.ICE_SHARD.get()))).withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(ItemRegistrar.FROZEN_COMB.comb().get()))).build());
    bootstrap.register(ApicuriousSpecies.GLACIAL.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.4f)).withResult(result -> result.withResult(ItemRegistrar.ICE_SHARD.get()))).withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(ItemRegistrar.FROZEN_COMB.comb().get()))).build());
    bootstrap.register(ApicuriousSpecies.FRIGID.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.FROZEN_COMB.comb(), 0.25f));
    bootstrap.register(ApicuriousSpecies.ABSOLUTE.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(ItemRegistrar.FROZEN_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.1f)).withResult(result -> result.withResult(ItemRegistrar.GLACIAL_COMB.comb().get()))).build());
  }
}
