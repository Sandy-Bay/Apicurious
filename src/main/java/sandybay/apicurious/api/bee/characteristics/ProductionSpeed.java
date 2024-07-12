package sandybay.apicurious.api.bee.characteristics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;

public class ProductionSpeed {
  public static final ProductionSpeed SLOWEST = new ProductionSpeed(600, "apicurious.production_speed.slowest");
  public static final ProductionSpeed SLOWER = new ProductionSpeed(500, "apicurious.production_speed.slower");
  public static final ProductionSpeed SLOW = new ProductionSpeed(400, "apicurious.production_speed.slow");
  public static final ProductionSpeed NORMAL = new ProductionSpeed(300, "apicurious.production_speed.normal");
  public static final ProductionSpeed FAST = new ProductionSpeed(200, "apicurious.production_speed.fast");
  public static final ProductionSpeed FASTER = new ProductionSpeed(100, "apicurious.production_speed.faster");
  public static final ProductionSpeed FASTEST = new ProductionSpeed(50, "apicurious.production_speed.fastest");

  public static final Codec<ProductionSpeed> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("productionDuration").forGetter(ProductionSpeed::getProductionDuration),
                  Codec.STRING.fieldOf("name").forGetter(ProductionSpeed::getName)
          ).apply(instance, ProductionSpeed::new)
  );

  private final int productionDuration;
  private final String name;
  private Component readableName;

  ProductionSpeed(int productionDuration, String name) {
    this.productionDuration = productionDuration;
    this.name = name;
  }

  public int getProductionDuration() {
    return productionDuration;
  }

  private String getName() {
    return name;
  }

  public Component getReadableName() {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }
}
