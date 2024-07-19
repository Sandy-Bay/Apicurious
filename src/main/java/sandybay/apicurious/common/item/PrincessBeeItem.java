package sandybay.apicurious.common.item;

import sandybay.apicurious.api.item.BaseBeeItem;
import sandybay.apicurious.common.bee.species.BeeSpecies;

public class PrincessBeeItem extends BaseBeeItem {

  public PrincessBeeItem(Properties properties) {
    super(properties);
  }

  @Override
  public String getType() {
    return "princess";
  }
}
