package sandybay.apicurious.client.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2f;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.item.BeeItem;
import sandybay.apicurious.common.menu.AnalyzerMenu;
import sandybay.apicurious.common.registrar.ItemRegistrar;

public class AnalyzerScreen extends AbstractContainerScreen<@NotNull AnalyzerMenu>
{

  public static final Identifier SCREEN_LOCATION = Apicurious.createIdentifier("textures/gui/analyzer.png");
  private int currentPage;
  private PageButton forwardButton;
  private PageButton backButton;

  private ItemStack queen;
  private ItemStack princess;
  private ItemStack drone;


  public AnalyzerScreen(AnalyzerMenu pMenu, Inventory pPlayerInventory, Component pTitle)
  {
    super(pMenu, pPlayerInventory, pTitle, 230, 219);
  }

  @Override
  protected void init()
  {
    super.init();
    this.forwardButton = this.addRenderableWidget(new PageButton(this.leftPos + 160, this.topPos + 112, true, btn -> this.pageForward(), false));
    this.backButton = this.addRenderableWidget(new PageButton(this.leftPos + 50, this.topPos + 112, false, btn -> this.pageBack(), false));
    this.currentPage = 0;
    updateButtonVisibility();
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a)
  {
    this.extractBackground(graphics, mouseX, mouseY, a);
    super.extractRenderState(graphics, mouseX, mouseY, a);
    this.extractTooltip(graphics, mouseX, mouseY);
    if (this.menu.hasIdentifiedBee())
    {
      renderInformation(graphics);
      renderBees(graphics);
    }
    updateButtonVisibility();
  }

  @Override
  protected void extractLabels(GuiGraphicsExtractor graphics, int pMouseX, int pMouseY)
  {}

  @Override
  public void extractBackground(GuiGraphicsExtractor graphics, int pMouseX, int pMouseY, float pPartialTick)
  {
    graphics.blit(RenderPipelines.GUI_TEXTURED, SCREEN_LOCATION, this.leftPos, this.topPos, 0f, 0f, this.imageWidth, this.imageHeight, 255, 255);
  }

  public void renderInformation(GuiGraphicsExtractor graphics)
  {
    renderSpeciesInformation(graphics, this.leftPos, this.topPos, true);
    renderSpeciesInformation(graphics, this.leftPos, this.topPos, false);
  }

  private void renderSpeciesInformation(GuiGraphicsExtractor graphics, int x, int y, boolean active)
  {
    if (active)
    {
      graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.genetics.active"), x + 12, x + 32, y + 9);
      drawInformation(graphics, x + 12, y, this.menu.getGenome(), true);
    }
    else
    {
      graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.genetics.inactive"), x + 120, x + 32, y + 9);
      drawInformation(graphics, x + 120, y, this.menu.getGenome(), false);
    }
  }

  private void drawInformation(GuiGraphicsExtractor graphics, int x, int y, Genome genome, boolean active)
  {
    if (genome == null) {return;}
    switch (this.currentPage)
    {
      case 0:
        graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.tooltip.species").withStyle(ChatFormatting.WHITE).append(genome.getSpecies(active).value().getReadableName()), x, y + 19, 0);
        graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.tooltip.lifespan").withStyle(ChatFormatting.WHITE).append(genome.getLifespan(active).value().getReadableName()), x, y + 29, 0);
        graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.tooltip.production").withStyle(ChatFormatting.WHITE).append(genome.getSpeed(active).value().getReadableName()), x, y + 39, 0);
        graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.tooltip.pollination").withStyle(ChatFormatting.WHITE).append(genome.getPollination(active).value().getReadableName()), x, y + 49, 0);
        graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.tooltip.flowers").withStyle(ChatFormatting.WHITE).append(genome.getFlowers(active).value().getReadableName()), x, y + 59, 0);
        graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.tooltip.fertility").withStyle(ChatFormatting.WHITE).append(genome.getFertility(active).value().getReadableName()), x, y + 69, 0);
        graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.tooltip.territory").withStyle(ChatFormatting.WHITE).append(genome.getArea(active).value().getReadableName()), x, y + 79, 0);
        graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.tooltip.effect").withStyle(ChatFormatting.WHITE).append("NYI"), x, y + 89, 0);
        break;
      case 1:
        graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.tooltip.preference.temperature").withStyle(ChatFormatting.WHITE).append(genome.getTemperaturePreference(active).value().getReadableName()), x, x + 20, y + 19); //ChatFormatting.AQUA
        graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.tooltip.tolerance").withStyle(ChatFormatting.WHITE).append(genome.getTemperatureTolerance(active).value().getReadableName()), x, x + 20, y + 29);
        graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.tooltip.preference.humidity").withStyle(ChatFormatting.WHITE).append(genome.getHumidityPreference(active).value().getReadableName()), x, x + 20, y + 39);
        graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.tooltip.tolerance").withStyle(ChatFormatting.WHITE).append(genome.getHumidityTolerance(active).value().getReadableName()), x, x + 20, y + 49);
        graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.tooltip.workcycle").withStyle(ChatFormatting.WHITE).append(genome.getWorkcycle(active).value().getReadableName()), x, x + 20, y + 59);
        graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.tooltip.ignores_rain").withStyle(ChatFormatting.WHITE).append(((BeeSpecies) genome.getSpecies(active).value()).getEnvironmentalData().ignoresRain() ? "Yes" : "No"), x, x + 20, y + 69);
        graphics.drawScrollingString(graphics.textRenderer(), this.font, Component.translatable("apicurious.tooltip.ignores_sky").withStyle(ChatFormatting.WHITE).append(((BeeSpecies) genome.getSpecies(active).value()).getEnvironmentalData().ignoresSky() ? "Yes" : "No"), x, x + 20, y + 79);
        break;
      case 2:
        break;
    }
  }

  protected void renderBees(GuiGraphicsExtractor graphics)
  {
    BeeSpecies species = (BeeSpecies) this.menu.getGenome().getSpecies(true).value();
    if (queen == null)
    {queen = BeeItem.getBeeWithSpecies(getMinecraft().level, species.getSpeciesKey(), ItemRegistrar.QUEEN.item());}
    if (princess == null)
    {princess = BeeItem.getBeeWithSpecies(getMinecraft().level, species.getSpeciesKey(), ItemRegistrar.PRINCESS.item());}
    if (drone == null)
    {drone = BeeItem.getBeeWithSpecies(getMinecraft().level, species.getSpeciesKey(), ItemRegistrar.DRONE.item());}
    Matrix3x2f stack = graphics.pose();
    stack.scale(1.25f, 1.25f);
    graphics.item(queen, 255, -65);
    graphics.item(princess, 255, -45);
    graphics.item(drone, 255, -25);
    stack.scale(0.75f, 0.75f);
  }

  protected void pageForward()
  {
    if (this.currentPage < 5) // TODO: Set this to a correct value
    {
      this.currentPage++;
    }
    this.updateButtonVisibility();
  }

  protected void pageBack()
  {
    if (this.currentPage > 0)
    {
      this.currentPage--;
    }
    this.updateButtonVisibility();
  }

  private void updateButtonVisibility()
  {
    this.forwardButton.visible = this.menu.hasIdentifiedBee() && this.currentPage < 5; // TODO: Set this to a correct value
    this.backButton.visible = this.menu.hasIdentifiedBee() && this.currentPage > 0;
  }
}
