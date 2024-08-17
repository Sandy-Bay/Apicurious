package sandybay.apicurious.data.defaults.tables.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

public class AgrarianOutputs
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.RURAL.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.WHEATEN_COMB, 0.2f));
    bootstrap.register(ApicuriousSpecies.FARMERLY.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.WHEATEN_COMB, 0.27f));
    bootstrap.register(ApicuriousSpecies.AGRARIAN.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.WHEATEN_COMB, 0.35f));
  }
}
