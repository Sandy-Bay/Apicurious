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

public class GemstoneBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.DIAMANTINE.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.DIAMANTINE.species(), "diamantine")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.DIAMANTINE))
                    .withProductionData(builder -> builder
                            .withFertility(Fertility.LOW_FERTILITY)
                            .withLifespan(Lifespan.SHORT)
                            .withPollinationRate(Pollination.SLOWEST)
                            .withProductionSpeed(Speed.SLOWEST)
                            .withWorkCycle(Workcycle.ALWAYS))
                    .withEnvironmentalData(builder -> builder
                            .withFlowers(Flowers.STONE)
                            .withHumidityTolerance(HumidityTolerance.LOW_TOLERANCE)
                            .withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)
                            .ignoresSky()
                            .ignoresRain())
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.DIAMANTINE.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.EMERALDINE.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.EMERALDINE.species(), "emeraldine")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.EMERALDINE))
                    .withProductionData(builder -> builder
                            .withFertility(Fertility.LOW_FERTILITY)
                            .withLifespan(Lifespan.SHORT)
                            .withPollinationRate(Pollination.SLOWEST)
                            .withProductionSpeed(Speed.SLOWEST)
                            .withWorkCycle(Workcycle.ALWAYS))
                    .withEnvironmentalData(builder -> builder
                            .withFlowers(Flowers.STONE)
                            .withHumidityTolerance(HumidityTolerance.LOW_TOLERANCE)
                            .withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)
                            .ignoresSky()
                            .ignoresRain())
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.EMERALDINE.output()))
                    .build()
    );
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.DIAMANTINE.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.LAZULI.species())
                    .withSecond(ApicuriousSpecies.CULTIVATED.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.DIAMANTINE.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.EMERALDINE.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.LAZULI.species())
                    .withSecond(ApicuriousSpecies.FOREST.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.EMERALDINE.species())
                    .build()
    );
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.DIAMANTINE.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.ROCKY_COMB.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.01f))
                    .withResult(result -> result.withResult(ItemRegistrar.DIAMOND_COMB.get()))
            )
            .build()
    );
    bootstrap.register(ApicuriousSpecies.EMERALDINE.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.ROCKY_COMB.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.04f))
                    .withResult(result -> result.withResult(ItemRegistrar.EMERALD_COMB.get()))
            )
            .build()
    );
  }
}
