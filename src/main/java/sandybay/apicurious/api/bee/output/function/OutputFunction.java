package sandybay.apicurious.api.bee.output.function;

import net.minecraft.world.item.ItemStack;
import sandybay.apicurious.api.bee.output.OutputContext;

import java.util.List;
import java.util.function.BiFunction;

public interface OutputFunction extends BiFunction<OutputContext, List<ItemStack>, List<ItemStack>> {
  // TODO: Figure out how to do typed codecs for this...

  default List<ItemStack> resolve(OutputContext context, List<ItemStack> stacks) {
    return this.apply(context, stacks);
  }

  OutputFunctionType getType();
}
