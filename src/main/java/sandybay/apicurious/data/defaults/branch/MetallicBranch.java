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
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class MetallicBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.CUPRUM.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.CUPRUM.species(), "cuprum")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.CUPRUM))
                    .withProductionData(builder -> builder
                            .withFertility(Fertility.LOW_FERTILITY)
                            .withLifespan(Lifespan.SHORTER)
                            .withPollinationRate(Pollination.SLOWEST)
                            .withProductionSpeed(Speed.SLOWEST)
                            .withWorkCycle(Workcycle.ALWAYS))
                    .withEnvironmentalData(builder -> builder
                            .withFlowers(Flowers.STONE)
                            .ignoresSky())
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.CUPRUM.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.FERRUS.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.FERRUS.species(), "ferrus")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.FERRUS))
                    .withProductionData(builder -> builder
                            .withFertility(Fertility.LOW_FERTILITY)
                            .withLifespan(Lifespan.SHORTER)
                            .withPollinationRate(Pollination.SLOWEST)
                            .withProductionSpeed(Speed.SLOWEST)
                            .withWorkCycle(Workcycle.ALWAYS))
                    .withEnvironmentalData(builder -> builder
                            .withFlowers(Flowers.STONE)
                            .ignoresSky())
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.FERRUS.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.AURUM.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.AURUM.species(), "aurum")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.AURUM))
                    .withProductionData(builder -> builder
                            .withFertility(Fertility.LOW_FERTILITY)
                            .withLifespan(Lifespan.SHORTER)
                            .withPollinationRate(Pollination.SLOWEST)
                            .withProductionSpeed(Speed.SLOWEST)
                            .withWorkCycle(Workcycle.ALWAYS))
                    .withEnvironmentalData(builder -> builder
                            .withFlowers(Flowers.STONE)
                            .ignoresSky())
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.AURUM.output()))
                    .build()
    );
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.CUPRUM.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.RESILIENT.species())
                    .withSecond(ApicuriousSpecies.FOREST.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.CUPRUM.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.FERRUS.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.RESILIENT.species())
                    .withSecond(ApicuriousSpecies.MEADOW.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.FERRUS.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.AURUM.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.CUPRUM.species())
                    .withSecond(ApicuriousSpecies.IMPERIAL.species())
                    .withChance(0.02f)
                    .withOutput(ApicuriousSpecies.AURUM.species())
                    .build()
    );
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.CUPRUM.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.ROCKY_COMB.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.06f))
                    .withResult(result -> result.withResult(ItemRegistrar.COPPER_COMB.get()))
            ).build()
    );
    bootstrap.register(ApicuriousSpecies.FERRUS.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.ROCKY_COMB.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.05f))
                    .withResult(result -> result.withResult(ItemRegistrar.IRON_COMB.get()))
            ).build()
    );
    bootstrap.register(ApicuriousSpecies.AURUM.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.ROCKY_COMB.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.02f))
                    .withResult(result -> result.withResult(ItemRegistrar.GOLD_COMB.get()))
            ).build()
    );
  }
}
