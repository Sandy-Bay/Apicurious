package sandybay.apicurious.client.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.client.event.ContainerScreenEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.housing.HousingError;
import sandybay.apicurious.api.util.ClimateHelper;
import sandybay.apicurious.client.gui.widget.ErrorWidget;
import sandybay.apicurious.client.gui.widget.InfoWidget;
import sandybay.apicurious.common.menu.ApiaryMenu;

import java.util.ArrayList;
import java.util.List;

public class ApiaryScreen extends AbstractContainerScreen<@NotNull ApiaryMenu>
{

  public static final Identifier SCREEN_LOCATION = Apicurious.createIdentifier("textures/gui/apiary.png");
  private final Player player;
  private final List<ErrorWidget> errorWidgets = new ArrayList<>();

  public ApiaryScreen(ApiaryMenu pMenu, Inventory pPlayerInventory, Component pTitle)
  {
    super(pMenu, pPlayerInventory, pTitle, 176, 190);
    this.player = pPlayerInventory.player;
    this.titleLabelX = this.titleLabelX + this.imageWidth / 2;
  }

  @Override
  protected void init()
  {
    super.init();
    addRenderableWidget(new InfoWidget(leftPos + imageWidth, topPos + 10, 25, 25, 120, 80, false, 0.0F, 0.0F, 1.5F, Apicurious.createIdentifier("textures/gui/widget/habitats/plains.png"), getTempTabInfo()));

    // snapshot open state before the old widgets are discarded
    List<Boolean> previousOpenStates = errorWidgets.stream()
            .map(InfoWidget::isOpen)
            .toList();

    errorWidgets.clear();
    updateErrorList();

    // reapply state to the freshly built widgets (matched by list order)
    for (int i = 0; i < errorWidgets.size() && i < previousOpenStates.size(); i++)
    {
      if (previousOpenStates.get(i))
      {
        errorWidgets.get(i).setOpen(true);
      }
    }
  }

  public void updateErrorList()
  {
    if (errorWidgets.isEmpty() || getMenu().getErrors().size() != errorWidgets.size())
    {
      errorWidgets.clear();
      int y = topPos + 10;
      int space = 27;

      for (HousingError error : getMenu().getErrors())
      {
        ErrorWidget errorWidget = new ErrorWidget(leftPos - 24, y, 25, 25, 120, 80, true, 1.0F, 0.1F, 0.1F, error);
        errorWidgets.add(errorWidget);
        y += space;
      }
    }
  }

  private List<Component> getTempTabInfo()
  {
    List<Component> tempTab = new ArrayList<>();
    tempTab.add(Component.literal("Climate").withStyle(ChatFormatting.YELLOW));
    tempTab.add(Component.literal("Temperature:").withStyle(ChatFormatting.GRAY));
    ClimateHelper climateHelper = new ClimateHelper(player.level(), null);
    var temp = climateHelper.getTemperatureAtPosition(player.blockPosition()).location().getPath();
    tempTab.add(Component.literal(temp).withStyle(ChatFormatting.BLACK));
    tempTab.add(Component.literal("Humidity:").withStyle(ChatFormatting.GRAY));
    var humid = climateHelper.getHumidityAtPosition(player.blockPosition()).location().getPath();
    tempTab.add(Component.literal(humid).withStyle(ChatFormatting.BLACK));
    return tempTab;
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a)
  {
    super.extractRenderState(graphics, mouseX, mouseY, a);
    this.extractContents(graphics, mouseX, mouseY, a);
    this.extractCarriedItem(graphics, mouseX, mouseY);
    this.extractTooltip(graphics, mouseX, mouseY);
    this.extractTooltip(graphics, mouseX, mouseY);

    updateErrorList();

    int cursorY = topPos + 10;
    for (ErrorWidget errorWidget : errorWidgets)
    {
      errorWidget.setY(cursorY);
      errorWidget.extractRenderState(graphics, mouseX, mouseY, a); // advances its own height animation internally
      cursorY += errorWidget.getHeight() + 2; // use the just-updated, current-frame height
    }

    int maxProgress = this.menu.getMaxProgress(), height = 46;
    if (maxProgress > 0)
    {
      int remaining = (this.menu.getProgress() * height) / maxProgress;
      graphics.blit(RenderPipelines.GUI_TEXTURED, SCREEN_LOCATION, leftPos + 21, topPos + 83 - remaining, 177, 45 - remaining, 2, remaining, 256, 256);
    }
  }

  @Override
  public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick)
  {
    boolean clicked = super.mouseClicked(event, isDoubleClick);
    boolean done = errorWidgets.stream()
            .filter(widget -> widget.isMouseOver(event.x(), event.y()))
            .findFirst()
            .map(widget -> widget.mouseClicked(event, isDoubleClick))
            .orElse(false);
    return done ? done : clicked;
  }

  @Override
  public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a)
  {
    graphics.blit(RenderPipelines.GUI_TEXTURED, SCREEN_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
  }
}
