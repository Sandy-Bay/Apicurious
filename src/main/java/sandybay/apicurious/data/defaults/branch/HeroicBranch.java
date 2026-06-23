package sandybay.apicurious.data.defaults.branch;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.bee.genetic.allele.Lifespan;
import sandybay.apicurious.common.bee.genetic.allele.Pollination;
import sandybay.apicurious.common.bee.genetic.allele.Speed;
import sandybay.apicurious.common.bee.genetic.allele.Workcycle;
import sandybay.apicurious.common.bee.genetic.allele.groups.EnvironmentalData;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.OutputTableDefaults;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

import static sandybay.apicurious.data.defaults.MutationDefaults.mutation;

public class HeroicBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.VALIANT.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.VALIANT.species(), "valiant").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.VALIANT)).withProductionData(builder -> builder.withLifespan(Lifespan.LONG).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOW).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(EnvironmentalData.Builder::ignoresSky).withOutputData(builder -> builder.withTable(ApicuriousSpecies.VALIANT.output())).build());
    bootstrap.register(ApicuriousSpecies.STEADFAST.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.STEADFAST.species(), "steadfast").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.STEADFAST).hasEffect()).withProductionData(builder -> builder.withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(EnvironmentalData.Builder::ignoresSky).withOutputData(builder -> builder.withTable(ApicuriousSpecies.STEADFAST.output())).build());
    bootstrap.register(ApicuriousSpecies.HEROIC.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.HEROIC.species(), "heroic").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.HEROIC).hasEffect()).withProductionData(builder -> builder.withLifespan(Lifespan.LONG).withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOW).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(EnvironmentalData.Builder::ignoresSky).withOutputData(builder -> builder.withTable(ApicuriousSpecies.HEROIC.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.HEROIC.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.STEADFAST.species()).withSecond(ApicuriousSpecies.VALIANT.species()).withChance(0.06f).withOutput(ApicuriousSpecies.HEROIC.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.VALIANT.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.3f)).withResult(result -> result.withResult(ItemRegistrar.COCOA_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.15f)).withResult(result -> result.withResult(Items.SUGAR))).build());
    bootstrap.register(ApicuriousSpecies.STEADFAST.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.COCOA_COMB.comb(), 0.2f));
    bootstrap.register(ApicuriousSpecies.HEROIC.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.4f)).withResult(result -> result.withResult(ItemRegistrar.COCOA_COMB.comb().get()))).build());
  }
}
