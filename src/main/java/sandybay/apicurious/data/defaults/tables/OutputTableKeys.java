package sandybay.apicurious.data.defaults.tables;

import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.output.OutputData;

import java.util.function.Consumer;

public class OutputTableKeys
{
  public static final ResourceKey<OutputTable> STANDARD_OUTPUT = key("standard");
  public static final ResourceKey<OutputTable> PARCHED_OUTPUT = key("modest");
  public static final ResourceKey<OutputTable> SILKY_OUTPUT = key("silky");
  public static final ResourceKey<OutputTable> FROZEN_OUTPUT = key("frozen");
  public static final ResourceKey<OutputTable> MOSSY_OUTPUT = key("mossy");
  public static final ResourceKey<OutputTable> ROCKY_OUTPUT = key("rocky");
  public static final ResourceKey<OutputTable> SIMMERING_OUTPUT = key("simmering");
  public static final ResourceKey<OutputTable> MYSTERIOUS_OUTPUT = key("mysterious");
  public static final ResourceKey<OutputTable> DRIPPING_OUTPUT = key("dripping");
  public static final ResourceKey<OutputTable> IMPERIAL_OUTPUT = key("imperial");
  public static final ResourceKey<OutputTable> STRINGY_OUTPUT = key("stringy");
  public static final ResourceKey<OutputTable> INDUSTRIOUS_OUTPUT = key("industrious");
  public static final ResourceKey<OutputTable> WHEATEN_OUTPUT = key("wheaten");
  public static final ResourceKey<OutputTable> FARMED_OUTPUT = key("farmed");
  public static final ResourceKey<OutputTable> LEPORINE_OUTPUT = key("leporine");
  public static final ResourceKey<OutputTable> MERRY_OUTPUT = key("merry");

  public static ResourceKey<OutputTable> key(String name)
  {
    return ResourceKey.create(ApicuriousRegistries.OUTPUT_TABLES, Apicurious.createResourceLocation(name));
  }
}
