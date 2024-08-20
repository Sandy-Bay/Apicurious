package sandybay.apicurious.data.defaults.allele.species;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.data.defaults.allele.species.dye.PrimarySpecies;
import sandybay.apicurious.data.defaults.allele.species.dye.SecondarySpecies;
import sandybay.apicurious.data.defaults.allele.species.dye.TertiarySpecies;

public class DyeSpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    PrimarySpecies.defaults(bootstrap);
    SecondarySpecies.defaults(bootstrap);
    TertiarySpecies.defaults(bootstrap);
  }
}
