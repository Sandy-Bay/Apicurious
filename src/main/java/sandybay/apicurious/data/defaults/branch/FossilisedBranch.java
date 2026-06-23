package sandybay.apicurious.data.defaults.branch;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.bee.genetic.allele.Pollination;
import sandybay.apicurious.common.bee.genetic.allele.Speed;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.OutputTableDefaults;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

import static sandybay.apicurious.data.defaults.MutationDefaults.mutation;

public class FossilisedBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.FOSSILISED.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.FOSSILISED.species(), "fossilised").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.FOSSILISED)).withProductionData(builder -> builder.withPollination(Pollination.SLOW).withSpeed(Speed.SLOWER)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.FOSSILISED.output())).build());
    bootstrap.register(ApicuriousSpecies.FORGOTTEN.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.FORGOTTEN.species(), "ancient").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.FORGOTTEN)).withProductionData(builder -> builder.withPollination(Pollination.SLOW).withSpeed(Speed.SLOWER)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.FORGOTTEN.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.FOSSILISED.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.PRIMEVAL.species()).withSecond(ApicuriousSpecies.FARMERLY.species()).withChance(0.08f).withOutput(ApicuriousSpecies.FOSSILISED.species()).build());
    bootstrap.register(ApicuriousSpecies.FORGOTTEN.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.FOSSILISED.species()).withSecond(ApicuriousSpecies.DEMONIC.species()).withChance(0.05f).withOutput(ApicuriousSpecies.FORGOTTEN.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.FOSSILISED.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(ItemRegistrar.ANCIENT_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.02f)).withResult(result -> result.withResult(ItemRegistrar.FOSSILISED_COMB.comb().get()))).build());
    bootstrap.register(ApicuriousSpecies.FORGOTTEN.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(ItemRegistrar.ANCIENT_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.02f)).withResult(result -> result.withResult(Items.ANCIENT_DEBRIS))).build());
  }
}
