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
    private final int closedSizeWidth;
    private final int closedSizeHeight;

    private final int openSizeWidth;
    private final int openSizeHeight;

    private boolean isOpen;
    private final boolean openLeft;
    private List<Component> info;


    public InfoWidget(int pX, int pY, int pWidth, int pHeight, int openSizeWidth, int openSizeHeight, boolean openLeft, List<Component> info)
    {
        super(pX, pY, pWidth, pHeight, Component.empty());
        this.closedSizeWidth = pWidth;
        this.closedSizeHeight = pHeight;
        this.openSizeWidth = openSizeWidth;
        this.openSizeHeight = openSizeHeight;
        this.openLeft = openLeft;
        this.info = info;
        this.isOpen = false;
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button)
    {
        isOpen = !isOpen;
        super.onClick(mouseX, mouseY, button);
    }

    private static final float msPerUpdate = 16.667f;
    private long lastUpdateTime = 0;

    private void update()
    {
        long updateTime;
        if (lastUpdateTime == 0) {
            lastUpdateTime = System.currentTimeMillis();
            updateTime = lastUpdateTime + Math.round(msPerUpdate);
        } else {
            updateTime = System.currentTimeMillis();
        }

        int moveAmount = (int) (8 * (updateTime - lastUpdateTime) / msPerUpdate);
        lastUpdateTime = updateTime;

        if(isOpen)
        {
            if (width < openSizeWidth)
            {
                width += moveAmount;
            }

            if(height < openSizeHeight)
            {
                height += moveAmount;
            }
        }

        if(!isOpen)
        {
            if(width > closedSizeWidth)
            {
                width -= moveAmount;
            }
            else
            {
                width = closedSizeWidth;
            }

            if(height > closedSizeHeight)
            {
                height -= moveAmount;
            }
            else
            {
                height = closedSizeHeight;
            }
        }
    }

    private void renderBackground(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick)
    {
        update();

        pGuiGraphics.blit(SCREEN_LOCATION, getX(), getY() + 4, 0, 256 - height + 4, 4, height - 4); // left edge
        pGuiGraphics.blit(SCREEN_LOCATION, getX() + 4, getY(), 256 - width + 4, 0, width - 4, 4); // top edge
        pGuiGraphics.blit(SCREEN_LOCATION, getX(), getY(), 0, 0, 4, 4); // top left corner

        pGuiGraphics.blit(SCREEN_LOCATION, getX() + 4, getY() + 4, 256 - width + 4, 256 - height + 4, width - 4, height - 4); // body + bottom + right
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick)
    {
        pGuiGraphics.setColor(1.0F, 0.0F, 0.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        if(isOpen)
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
    protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {}
}
