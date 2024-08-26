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

public class CausticBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.CORROSIVE.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.CORROSIVE.species(), "corrosive").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.CORROSIVE)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withSpeed(Speed.FAST)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.JUNGLE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.CORROSIVE.output())).build());
    bootstrap.register(ApicuriousSpecies.CAUSTIC.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.CAUSTIC.species(), "caustic").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.CAUSTIC)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withSpeed(Speed.FAST)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.JUNGLE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.CAUSTIC.output())).build());
    bootstrap.register(ApicuriousSpecies.ACIDIC.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ACIDIC.species(), "acidic").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ACIDIC)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORT).withSpeed(Speed.FAST)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.JUNGLE).withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.ACIDIC.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.CORROSIVE.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.VIRULENT.species()).withSecond(ApicuriousSpecies.STICKY.species()).withChance(0.1f).withOutput(ApicuriousSpecies.CORROSIVE.species()).build());
    bootstrap.register(ApicuriousSpecies.CAUSTIC.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.CORROSIVE.species()).withSecond(ApicuriousSpecies.FIENDISH.species()).withChance(0.08f).withOutput(ApicuriousSpecies.CAUSTIC.species()).build());
    bootstrap.register(ApicuriousSpecies.ACIDIC.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.CORROSIVE.species()).withSecond(ApicuriousSpecies.CAUSTIC.species()).withChance(0.04f).withOutput(ApicuriousSpecies.ACIDIC.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.CORROSIVE.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.SILKY_COMB.comb(), 0.2f));
    bootstrap.register(ApicuriousSpecies.CAUSTIC.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.25f)).withResult(result -> result.withResult(ItemRegistrar.SILKY_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.03f)).withResult(result -> result.withResult(ItemRegistrar.BRIMSTONE_COMB.comb().get()))).build());
    bootstrap.register(ApicuriousSpecies.ACIDIC.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(ItemRegistrar.SILKY_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.16f)).withResult(result -> result.withResult(ItemRegistrar.BRIMSTONE_COMB.comb().get()))).build());
  }
}
