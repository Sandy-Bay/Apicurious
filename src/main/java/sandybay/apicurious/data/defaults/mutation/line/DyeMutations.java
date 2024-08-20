package sandybay.apicurious.data.defaults.mutation.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.data.defaults.mutation.line.dye.PrimaryMutations;
import sandybay.apicurious.data.defaults.mutation.line.dye.SecondaryMutations;
import sandybay.apicurious.data.defaults.mutation.line.dye.TertiaryMutations;

public class DyeMutations
{
  public static void defaults(BootstrapContext<IMutation> bootstrap)
  {
    PrimaryMutations.defaults(bootstrap);
    SecondaryMutations.defaults(bootstrap);
    TertiaryMutations.defaults(bootstrap);
  }
}
