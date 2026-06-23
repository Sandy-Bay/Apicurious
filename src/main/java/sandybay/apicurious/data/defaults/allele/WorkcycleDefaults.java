package sandybay.apicurious.data.defaults.allele;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.common.bee.genetic.allele.Workcycle;

import java.util.List;

public class WorkcycleDefaults
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(Workcycle.MATUTINAL, workcycle(List.of(new Workcycle.Interval(4000, 10000)), true, "matutinal"));
    bootstrap.register(Workcycle.DIURNAL, workcycle(List.of(new Workcycle.Interval(6000, 18000)), true, "diurnal"));
    bootstrap.register(Workcycle.VESPERTINAL, workcycle(List.of(new Workcycle.Interval(14000, 20000)), true, "vespertinal"));
    bootstrap.register(Workcycle.NOCTURNAL, workcycle(List.of(new Workcycle.Interval(18000, 24000), new Workcycle.Interval(0, 6000)), true, "nocturnal"));
    bootstrap.register(Workcycle.ALWAYS, workcycle(List.of(new Workcycle.Interval(0, 24000)), false, "always"));
  }

  private static Workcycle workcycle(List<Workcycle.Interval> activeTimes, boolean isDominantTrait, String name)
  {
    return new Workcycle(activeTimes, isDominantTrait, "apicurious.workcycle." + name);
  }
}
