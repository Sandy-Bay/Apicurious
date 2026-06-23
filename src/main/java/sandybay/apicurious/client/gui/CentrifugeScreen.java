package sandybay.apicurious.client.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.menu.CentrifugeMenu;

public class CentrifugeScreen extends AbstractContainerScreen<@NotNull CentrifugeMenu>
{
  public static final Identifier SCREEN_LOCATION = Apicurious.createIdentifier("textures/gui/centrifuge.png");
  private final Player player;

  public CentrifugeScreen(CentrifugeMenu menu, Inventory playerInventory, Component title)
  {
    super(menu, playerInventory, title);
    this.player = playerInventory.player;
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a)
  {
    this.extractBackground(graphics, mouseX, mouseY, a);
    super.extractRenderState(graphics, mouseX, mouseY, a);
    this.extractTooltip(graphics, mouseX, mouseY);
    this.renderProgress(graphics);
  }

  @Override
  public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a)
  {
    graphics.blit(RenderPipelines.GUI_TEXTURED, SCREEN_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
  }

  @Override
  protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym)
  {
    // TODO: FIX
    //graphics.drawScrollingString(graphics.textRenderer(), this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    //graphics.drawScrollingString(graphics.textRenderer(), this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
  }

  protected void renderProgress(GuiGraphicsExtractor graphics)
  {
    int maxProgress = this.menu.getMaxWork();
    int width = 23; // Overlay Width
    if (maxProgress > 0)
    {
      int remaining = this.menu.getWork();
      float progressRatio = 1.0f - ((float) remaining / (float) maxProgress);
      int uWidth = (int)(progressRatio * width);
      graphics.blit(RenderPipelines.GUI_TEXTURED, SCREEN_LOCATION, leftPos + 69, topPos + 35, 177, 0, uWidth, 15, 256, 256);
    }
  }
}
