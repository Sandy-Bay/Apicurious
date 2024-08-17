package sandybay.apicurious.data.defaults.mutation.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.common.bee.ApicuriousSpecies;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class ImperialMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.NOBLE.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.COMMON.species())
                    .withSecond(ApicuriousSpecies.CULTIVATED.species())
                    .withChance(0.1f)
                    .withOutput(ApicuriousSpecies.NOBLE.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.MAJESTIC.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.NOBLE.species())
                    .withSecond(ApicuriousSpecies.CULTIVATED.species())
                    .withChance(0.08f)
                    .withOutput(ApicuriousSpecies.MAJESTIC.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.IMPERIAL.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.NOBLE.species())
                    .withSecond(ApicuriousSpecies.MAJESTIC.species())
                    .withChance(0.08f)
                    .withOutput(ApicuriousSpecies.IMPERIAL.species())
                    .build()
    );
  }
}
