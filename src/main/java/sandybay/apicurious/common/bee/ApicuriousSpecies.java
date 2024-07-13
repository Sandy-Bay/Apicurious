package sandybay.apicurious.common.bee;

import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousHolder;

public class ApicuriousSpecies {

  public static final ApicuriousHolder<BeeSpecies> FOREST = species("forest", BeeSpecies.Builder.create().build());
  public static final ApicuriousHolder<BeeSpecies> MEADOW = species("meadow", BeeSpecies.Builder.create().build());
  public static final ApicuriousHolder<BeeSpecies> COMMON = species("common", BeeSpecies.Builder.create().build());
  public static final ApicuriousHolder<BeeSpecies> CULTIVATED = species("cultivated", BeeSpecies.Builder.create().build());


  public static ApicuriousHolder<BeeSpecies> species(String name, BeeSpecies species) {
    return new ApicuriousHolder<>(
            ResourceKey.create(ApicuriousRegistries.BEE_SPECIES, Apicurious.createResourceLocation(name)),
            species
    );
  }


}
