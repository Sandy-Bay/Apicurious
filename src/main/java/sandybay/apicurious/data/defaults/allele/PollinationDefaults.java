package sandybay.apicurious.data.defaults.allele;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.AlleleNaming;
import sandybay.apicurious.common.bee.genetic.allele.Pollination;

public class PollinationDefaults
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(Pollination.SLOWEST, pollination(0.05f, false, "slowest"));
    bootstrap.register(Pollination.SLOWER, pollination(0.1f, false, "slower"));
    bootstrap.register(Pollination.SLOW, pollination(0.15f, true, "slow"));
    bootstrap.register(Pollination.AVERAGE, pollination(0.2f, true, "average"));
    bootstrap.register(Pollination.FAST, pollination(0.25f, true, "fast"));
    bootstrap.register(Pollination.FASTER, pollination(0.3f, false, "faster"));
    bootstrap.register(Pollination.FASTEST, pollination(0.35f, false, "fastest"));
  }

  private static Pollination pollination(float pollinationChance, boolean isDominantTrait, String name)
  {
    return new Pollination(pollinationChance, isDominantTrait, AlleleNaming.key("pollination", name));
  }
}
