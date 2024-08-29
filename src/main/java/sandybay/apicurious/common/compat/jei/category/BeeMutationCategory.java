package sandybay.apicurious.common.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.compat.jei.ApicuriousRecipeTypes;
import sandybay.apicurious.common.item.BeeItem;
import sandybay.apicurious.common.registrar.ItemRegistrar;

import java.text.DecimalFormat;
import java.util.List;

public class BeeMutationCategory implements IRecipeCategory<BeeMutationCategory.Recipe>
{
  public static final Component TITLE = Component.translatable("apicurious.jei.bee_mutations.title");
  private final IGuiHelper iGuiHelper;

  public BeeMutationCategory(final IGuiHelper helper)
  {
    this.iGuiHelper = helper;
  }

  @Override
  public RecipeType<Recipe> getRecipeType()
  {
    return ApicuriousRecipeTypes.BEE_MUTATIONS;
  }

  @Override
  public Component getTitle()
  {
    return TITLE;
  }

  @Override
  public IDrawable getBackground()
  {
    return iGuiHelper
            .drawableBuilder(Apicurious.createResourceLocation("textures/gui/jei/bee_mutation.png"), 0, 0, 116, 18)
            .setTextureSize(116, 18)
            .build();
  }

  @Override
  public @Nullable IDrawable getIcon()
  {
    return iGuiHelper.createDrawableItemStack(
            BeeItem.getBeeWithSpecies(
                    Minecraft.getInstance().level,
                    ApicuriousSpecies.MEADOW.species(),
                    ItemRegistrar.QUEEN
            )
    );
  }

  @Override
  public void setRecipe(IRecipeLayoutBuilder builder, Recipe recipe, IFocusGroup focuses)
  {
      builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(Ingredient.of(recipe.first.toArray(new ItemStack[0])));
      builder.addSlot(RecipeIngredientRole.INPUT, 50, 1).addIngredients(Ingredient.of(recipe.second.toArray(new ItemStack[0])));
      builder.addSlot(RecipeIngredientRole.INPUT, 99, 1).addIngredients(Ingredient.of(recipe.output))
              .addRichTooltipCallback((view, tooltip) -> {
                tooltip.add(Component.literal(""));
                tooltip.add(Component.translatable("apicurious.condition.chance").withStyle(ChatFormatting.DARK_AQUA).append(Component.literal(DecimalFormat.getPercentInstance().format(recipe.chance)).withStyle(ChatFormatting.WHITE)));
                if (!recipe.conditions.isEmpty())
                {
                  tooltip.add(Component.translatable("apicurious.jei.tooltip.conditions").withStyle(ChatFormatting.GOLD));
                  for (ICondition condition : recipe.conditions)
                  {
                    tooltip.add(condition.getDisplayText());
                  }
                }
              });
  }

  public record Recipe(List<ItemStack> first, List<ItemStack> second, float chance, List<ICondition> conditions, ItemStack output) { }
}
