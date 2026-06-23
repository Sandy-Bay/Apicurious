package sandybay.apicurious.common.item;

import net.minecraft.world.item.Item;
import sandybay.apicurious.api.util.Coloring;

public class DropItem extends Item
{
  private final Coloring dropTint;
  private final Coloring shineTint;

  public DropItem(Properties pProperties, Coloring dropTint, Coloring shineTint)
  {
    super(pProperties);
    this.dropTint = dropTint;
    this.shineTint = shineTint;
  }

  public Coloring getDropTint()
  {
    return dropTint;
  }

  public Coloring getShineTint()
  {
    return shineTint;
  }
}
