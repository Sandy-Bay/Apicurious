package sandybay.apicurious.data.defaults.allele;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.common.bee.genetic.allele.Area;

public class AreaDefaults
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(Area.SMALLEST, area(1, 1, true, "smallest"));
    bootstrap.register(Area.SMALLER, area(2, 2, true, "smaller"));
    bootstrap.register(Area.SMALL, area(3, 3, true, "small"));
    bootstrap.register(Area.AVERAGE, area(4, 4, true, "average"));
    bootstrap.register(Area.LARGE, area(5, 5, false, "large"));
    bootstrap.register(Area.LARGER, area(6, 6, false, "larger"));
    bootstrap.register(Area.LARGEST, area(7, 7, false, "largest"));
  }

  private static Area area(int xzOffset, int yOffset, boolean isDominantTrait, String name)
  {
    return new Area(xzOffset, yOffset, isDominantTrait, "apicurious.area." + name);
  }
}
