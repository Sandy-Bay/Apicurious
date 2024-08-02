package sandybay.apicurious.api;

import net.minecraft.resources.ResourceLocation;
import sandybay.apicurious.Apicurious;

public enum EnumApiaryError
{
    MISSING_QUEEN("apicurious.error.missing_queen", "apicurious.error.missing_queen_tooltip", Apicurious.createResourceLocation("textures/gui/widget/no_queen.png")),
    MISSING_DRONE("apicurious.error.missing_drone", "apicurious.error.missing_drone_tooltip", Apicurious.createResourceLocation("textures/gui/widget/no_drone.png")),
    MISSING_PRINCESS("apicurious.error.missing_princess", "apicurious.error.missing_princess_tooltip", Apicurious.createResourceLocation("textures/gui/widget/no_princess.png")),
    MISSING_FLOWER("apicurious.error.missing_flower", "apicurious.error.missing_flower_tooltip", Apicurious.createResourceLocation("textures/gui/widget/no_flower.png"));


    private final String message;
    private final String tooltip;
    private final ResourceLocation icon;

    EnumApiaryError(String message, String tooltip, ResourceLocation icon)
    {
        this.message = message;
        this.tooltip = tooltip;
        this.icon = icon;
    }

    public String getMessage()
    {
        return message;
    }

    public String getTooltip()
    {
        return tooltip;
    }

    public ResourceLocation getIcon()
    {
        return icon;
    }
}
