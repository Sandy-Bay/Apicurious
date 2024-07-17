package sandybay.apicurious.common.bee.traits.groups;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.traits.*;

public class ProductionData {

  public static final Codec<ProductionData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  RegistryFixedCodec.create(ApicuriousRegistries.LIFESPANS).fieldOf("lifespan").forGetter(ProductionData::getLifespan),
                  RegistryFixedCodec.create(ApicuriousRegistries.WORKCYCLES).fieldOf("workCycle").forGetter(ProductionData::getWorkCycle),
                  RegistryFixedCodec.create(ApicuriousRegistries.AREAS).fieldOf("area").forGetter(ProductionData::getArea),
                  RegistryFixedCodec.create(ApicuriousRegistries.SPEEDS).fieldOf("speed").forGetter(ProductionData::getSpeed),
                  RegistryFixedCodec.create(ApicuriousRegistries.POLLINATIONS).fieldOf("pollination").forGetter(ProductionData::getPollination)
          ).apply(instance, ProductionData::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, ProductionData> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.LIFESPANS), ProductionData::getLifespan,
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.WORKCYCLES), ProductionData::getWorkCycle,
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.AREAS), ProductionData::getArea,
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.SPEEDS), ProductionData::getSpeed,
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.POLLINATIONS), ProductionData::getPollination,
          ProductionData::new
  );

  private final Holder<Lifespan> lifespan;
  private final Holder<WorkCycle> workCycle;
  private final Holder<Area> area;
  private final Holder<Speed> speed;
  private final Holder<Pollination> pollination;

  public ProductionData(Holder<Lifespan> lifespan, Holder<WorkCycle> workCycle, Holder<Area> area, Holder<Speed> speed, Holder<Pollination> pollination) {
    this.lifespan = lifespan;
    this.workCycle = workCycle;
    this.area = area;
    this.speed = speed;
    this.pollination = pollination;
  }

  public Holder<Lifespan> getLifespan() {
    return lifespan;
  }

  public Holder<Area> getArea() {
    return area;
  }

  public Holder<Speed> getSpeed() {
    return speed;
  }

  public Holder<Pollination> getPollination() {
    return pollination;
  }

  public Holder<WorkCycle> getWorkCycle() {
    return workCycle;
  }

  public static class Builder {

    private Lifespan lifespan = Lifespan.AVERAGE.value();
    private WorkCycle workCycle = WorkCycle.DIURNAL.value();
    private Area area = Area.AVERAGE.value();
    private Speed speed = Speed.NORMAL.value();
    private Pollination pollination = Pollination.AVERAGE.value();

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
      return new ProductionData(Holder.direct(lifespan), Holder.direct(workCycle), Holder.direct(area), Holder.direct(speed), Holder.direct(pollination));
    }

  }
}
