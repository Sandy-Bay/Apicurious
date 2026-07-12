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
  public int getWidth()
  {
    return 107;
  }

  @Override
  public int getHeight()
  {
    return 54;
  }

  @Override
  public void draw(CentrifugeCategory.Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY)
  {
    IDrawable background = iGuiHelper.drawableBuilder(Apicurious.createIdentifier("textures/gui/jei/centrifuge.png"), 0, 0, 107, 54)
            .setTextureSize(107, 54)
            .build();
    background.draw(guiGraphics);
  }

  @Override
  public @Nullable IDrawable getIcon()
  {
    return iGuiHelper.createDrawableItemStack(BlockRegistrar.CENTRIFUGE.asItemStack());
  }

  @Override
  public @NotNull IRecipeType<Recipe> getRecipeType()
  {
    return ApicuriousRecipeTypes.CENTRIFUGE;
  }

  @Override
  public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull CentrifugeCategory.Recipe recipe, @NotNull IFocusGroup focuses)
  {
    builder.addInputSlot(1, 17).add(recipe.input);
    int x = 52;
    int y = 1;
    int i = 0;
    int j = 0;
    for (CentrifugeRecipe.CentrifugeOutput output : recipe.output)
    {
      builder.addOutputSlot(x + j * 18, y + i * 18)
              .add(output.output().create())
              .addRichTooltipCallback((view, tooltip) -> {
                tooltip.add(Component.translatable("apicurious.condition.chance")
                        .append(NumberFormat.getPercentInstance().format(output.chance())));
              });
      j++;
      if (j == 3)
      {
        i++;
        j = 0;
      }
      if (i == 2)
      {
        return;
      }
    }
  }

  @Override
  public @Nullable Identifier getIdentifier(Recipe recipe)
  {
    return recipe.key.identifier();
  }

  public record Recipe(ResourceKey<CentrifugeRecipe> key, ItemStack input, int duration, List<CentrifugeRecipe.CentrifugeOutput> output) {}
}