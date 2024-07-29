package sandybay.apicurious.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.menu.ApiaryMenu;

public class ApiaryScreen extends AbstractContainerScreen<ApiaryMenu> {

  public static final ResourceLocation SCREEN_LOCATION = Apicurious.createResourceLocation("textures/gui/apiary.png");
  private final Player player;

  public ApiaryScreen(ApiaryMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
    super(pMenu, pPlayerInventory, pTitle);
    this.player = pPlayerInventory.player;
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
    var level = player.level();
    var tags = level.getBiome(player.blockPosition()).tags();
    int y = 0;
    for (var biomeTagKey : tags.toList())
    {
      guiGraphics.drawString(this.font, biomeTagKey.location().toString(), 150, y, -1, false);
      y+=20;
    }
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
