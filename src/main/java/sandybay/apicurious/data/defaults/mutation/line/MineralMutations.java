package sandybay.apicurious.data.defaults.mutation.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.common.bee.ApicuriousSpecies;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class MineralMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.LAZULI.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.RESILIENT.species())
                    .withSecond(ApicuriousSpecies.WATER.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.LAZULI.species())
                    .build()
    );
  }
}
