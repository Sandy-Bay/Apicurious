package sandybay.apicurious.data.defaults.branch;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class InfernalBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.SINISTER.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.SINISTER.species(), "sinister")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.SINISTER))
                    .withProductionData(builder -> builder
                            .withProductionSpeed(Speed.SLOWER)
                            .withWorkCycle(Workcycle.ALWAYS))
                    .withEnvironmentalData(builder -> builder
                            .withFlowers(Flowers.NETHER_STONE)
                            .withHumidityPreference(HumidityPreference.ARID)
                            .withTemperaturePreference(TemperaturePreference.HELLISH)
                            .withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)
                    )
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.SINISTER.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.FIENDISH.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.FIENDISH.species(), "fiendish")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.FIENDISH))
                    .withProductionData(builder -> builder
                            .withLifespan(Lifespan.LONG)
                            .withWorkCycle(Workcycle.ALWAYS))
                    .withEnvironmentalData(builder -> builder
                            .withFlowers(Flowers.NETHER_STONE)
                            .withHumidityPreference(HumidityPreference.ARID)
                            .withTemperaturePreference(TemperaturePreference.HELLISH)
                            .withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)
                    )
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.MODEST.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.DEMONIC.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.DEMONIC.species(), "demonic")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.DEMONIC).hasEffect())
                    .withProductionData(builder -> builder
                            .withLifespan(Lifespan.LONGER)
                            .withProductionSpeed(Speed.SLOWER)
                            .withWorkCycle(Workcycle.ALWAYS))
                    .withEnvironmentalData(builder -> builder
                            .withFlowers(Flowers.NETHER_STONE)
                            .withHumidityPreference(HumidityPreference.ARID)
                            .withTemperaturePreference(TemperaturePreference.HELLISH)
                            .withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)
                    )
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.DEMONIC.output()))
                    .build()
    );
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("sinister_modest")),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.MODEST.species())
                    .withSecond(ApicuriousSpecies.CULTIVATED.species())
                    .withChance(0.6f)
                    .withOutput(ApicuriousSpecies.SINISTER.species())
                    .build()
    );
    bootstrap.register(ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("sinister_tropical")),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.TROPICAL.species())
                    .withSecond(ApicuriousSpecies.CULTIVATED.species())
                    .withChance(0.6f)
                    .withOutput(ApicuriousSpecies.SINISTER.species())
                    .build()
    );
    bootstrap.register(ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("fiendish_cultivated")),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.CULTIVATED.species())
                    .withSecond(ApicuriousSpecies.SINISTER.species())
                    .withChance(0.4f)
                    .withOutput(ApicuriousSpecies.FIENDISH.species())
                    .build()
    );
    bootstrap.register(ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("fiendish_modest")),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.MODEST.species())
                    .withSecond(ApicuriousSpecies.SINISTER.species())
                    .withChance(0.4f)
                    .withOutput(ApicuriousSpecies.FIENDISH.species())
                    .build()
    );
    bootstrap.register(ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("fiendish_tropical")),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.TROPICAL.species())
                    .withSecond(ApicuriousSpecies.SINISTER.species())
                    .withChance(0.4f)
                    .withOutput(ApicuriousSpecies.FIENDISH.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.DEMONIC.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.SINISTER.species())
                    .withSecond(ApicuriousSpecies.FIENDISH.species())
                    .withChance(0.25f)
                    .withOutput(ApicuriousSpecies.DEMONIC.species())
                    .build()
    );
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.SINISTER.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.SIMMERING_COMB, 0.45f));
    bootstrap.register(ApicuriousSpecies.FIENDISH.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.15f))
                    .withResult(result -> result.withResult(ItemRegistrar.ASH.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.55f))
                    .withResult(result -> result.withResult(ItemRegistrar.SIMMERING_COMB.get()))
            ).build()
    );
    bootstrap.register(ApicuriousSpecies.DEMONIC.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.15f))
                    .withResult(result -> result.withResult(Items.GLOWSTONE_DUST))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.45f))
                    .withResult(result -> result.withResult(ItemRegistrar.SIMMERING_COMB.get()))
            ).build()
    );
  }
}
