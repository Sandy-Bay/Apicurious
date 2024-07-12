package sandybay.apicurious.api.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.species.BeeSpecies;

public class ApicuriousRegistries {

  public static final ResourceKey<Registry<BeeSpecies>> BEE_SPECIES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("bee_species_registry"));

  public static void registerDatapackRegistries(final DataPackRegistryEvent.NewRegistry event) {
    event.dataPackRegistry(BEE_SPECIES, BeeSpecies.CODEC);
  }


}
