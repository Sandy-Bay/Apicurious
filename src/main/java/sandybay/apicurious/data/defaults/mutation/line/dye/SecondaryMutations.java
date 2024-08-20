package sandybay.apicurious.data.defaults.mutation.line.dye;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.neoforged.neoforge.common.Tags;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.BiomeCondition;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class SecondaryMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.AMBER.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.MAROON.species())
                    .withSecond(ApicuriousSpecies.SAFFRON.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.AMBER.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.TURQUOISE.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.NATURAL.species())
                    .withSecond(ApicuriousSpecies.PRUSSIAN.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.TURQUOISE.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.INDIGO.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.MAROON.species())
                    .withSecond(ApicuriousSpecies.PRUSSIAN.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.INDIGO.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.SLATE.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.EBONY.species())
                    .withSecond(ApicuriousSpecies.BLEACHED.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.SLATE.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.AZURE.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.PRUSSIAN.species())
                    .withSecond(ApicuriousSpecies.BLEACHED.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.AZURE.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.LAVENDER.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.MAROON.species())
                    .withSecond(ApicuriousSpecies.BLEACHED.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.LAVENDER.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.LIME.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.NATURAL.species())
                    .withSecond(ApicuriousSpecies.BLEACHED.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.LIME.species())
                    .build()
    );
  }
}
