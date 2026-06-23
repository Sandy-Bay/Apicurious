package sandybay.apicurious.common.compat.jei;

import mezz.jei.api.recipe.types.IRecipeType;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.compat.jei.category.BeeMutationCategory;
import sandybay.apicurious.common.compat.jei.category.BeeOutputCategory;
import sandybay.apicurious.common.compat.jei.category.CentrifugeCategory;

public class ApicuriousRecipeTypes
{
  public static final IRecipeType<BeeMutationCategory.Recipe> BEE_MUTATIONS = IRecipeType.create(Apicurious.MODID, "bee_mutations", BeeMutationCategory.Recipe.class);
  public static final IRecipeType<BeeOutputCategory.Recipe> BEE_OUTPUTS = IRecipeType.create(Apicurious.MODID, "bee_outputs", BeeOutputCategory.Recipe.class);
  public static final IRecipeType<CentrifugeCategory.Recipe> CENTRIFUGE = IRecipeType.create(Apicurious.MODID, "centrifuge", CentrifugeCategory.Recipe.class);
}
