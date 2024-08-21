package sandybay.apicurious.data.defaults.branch;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;
import sandybay.apicurious.data.defaults.OutputTableDefaults;

import static sandybay.apicurious.data.defaults.MutationDefaults.mutation;

public class ApisBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.FOREST.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.FOREST.species(), "forest")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.FOREST))
                    .withProductionData(builder -> builder
                            .withFertility(Fertility.HIGH_FERTILITY)
                            .withLifespan(Lifespan.SHORTER)
                            .withPollination(Pollination.SLOWER)
                            .withSpeed(Speed.SLOWEST))
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.FOREST.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.MEADOW.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MEADOW.species(), "meadow")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MEADOW))
                    .withProductionData(builder -> builder
                            .withLifespan(Lifespan.SHORTER)
                            .withPollination(Pollination.SLOWER)
                            .withSpeed(Speed.SLOWEST))
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.MEADOW.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.COMMON.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.COMMON.species(), "common")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.COMMON))
                    .withProductionData(builder -> builder
                            .withLifespan(Lifespan.SHORTER)
                            .withPollination(Pollination.SLOWEST)
                            .withSpeed(Speed.SLOWER))
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.COMMON.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.CULTIVATED.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.CULTIVATED.species(), "cultivated")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.CULTIVATED))
                    .withProductionData(builder -> builder
                            .withLifespan(Lifespan.SHORTEST)
                            .withPollination(Pollination.SLOWEST)
                            .withSpeed(Speed.FAST))
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.CULTIVATED.output()))
                    .build()
    );
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.COMMON.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousTags.AlleleTags.BASELINE_BEE)
                    .withSecond(ApicuriousTags.AlleleTags.BASELINE_BEE)
                    .withChance(0.15f)
                    .withOutput(ApicuriousSpecies.COMMON.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.CULTIVATED.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousTags.AlleleTags.BASELINE_BEE)
                    .withSecond(ApicuriousSpecies.COMMON.species())
                    .withChance(0.12f)
                    .withOutput(ApicuriousSpecies.CULTIVATED.species())
                    .build()
    );
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.FOREST.output(), OutputTableDefaults.simpleCombTable(Items.HONEYCOMB.builtInRegistryHolder(), 0.3f));
    bootstrap.register(ApicuriousSpecies.MEADOW.output(), OutputTableDefaults.simpleCombTable(Items.HONEYCOMB.builtInRegistryHolder(), 0.3f));
    bootstrap.register(ApicuriousSpecies.COMMON.output(), OutputTableDefaults.simpleCombTable(Items.HONEYCOMB.builtInRegistryHolder(), 0.35f));
    bootstrap.register(ApicuriousSpecies.CULTIVATED.output(), OutputTableDefaults.simpleCombTable(Items.HONEYCOMB.builtInRegistryHolder(), 0.4f));
  }
}
