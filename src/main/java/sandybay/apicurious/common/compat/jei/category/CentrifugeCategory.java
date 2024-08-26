package sandybay.apicurious.common.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.recipe.CentrifugeRecipe;
import sandybay.apicurious.common.compat.jei.ApicuriousRecipeTypes;
import sandybay.apicurious.common.registrar.BlockRegistrar;

import java.text.NumberFormat;
import java.util.List;

public class CentrifugeCategory implements IRecipeCategory<CentrifugeCategory.Recipe>
{
  public static final Component TITLE = Component.translatable("apicurious.jei.centrifuge.title");
  private final IGuiHelper iGuiHelper;

  public CentrifugeCategory(final IGuiHelper helper)
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
    return iGuiHelper
            .drawableBuilder(Apicurious.createResourceLocation("textures/gui/jei/centrifuge.png"), 0, 0, 107, 54)
            .setTextureSize(107, 54)
            .build();
  }

  @Override
  public @Nullable IDrawable getIcon()
  {
    return iGuiHelper.createDrawableItemStack(BlockRegistrar.CENTRIFUGE.asItemStack());
  }

  @Override
  public @NotNull RecipeType<CentrifugeCategory.Recipe> getRecipeType()
  {
    return ApicuriousRecipeTypes.CENTRIFUGE;
  }

  @Override
  public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull CentrifugeCategory.Recipe recipe, @NotNull IFocusGroup focuses)
  {
    builder.addSlot(RecipeIngredientRole.INPUT, 1, 17).addIngredients(Ingredient.of(recipe.input));
    int x = 54;
    int y = 1;
    int i = 0;
    int j = 0;
    for (CentrifugeRecipe.CentrifugeOutput output : recipe.output)
    {
      builder.addSlot(RecipeIngredientRole.OUTPUT, x + j * 18, y + i * 18).addIngredients(Ingredient.of(output.output().copy())).addRichTooltipCallback((view, tooltip) -> tooltip.add(Component.translatable("apicurious.jei.tooltip.chance").append(NumberFormat.getPercentInstance().format(output.chance()))));
      j++;
      if (j == 3)
      {
        i++;
        j = 0;
      }
    }
  }

  public record Recipe(ItemStack input, int duration, List<CentrifugeRecipe.CentrifugeOutput> output) {}
}
