package sandybay.apicurious.data.defaults.tables;

import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class OutputTableKeys
{
  public static final ResourceKey<OutputTable> STANDARD_OUTPUT = key("standard");

  public static ResourceKey<OutputTable> key(String name)
  {
    return ResourceKey.create(ApicuriousRegistries.OUTPUT_TABLES, Apicurious.createResourceLocation(name));
  }
}
