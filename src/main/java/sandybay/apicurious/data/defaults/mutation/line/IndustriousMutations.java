package sandybay.apicurious.data.defaults.mutation.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.common.bee.ApicuriousSpecies;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class IndustriousMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.DILIGENT.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.COMMON.species())
                    .withSecond(ApicuriousSpecies.CULTIVATED.species())
                    .withChance(0.1f)
                    .withOutput(ApicuriousSpecies.DILIGENT.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.UNWEARY.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.DILIGENT.species())
                    .withSecond(ApicuriousSpecies.CULTIVATED.species())
                    .withChance(0.08f)
                    .withOutput(ApicuriousSpecies.UNWEARY.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.INDUSTRIOUS.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.DILIGENT.species())
                    .withSecond(ApicuriousSpecies.UNWEARY.species())
                    .withChance(0.08f)
                    .withOutput(ApicuriousSpecies.INDUSTRIOUS.species())
                    .build()
    );
  }
}
