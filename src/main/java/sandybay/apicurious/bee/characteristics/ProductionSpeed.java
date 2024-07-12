package sandybay.apicurious.bee.characteristics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ProductionSpeed {
  public static final ProductionSpeed SLOWEST = new ProductionSpeed(600);
  public static final ProductionSpeed SLOWER = new ProductionSpeed(500);
  public static final ProductionSpeed SLOW = new ProductionSpeed(400);
  public static final ProductionSpeed NORMAL = new ProductionSpeed(300);
  public static final ProductionSpeed FAST = new ProductionSpeed(200);
  public static final ProductionSpeed FASTER = new ProductionSpeed(100);
  public static final ProductionSpeed FASTEST = new ProductionSpeed(50);

  public static final Codec<ProductionSpeed> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("productionDuration").forGetter(ProductionSpeed::getProductionDuration)
          ).apply(instance, ProductionSpeed::new)
  );

  private final int productionDuration;

  ProductionSpeed(int productionDuration) {
    this.productionDuration = productionDuration;
  }

  public int getProductionDuration() {
    return productionDuration;
  }
}
