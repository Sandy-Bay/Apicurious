package sandybay.apicurious.common.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.mutation.Mutation;
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
  public IRecipeType<Recipe> getRecipeType()
  {
    return ApicuriousRecipeTypes.BEE_MUTATIONS;
  }

  @Override
  public Component getTitle()
  {
    return TITLE;
  }

  @Override
  public int getWidth()
  {
    return 116;
  }

  @Override
  public int getHeight()
  {
    return 18;
  }

  @Override
  public void draw(Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY)
  {
    IDrawable background = iGuiHelper.drawableBuilder(Apicurious.createIdentifier("textures/gui/jei/bee_mutation.png"), 0, 0, 116, 18)
            .setTextureSize(116, 18)
            .build();
    background.draw(guiGraphics);
  }

  @Override
  public @Nullable IDrawable getIcon()
  {
    return iGuiHelper.createDrawableItemStack(BeeItem.getBeeWithSpecies(Minecraft.getInstance().level, ApicuriousSpecies.FOREST.species(), ItemRegistrar.QUEEN.item()));
  }

  @Override
  public @Nullable Identifier getIdentifier(Recipe recipe)
  {
    return recipe.key.identifier();
  }

  @Override
  public void setRecipe(IRecipeLayoutBuilder builder, Recipe recipe, IFocusGroup focuses)
  {
    builder.addInputSlot(1, 1).addItemStacks(recipe.first);
    builder.addInputSlot(50, 1).addItemStacks(recipe.second);
    builder.addOutputSlot(99, 1).add(recipe.output).addRichTooltipCallback((view, tooltip) ->
    {
      tooltip.add(Component.literal(""));
      tooltip.add(Component.translatable("apicurious.condition.chance").withStyle(ChatFormatting.DARK_AQUA)
              .append(Component.literal(DecimalFormat.getPercentInstance().format(recipe.chance)).withStyle(ChatFormatting.WHITE)));
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

  public record Recipe(ResourceKey<IMutation> key, List<ItemStack> first, List<ItemStack> second, float chance, List<ICondition> conditions,
                       ItemStack output) {}
}
