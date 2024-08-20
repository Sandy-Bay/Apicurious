package sandybay.apicurious.data.defaults.mutation.line.dye;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.neoforged.neoforge.common.Tags;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.BiomeCondition;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class TertiaryMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.ASHEN.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.SLATE.species())
                    .withSecond(ApicuriousSpecies.BLEACHED.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.ASHEN.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.FUCHSIA.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.INDIGO.species())
                    .withSecond(ApicuriousSpecies.LAVENDER.species())
                    .withChance(0.05f)
                    .withOutput(ApicuriousSpecies.FUCHSIA.species())
                    .build()
    );
  }
}
