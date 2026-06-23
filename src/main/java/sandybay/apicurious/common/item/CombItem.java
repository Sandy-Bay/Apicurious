package sandybay.apicurious.common.item;

import net.minecraft.world.item.Item;
import sandybay.apicurious.api.util.Coloring;

public class CombItem extends Item
{
  private final Coloring outline;
  private final Coloring cells;

  public CombItem(Properties pProperties, Coloring outline, Coloring cells)
  {
    super(pProperties);
    this.outline = outline;
    this.cells = cells;
  }

  public Coloring getOutline()
  {
    return outline;
  }

  public Coloring getCells()
  {
    return cells;
  }
}
