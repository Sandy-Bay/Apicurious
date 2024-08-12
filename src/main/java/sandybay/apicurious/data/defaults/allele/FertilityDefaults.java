package sandybay.apicurious.data.defaults.allele;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.common.bee.genetic.allele.Fertility;

public class FertilityDefaults
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(Fertility.LOW_FERTILITY, fertility(1, true, "low"));
    bootstrap.register(Fertility.AVERAGE_FERTILITY, fertility(2, true, "average"));
    bootstrap.register(Fertility.HIGH_FERTILITY, fertility(3, false, "high"));
    bootstrap.register(Fertility.MAXIMUM_FERTILITY, fertility(4, false, "maximum"));
  }

  private static Fertility fertility(int offspring, boolean isDominantTrait, String name)
  {
    return new Fertility(offspring, isDominantTrait, "apicurious.fertility." + name);
  }
}
