package sandybay.apicurious.common.compat.jei;

import mezz.jei.api.recipe.RecipeType;
import sandybay.apicurious.Apicurious;

public class ApicuriousRecipeTypes
{
    public static final RecipeType<BeeOutputCategory.Recipe> OUTPUTS = RecipeType.create(Apicurious.MODID, "outputs", BeeOutputCategory.Recipe.class);

}
