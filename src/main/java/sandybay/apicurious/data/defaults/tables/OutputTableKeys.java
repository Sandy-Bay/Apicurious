package sandybay.apicurious.data.defaults.tables;

import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

import java.util.function.Consumer;

public class OutputTableKeys
{
  public static final ResourceKey<OutputTable> STANDARD_OUTPUT = output("standard");
  public static final ResourceKey<OutputTable> PARCHED_OUTPUT = output("modest");
  public static final ResourceKey<OutputTable> SILKY_OUTPUT = output("silky");
  public static final ResourceKey<OutputTable> FROZEN_OUTPUT = output("frozen");
  public static final ResourceKey<OutputTable> MOSSY_OUTPUT = output("mossy");
  public static final ResourceKey<OutputTable> ROCKY_OUTPUT = output("rocky");
  public static final ResourceKey<OutputTable> SIMMERING_OUTPUT = output("simmering");
  public static final ResourceKey<OutputTable> MYSTERIOUS_OUTPUT = output("mysterious");
  public static final ResourceKey<OutputTable> DRIPPING_OUTPUT = output("dripping");
  public static final ResourceKey<OutputTable> IMPERIAL_OUTPUT = output("imperial");
  public static final ResourceKey<OutputTable> STRINGY_OUTPUT = output("stringy");
  public static final ResourceKey<OutputTable> INDUSTRIOUS_OUTPUT = output("industrious");
  public static final ResourceKey<OutputTable> WHEATEN_OUTPUT = output("wheaten");
  public static final ResourceKey<OutputTable> FARMED_OUTPUT = output("farmed");
  public static final ResourceKey<OutputTable> LEPORINE_OUTPUT = output("leporine");
  public static final ResourceKey<OutputTable> MERRY_OUTPUT = output("merry");
  public static final ResourceKey<OutputTable> DUSTY_OUTPUT = output("dusty");
  public static final ResourceKey<OutputTable> COCOA_OUTPUT = output("cocoa");
  public static final ResourceKey<OutputTable> VALIANT_OUTPUT = output("valiant");

  public static ResourceKey<OutputTable> output(String name)
  {
    return ResourceKey.create(ApicuriousRegistries.OUTPUT_TABLES, Apicurious.createResourceLocation(name));
  }
}
