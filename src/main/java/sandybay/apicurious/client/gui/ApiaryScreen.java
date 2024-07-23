package sandybay.apicurious.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.menu.ApiaryMenu;

public class ApiaryScreen extends AbstractContainerScreen<ApiaryMenu> {

  public static final ResourceLocation SCREEN_LOCATION = Apicurious.createResourceLocation("textures/gui/apiary.png");

  public ApiaryScreen(ApiaryMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
    super(pMenu, pPlayerInventory, pTitle);
    this.imageWidth = 176;
    this.imageHeight = 190;
    this.titleLabelX = this.titleLabelX + this.imageWidth / 2;
    this.inventoryLabelY = this.inventoryLabelY + 22;
  }

  @Override
  public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partial) {
    this.renderBackground(guiGraphics, mouseX, mouseY, partial);
    super.render(guiGraphics, mouseX, mouseY, partial);
    this.renderTooltip(guiGraphics, mouseX, mouseY);
  }

  @Override
  protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
    guiGraphics.blit(SCREEN_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
  }

  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);

  }
}
