package sandybay.apicurious.common.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.output.OutputPool;
import sandybay.apicurious.api.bee.output.OutputPoolEntry;
import sandybay.apicurious.api.bee.output.OutputResult;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.common.compat.jei.ApicuriousRecipeTypes;
import sandybay.apicurious.common.registrar.BlockRegistrar;

import java.util.List;

public class BeeOutputCategory implements IRecipeCategory<BeeOutputCategory.Recipe>
{
  public static final Component TITLE = Component.translatable("apicurious.jei.bee_outputs.title");
  private final IGuiHelper iGuiHelper;

  public BeeOutputCategory(IGuiHelper helper)
  {
    this.iGuiHelper = helper;
  }

  @Override
  public @NotNull Component getTitle()
  {
    return TITLE;
  }

  @Override
  public int getWidth()
  {
    return 90;
  }

  @Override
  public int getHeight()
  {
    return 96;
  }

  @Override
  public void draw(Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX,
                   double mouseY)
  {
    IDrawable background = iGuiHelper.drawableBuilder(Apicurious.createIdentifier("textures/gui/jei/bee_output.png"), 0, 0, 90, 96).setTextureSize(90, 96).build();
    background.draw(guiGraphics);
  }

  @Override
  public @Nullable IDrawable getIcon()
  {
    return iGuiHelper.createDrawableItemStack(new ItemStack(BlockRegistrar.APIARY.asItem()));
  }

  @Override
  public @Nullable Identifier getIdentifier(Recipe recipe)
  {
    return recipe.key.identifier();
  }

  @Override
  public @NotNull IRecipeType<Recipe> getRecipeType()
  {
    return ApicuriousRecipeTypes.BEE_OUTPUTS;
  }

  @Override
  public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull Recipe recipe, @NotNull IFocusGroup focuses)
  {
    // Queen/Princess/Drone morphs of the input species are all offered here
    // (instead of a single Drone stack) so that clicking any of them in JEI
    // or the player's inventory surfaces this output recipe.
    builder.addInputSlot(38, 4).addItemStacks(recipe.input);
    int x = 0;
    int y = 0;
    for (OutputPool pool : recipe.output().pools())
    {
      for (OutputPoolEntry entry : pool.entries())
      {
        for (OutputResult result : entry.outputs())
        {
          builder.addOutputSlot(20 + x * 18, 40 + y * 18).add(result.output().create()).addRichTooltipCallback((view, tooltip) ->
          {
            if (!pool.conditions().isEmpty())
            {
              for (ICondition condition : pool.conditions())
              {
                tooltip.add(condition.getDisplayText());
              }
            }
            if (!entry.conditions().isEmpty())
            {
              for (ICondition condition : entry.conditions())
              {
                tooltip.add(condition.getDisplayText());
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

  public record Recipe(ResourceKey<IAllele<?>> key, List<ItemStack> input, OutputTable output)
  {
  }
}