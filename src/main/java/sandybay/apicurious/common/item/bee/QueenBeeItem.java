package sandybay.apicurious.common.item.bee;

import sandybay.apicurious.api.item.BaseBeeItem;
import sandybay.apicurious.common.bee.species.BeeSpecies;

public class QueenBeeItem extends BaseBeeItem {

  public QueenBeeItem(Properties properties) {
    super(properties);
  }

  @Override
  public String getType() {
    return "queen";
  }
}
