package sandybay.apicurious.common.item;

import net.minecraft.world.item.Item;
import sandybay.apicurious.api.util.Coloring;

public class PollenItem extends Item
{
  private final Coloring primary;
  private final Coloring secondary;

  public PollenItem(Properties pProperties, Coloring primary, Coloring secondary)
  {
    super(pProperties);
    this.primary = primary;
    this.secondary = secondary;
  }

  public Coloring getPrimary()
  {
    return primary;
  }

  public Coloring getSecondary()
  {
    return secondary;
  }
}
