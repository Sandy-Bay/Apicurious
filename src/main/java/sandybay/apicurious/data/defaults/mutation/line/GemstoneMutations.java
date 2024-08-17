package sandybay.apicurious.data.defaults.mutation.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.common.bee.ApicuriousSpecies;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class GemstoneMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
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
}
