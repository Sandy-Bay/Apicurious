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
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;
import sandybay.apicurious.data.defaults.OutputTableDefaults;

import static sandybay.apicurious.data.defaults.MutationDefaults.mutation;

public class TimberedBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.WOODEN.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.WOODEN.species(), "wooden")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.WOODEN))
                    .withProductionData(builder -> builder
                            .withFertility(Fertility.HIGH_FERTILITY)
                            .withLifespan(Lifespan.SHORTER)
                            .withPollination(Pollination.SLOWER)
                            .withSpeed(Speed.SLOWEST))
                    .withEnvironmentalData(builder -> builder.withFlowers(Flowers.WOOD))
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.WOODEN.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.LUMBERED.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.LUMBERED.species(), "lumbered")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.LUMBERED))
                    .withProductionData(builder -> builder
                            .withFertility(Fertility.HIGH_FERTILITY)
                            .withLifespan(Lifespan.SHORTER)
                            .withPollination(Pollination.SLOWEST)
                            .withSpeed(Speed.SLOWEST))
                    .withEnvironmentalData(builder -> builder.withFlowers(Flowers.WOOD))
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.LUMBERED.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.TIMBERED.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.TIMBERED.species(), "timbered")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.TIMBERED))
                    .withProductionData(builder -> builder
                            .withFertility(Fertility.AVERAGE_FERTILITY)
                            .withLifespan(Lifespan.SHORTER)
                            .withPollination(Pollination.SLOWEST)
                            .withSpeed(Speed.SLOWEST))
                    .withEnvironmentalData(builder -> builder.withFlowers(Flowers.WOOD))
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.TIMBERED.output()))
                    .build()
    );
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.WOODEN.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.FOREST.species())
                    .withSecond(ApicuriousSpecies.DILIGENT.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.WOODEN.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.LUMBERED.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.WOODEN.species())
                    .withSecond(ApicuriousSpecies.DILIGENT.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.LUMBERED.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.TIMBERED.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.WOODEN.species())
                    .withSecond(ApicuriousSpecies.LUMBERED.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.TIMBERED.species())
                    .build()
    );
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.WOODEN.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.3f))
                    .withResult(result -> result.withResult(ItemRegistrar.DUSTY_COMB.get()))
            ).build()
    );
    bootstrap.register(ApicuriousSpecies.LUMBERED.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.3f))
                    .withResult(result -> result.withResult(ItemRegistrar.DUSTY_COMB.get()))
            ).build()
    );
    bootstrap.register(ApicuriousSpecies.TIMBERED.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.3f))
                    .withResult(result -> result.withResult(ItemRegistrar.DUSTY_COMB.get()))
            ).build()
    );
  }
}
