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
  public static final Identifier SCREEN_LOCATION = Apicurious.createIdentifier("textures/gui/info.png");
  private static final float msPerUpdate = 16.667f;
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
    isOpen = !isOpen;
    super.onClick(event, isDoubleClick);
  }

  private void update()
  {
    long updateTime;
    if (lastUpdateTime == 0)
    {
      lastUpdateTime = System.currentTimeMillis();
      updateTime = lastUpdateTime + Math.round(msPerUpdate);
    }
    else
    {
      updateTime = System.currentTimeMillis();
    }

    int moveAmount = (int) (8 * (updateTime - lastUpdateTime) / msPerUpdate);
    lastUpdateTime = updateTime;

    if (isOpen)
    {
      if (width < openSizeWidth)
      {
        if (openLeft) {setX(getX() - moveAmount);}
        width += moveAmount;
      }

      if (height < openSizeHeight)
      {
        height += moveAmount;
      }
    }

    if (!isOpen)
    {
      if (width > closedSizeWidth)
      {
        if (openLeft) {setX(getX() + moveAmount);}
        width -= moveAmount;
      }
      else
      {
        if (openLeft) {setX(defaultX);}
        width = closedSizeWidth;
      }

      if (height > closedSizeHeight)
      {
        height -= moveAmount;
      }
      else
      {
        height = closedSizeHeight;
      }
    }
  }

  private void renderBackground(@NotNull GuiGraphicsExtractor graphics, int pMouseX, int pMouseY, float pPartialTick)
  {
    update();

    // top edge
    graphics.blit(RenderPipelines.GUI_TEXTURED, SCREEN_LOCATION, getX(), getY() + 4, 0, 256 - height + 4, 4, height - 4, 256, 256, ARGB.color((int) this.alpha, (int) red, (int) green, (int) blue));
    // top left corner
    graphics.blit(RenderPipelines.GUI_TEXTURED, SCREEN_LOCATION, getX(), getY(), 0, 0, 4, 4, 256, 256, ARGB.color((int) this.alpha, (int) red, (int) green, (int) blue));
    // body + bottom + right
    graphics.blit(RenderPipelines.GUI_TEXTURED, SCREEN_LOCATION, getX() + 4, getY() + 4, 256 - width + 4, 256 - height + 4, width - 4, height - 4, 256, 256, ARGB.color((int) this.alpha, (int) red, (int) green, (int) blue));

    graphics.blit(icon, getX() + 4, getY() + 4, 0, 0, 16, 16, 16, 16);
  }

  @Override
  protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partial)
  {
    renderBackground(graphics, mouseX, mouseY, partial);

    if (isOpen)
    {
      int xOffset = 22;
      int yOffset = 8;
      for (Component component : info)
      {
        // TODO: FIX
        //graphics.drawScrollingString(Minecraft.getInstance().font, component, getX() + xOffset, getY() + 5 + yOffset, -1, false);
        yOffset += 10;
      }
    }
  }

  @Override
  protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput)
  {
  }
}
