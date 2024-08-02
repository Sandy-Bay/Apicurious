package sandybay.apicurious.api.bee.output.condition;

import sandybay.apicurious.api.bee.output.OutputContext;

import java.util.function.Predicate;

public interface OutputCondition extends Predicate<OutputContext> {
  // TODO: Figure out how to do typed codecs for this...

  default boolean validate(OutputContext context) {
    return this.test(context);
  }

  OutputConditionType getType();

}
