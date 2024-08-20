package sandybay.apicurious.data.defaults.mutation.line.dye;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.neoforged.neoforge.common.Tags;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.BiomeCondition;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class PrimaryMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.MAROON.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.FOREST.species())
                    .withSecond(ApicuriousSpecies.VALIANT.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.MAROON.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.SAFFRON.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.MEADOW.species())
                    .withSecond(ApicuriousSpecies.VALIANT.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.SAFFRON.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.PRUSSIAN.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.WATER.species())
                    .withSecond(ApicuriousSpecies.VALIANT.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.PRUSSIAN.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.NATURAL.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.TROPICAL.species())
                    .withSecond(ApicuriousSpecies.VALIANT.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.NATURAL.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.SEPIA.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.MARSHY.species())
                    .withSecond(ApicuriousSpecies.VALIANT.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.SEPIA.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.BLEACHED.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.WINTRY.species())
                    .withSecond(ApicuriousSpecies.VALIANT.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.BLEACHED.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.EBONY.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.ROCKY.species())
                    .withSecond(ApicuriousSpecies.VALIANT.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.EBONY.species())
                    .build()
    );
  }
}
