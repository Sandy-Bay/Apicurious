package sandybay.apicurious.data.defaults.tables.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.data.defaults.tables.line.dye.PrimaryOutputs;
import sandybay.apicurious.data.defaults.tables.line.dye.SecondaryOutputs;
import sandybay.apicurious.data.defaults.tables.line.dye.TertiaryOutputs;

public class DyeOutputs
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    PrimaryOutputs.defaults(bootstrap);
    SecondaryOutputs.defaults(bootstrap);
    TertiaryOutputs.defaults(bootstrap);
  }
}
