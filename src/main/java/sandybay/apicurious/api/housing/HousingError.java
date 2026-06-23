package sandybay.apicurious.api.housing;

import net.minecraft.resources.Identifier;
import sandybay.apicurious.Apicurious;

public enum HousingError
{
  MISSING_QUEEN("apicurious.error.missing_queen", "apicurious.error.missing_queen_tooltip", Apicurious.createIdentifier("textures/gui/widget/no_queen.png")), MISSING_PRINCESS("apicurious.error.missing_princess", "apicurious.error.missing_princess_tooltip", Apicurious.createIdentifier("textures/gui/widget/no_princess.png")), MISSING_DRONE("apicurious.error.missing_drone", "apicurious.error.missing_drone_tooltip", Apicurious.createIdentifier("textures/gui/widget/no_drone.png")), MISSING_FLOWER("apicurious.error.missing_flower", "apicurious.error.missing_flower_tooltip", Apicurious.createIdentifier("textures/gui/widget/no_flower.png")), TOO_DRY("apicurious.error.too_dry", "apicurious.error.too_dry_tooltip", Apicurious.createIdentifier("textures/gui/widget/too_dry.png")), TOO_HUMID("apicurious.error.too_humid", "apicurious.error.too_humid_tooltip", Apicurious.createIdentifier("textures/gui/widget/too_humid.png")), TOO_HOT("apicurious.error.too_warm", "apicurious.error.too_warm_tooltip", Apicurious.createIdentifier("textures/gui/widget/too_warm.png")), TOO_COLD("apicurious.error.too_cold", "apicurious.error.too_cold_tooltip", Apicurious.createIdentifier("textures/gui/widget/too_cold.png")), INVALID_TIME("apicurious.error.invalid_time", "apicurious.error.invalid_time_tooltip", Apicurious.createIdentifier("textures/gui/widget/invalid_time.png")), IS_RAINING("apicurious.error.is_raining", "apicurious.error.is_raining_tooltip", Apicurious.createIdentifier("textures/gui/widget/is_raining.png")), NO_SKY("apicurious.error.no_sky", "apicurious.error.no_sky_tooltip", Apicurious.createIdentifier("textures/gui/widget/no_sky.png")), FULL_INVENTORY("apicurious.error.full_inventory", "apicurious.error.full_inventory_tooltip", Apicurious.createIdentifier("textures/gui/widget/full_inventory.png"));

  private final String message;
  private final String tooltip;
  private final Identifier icon;

  HousingError(String message, String tooltip, Identifier icon)
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

  public Identifier getIcon()
  {
    return icon;
  }


}
