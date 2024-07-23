package sandybay.apicurious.common.item.bee;

import net.minecraft.world.item.ItemStack;
import sandybay.apicurious.api.item.BaseBeeItem;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.register.ApicuriousItemRegistration;

public class DroneBeeItem extends BaseBeeItem {

  public DroneBeeItem(Properties properties) {
    super(properties);
  }

  @Override
  public String getType() {
    return "drone";
  }

  @Override
  public ItemStack getDefaultInstance() {
    return BaseBeeItem.getBeeWithSpecies(null, ApicuriousSpecies.FOREST, ApicuriousItemRegistration.DRONE);
  }
}
