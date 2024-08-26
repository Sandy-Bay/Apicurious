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

public class MineralBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.LAZULI.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.LAZULI.species(), "lazuli").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.LAZULI)).withProductionData(builder -> builder.withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORT).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWEST).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.STONE).withHumidityTolerance(HumidityTolerance.LOW_TOLERANCE).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE).ignoresSky().ignoresRain()).withOutputData(builder -> builder.withTable(ApicuriousSpecies.LAZULI.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.LAZULI.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.RESILIENT.species()).withSecond(ApicuriousSpecies.WATER.species()).withChance(0.05f).withOutput(ApicuriousSpecies.LAZULI.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.LAZULI.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(ItemRegistrar.ROCKY_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.05f)).withResult(result -> result.withResult(ItemRegistrar.LAPIS_COMB.comb().get()))).build());
  }
}
