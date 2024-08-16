package sandybay.apicurious.data.defaults.mutation.line;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.neoforged.neoforge.common.Tags;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.BiomeCondition;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class WoodenMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.WOODEN.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.FOREST.species())
                    .withSecond(ApicuriousSpecies.DILIGENT.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.WOODEN.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.LUMBERED.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.WOODEN.species())
                    .withSecond(ApicuriousSpecies.DILIGENT.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.LUMBERED.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.TIMBERED.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.WOODEN.species())
                    .withSecond(ApicuriousSpecies.LUMBERED.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.TIMBERED.species())
                    .build()
    );
  }
}
