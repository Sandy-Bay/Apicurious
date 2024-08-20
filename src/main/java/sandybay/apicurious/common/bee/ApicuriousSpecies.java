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

  /// Branches:
  // Agrarian
  public static final KeyHolder RURAL = species("rural");
  public static final KeyHolder FARMERLY = species("farmed");
  public static final KeyHolder AGRARIAN = species("agrarian");
  // Apis
  public static final KeyHolder FOREST = species("forest");
  public static final KeyHolder MEADOW = species("meadow");
  public static final KeyHolder COMMON = species("common");
  public static final KeyHolder CULTIVATED = species("cultivated");
  // Aquatic
  public static final KeyHolder WATER = species("water");
  public static final KeyHolder RIVER = species("river");
  public static final KeyHolder OCEAN = species("ocean");
  public static final KeyHolder STAINED = species("stained");
  // Austere
  public static final KeyHolder MODEST = species("modest");
  public static final KeyHolder FRUGAL = species("frugal");
  public static final KeyHolder AUSTERE = species("austere");
  public static final KeyHolder HAZARDOUS = species("hazardous");
  // Barren
  public static final KeyHolder ARID = species("arid");
  public static final KeyHolder BARREN = species("barren");
  public static final KeyHolder DESOLATE = species("desolate");
  public static final KeyHolder GNAWING = species("gnawing");
  public static final KeyHolder DECOMPOSING = species("decomposing");
  // Boggy
  public static final KeyHolder MARSHY = species("marshy");
  public static final KeyHolder DAMP = species("damp");
  public static final KeyHolder BOGGY = species("boggy");
  public static final KeyHolder FUNGAL = species("fungal");
  public static final KeyHolder MIRY = species("miry");
  // Dye
  /// Primary
  public static final KeyHolder MAROON = species("maroon");
  public static final KeyHolder SAFFRON = species("saffron");
  public static final KeyHolder PRUSSIAN = species("prussian");
  public static final KeyHolder NATURAL = species("natural");
  public static final KeyHolder SEPIA = species("sepia");
  public static final KeyHolder BLEACHED = species("bleached");
  public static final KeyHolder EBONY = species("ebony");
  /// Secondary
  public static final KeyHolder AMBER = species("amber");
  public static final KeyHolder TURQUOISE = species("turquoise");
  public static final KeyHolder INDIGO = species("indigo");
  public static final KeyHolder SLATE = species("slate");
  public static final KeyHolder AZURE = species("azure");
  public static final KeyHolder LAVENDER = species("lavender");
  public static final KeyHolder LIME = species("lime");
  /// Tertiary
  public static final KeyHolder ASHEN = species("ashen");
  public static final KeyHolder FUCHSIA = species("fuchsia");
  // End
  public static final KeyHolder ENDER = species("ender");
  public static final KeyHolder SPECTRAL = species("spectral");
  public static final KeyHolder PHANTASMAL = species("phantasmal");
  // Energetic
  public static final KeyHolder EXCITED = species("excited");
  public static final KeyHolder ENERGETIC = species("energetic");
  public static final KeyHolder ECSTATIC = species("ecstatic");
  // Festive
  public static final KeyHolder LEPORINE = species("leporine");
  public static final KeyHolder MERRY = species("merry");
  public static final KeyHolder TIPSY = species("tispy");
  public static final KeyHolder CELEBRATORY = species("celebratory");
  public static final KeyHolder TRICKY = species("tricky");
  // Infernal
  public static final KeyHolder SINISTER = species("sinister");
  public static final KeyHolder FIENDISH = species("fiendish");
  public static final KeyHolder DEMONIC = species("demonic");
  // Monastic
  public static final KeyHolder MONASTIC = species("monastic");
  public static final KeyHolder SECLUDED = species("secluded");
  public static final KeyHolder HERMITIC = species("hermitic");
  // Fossilised
  public static final KeyHolder FOSSILISED = species("fossilised");
  public static final KeyHolder FORGOTTEN = species("forgotten");
  // Historic
  public static final KeyHolder ANCIENT = species("ancient");
  public static final KeyHolder PRIMEVAL = species("primeval");
  public static final KeyHolder PREHISTORIC = species("prehistoric");
  public static final KeyHolder RELIC = species("relic");
  // Frozen
  public static final KeyHolder ICY = species("icy");
  public static final KeyHolder GLACIAL = species("glacial");
  public static final KeyHolder FRIGID = species("frigid");
  public static final KeyHolder ABSOLUTE = species("absolute");





  // Base Types
  public static final KeyHolder TROPICAL = species("tropical");
  public static final KeyHolder WINTRY = species("wintry");
  public static final KeyHolder ROCKY = species("rocky");
  public static final KeyHolder NETHER = species("nether");
  public static final KeyHolder VALIANT = species("valiant");
  public static final KeyHolder STEADFAST = species("steadfast");
  // Noble
  public static final KeyHolder NOBLE = species("noble");
  public static final KeyHolder MAJESTIC = species("majestic");
  public static final KeyHolder IMPERIAL = species("imperial");
  // Diligent
  public static final KeyHolder DILIGENT = species("diligent");
  public static final KeyHolder UNWEARY = species("unweary");
  public static final KeyHolder INDUSTRIOUS = species("industrious");


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
