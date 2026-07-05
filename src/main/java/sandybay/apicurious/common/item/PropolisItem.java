package sandybay.apicurious.common.item;

import net.minecraft.world.item.Item;
import sandybay.apicurious.api.util.Coloring;

public class PropolisItem extends Item
{
  private final Coloring tint;

  public PropolisItem(Properties properties, Coloring tint)
  {
    super(properties);
    this.tint = tint;
  }

  public Coloring getTint()
  {
    return tint;
  }
}
