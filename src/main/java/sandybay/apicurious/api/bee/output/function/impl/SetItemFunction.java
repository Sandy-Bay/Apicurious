package sandybay.apicurious.api.bee.output.function.impl;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import sandybay.apicurious.api.bee.output.OutputContext;
import sandybay.apicurious.api.bee.output.condition.OutputCondition;
import sandybay.apicurious.api.bee.output.function.OutputFunction;
import sandybay.apicurious.api.bee.output.function.OutputFunctionType;

import java.util.List;

public class SetItemFunction implements OutputFunction {

  private final List<OutputCondition> conditions;
  private final Holder<Item> item;

  private SetItemFunction(List<OutputCondition> conditions, Holder<Item> item) {
    this.conditions = conditions;
    this.item = item;
  }

  @Override
  public OutputFunctionType getType() {
    return null;
  }

  @Override
  public List<ItemStack> apply(OutputContext context, List<ItemStack> itemStacks) {
    if (!conditions.stream().allMatch(c -> c.validate(context))) return itemStacks;
    for (ItemStack stack : itemStacks) {
      stack.transmuteCopy(this.item.value());
    }
    return itemStacks;
  }

}
