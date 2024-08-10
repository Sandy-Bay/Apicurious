package sandybay.apicurious.api.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.AlleleType;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.common.bee.genetic.mutation.Mutation;

public class ApicuriousRegistries
{

  // Allele Registries
  public static final ResourceKey<Registry<AlleleType<?>>> ALLELE_TYPES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("allele_type"));
  public static final Registry<AlleleType<?>> ALLELE_TYPES_REGISTRY = new RegistryBuilder<>(ALLELE_TYPES).sync(true).create();
  public static final ResourceKey<Registry<IAllele<?>>> ALLELES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("alleles"));

  // Mutation Registry
  public static final ResourceKey<Registry<Mutation>> MUTATIONS = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("mutations"));

  public static void registerRegistries(final NewRegistryEvent event)
  {
    event.register(ALLELE_TYPES_REGISTRY);
  }

  public static void registerDatapackRegistries(final DataPackRegistryEvent.NewRegistry event)
  {
    event.dataPackRegistry(ALLELES, IAllele.TYPED_CODEC, IAllele.TYPED_CODEC);
    event.dataPackRegistry(MUTATIONS, Mutation.CODEC, Mutation.CODEC);
  }
}
