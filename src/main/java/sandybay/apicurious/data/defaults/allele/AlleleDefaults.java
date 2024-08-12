package sandybay.apicurious.data.defaults.allele;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.data.defaults.allele.*;

public class AlleleDefaults
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    AreaDefaults.defaults(bootstrap);
    FertilityDefaults.defaults(bootstrap);
    FlowersDefaults.defaults(bootstrap);
    HumidityDefaults.defaults(bootstrap);
    LifespanDefaults.defaults(bootstrap);
    PollinationDefaults.defaults(bootstrap);
    SpeedDefaults.defaults(bootstrap);
    TemperatureDefaults.defaults(bootstrap);
    WorkcycleDefaults.defaults(bootstrap);
    SpeciesDefaults.defaults(bootstrap);
  }
}
