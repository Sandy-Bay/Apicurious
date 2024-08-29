package sandybay.apicurious.common.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.output.OutputPool;
import sandybay.apicurious.api.bee.output.OutputPoolEntry;
import sandybay.apicurious.api.bee.output.OutputResult;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.compat.jei.ApicuriousRecipeTypes;
import sandybay.apicurious.common.item.BeeItem;
import sandybay.apicurious.common.registrar.ItemRegistrar;

public class BeeOutputCategory implements IRecipeCategory<BeeOutputCategory.Recipe>
{
  public static final Component TITLE = Component.translatable("apicurious.jei.bee_outputs.title");
  private final IGuiHelper iGuiHelper;

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
    return iGuiHelper
            .drawableBuilder(Apicurious.createResourceLocation("textures/gui/jei/bee_output.png"), 0, 0, 90, 96)
            .setTextureSize(90, 96)
            .build();
  }

  @Override
  public @Nullable IDrawable getIcon()
  {
    return iGuiHelper.createDrawableItemStack(
            BeeItem.getBeeWithSpecies(
                    Minecraft.getInstance().level,
                    ApicuriousSpecies.FOREST.species(),
                    ItemRegistrar.QUEEN
            )
    );
  }

  @Override
  public @NotNull RecipeType<Recipe> getRecipeType()
  {
    return ApicuriousRecipeTypes.BEE_OUTPUTS;
  }

  @Override
  public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull Recipe recipe, @NotNull IFocusGroup focuses)
  {
    builder.addSlot(RecipeIngredientRole.INPUT, 38, 4).addIngredients(Ingredient.of(recipe.input));
    int x = 0;
    int y = 0;
    for (OutputPool pool : recipe.output().pools())
    {
      for (OutputPoolEntry entry : pool.entries())
      {
        for (OutputResult result : entry.outputs())
        {
          builder.addSlot(RecipeIngredientRole.OUTPUT, 20 + x * 18, 40 + y * 18)
                  .addIngredients(Ingredient.of(result.output()))
                  .addRichTooltipCallback((view, tooltip) -> {
                    if (!pool.conditions().isEmpty())
                    {
                      for (ICondition condition : pool.conditions())
                      {
                        tooltip.add(Component.literal("- ").append(condition.getDisplayText()));
                      }
                    }
                    if (!entry.conditions().isEmpty())
                    {
                      for (ICondition condition : entry.conditions())
                      {
                        tooltip.add(Component.literal("- ").append(condition.getDisplayText()));
                      }
                    }
                  });
          x++;
          if (x == 3)
          {
            y++;
            x = 0;
          }
          if (y == 3)
          {
            return;
          }
        }
      }
    }
  }

  public record Recipe(ItemStack input, OutputTable output) {}
}
