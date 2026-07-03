package sandybay.apicurious.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.Apicurious;

import java.util.List;

public class InfoWidget extends AbstractWidget
{
  public static final Identifier LEFT_LOCATION = Apicurious.createIdentifier("textures/gui/info_left.png");
  public static final Identifier RIGHT_LOCATION = Apicurious.createIdentifier("textures/gui/info_right.png");

  // -- animation / layout constants --
  private static final float MS_PER_UPDATE = 33.334f;
  private static final int CORNER_SIZE = 4;
  private static final int ANIM_PIXELS_PER_FRAME = 8;
  private static final int ICON_SIZE = 16;
  private static final int PANEL_TEX_SIZE = 256;
  private static final int TEXT_X_OFFSET = 22;
  private static final int TEXT_RIGHT_PADDING = 4;
  private static final int TEXT_Y_OFFSET = 8;
  private static final int TEXT_Y_START = 5;
  private static final int LINE_HEIGHT = 10;

  private final int closedSizeWidth;
  private final int closedSizeHeight;
  private final int openSizeWidth;
  private final int openSizeHeight;
  private final boolean openLeft;
  private final float red;
  private final float green;
  private final float blue;
  private final Identifier icon;
  private final List<Component> info;
  private final int defaultX;
  private boolean isOpen;
  private long lastUpdateTime = 0;

  public InfoWidget(int pX, int pY, int pWidth, int pHeight, int openSizeWidth, int openSizeHeight, boolean openLeft, float red, float green, float blue, Identifier icon, List<Component> info)
  {
    super(pX, pY, pWidth, pHeight, Component.empty());
    this.closedSizeWidth = pWidth;
    this.closedSizeHeight = pHeight;
    this.openSizeWidth = openSizeWidth;
    this.openSizeHeight = openSizeHeight;
    this.openLeft = openLeft;
    this.info = info;
    this.isOpen = false;
    this.defaultX = pX;
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.icon = icon;
  }

  @Override
  public void onClick(MouseButtonEvent event, boolean isDoubleClick)
  {
    if (!isDoubleClick)
    {
      isOpen = !isOpen;
    }
    super.onClick(event, isDoubleClick);
  }

  private void update()
  {
    long updateTime;
    if (lastUpdateTime == 0)
    {
      lastUpdateTime = System.currentTimeMillis();
      updateTime = lastUpdateTime + Math.round(MS_PER_UPDATE);
    }
    else
    {
      updateTime = System.currentTimeMillis();
    }

    int moveAmount = (int) (ANIM_PIXELS_PER_FRAME * (updateTime - lastUpdateTime) / MS_PER_UPDATE);
    moveAmount = Math.min(moveAmount, Math.max(openSizeWidth, openSizeHeight));
    lastUpdateTime = updateTime;

    if (isOpen)
    {
      if (width < openSizeWidth)
      {
        if (openLeft) {setX(getX() - moveAmount);}
        width = Math.min(width + moveAmount, openSizeWidth);
      }
      else
      {
        width = openSizeWidth;
      }

      if (height < openSizeHeight)
      {
        height = Math.min(height + moveAmount, openSizeHeight);
      }
      else
      {
        height = openSizeHeight;
      }
    }
    else
    {
      if (width > closedSizeWidth)
      {
        if (openLeft) {setX(getX() + moveAmount);}
        width = Math.max(width - moveAmount, closedSizeWidth);
      }
      else
      {
        if (openLeft) {setX(defaultX);}
        width = closedSizeWidth;
      }

      if (height > closedSizeHeight)
      {
        height = Math.max(height - moveAmount, closedSizeHeight);
      }
      else
      {
        height = closedSizeHeight;
      }
    }
  }

  private int panelColor()
  {
    return ARGB.color((int) (this.alpha * 255), (int) red * 255, (int) green * 255, (int) blue * 255);
  }

  private void blitPanelPart(GuiGraphicsExtractor graphics, int x, int y, int u, int v, int w, int h, boolean left)
  {
    graphics.blit(RenderPipelines.GUI_TEXTURED, left ? LEFT_LOCATION : RIGHT_LOCATION, x, y, u, v, w, h, PANEL_TEX_SIZE, PANEL_TEX_SIZE, panelColor());
  }

  private void renderBackground(@NotNull GuiGraphicsExtractor graphics, int pMouseX, int pMouseY, float pPartialTick)
  {
    update();

    // left/right edge
    blitPanelPart(graphics, getX(), getY() + CORNER_SIZE, 0, PANEL_TEX_SIZE - height + CORNER_SIZE, CORNER_SIZE, height - CORNER_SIZE, openLeft);
    // top left/right corner
    blitPanelPart(graphics, getX(), getY(), 0, 0, CORNER_SIZE, CORNER_SIZE, openLeft);
    // body + bottom + top
    blitPanelPart(graphics, getX() + CORNER_SIZE, getY(), PANEL_TEX_SIZE - width + CORNER_SIZE, 0, width - CORNER_SIZE, CORNER_SIZE, openLeft);
    blitPanelPart(graphics, getX() + CORNER_SIZE, getY() + CORNER_SIZE, PANEL_TEX_SIZE - width + CORNER_SIZE, PANEL_TEX_SIZE - height + CORNER_SIZE, width - CORNER_SIZE, height - CORNER_SIZE, openLeft);

    // icon is its own 16x16 texture — do NOT reuse PANEL_TEX_SIZE here, and don't tint it with panelColor()
    graphics.blit(RenderPipelines.GUI_TEXTURED, icon, getX() + 4, getY() + 4, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
  }

  @Override
  protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partial)
  {
    renderBackground(graphics, mouseX, mouseY, partial);

    if (isOpen)
    {
      int yOffset = TEXT_Y_OFFSET;
      int minX = getX() + TEXT_X_OFFSET;
      int maxX = getX() + width - TEXT_RIGHT_PADDING;

      for (Component component : info)
      {
        graphics.drawScrollingString(graphics.textRenderer(), Minecraft.getInstance().font, component, minX + 4, maxX, getY() + TEXT_Y_START + yOffset - 7);
        yOffset += LINE_HEIGHT;
      }
    }
  }

  @Override
  protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput)
  {
  }

  public boolean isOpen()
  {
    return isOpen;
  }

  public int getOpenSizeHeight()
  {
    return openSizeHeight;
  }

  public int getClosedSizeHeight()
  {
    return closedSizeHeight;
  }

  public void setOpen(boolean open)
  {
    this.isOpen = open;
    if (isOpen)
    {
      this.width = openSizeWidth;
      this.height = openSizeHeight;
      if (openLeft) { setX(defaultX - (openSizeWidth - closedSizeWidth)); }
    }
    else
    {
      this.width = closedSizeWidth;
      this.height = closedSizeHeight;
      if (openLeft) { setX(defaultX); }
    }
  }
}