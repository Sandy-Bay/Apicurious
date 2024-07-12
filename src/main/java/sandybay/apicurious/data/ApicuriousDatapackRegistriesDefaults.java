package sandybay.apicurious.data;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.species.BeeSpecies;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousHolder;
import sandybay.apicurious.common.bee.ApicuriousSpecies;

public class ApicuriousDatapackRegistriesDefaults {

  public static RegistrySetBuilder registerDataPackRegistryDefaults() {
    RegistrySetBuilder builder = new RegistrySetBuilder();
    builder.add(ApicuriousRegistries.BEE_SPECIES, ApicuriousDatapackRegistriesDefaults::registerBeeSpeciesDefaults);
    return builder;
  }

  public static void registerBeeSpeciesDefaults(BootstrapContext<BeeSpecies> bootstrap) {
    register(bootstrap,
            ApicuriousSpecies.FOREST, ApicuriousSpecies.MEADOW, ApicuriousSpecies.COMMON, ApicuriousSpecies.CULTIVATED
    );
  }

  @SafeVarargs
  private static <T> void register(BootstrapContext<T> bootstrap, ApicuriousHolder<T>... holders) {
    for (ApicuriousHolder<T> holder : holders) {
      bootstrap.register(holder.key(), holder.value());
    }
  }


}
