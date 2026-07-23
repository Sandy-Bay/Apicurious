package sandybay.apicurious.data.defaults.branch;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.bee.genetic.allele.Flowers;
import sandybay.apicurious.common.bee.genetic.allele.Lifespan;
import sandybay.apicurious.common.bee.genetic.allele.Pollination;
import sandybay.apicurious.common.bee.genetic.allele.Speed;
import sandybay.apicurious.data.defaults.OutputTableDefaults;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

import static sandybay.apicurious.data.defaults.MutationDefaults.mutation;

public class SaccharineBranch
{
  /*
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.SWEETENED.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.SWEETENED.species(), "sweetened").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.SWEETENED)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORTER).withPollination(Pollination.FASTER).withSpeed(Speed.SLOWER)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.SUGAR_CANE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.SWEETENED.output())).build());
    bootstrap.register(ApicuriousSpecies.SUGARY.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.SUGARY.species(), "sugary").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.SUGARY)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORTER).withPollination(Pollination.FASTER).withSpeed(Speed.SLOWER)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.SUGAR_CANE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.SUGARY.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.SWEETENED.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.VALIANT.species()).withSecond(ApicuriousSpecies.DILIGENT.species()).withChance(0.15f).withOutput(ApicuriousSpecies.SWEETENED.species()).build());
    bootstrap.register(ApicuriousSpecies.SUGARY.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.SWEETENED.species()).withSecond(ApicuriousSpecies.RURAL.species()).withChance(0.15f).withOutput(ApicuriousSpecies.SUGARY.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.SWEETENED.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.4f)).withResult(result -> result.withResult(Items.HONEYCOMB))).withPool(pool -> pool.when(new ChanceCondition(0.1f)).withResult(result -> result.withResult(Items.SUGAR))).build());
    bootstrap.register(ApicuriousSpecies.SUGARY.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.4f)).withResult(result -> result.withResult(Items.HONEYCOMB))).withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(Items.SUGAR))).build());
  }
   */
}
