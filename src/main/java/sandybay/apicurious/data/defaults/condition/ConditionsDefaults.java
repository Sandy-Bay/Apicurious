package sandybay.apicurious.data.defaults.condition;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.common.bee.condition.DateCondition;

import java.time.LocalDate;

public class ConditionsDefaults
{
  public static void defaults(BootstrapContext<ICondition> bootstrap)
  {
    bootstrap.register(ConditionKeys.IS_CHRISTMAS, new DateCondition(
            LocalDate.of(1970, 12, 23),
            LocalDate.of(1970, 12, 26)
    ));
    bootstrap.register(ConditionKeys.IS_NEW_YEARS, new DateCondition(
            LocalDate.of(1970, 12, 27),
            LocalDate.of(1970, 1, 2)
    ));
    bootstrap.register(ConditionKeys.IS_HALLOWEEN, new DateCondition(
            LocalDate.of(1970, 10, 15),
            LocalDate.of(1970, 11, 3)
    ));
  }
}
