package sandybay.apicurious.common.bee.traits.groups;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.traits.Lifespan;
import sandybay.apicurious.common.bee.traits.PollinationRate;
import sandybay.apicurious.common.bee.traits.ProductionSpeed;
import sandybay.apicurious.common.bee.traits.WorkCycle;

public class ProductionData {

  public static final Codec<ProductionData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Lifespan.CODEC.fieldOf("lifespan").forGetter(ProductionData::getLifespan),
                  ProductionSpeed.CODEC.fieldOf("productionSpeed").forGetter(ProductionData::getProductionSpeed),
                  PollinationRate.CODEC.fieldOf("pollinationRate").forGetter(ProductionData::getPollinationRate),
                  WorkCycle.CODEC.fieldOf("workCycle").forGetter(ProductionData::getWorkCycle)
          ).apply(instance, ProductionData::new)
  );

  public static final StreamCodec<ByteBuf, ProductionData> NETWORK_CODEC = StreamCodec.composite(
          Lifespan.NETWORK_CODEC, ProductionData::getLifespan,
          ProductionSpeed.NETWORK_CODEC, ProductionData::getProductionSpeed,
          PollinationRate.NETWORK_CODEC, ProductionData::getPollinationRate,
          WorkCycle.NETWORK_CODEC, ProductionData::getWorkCycle,
          ProductionData::new
  );

  private final Lifespan lifespan;
  private final ProductionSpeed productionSpeed;
  private final PollinationRate pollinationRate;
  private final WorkCycle workCycle;

  public ProductionData(Lifespan lifespan, ProductionSpeed productionSpeed, PollinationRate pollinationRate, WorkCycle workCycle) {
    this.lifespan = lifespan;
    this.productionSpeed = productionSpeed;
    this.pollinationRate = pollinationRate;
    this.workCycle = workCycle;
  }

  public Lifespan getLifespan() {
    return lifespan;
  }

  public ProductionSpeed getProductionSpeed() {
    return productionSpeed;
  }

  public PollinationRate getPollinationRate() {
    return pollinationRate;
  }

  public WorkCycle getWorkCycle() {
    return workCycle;
  }

  public static class Builder {

    private Lifespan lifespan = Lifespan.NORMAL;
    private ProductionSpeed productionSpeed = ProductionSpeed.NORMAL;
    private PollinationRate pollinationRate = PollinationRate.NORMAL;
    private WorkCycle workCycle = WorkCycle.DIURNAL;

    private Builder() {}

    public static Builder create() {
      return new Builder();
    }

    public Builder withLifespan(Lifespan lifespan) {
      this.lifespan = lifespan;
      return this;
    }

    public Builder withProductionSpeed(ProductionSpeed productionSpeed) {
      this.productionSpeed = productionSpeed;
      return this;
    }

    public Builder withPollinationRate(PollinationRate pollinationRate) {
      this.pollinationRate = pollinationRate;
      return this;
    }

    public Builder withWorkCycle(WorkCycle workCycle) {
      this.workCycle = workCycle;
      return this;
    }

    public ProductionData build() {
      return new ProductionData(lifespan, productionSpeed, pollinationRate, workCycle);
    }

  }
}
