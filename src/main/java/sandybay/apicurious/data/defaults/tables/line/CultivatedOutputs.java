package sandybay.apicurious.data.defaults.tables.line;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

public class CultivatedOutputs
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.COMMON.output(), OutputTableDefaults.simpleCombTable(Items.HONEYCOMB.builtInRegistryHolder(), 0.35f));
    bootstrap.register(ApicuriousSpecies.CULTIVATED.output(), OutputTableDefaults.simpleCombTable(Items.HONEYCOMB.builtInRegistryHolder(), 0.4f));
  }
}
