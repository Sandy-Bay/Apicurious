package sandybay.apicurious.data.defaults.allele;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.AlleleNaming;
import sandybay.apicurious.common.bee.genetic.allele.Speed;

public class SpeedDefaults
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(Speed.SLOWEST, speed(1.7f, false, "slowest"));
    bootstrap.register(Speed.SLOWER, speed(1.4f, false, "slower"));
    bootstrap.register(Speed.SLOW, speed(1.2f, true, "slow"));
    bootstrap.register(Speed.AVERAGE, speed(1.0f, true, "average"));
    bootstrap.register(Speed.FAST, speed(0.7f, true, "fast"));
    bootstrap.register(Speed.FASTER, speed(0.4f, false, "faster"));
    bootstrap.register(Speed.FASTEST, speed(0.2f, false, "fastest"));
  }

  private static Speed speed(float productionModifier, boolean isDominantTrait, String name)
  {
    return new Speed(productionModifier, isDominantTrait, AlleleNaming.key("speed", name));
  }
}
