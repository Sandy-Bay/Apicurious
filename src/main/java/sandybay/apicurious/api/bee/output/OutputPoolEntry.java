package sandybay.apicurious.api.bee.output;

import net.minecraft.world.item.ItemStack;
import org.apache.commons.compress.utils.Lists;
import sandybay.apicurious.api.bee.output.condition.OutputCondition;
import sandybay.apicurious.api.bee.output.function.OutputFunction;

import java.util.List;

public class OutputPoolEntry {
  //public static final Codec<OutputPoolEntry> CODEC;

  private final List<OutputResult> outputs;
  private final List<OutputCondition> conditions;
  private final List<OutputFunction> functions;

  OutputPoolEntry(List<OutputResult> outputs, List<OutputCondition> conditions, List<OutputFunction> functions) {
    this.outputs = outputs;
    this.conditions = conditions;
    this.functions = functions;
  }

  public List<ItemStack> generate(OutputContext context) {
    List<ItemStack> stacks = Lists.newArrayList();
    if (conditions.stream().allMatch(c -> c.test(context))) {
      stacks = outputs.stream().map(OutputResult::getOutput).toList();
      for (OutputFunction function : functions) {
        stacks = function.resolve(context, stacks);
      }
    }
    return stacks;
  }

  public List<OutputCondition> getConditions() {
    return conditions;
  }

  public List<OutputFunction> getFunctions() {
    return functions;
  }

  public static OutputPoolEntry.Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private final List<OutputResult> outputs;
    private final List<OutputCondition> conditions;
    private final List<OutputFunction> functions;

    private Builder() {
      this.outputs = Lists.newArrayList();
      this.conditions = Lists.newArrayList();
      this.functions = Lists.newArrayList();
    }

    public Builder withResult(OutputResult output) {
      this.outputs.add(output);
      return this;
    }

    public Builder when(OutputCondition condition) {
      this.conditions.add(condition);
      return this;
    }

    public Builder withFunction(OutputFunction function) {
      this.functions.add(function);
      return this;
    }

    public OutputPoolEntry build() {
      return new OutputPoolEntry(this.outputs, this.conditions, this.functions);
    }


  }
}
