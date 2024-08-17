package sandybay.apicurious.data.defaults.mutation.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.common.bee.ApicuriousSpecies;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class CultivatedMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
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
}