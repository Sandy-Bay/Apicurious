package sandybay.apicurious.common.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.Apicurious;

import java.util.ArrayList;
import java.util.List;

public class ApicuriousJeiPlugin implements IModPlugin
{
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Apicurious.MODID, "jei_plugin");

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registry)
    {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        registry.addRecipeCategories(new BeeOutputCategory(jeiHelpers.getGuiHelper()));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration)
    {
        registration.addRecipes(ApicuriousRecipeTypes.OUTPUTS, getOutputRecipes());
    }

    private List<BeeOutputCategory.Recipe> getOutputRecipes()
    {
        List<BeeOutputCategory.Recipe> recipes = new ArrayList<>();
        //TODO

        return recipes;
    }

    @Override
    public @NotNull ResourceLocation getPluginUid()
    {
        return ID;
    }
}
