package sandybay.apicurious.api.bee.output;

import net.minecraft.world.item.ItemStack;
import org.apache.commons.compress.utils.Lists;
import sandybay.apicurious.api.bee.output.condition.OutputCondition;
import sandybay.apicurious.api.bee.output.function.OutputFunction;

import java.util.List;

public class OutputPool {
  //public static final Codec<OutputPool> CODEC;

  private final int rolls;
  private final List<OutputPoolEntry> entries;
  private final List<OutputCondition> conditions;
  private final List<OutputFunction> functions;

  OutputPool(List<OutputPoolEntry> entries, List<OutputCondition> conditions, List<OutputFunction> functions) {
    this(1, entries, conditions, functions);
  }

  OutputPool(int rolls, List<OutputPoolEntry> entries, List<OutputCondition> conditions, List<OutputFunction> functions) {
    this.rolls = rolls;
    this.entries = entries;
    this.conditions = conditions;
    this.functions = functions;
  }

  public List<ItemStack> generate(OutputContext context) {
    List<ItemStack> output = Lists.newArrayList();
    if (conditions.stream().allMatch(c -> c.test(context))) {
      for (float i = 0; i < rolls; i++) {
        List<List<ItemStack>> entryResults = entries.stream().map(e -> e.generate(context)).toList();
        for (List<ItemStack> result : entryResults) {
          output.addAll(result);
        }
      }
      for (OutputFunction function : functions) {
        output = function.resolve(context, output);
      }
    }
    return output;
  }

  public List<OutputPoolEntry> getEntries() {
    return entries;
  }

  public List<OutputCondition> getConditions() {
    return conditions;
  }

  public List<OutputFunction> getFunctions() {
    return functions;
  }

}
