package sandybay.apicurious.client.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.housing.blockentity.BaseHousingBE;
import sandybay.apicurious.api.util.ClimateHelper;
import sandybay.apicurious.common.menu.ApiaryMenu;

import java.util.ArrayList;
import java.util.List;

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
  protected void init()
  {
    super.init();
    addRenderableWidget(new InfoWidget(leftPos - 25, topPos + 10, 25, 25, 120, 80, true, 1.0F, 0.0F, 0.0F, Apicurious.createResourceLocation("textures/gui/widget/no_queen.png"), new ArrayList<>()));
    addRenderableWidget(new InfoWidget(leftPos + imageWidth, topPos + 10, 25, 25, 120, 80, false, 0.0F, 0.0F, 1.5F, Apicurious.createResourceLocation("textures/gui/widget/habitats/plains.png"), getTempTabInfo()));

  }

  private List<Component> getTempTabInfo()
  {
    List<Component> tempTab = new ArrayList<>();
    tempTab.add(Component.literal("Climate").withColor(ChatFormatting.YELLOW.getColor()));
    tempTab.add(Component.literal("Temperature:").withColor(ChatFormatting.GRAY.getColor()));
    ClimateHelper climateHelper = new ClimateHelper(player.level());
    var temp = climateHelper.getTemperatureAtPosition(player.blockPosition()).location().getPath();
    tempTab.add(Component.literal(temp.toString()).withColor(ChatFormatting.BLACK.getColor()));
    tempTab.add(Component.literal("Humidity:").withColor(ChatFormatting.GRAY.getColor()));
    var humid = climateHelper.getHumidityAtPosition(player.blockPosition()).location().getPath();
    tempTab.add(Component.literal(humid.toString()).withColor(ChatFormatting.BLACK.getColor()));

    return tempTab;
  }

  @Override
  public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partial) {
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
