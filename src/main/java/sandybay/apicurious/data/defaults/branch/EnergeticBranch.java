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

public class EnergeticBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.EXCITED.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.EXCITED.species(), "excited")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.EXCITED))
                    .withProductionData(builder -> builder
                            .withLifespan(Lifespan.SHORTER)
                            .withPollinationRate(Pollination.SLOWEST)
                            .withProductionSpeed(Speed.SLOWEST))
                    .withEnvironmentalData(builder -> builder
                            .withFlowers(Flowers.REDSTONE)
                            .ignoresSky())
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.EXCITED.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.ENERGETIC.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ENERGETIC.species(), "energetic")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ENERGETIC))
                    .withProductionData(builder -> builder
                            .withLifespan(Lifespan.SHORTER)
                            .withPollinationRate(Pollination.SLOWEST)
                            .withProductionSpeed(Speed.SLOWEST))
                    .withEnvironmentalData(builder -> builder
                            .withFlowers(Flowers.REDSTONE)
                            .ignoresSky())
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.EXCITED.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.ECSTATIC.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ECSTATIC.species(), "ecstatic")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ECSTATIC))
                    .withProductionData(builder -> builder
                            .withLifespan(Lifespan.SHORTER)
                            .withPollinationRate(Pollination.SLOWEST)
                            .withProductionSpeed(Speed.SLOWEST))
                    .withEnvironmentalData(builder -> builder
                            .withFlowers(Flowers.REDSTONE)
                            .ignoresSky())
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.EXCITED.output()))
                    .build()
    );
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.EXCITED.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.CULTIVATED.species())
                    .withSecond(ApicuriousSpecies.VALIANT.species())
                    .withChance(0.1f)
                    .withOutput(ApicuriousSpecies.EXCITED.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.ENERGETIC.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.EXCITED.species())
                    .withSecond(ApicuriousSpecies.DILIGENT.species())
                    .withChance(0.08f)
                    .withOutput(ApicuriousSpecies.ENERGETIC.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.ECSTATIC.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.EXCITED.species())
                    .withSecond(ApicuriousSpecies.ENERGETIC.species())
                    .withChance(0.08f)
                    .withOutput(ApicuriousSpecies.ECSTATIC.species())
                    .build()
    );
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.EXCITED.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.ENERGETIC_COMB, 0.1f));
    bootstrap.register(ApicuriousSpecies.ENERGETIC.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.ENERGETIC_COMB, 0.12f));
    bootstrap.register(ApicuriousSpecies.ECSTATIC.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.ENERGETIC_COMB.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.08f))
                    .withResult(result -> result.withResult(ItemRegistrar.STATIC_COMB.get()))
            )
            .build()
    );
  }
}
