package sandybay.apicurious.common.item;

import net.minecraft.world.item.Item;
import sandybay.apicurious.api.util.Coloring;

public class PollenItem extends Item
{
  private final Coloring pollenTint;
  private final Coloring pollenHighlight;

  public PollenItem(Properties properties, Coloring pollenTint, Coloring dropHighlight)
  {
    super(properties);
    this.pollenTint = pollenTint;
    this.pollenHighlight = dropHighlight;
  }

  public Coloring getPollenTint()
  {
    return pollenTint;
  }

  public Coloring getPollenHighlight()
  {
    return pollenHighlight;
  }
}
