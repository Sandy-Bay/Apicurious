package sandybay.apicurious.api.bee.output.condition.impl;

import sandybay.apicurious.api.bee.output.OutputContext;
import sandybay.apicurious.api.bee.output.condition.OutputCondition;
import sandybay.apicurious.api.bee.output.condition.OutputConditionType;

public class ItemStackCondition implements OutputCondition {



  @Override
  public OutputConditionType getType() {
    return null;
  }

  @Override
  public boolean test(OutputContext context) {
    return false;
  }
}
