package sandybay.apicurious.data.defaults.allele.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.data.defaults.allele.line.dye.PrimarySpecies;
import sandybay.apicurious.data.defaults.allele.line.dye.SecondarySpecies;
import sandybay.apicurious.data.defaults.allele.line.dye.TertiarySpecies;

public class DyeSpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    PrimarySpecies.defaults(bootstrap);
    SecondarySpecies.defaults(bootstrap);
    TertiarySpecies.defaults(bootstrap);
  }
}
