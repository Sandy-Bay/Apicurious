package sandybay.apicurious.data.defaults.mutation.line;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.neoforged.neoforge.common.Tags;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.BiomeCondition;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class AgrarianMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.RURAL.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.MEADOW.species())
                    .withSecond(ApicuriousSpecies.DILIGENT.species())
                    .withChance(0.12f)
                    .withCondition(
                            new BiomeCondition(bootstrap.lookup(Registries.BIOME).getOrThrow(Tags.Biomes.IS_PLAINS))
                    )
                    .withOutput(ApicuriousSpecies.RURAL.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.FARMED.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.CULTIVATED.species())
                    .withSecond(ApicuriousSpecies.RURAL.species())
                    .withChance(0.1f)
                    .withOutput(ApicuriousSpecies.FARMED.species())
                    .build()
    );

  }
}
