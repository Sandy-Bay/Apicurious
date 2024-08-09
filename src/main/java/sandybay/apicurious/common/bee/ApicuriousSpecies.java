package sandybay.apicurious.common.bee;

import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class ApicuriousSpecies
{

  // Debug
  public static final ResourceKey<IAllele<?>> EMPTY = species("undefined");
  public static final ResourceKey<IAllele<?>> DEBUG = species("debug");
  // Base Types
  public static final ResourceKey<IAllele<?>> FOREST = species("forest");
  public static final ResourceKey<IAllele<?>> MEADOW = species("meadow");
  public static final ResourceKey<IAllele<?>> MODEST = species("modest");
  public static final ResourceKey<IAllele<?>> TROPICAL = species("tropical");
  public static final ResourceKey<IAllele<?>> WINTRY = species("wintry");
  public static final ResourceKey<IAllele<?>> MARSHY = species("marshy");
  public static final ResourceKey<IAllele<?>> ROCKY = species("rocky");
  public static final ResourceKey<IAllele<?>> NETHER = species("nether");
  public static final ResourceKey<IAllele<?>> ENDER = species("ender");
  // Bee Lines:
  public static final ResourceKey<IAllele<?>> COMMON = species("common");
  public static final ResourceKey<IAllele<?>> CULTIVATED = species("cultivated");
  public static final ResourceKey<IAllele<?>> IMPERIAL = species("imperial");
  public static final ResourceKey<IAllele<?>> INDUSTRIOUS = species("industrious");
  public static final ResourceKey<IAllele<?>> AUSTERE = species("austere");

  private static ResourceKey<IAllele<?>> species(String name)
  {
    return ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("species/" + name));
  }
}
