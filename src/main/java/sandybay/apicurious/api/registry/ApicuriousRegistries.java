package sandybay.apicurious.api.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.AlleleType;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.genetic.mutation.MutationType;
import sandybay.apicurious.api.bee.genetic.mutation.condition.IMutationCondition;
import sandybay.apicurious.api.bee.genetic.mutation.condition.MutationConditionType;

public class ApicuriousRegistries
{

  // Allele Registries
  public static final ResourceKey<Registry<AlleleType<?>>> ALLELE_TYPES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("allele_type"));
  public static final Registry<AlleleType<?>> ALLELE_TYPES_REGISTRY = new RegistryBuilder<>(ALLELE_TYPES).sync(true).create();
  public static final ResourceKey<Registry<IAllele<?>>> ALLELES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("alleles"));


  // INFO: Mutation Types exists for future expandability, allowing mod authors and ourselves to define new mutation types,
  //       with additional requirements outside just matching species and chance.
  // Mutation Registry
  public static final ResourceKey<Registry<MutationType>> MUTATION_TYPES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("mutation_types"));
  public static final Registry<MutationType> MUTATION_TYPE_REGISTRY = new RegistryBuilder<>(MUTATION_TYPES).sync(true).create();
  public static final ResourceKey<Registry<IMutation>> MUTATIONS = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("mutations"));
  // Mutation Condition
  public static final ResourceKey<Registry<MutationConditionType>> MUTATION_CONDITION_TYPES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("mutation_condition_types"));
  public static final Registry<MutationConditionType> MUTATION_CONDITION_TYPE_REGISTRY = new RegistryBuilder<>(MUTATION_CONDITION_TYPES).sync(true).create();
  public static final ResourceKey<Registry<IMutationCondition>> MUTATION_CONDITIONS = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("mutation_conditions"));

  public static void registerRegistries(final NewRegistryEvent event)
  {
    event.register(ALLELE_TYPES_REGISTRY);
    event.register(MUTATION_TYPE_REGISTRY);
    event.register(MUTATION_CONDITION_TYPE_REGISTRY);
  }

  public static void registerDatapackRegistries(final DataPackRegistryEvent.NewRegistry event)
  {
    event.dataPackRegistry(ALLELES, IAllele.TYPED_CODEC, IAllele.TYPED_CODEC);
    event.dataPackRegistry(MUTATIONS, IMutation.TYPED_CODEC, IMutation.TYPED_CODEC);
    event.dataPackRegistry(MUTATION_CONDITIONS, IMutationCondition.TYPED_CODEC, IMutationCondition.TYPED_CODEC);
  }
}
