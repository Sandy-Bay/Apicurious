package sandybay.apicurious.data.defaults.tables;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Item;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.data.defaults.branch.*;
import sandybay.apicurious.data.defaults.tables.line.*;

public class OutputTableDefaults
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    AgrarianBranch.outputsDefaults(bootstrap);
    ApisBranch.outputsDefaults(bootstrap);
    AquaticBranch.outputsDefaults(bootstrap);
    AustereBranch.outputsDefaults(bootstrap);
    BarrenBranch.outputsDefaults(bootstrap);
  }

  public static OutputTable.Builder custom()
  {
    return OutputTable.builder();
  }

  public static OutputTable simpleCombTable(Holder<Item> item)
  {
    return custom().withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(item.value()))).build();
  }

  public static OutputTable simpleCombTable(Holder<Item> item, float chance)
  {
    return custom().withPool(pool -> pool.withRolls(1).when(new ChanceCondition(chance)).withResult(entry -> entry.withResult(item.value()))).build();
  }
}
