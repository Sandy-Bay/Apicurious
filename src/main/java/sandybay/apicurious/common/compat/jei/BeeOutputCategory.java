package sandybay.apicurious.common.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.registrar.ItemRegistrar;

import java.util.List;

public class BeeOutputCategory implements IRecipeCategory<BeeOutputCategory.Recipe>
{
    public static final Component TITLE = Component.translatable("bee.output.title");
    IGuiHelper iGuiHelper;

    public BeeOutputCategory(final IGuiHelper helper)
    {
        this.iGuiHelper = helper;
    }

    @Override
    public @NotNull Component getTitle()
    {
        return TITLE;
    }

    @Override
    public @NotNull IDrawable getBackground()
    {
        return iGuiHelper.drawableBuilder(ResourceLocation.fromNamespaceAndPath(Apicurious.MODID, "textures/gui/output.png"),
                0, 0, 82, 54).addPadding(0, 20, 0, 0).build();
    }

    @Override
    public @Nullable IDrawable getIcon()
    {
        return iGuiHelper.createDrawableItemStack(new ItemStack(ItemRegistrar.QUEEN.get()));
    }

    @Override
    public @NotNull RecipeType<Recipe> getRecipeType()
    {
        return ApicuriousRecipeTypes.OUTPUTS;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull Recipe recipe, @NotNull IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 15).addIngredients(Ingredient.of(recipe.input));
    }

    public record Recipe(ItemStack input, List<ItemStack> outputs) { }
}
