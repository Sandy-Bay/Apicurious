package sandybay.apicurious.data.defaults.mutation.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.common.bee.ApicuriousSpecies;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class HeroicMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.HEROIC.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.STEADFAST.species())
                    .withSecond(ApicuriousSpecies.VALIANT.species())
                    .withChance(0.06f)
                    .withOutput(ApicuriousSpecies.HEROIC.species())
                    .build()
    );
  }
}
