package sandybay.apicurious.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.Apicurious;

import java.util.List;

public class InfoWidget extends AbstractWidget
{
  public static final ResourceLocation SCREEN_LOCATION = Apicurious.createResourceLocation("textures/gui/info.png");
  private static final float msPerUpdate = 16.667f;
  private final int closedSizeWidth;
  private final int closedSizeHeight;
  private final int openSizeWidth;
  private final int openSizeHeight;
  private final boolean openLeft;
  private final float red;
  private final float green;
  private final float blue;
  private final ResourceLocation icon;
  private final List<Component> info;
  private final int defaultX;
  private boolean isOpen;
  private long lastUpdateTime = 0;

  public InfoWidget(int pX, int pY, int pWidth, int pHeight, int openSizeWidth, int openSizeHeight, boolean openLeft, float red, float green, float blue, ResourceLocation icon, List<Component> info)
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
  public void onClick(double mouseX, double mouseY, int button)
  {
    isOpen = !isOpen;
    super.onClick(mouseX, mouseY, button);
  }

  private void update()
  {
    long updateTime;
    if (lastUpdateTime == 0)
    {
      lastUpdateTime = System.currentTimeMillis();
      updateTime = lastUpdateTime + Math.round(msPerUpdate);
    } else
    {
      updateTime = System.currentTimeMillis();
    }

    int moveAmount = (int) (8 * (updateTime - lastUpdateTime) / msPerUpdate);
    lastUpdateTime = updateTime;

    if (isOpen)
    {
      if (width < openSizeWidth)
      {
        if (openLeft) setX(getX() - moveAmount);
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
        if (openLeft) setX(getX() + moveAmount);
        width -= moveAmount;
      } else
      {
        if (openLeft) setX(defaultX);
        width = closedSizeWidth;
      }

      if (height > closedSizeHeight)
      {
        height -= moveAmount;
      } else
      {
        height = closedSizeHeight;
      }
    }
  }

  private void renderBackground(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick)
  {
    update();

    pGuiGraphics.setColor(red, green, blue, this.alpha);


    pGuiGraphics.blit(SCREEN_LOCATION, getX(), getY() + 4, 0, 256 - height + 4, 4, height - 4); // left edge
    pGuiGraphics.blit(SCREEN_LOCATION, getX() + 4, getY(), 256 - width + 4, 0, width - 4, 4); // top edge
    pGuiGraphics.blit(SCREEN_LOCATION, getX(), getY(), 0, 0, 4, 4); // top left corner

    pGuiGraphics.blit(SCREEN_LOCATION, getX() + 4, getY() + 4, 256 - width + 4, 256 - height + 4, width - 4, height - 4); // body + bottom + right

    pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);


    pGuiGraphics.blit(icon, getX() + 4, getY() + 4, 0, 0, 16, 16, 16, 16);
  }

  @Override
  protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick)
  {
    RenderSystem.enableBlend();
    RenderSystem.enableDepthTest();

    renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

    if (isOpen)
    {
      int xOffset = 22;
      int yOffset = 8;
      pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

      for (Component component : info)
      {
        pGuiGraphics.drawString(Minecraft.getInstance().font, component, getX() + xOffset, getY() + 5 + yOffset, -1, false);
        yOffset += 10;
      }
    }
    pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
  }


  @Override
  protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput)
  {
  }
}
