package sandybay.apicurious.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.item.BeeItem;
import sandybay.apicurious.common.menu.AnalyzerMenu;
import sandybay.apicurious.common.registrar.ItemRegistrar;

public class AnalyzerScreen extends AbstractContainerScreen<AnalyzerMenu>
{

  public static final ResourceLocation SCREEN_LOCATION = Apicurious.createResourceLocation("textures/gui/analyzer.png");
  private int currentPage;
  private PageButton forwardButton;
  private PageButton backButton;

  private ItemStack queen;
  private ItemStack princess;
  private ItemStack drone;


  public AnalyzerScreen(AnalyzerMenu pMenu, Inventory pPlayerInventory, Component pTitle)
  {
    super(pMenu, pPlayerInventory, pTitle);
    this.imageWidth = 230;
    this.imageHeight = 219;
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
  public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick)
  {
    this.renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    if (this.menu.hasIdentifiedBee())
    {
      renderInformation(pGuiGraphics);
      renderBees(pGuiGraphics);
    }
    updateButtonVisibility();
  }

  @Override
  protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY)
  {}

  @Override
  protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY)
  {
    pGuiGraphics.blit(SCREEN_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
  }

  public void renderInformation(GuiGraphics graphics)
  {
    renderSpeciesInformation(graphics, this.leftPos, this.topPos, true);
    renderSpeciesInformation(graphics, this.leftPos, this.topPos, false);
  }

  private void renderSpeciesInformation(GuiGraphics graphics, int x, int y, boolean active)
  {
    if (active)
    {
      graphics.drawString(this.font, Component.translatable("apicurious.genetics.active"), x + 12, y + 9, ChatFormatting.GREEN.getColor());
      drawInformation(graphics, x + 12, y, this.menu.getGenome(), true);
    }
    else
    {
      graphics.drawString(this.font, Component.translatable("apicurious.genetics.inactive"), x + 120, y + 9, ChatFormatting.RED.getColor());
      drawInformation(graphics, x + 120, y, this.menu.getGenome(), false);
    }
  }

  private void drawInformation(GuiGraphics graphics, int x, int y, Genome genome, boolean active)
  {
    if (genome == null) {return;}
    switch (this.currentPage)
    {
      case 0:
        graphics.drawString(this.font, Component.translatable("apicurious.tooltip.species").withStyle(ChatFormatting.WHITE).append(genome.getSpecies(active).value().getReadableName()), x, y + 19, 0);
        graphics.drawString(this.font, Component.translatable("apicurious.tooltip.lifespan").withStyle(ChatFormatting.WHITE).append(genome.getLifespan(active).value().getReadableName()), x, y + 29, 0);
        graphics.drawString(this.font, Component.translatable("apicurious.tooltip.production").withStyle(ChatFormatting.WHITE).append(genome.getSpeed(active).value().getReadableName()), x, y + 39, 0);
        graphics.drawString(this.font, Component.translatable("apicurious.tooltip.pollination").withStyle(ChatFormatting.WHITE).append(genome.getPollination(active).value().getReadableName()), x, y + 49, 0);
        graphics.drawString(this.font, Component.translatable("apicurious.tooltip.flowers").withStyle(ChatFormatting.WHITE).append(genome.getFlowers(active).value().getReadableName()), x, y + 59, 0);
        graphics.drawString(this.font, Component.translatable("apicurious.tooltip.fertility").withStyle(ChatFormatting.WHITE).append(genome.getFertility(active).value().getReadableName()), x, y + 69, 0);
        graphics.drawString(this.font, Component.translatable("apicurious.tooltip.territory").withStyle(ChatFormatting.WHITE).append(genome.getArea(active).value().getReadableName()), x, y + 79, 0);
        graphics.drawString(this.font, Component.translatable("apicurious.tooltip.effect").withStyle(ChatFormatting.WHITE).append("NYI"), x, y + 89, 0);
        break;
      case 1:
        graphics.drawString(this.font, Component.translatable("apicurious.tooltip.preference.temperature").withStyle(ChatFormatting.WHITE).append(genome.getTemperaturePreference(active).value().getReadableName()), x, y + 19, ChatFormatting.AQUA.getColor());
        graphics.drawString(this.font, Component.translatable("apicurious.tooltip.tolerance").withStyle(ChatFormatting.WHITE).append(genome.getTemperatureTolerance(active).value().getReadableName()), x, y + 29, ChatFormatting.AQUA.getColor());
        graphics.drawString(this.font, Component.translatable("apicurious.tooltip.preference.humidity").withStyle(ChatFormatting.WHITE).append(genome.getHumidityPreference(active).value().getReadableName()), x, y + 39, ChatFormatting.AQUA.getColor());
        graphics.drawString(this.font, Component.translatable("apicurious.tooltip.tolerance").withStyle(ChatFormatting.WHITE).append(genome.getHumidityTolerance(active).value().getReadableName()), x, y + 49, ChatFormatting.AQUA.getColor());
        graphics.drawString(this.font, Component.translatable("apicurious.tooltip.workcycle").withStyle(ChatFormatting.WHITE).append(genome.getWorkcycle(active).value().getReadableName()), x, y + 59, ChatFormatting.AQUA.getColor());
        graphics.drawString(this.font, Component.translatable("apicurious.tooltip.ignores_rain").withStyle(ChatFormatting.WHITE).append(((BeeSpecies) genome.getSpecies(active).value()).getEnvironmentalData().ignoresRain() ? "Yes" : "No"), x, y + 69, ChatFormatting.AQUA.getColor());
        graphics.drawString(this.font, Component.translatable("apicurious.tooltip.ignores_sky").withStyle(ChatFormatting.WHITE).append(((BeeSpecies) genome.getSpecies(active).value()).getEnvironmentalData().ignoresSky() ? "Yes" : "No"), x, y + 79, ChatFormatting.AQUA.getColor());
        break;
      case 2:
        break;
    }
  }

  protected void renderBees(GuiGraphics graphics)
  {
    BeeSpecies species = (BeeSpecies) this.menu.getGenome().getSpecies(true).value();
    if (queen == null)
    {queen = BeeItem.getBeeWithSpecies(getMinecraft().level, species.getSpeciesKey(), ItemRegistrar.QUEEN);}
    if (princess == null)
    {princess = BeeItem.getBeeWithSpecies(getMinecraft().level, species.getSpeciesKey(), ItemRegistrar.PRINCESS);}
    if (drone == null)
    {drone = BeeItem.getBeeWithSpecies(getMinecraft().level, species.getSpeciesKey(), ItemRegistrar.DRONE);}
    PoseStack stack = graphics.pose();
    stack.pushPose();
    stack.scale(1.25f, 1.25f, 1.25f);
    graphics.renderItem(queen, 255, -65);
    graphics.renderItem(princess, 255, -45);
    graphics.renderItem(drone, 255, -25);
    stack.scale(0.75f, 0.75f, 0.75f);
    stack.popPose();
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
