package sandybay.apicurious.data.defaults.tables;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Item;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.data.defaults.tables.line.*;

public class OutputTableDefaults
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    AgrarianOutputs.defaults(bootstrap);
    BaseOutputs.defaults(bootstrap);
    CultivatedOutputs.defaults(bootstrap);
    DyeOutputs.defaults(bootstrap);
    EcstaticOutputs.defaults(bootstrap);
    FestiveOutputs.defaults(bootstrap);
    GemstoneOutputs.defaults(bootstrap);
    HeroicOutputs.defaults(bootstrap);
    ImperialOutputs.defaults(bootstrap);
    IndustriousOutputs.defaults(bootstrap);
    MetallicOutputs.defaults(bootstrap);
    MineralOutputs.defaults(bootstrap);
    ResilientOutputs.defaults(bootstrap);
    TimberedOutputs.defaults(bootstrap);
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
