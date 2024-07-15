package sandybay.apicurious.common.bee.traits.groups;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.common.bee.traits.*;

public class ProductionData {

  public static final Codec<ProductionData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Lifespan.CODEC.fieldOf("lifespan").forGetter(ProductionData::getLifespan),
                  WorkCycle.CODEC.fieldOf("workCycle").forGetter(ProductionData::getWorkCycle),
                  Area.CODEC.fieldOf("area").forGetter(ProductionData::getArea),
                  Speed.CODEC.fieldOf("speed").forGetter(ProductionData::getProductionSpeed),
                  Pollination.CODEC.fieldOf("pollination").forGetter(ProductionData::getPollinationRate)
          ).apply(instance, ProductionData::new)
  );

  public static final StreamCodec<ByteBuf, ProductionData> NETWORK_CODEC = StreamCodec.composite(
          Lifespan.NETWORK_CODEC, ProductionData::getLifespan,
          WorkCycle.NETWORK_CODEC, ProductionData::getWorkCycle,
          Area.NETWORK_CODEC, ProductionData::getArea,
          Speed.NETWORK_CODEC, ProductionData::getProductionSpeed,
          Pollination.NETWORK_CODEC, ProductionData::getPollinationRate,
          ProductionData::new
  );

  private final Lifespan lifespan;
  private final WorkCycle workCycle;
  private final Area area;
  private final Speed speed;
  private final Pollination pollination;

  public ProductionData(Lifespan lifespan, WorkCycle workCycle, Area area, Speed speed, Pollination pollination) {
    this.lifespan = lifespan;
    this.workCycle = workCycle;
    this.area = area;
    this.speed = speed;
    this.pollination = pollination;
  }

  public Lifespan getLifespan() {
    return lifespan;
  }

  public Area getArea() {
    return area;
  }

  public Speed getProductionSpeed() {
    return speed;
  }

  public Pollination getPollinationRate() {
    return pollination;
  }

  public WorkCycle getWorkCycle() {
    return workCycle;
  }

  public static class Builder {

    private Lifespan lifespan = Lifespan.NORMAL;
    private WorkCycle workCycle = WorkCycle.DIURNAL;
    private Area area = Area.AVERAGE;
    private Speed speed = Speed.NORMAL;
    private Pollination pollination = Pollination.AVERAGE;

    private Builder() {}

    public static Builder create() {
      return new Builder();
    }

    public Builder withLifespan(Lifespan lifespan) {
      this.lifespan = lifespan;
      return this;
    }

    public Builder withArea(Area area) {
      this.area = area;
      return this;
    }

    public Builder withProductionSpeed(Speed speed) {
      this.speed = speed;
      return this;
    }

    public Builder withPollinationRate(Pollination pollination) {
      this.pollination = pollination;
      return this;
    }

    public Builder withWorkCycle(WorkCycle workCycle) {
      this.workCycle = workCycle;
      return this;
    }

    public ProductionData build() {
      return new ProductionData(lifespan, workCycle, area, speed, pollination);
    }

  }
}
