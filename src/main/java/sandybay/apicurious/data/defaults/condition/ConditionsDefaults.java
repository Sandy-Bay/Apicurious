package sandybay.apicurious.data.defaults.condition;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.common.bee.condition.DateCondition;

import java.time.MonthDay;

public class ConditionsDefaults
{
  public static void defaults(BootstrapContext<ICondition> bootstrap)
  {
    bootstrap.register(ConditionKeys.IS_CHRISTMAS, new DateCondition(MonthDay.of(12, 24), MonthDay.of(12, 27)));
    bootstrap.register(ConditionKeys.IS_NEW_YEARS, new DateCondition(MonthDay.of(12, 31), MonthDay.of(1, 2)));
    bootstrap.register(ConditionKeys.IS_HALLOWEEN, new DateCondition(MonthDay.of(10, 15), MonthDay.of(11, 3)));
  }
}
