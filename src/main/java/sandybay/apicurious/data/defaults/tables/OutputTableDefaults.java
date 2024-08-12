package sandybay.apicurious.data.defaults.tables;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.output.OutputResult;
import sandybay.apicurious.api.bee.output.OutputTable;

public class OutputTableDefaults
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(OutputTableKeys.STANDARD_OUTPUT, simpleCombTable(new ItemStack(Items.HONEYCOMB, 1)));
  }

  public static OutputTable.Builder custom()
  {
    return OutputTable.builder();
  }

  public static OutputTable simpleCombTable(ItemStack stack) {
    return custom().withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(new OutputResult(stack)))).build();
  }
}
