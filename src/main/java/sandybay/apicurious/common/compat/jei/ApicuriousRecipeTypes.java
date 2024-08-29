package sandybay.apicurious.common.compat.jei;

import mezz.jei.api.recipe.RecipeType;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.compat.jei.category.BeeMutationCategory;
import sandybay.apicurious.common.compat.jei.category.BeeOutputCategory;
import sandybay.apicurious.common.compat.jei.category.CentrifugeCategory;

public class ApicuriousRecipeTypes
{
  public static final RecipeType<BeeMutationCategory.Recipe> BEE_MUTATIONS = RecipeType.create(Apicurious.MODID, "bee_mutations", BeeMutationCategory.Recipe.class);
  public static final RecipeType<BeeOutputCategory.Recipe> BEE_OUTPUTS = RecipeType.create(Apicurious.MODID, "bee_outputs", BeeOutputCategory.Recipe.class);
  public static final RecipeType<CentrifugeCategory.Recipe> CENTRIFUGE = RecipeType.create(Apicurious.MODID, "centrifuge", CentrifugeCategory.Recipe.class);
}
