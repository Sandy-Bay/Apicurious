package sandybay.apicurious.common.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.Tool;
import sandybay.apicurious.api.util.ApicuriousTags;

import java.util.List;

public class SieveItem extends DiggerItem
{

  private static final Item.Properties sieveProps = new Properties()
          .durability(32);

  public SieveItem(Tier tier, Properties properties)
  {
    super(tier, ApicuriousTags.BlockTags.HIVE, properties.component(DataComponents.TOOL, createDefaultToolData()));
  }

  public static Tool createDefaultToolData()
  {
    return new Tool(
            List.of(Tool.Rule.minesAndDrops(ApicuriousTags.BlockTags.HIVE, 15.0f)),
            1.0f,
            1
    );
  }
}
