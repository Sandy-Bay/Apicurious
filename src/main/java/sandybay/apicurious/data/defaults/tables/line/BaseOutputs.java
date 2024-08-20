package sandybay.apicurious.data.defaults.tables.line;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

public class BaseOutputs
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.TROPICAL.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.SILKY_COMB, 0.2f));
    bootstrap.register(ApicuriousSpecies.WINTRY.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.FROZEN_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.MARSHY.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.MOSSY_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.ROCKY.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.ROCKY_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.NETHER.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.SIMMERING_COMB, 0.25f));


  }
}
