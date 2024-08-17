package sandybay.apicurious.data.defaults.mutation.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.common.bee.ApicuriousSpecies;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class ResilientMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.TOLERANT.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.DILIGENT.species())
                    .withSecond(ApicuriousSpecies.ROCKY.species())
                    .withChance(0.15f)
                    .withOutput(ApicuriousSpecies.TOLERANT.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.ROBUST.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.UNWEARY.species())
                    .withSecond(ApicuriousSpecies.TOLERANT.species())
                    .withChance(0.15f)
                    .withOutput(ApicuriousSpecies.ROBUST.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.RESILIENT.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.INDUSTRIOUS.species())
                    .withSecond(ApicuriousSpecies.ROBUST.species())
                    .withChance(0.15f)
                    .withOutput(ApicuriousSpecies.RESILIENT.species())
                    .build()
    );
  }
}
