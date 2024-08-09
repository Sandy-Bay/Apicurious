package sandybay.apicurious.api.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.AlleleType;
import sandybay.apicurious.api.bee.genetic.IAllele;

public class ApicuriousRegistries
{

  // Trait Registries
  public static final ResourceKey<Registry<AlleleType<?>>> TRAIT_TYPES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("trait_type"));
  public static final Registry<AlleleType<?>> TRAIT_TYPES_REGISTRY = new RegistryBuilder<>(TRAIT_TYPES).sync(true).create();
  public static final ResourceKey<Registry<IAllele<?>>> ALLELES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("alleles"));

  public static void registerRegistries(final NewRegistryEvent event)
  {
    event.register(TRAIT_TYPES_REGISTRY);
  }

  public static void registerDatapackRegistries(final DataPackRegistryEvent.NewRegistry event)
  {
    event.dataPackRegistry(ALLELES, IAllele.TYPED_CODEC, IAllele.TYPED_CODEC);
  }
}
