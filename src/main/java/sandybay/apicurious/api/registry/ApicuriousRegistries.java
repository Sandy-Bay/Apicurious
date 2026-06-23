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
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.condition.ConditionType;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.function.FunctionType;
import sandybay.apicurious.api.function.IFunction;
import sandybay.apicurious.api.recipe.CentrifugeRecipe;

public class ApicuriousRegistries
{

  // Allele Registries
  public static final ResourceKey<Registry<AlleleType<?>>> ALLELE_TYPES = ResourceKey.createRegistryKey(Apicurious.createIdentifier("allele_type"));
  public static final Registry<AlleleType<?>> ALLELE_TYPE_REGISTRY = new RegistryBuilder<>(ALLELE_TYPES).sync(true).create();
  public static final ResourceKey<Registry<IAllele<?>>> ALLELES = ResourceKey.createRegistryKey(Apicurious.createIdentifier("alleles"));

  // Mutation
  public static final ResourceKey<Registry<MutationType>> MUTATION_TYPES = ResourceKey.createRegistryKey(Apicurious.createIdentifier("mutation_types"));
  public static final Registry<MutationType> MUTATION_TYPE_REGISTRY = new RegistryBuilder<>(MUTATION_TYPES).sync(true).create();
  public static final ResourceKey<Registry<IMutation>> MUTATIONS = ResourceKey.createRegistryKey(Apicurious.createIdentifier("mutations"));

  // Conditions
  public static final ResourceKey<Registry<ConditionType>> CONDITION_TYPES = ResourceKey.createRegistryKey(Apicurious.createIdentifier("condition_types"));
  public static final Registry<ConditionType> CONDITION_TYPE_REGISTRY = new RegistryBuilder<>(CONDITION_TYPES).sync(true).create();
  public static final ResourceKey<Registry<ICondition>> CONDITIONS = ResourceKey.createRegistryKey(Apicurious.createIdentifier("conditions"));

  // Functions
  public static final ResourceKey<Registry<FunctionType>> FUNCTION_TYPES = ResourceKey.createRegistryKey(Apicurious.createIdentifier("function_types"));
  public static final Registry<FunctionType> FUNCTION_TYPE_REGISTRY = new RegistryBuilder<>(FUNCTION_TYPES).sync(true).create();
  public static final ResourceKey<Registry<IFunction>> FUNCTIONS = ResourceKey.createRegistryKey(Apicurious.createIdentifier("functions"));

  // OutputTables
  public static final ResourceKey<Registry<OutputTable>> OUTPUT_TABLES = ResourceKey.createRegistryKey(Apicurious.createIdentifier("output_tables"));

  // CentrifugeRecipes
  public static final ResourceKey<Registry<CentrifugeRecipe>> CENTRIFUGE_RECIPES = ResourceKey.createRegistryKey(Apicurious.createIdentifier("recipes/centrifuge"));

  public static void registerRegistries(final NewRegistryEvent event)
  {
    event.register(ALLELE_TYPE_REGISTRY);
    event.register(MUTATION_TYPE_REGISTRY);
    event.register(CONDITION_TYPE_REGISTRY);
    event.register(FUNCTION_TYPE_REGISTRY);
  }

  public static void registerDatapackRegistries(final DataPackRegistryEvent.NewRegistry event)
  {
    event.dataPackRegistry(ALLELES, IAllele.TYPED_CODEC, IAllele.TYPED_CODEC);
    event.dataPackRegistry(MUTATIONS, IMutation.TYPED_CODEC, IMutation.TYPED_CODEC);
    event.dataPackRegistry(CONDITIONS, ICondition.TYPED_CODEC, ICondition.TYPED_CODEC);
    event.dataPackRegistry(FUNCTIONS, IFunction.TYPED_CODEC, IFunction.TYPED_CODEC);
    event.dataPackRegistry(OUTPUT_TABLES, OutputTable.TYPED_CODEC, OutputTable.TYPED_CODEC);
    event.dataPackRegistry(CENTRIFUGE_RECIPES, CentrifugeRecipe.TYPED_CODEC, CentrifugeRecipe.TYPED_CODEC);
  }
}
