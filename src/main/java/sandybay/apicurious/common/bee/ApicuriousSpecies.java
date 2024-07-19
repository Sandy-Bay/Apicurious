package sandybay.apicurious.common.bee;

import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class ApicuriousSpecies {

  public static void load() {}

  public static final ResourceKey<BeeSpecies> EMPTY = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("undefined"));
  public static final ResourceKey<BeeSpecies> FOREST = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("forest"));
  public static final ResourceKey<BeeSpecies> MEADOW = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("meadow"));
  public static final ResourceKey<BeeSpecies> COMMON = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("common"));
  public static final ResourceKey<BeeSpecies> CULTIVATED = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("cultivated"));

  public static final ResourceKey<BeeSpecies> IMPERIAL = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("imperial"));
  public static final ResourceKey<BeeSpecies> INDUSTRIOUS = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("industrious"));
  public static final ResourceKey<BeeSpecies> AUSTERE = ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation("austere"));

}
