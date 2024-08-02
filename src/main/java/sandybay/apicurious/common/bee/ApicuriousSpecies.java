package sandybay.apicurious.common.bee;

import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.BeeSpecies;

public class ApicuriousSpecies
{

  // Debug
  public static final ResourceKey<BeeSpecies> EMPTY = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("undefined"));
  public static final ResourceKey<BeeSpecies> DEBUG = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("debug"));
  // Base Types
  public static final ResourceKey<BeeSpecies> FOREST = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("forest"));
  public static final ResourceKey<BeeSpecies> MEADOW = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("meadow"));
  public static final ResourceKey<BeeSpecies> MODEST = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("modest"));
  public static final ResourceKey<BeeSpecies> TROPICAL = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("tropical"));
  public static final ResourceKey<BeeSpecies> WINTRY = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("wintry"));
  public static final ResourceKey<BeeSpecies> MARSHY = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("marshy"));
  public static final ResourceKey<BeeSpecies> ROCKY = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("rocky"));
  public static final ResourceKey<BeeSpecies> NETHER = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("nether"));
  public static final ResourceKey<BeeSpecies> ENDER = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("ender"));
  // Bee Lines:
  public static final ResourceKey<BeeSpecies> COMMON = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("common"));
  public static final ResourceKey<BeeSpecies> CULTIVATED = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("cultivated"));
  public static final ResourceKey<BeeSpecies> IMPERIAL = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("imperial"));
  public static final ResourceKey<BeeSpecies> INDUSTRIOUS = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("industrious"));
  public static final ResourceKey<BeeSpecies> AUSTERE = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("austere"));

  public static void load()
  {
  }

}
