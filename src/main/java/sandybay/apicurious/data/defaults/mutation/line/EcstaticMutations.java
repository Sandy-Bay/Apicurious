package sandybay.apicurious.data.defaults.mutation.line;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.neoforged.neoforge.common.Tags;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.BiomeCondition;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class EcstaticMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.EXCITED.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.CULTIVATED.species())
                    .withSecond(ApicuriousSpecies.VALIANT.species())
                    .withChance(0.1f)
                    .withOutput(ApicuriousSpecies.EXCITED.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.ENERGETIC.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.EXCITED.species())
                    .withSecond(ApicuriousSpecies.DILIGENT.species())
                    .withChance(0.08f)
                    .withOutput(ApicuriousSpecies.ENERGETIC.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.ECSTATIC.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.EXCITED.species())
                    .withSecond(ApicuriousSpecies.ENERGETIC.species())
                    .withChance(0.08f)
                    .withOutput(ApicuriousSpecies.ECSTATIC.species())
                    .build()
    );
  }
}
