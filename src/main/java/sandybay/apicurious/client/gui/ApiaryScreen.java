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
import sandybay.apicurious.api.housing.HousingError;
import sandybay.apicurious.api.util.ClimateHelper;
import sandybay.apicurious.common.menu.ApiaryMenu;

import java.util.ArrayList;
import java.util.List;

public class ApiaryScreen extends AbstractContainerScreen<ApiaryMenu>
{

  public static final ResourceLocation SCREEN_LOCATION = Apicurious.createResourceLocation("textures/gui/apiary.png");
  private final Player player;
  private final List<ErrorWidget> errorWidgets = new ArrayList<>();

  public ApiaryScreen(ApiaryMenu pMenu, Inventory pPlayerInventory, Component pTitle)
  {
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
    addRenderableWidget(new InfoWidget(leftPos + imageWidth, topPos + 10, 25, 25, 120, 80, false, 0.0F, 0.0F, 1.5F, Apicurious.createResourceLocation("textures/gui/widget/habitats/plains.png"), getTempTabInfo()));
    updateErrorList();
  }

  public void updateErrorList()
  {
    if (errorWidgets.isEmpty() || getMenu().getErrors().size() != errorWidgets.size())
    {
      errorWidgets.clear();
      int y = topPos + 10;
      int space = 25;

      for (HousingError error : getMenu().getErrors())
      {
        ErrorWidget errorWidget = new ErrorWidget(leftPos - 25, y, 25, 25, 120, 80, true, 1.0F, 0F, 0F, error);
        errorWidgets.add(errorWidget);
        y += space;
      }
    }
  }

  private List<Component> getTempTabInfo()
  {
    List<Component> tempTab = new ArrayList<>();
    tempTab.add(Component.literal("Climate").withColor(ChatFormatting.YELLOW.getColor()));
    tempTab.add(Component.literal("Temperature:").withColor(ChatFormatting.GRAY.getColor()));
    ClimateHelper climateHelper = new ClimateHelper(player.level(), null);
    var temp = climateHelper.getTemperatureAtPosition(player.blockPosition()).location().getPath();
    tempTab.add(Component.literal(temp).withColor(ChatFormatting.BLACK.getColor()));
    tempTab.add(Component.literal("Humidity:").withColor(ChatFormatting.GRAY.getColor()));
    var humid = climateHelper.getHumidityAtPosition(player.blockPosition()).location().getPath();
    tempTab.add(Component.literal(humid).withColor(ChatFormatting.BLACK.getColor()));

    return tempTab;
  }

  @Override
  public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partial)
  {
    this.renderBackground(guiGraphics, mouseX, mouseY, partial);
    super.render(guiGraphics, mouseX, mouseY, partial);
    this.renderTooltip(guiGraphics, mouseX, mouseY);

    updateErrorList();
    for (ErrorWidget errorWidget : errorWidgets)
    {
      errorWidget.renderWidget(guiGraphics, mouseX, mouseY, partial);
    }

    int maxProgress = this.menu.getMaxProgress(), height = 46;
    if (maxProgress > 0)
    {
      int remaining = (this.menu.getProgress() * height) / maxProgress;
      guiGraphics.blit(SCREEN_LOCATION, leftPos + 21, topPos + 83 - remaining, 177, 45 - remaining, 2, remaining);
    }
  }

  @Override
  public boolean mouseClicked(double pMouseX, double pMouseY, int pButton)
  {
    boolean clicked = super.mouseClicked(pMouseX, pMouseY, pButton);
    for (ErrorWidget errorWidget : errorWidgets)
    {
      boolean done = errorWidget.mouseClicked(pMouseX, pMouseY, pButton);
      if (done) return done;
    }

    return clicked;
  }

  @Override
  protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
  {
    guiGraphics.blit(SCREEN_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
  }

  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
  {
    guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
  }
}
