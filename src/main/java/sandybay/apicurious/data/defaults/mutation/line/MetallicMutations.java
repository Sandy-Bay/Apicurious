package sandybay.apicurious.data.defaults.mutation.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.common.bee.ApicuriousSpecies;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class MetallicMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
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
}
