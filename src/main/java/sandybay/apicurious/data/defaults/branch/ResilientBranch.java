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

public class ResilientBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.ROCKY.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ROCKY.species(), "rocky").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ROCKY)).withProductionData(builder -> builder.withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWEST).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.STONE).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE).ignoresRain().ignoresSky()).withOutputData(builder -> builder.withTable(ApicuriousSpecies.ROCKY.output())).build());
    bootstrap.register(ApicuriousSpecies.TOLERANT.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.TOLERANT.species(), "tolerant").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.TOLERANT)).withProductionData(builder -> builder.withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWEST).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.STONE).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE).ignoresRain().ignoresSky()).withOutputData(builder -> builder.withTable(ApicuriousSpecies.TOLERANT.output())).build());
    bootstrap.register(ApicuriousSpecies.ROBUST.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ROBUST.species(), "robust").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ROBUST)).withProductionData(builder -> builder.withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWEST).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.STONE).withHumidityTolerance(HumidityTolerance.LOW_TOLERANCE).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE).ignoresRain().ignoresSky()).withOutputData(builder -> builder.withTable(ApicuriousSpecies.ROBUST.output())).build());
    bootstrap.register(ApicuriousSpecies.RESILIENT.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.RESILIENT.species(), "resilient").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.RESILIENT)).withProductionData(builder -> builder.withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWEST).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.STONE).withHumidityTolerance(HumidityTolerance.AVERAGE_TOLERANCE).withTemperatureTolerance(TemperatureTolerance.AVERAGE_TOLERANCE).ignoresRain().ignoresSky()).withOutputData(builder -> builder.withTable(ApicuriousSpecies.RESILIENT.output())).recessive().build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.TOLERANT.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.DILIGENT.species()).withSecond(ApicuriousSpecies.ROCKY.species()).withChance(0.15f).withOutput(ApicuriousSpecies.TOLERANT.species()).build());
    bootstrap.register(ApicuriousSpecies.ROBUST.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.UNWEARY.species()).withSecond(ApicuriousSpecies.TOLERANT.species()).withChance(0.15f).withOutput(ApicuriousSpecies.ROBUST.species()).build());
    bootstrap.register(ApicuriousSpecies.RESILIENT.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.INDUSTRIOUS.species()).withSecond(ApicuriousSpecies.ROBUST.species()).withChance(0.15f).withOutput(ApicuriousSpecies.RESILIENT.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.ROCKY.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.ROCKY_COMB.comb(), 0.3f));
    bootstrap.register(ApicuriousSpecies.TOLERANT.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.ROCKY_COMB.comb(), 0.3f));
    bootstrap.register(ApicuriousSpecies.ROBUST.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.ROCKY_COMB.comb(), 0.3f));
    bootstrap.register(ApicuriousSpecies.RESILIENT.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.ROCKY_COMB.comb(), 0.3f));
  }
}
