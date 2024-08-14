package sandybay.apicurious.data.defaults.allele;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.common.bee.genetic.allele.Lifespan;

public class LifespanDefaults
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(Lifespan.SHORTEST, lifespan(10, false, "shortest"));
    bootstrap.register(Lifespan.SHORTER, lifespan(20, false, "shorter"));
    bootstrap.register(Lifespan.SHORT, lifespan(30, false, "short"));
    bootstrap.register(Lifespan.SHORTENED, lifespan(35, true, "shortened"));
    bootstrap.register(Lifespan.AVERAGE, lifespan(40, true, "average"));
    bootstrap.register(Lifespan.ELONGATED, lifespan(45, true, "elongated"));
    bootstrap.register(Lifespan.LONG, lifespan(50, false, "long"));
    bootstrap.register(Lifespan.LONGER, lifespan(60, false, "longer"));
    bootstrap.register(Lifespan.LONGEST, lifespan(70, false, "longest"));
  }

  private static Lifespan lifespan(int cycles, boolean isDominantTrait, String name)
  {
    return new Lifespan(cycles, isDominantTrait, "apicurious.lifespan." + name);
  }
}
