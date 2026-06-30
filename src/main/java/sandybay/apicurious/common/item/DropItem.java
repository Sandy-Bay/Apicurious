package sandybay.apicurious.common.item;

import net.minecraft.world.item.Item;
import sandybay.apicurious.api.util.Coloring;

public class DropItem extends Item
{
  private final Coloring dropTint;
  private final Coloring dropHighlight;

  public DropItem(Properties properties, Coloring dropTint, Coloring dropHighlight)
  {
    super(properties);
    this.dropTint = dropTint;
    this.dropHighlight = dropHighlight;
  }

  public Coloring getDropTint()
  {
    return dropTint;
  }

  public Coloring getDropHighlight()
  {
    return dropHighlight;
  }
}
