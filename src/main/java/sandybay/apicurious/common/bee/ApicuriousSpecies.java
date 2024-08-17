package sandybay.apicurious.common.bee;

import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class ApicuriousSpecies
{

  // Debug
  public static final KeyHolder EMPTY = species("undefined");
  public static final KeyHolder DEBUG = species("debug");
  // Base Types
  public static final KeyHolder FOREST = species("forest");
  public static final KeyHolder MEADOW = species("meadow");
  public static final KeyHolder MODEST = species("modest");
  public static final KeyHolder TROPICAL = species("tropical");
  public static final KeyHolder WINTRY = species("wintry");
  public static final KeyHolder MARSHY = species("marshy");
  public static final KeyHolder ROCKY = species("rocky");
  public static final KeyHolder WATER = species("water");
  public static final KeyHolder NETHER = species("nether");
  public static final KeyHolder ENDER = species("ender");
  public static final KeyHolder VALIANT = species("valiant");
  public static final KeyHolder STEADFAST = species("steadfast");

  /// Bee-Lines:
  // Common
  public static final KeyHolder COMMON = species("common");
  public static final KeyHolder CULTIVATED = species("cultivated");
  // Noble
  public static final KeyHolder NOBLE = species("noble");
  public static final KeyHolder MAJESTIC = species("majestic");
  public static final KeyHolder IMPERIAL = species("imperial");
  // Diligent
  public static final KeyHolder DILIGENT = species("diligent");
  public static final KeyHolder UNWEARY = species("unweary");
  public static final KeyHolder INDUSTRIOUS = species("industrious");
  // Agrarian
  public static final KeyHolder RURAL = species("rural");
  public static final KeyHolder FARMERLY = species("farmed");
  public static final KeyHolder AGRARIAN = species("agrarian");
  // Festive
  public static final KeyHolder LEPORINE = species("leporine");
  public static final KeyHolder MERRY = species("merry");
  // Wooden
  public static final KeyHolder WOODEN = species("wooden");
  public static final KeyHolder LUMBERED = species("lumbered");
  public static final KeyHolder TIMBERED = species("timbered");
  // Heroic
  public static final KeyHolder HEROIC = species("heroic");
  // Resilient
  public static final KeyHolder TOLERANT = species("tolerant");
  public static final KeyHolder ROBUST = species("robust");
  public static final KeyHolder RESILIENT = species("resilient");
  // Metallic
  public static final KeyHolder CUPRUM = species("cuprum");
  public static final KeyHolder FERRUS = species("ferrus");
  public static final KeyHolder AURUM = species("aurum");
  // Mineral
  public static final KeyHolder LAZULI = species("lazuli");
  public static final KeyHolder ENERGETIC = species("energetic");
  // Gemstone
  public static final KeyHolder DIAMANTINE = species("diamantine");
  public static final KeyHolder EMERALDINE = species("emeraldine");

  private static KeyHolder species(String name)
  {
    return new KeyHolder(
            ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("species/" + name)),
            ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation(name)),
            ResourceKey.create(ApicuriousRegistries.OUTPUT_TABLES, Apicurious.createResourceLocation(name))
    );
  }

  public record KeyHolder(ResourceKey<IAllele<?>> species, ResourceKey<IMutation> mutation,
                          ResourceKey<OutputTable> output)
  {
  }
}
