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

public class EndBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.ENDER.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ENDER.species(), "ender").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ENDER)).withProductionData(builder -> builder.withArea(Area.LARGE).withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.LONGER).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.END_STONE).withTemperaturePreference(TemperaturePreference.COLD).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.ENDER.output())).build());
    bootstrap.register(ApicuriousSpecies.SPECTRAL.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.SPECTRAL.species(), "spectral").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.SPECTRAL)).withProductionData(builder -> builder.withArea(Area.LARGE).withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.LONGER).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.END_STONE).withTemperaturePreference(TemperaturePreference.COLD).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.SPECTRAL.output())).build());
    bootstrap.register(ApicuriousSpecies.PHANTASMAL.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.PHANTASMAL.species(), "phantasmal").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.PHANTASMAL)).withProductionData(builder -> builder.withArea(Area.LARGE).withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.LONGEST).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWEST).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.END_STONE).withTemperaturePreference(TemperaturePreference.COLD).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.PHANTASMAL.output())).recessive().build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.SPECTRAL.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.ENDER.species()).withSecond(ApicuriousSpecies.HERMITIC.species()).withChance(0.04f).withOutput(ApicuriousSpecies.SPECTRAL.species()).build());
    bootstrap.register(ApicuriousSpecies.PHANTASMAL.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.ENDER.species()).withSecond(ApicuriousSpecies.SPECTRAL.species()).withChance(0.02f).withOutput(ApicuriousSpecies.PHANTASMAL.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.ENDER.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.MYSTERIOUS_COMB.comb(), 0.3f));
    bootstrap.register(ApicuriousSpecies.SPECTRAL.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.MYSTERIOUS_COMB.comb(), 0.5f));
    bootstrap.register(ApicuriousSpecies.PHANTASMAL.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.MYSTERIOUS_COMB.comb(), 0.4f));
  }
}
