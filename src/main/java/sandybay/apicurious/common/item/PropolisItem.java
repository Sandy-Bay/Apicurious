package sandybay.apicurious.common.item;

import net.minecraft.world.item.Item;
import sandybay.apicurious.api.util.Coloring;

public class PropolisItem extends Item
{
  private final Coloring tint;

  public PropolisItem(Properties pProperties, Coloring tint)
  {
    super(pProperties);
    this.tint = tint;
  }

  public Coloring getTint()
  {
    return tint;
  }
}
