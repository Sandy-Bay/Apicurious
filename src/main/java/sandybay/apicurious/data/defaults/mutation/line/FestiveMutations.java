package sandybay.apicurious.data.defaults.mutation.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.data.defaults.condition.ConditionKeys;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class FestiveMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
  {
    /*
    bootstrap.register(ApicuriousSpecies.LEPORINE.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.FOREST)
                    .withSecond(ApicuriousSpecies.MEADOW)
                    .withChance(0.1f)
                    //.withCondition() TODO: Figure out how to do date conditions that respect date changes...
                    .withOutput(ApicuriousSpecies.LEPORINE)
                    .build()
    );
     */
    bootstrap.register(ApicuriousSpecies.MERRY.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.FOREST.species())
                    .withSecond(ApicuriousSpecies.WINTRY.species())
                    .withChance(0.1f)
                    .withCondition(ConditionKeys.IS_CHRISTMAS)
                    .withOutput(ApicuriousSpecies.MERRY.species())
                    .build()
    );
  }
}
