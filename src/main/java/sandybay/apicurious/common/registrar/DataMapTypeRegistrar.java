package sandybay.apicurious.common.registrar;

import net.neoforged.neoforge.registries.datamaps.DataMapType;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.function.IFunction;
import sandybay.apicurious.api.recipe.CentrifugeRecipe;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class DataMapTypeRegistrar
{
  public static final DataMapType<IAllele<?>, IAllele<?>> ALLELE_DATA_MAP_TYPE = DataMapType.builder(Apicurious.createIdentifier("dmt_allele"), ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).synced(IAllele.TYPED_CODEC, true).build();
  public static final DataMapType<IMutation, IMutation> MUTATION_DATA_MAP_TYPE = DataMapType.builder(Apicurious.createIdentifier("dmt_mutations"), ApicuriousRegistries.MUTATIONS, IMutation.TYPED_CODEC).synced(IMutation.TYPED_CODEC, true).build();
  public static final DataMapType<ICondition, ICondition> CONDITIONS_DATA_MAP_TYPE = DataMapType.builder(Apicurious.createIdentifier("dmt_conditions"), ApicuriousRegistries.CONDITIONS, ICondition.TYPED_CODEC).synced(ICondition.TYPED_CODEC, true).build();
  public static final DataMapType<IFunction, IFunction> FUNCTION_DATA_MAP_TYPE = DataMapType.builder(Apicurious.createIdentifier("dmt_functions"), ApicuriousRegistries.FUNCTIONS, IFunction.TYPED_CODEC).synced(IFunction.TYPED_CODEC, true).build();
  public static final DataMapType<OutputTable, OutputTable> OUTPUT_TABLE_DATA_MAP_TYPE = DataMapType.builder(Apicurious.createIdentifier("dmt_output_tables"), ApicuriousRegistries.OUTPUT_TABLES, OutputTable.TYPED_CODEC).synced(OutputTable.TYPED_CODEC, true).build();
  public static final DataMapType<CentrifugeRecipe, CentrifugeRecipe> CENTRIFUGE_RECIPES_DATA_MAP_TYPE = DataMapType.builder(Apicurious.createIdentifier("dmt_centrifuge_recipes"), ApicuriousRegistries.CENTRIFUGE_RECIPES, CentrifugeRecipe.TYPED_CODEC).synced(CentrifugeRecipe.TYPED_CODEC, true).build();

}
