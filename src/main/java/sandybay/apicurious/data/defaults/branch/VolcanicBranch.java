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

public class VolcanicBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.NETHER.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.NETHER.species(), "nether").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.NETHER)).withProductionData(builder -> builder.withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.NETHER_STONE).withHumidityPreference(HumidityPreference.ARID).withTemperaturePreference(TemperaturePreference.HELLISH).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.NETHER.output())).build());
    bootstrap.register(ApicuriousSpecies.FURIOUS.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.FURIOUS.species(), "furious").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.FURIOUS)).withProductionData(builder -> builder.withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.NETHER_STONE).withHumidityPreference(HumidityPreference.ARID).withTemperaturePreference(TemperaturePreference.HELLISH).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.FURIOUS.output())).build());
    bootstrap.register(ApicuriousSpecies.VOLCANIC.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.VOLCANIC.species(), "volcanic").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.VOLCANIC).hasEffect()).withProductionData(builder -> builder.withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.NETHER_STONE).withHumidityPreference(HumidityPreference.ARID).withTemperaturePreference(TemperaturePreference.HELLISH).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.VOLCANIC.output())).recessive().build());
    bootstrap.register(ApicuriousSpecies.GLOWERING.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.GLOWERING.species(), "glowering").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.GLOWERING)).withProductionData(builder -> builder.withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.NETHER_STONE).withHumidityPreference(HumidityPreference.ARID).withTemperaturePreference(TemperaturePreference.HELLISH).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.GLOWERING.output())).recessive().build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.FURIOUS.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.FIENDISH.species()).withSecond(ApicuriousSpecies.NETHER.species()).withChance(0.3f).withOutput(ApicuriousSpecies.FURIOUS.species()).build());
    bootstrap.register(ApicuriousSpecies.VOLCANIC.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.FURIOUS.species()).withSecond(ApicuriousSpecies.DEMONIC.species()).withChance(0.2f).withOutput(ApicuriousSpecies.VOLCANIC.species()).build());
    bootstrap.register(ApicuriousSpecies.GLOWERING.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.FURIOUS.species()).withSecond(ApicuriousSpecies.EXCITED.species()).withChance(0.05f).withOutput(ApicuriousSpecies.GLOWERING.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.NETHER.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.SIMMERING_COMB.comb(), 0.25f));
    bootstrap.register(ApicuriousSpecies.FURIOUS.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.SIMMERING_COMB.comb(), 0.25f));
    bootstrap.register(ApicuriousSpecies.VOLCANIC.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.25f)).withResult(result -> result.withResult(ItemRegistrar.SIMMERING_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.25f)).withResult(result -> result.withResult(ItemRegistrar.BLAZING_COMB.comb().get()))).build());
    bootstrap.register(ApicuriousSpecies.GLOWERING.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.GLOWING_COMB.comb(), 0.15f));
  }
}
