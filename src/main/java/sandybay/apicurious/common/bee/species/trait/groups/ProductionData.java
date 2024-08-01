package sandybay.apicurious.common.bee.species.trait.groups;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.trait.*;

public class ProductionData {

  public static final Codec<ProductionData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  RegistryFixedCodec.create(ApicuriousRegistries.LIFESPANS).fieldOf("lifespan").forGetter(ProductionData::getLifespan),
                  RegistryFixedCodec.create(ApicuriousRegistries.WORKCYCLES).fieldOf("workCycle").forGetter(ProductionData::getWorkCycle),
                  RegistryFixedCodec.create(ApicuriousRegistries.AREAS).fieldOf("area").forGetter(ProductionData::getArea),
                  RegistryFixedCodec.create(ApicuriousRegistries.SPEEDS).fieldOf("speed").forGetter(ProductionData::getSpeed),
                  RegistryFixedCodec.create(ApicuriousRegistries.FERTILITIES).fieldOf("fertility").forGetter(ProductionData::getFertility),
                  RegistryFixedCodec.create(ApicuriousRegistries.POLLINATIONS).fieldOf("pollination").forGetter(ProductionData::getPollination)
          ).apply(instance, ProductionData::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, ProductionData> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.LIFESPANS), ProductionData::getLifespan,
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.WORKCYCLES), ProductionData::getWorkCycle,
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.AREAS), ProductionData::getArea,
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.SPEEDS), ProductionData::getSpeed,
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.FERTILITIES), ProductionData::getFertility,
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.POLLINATIONS), ProductionData::getPollination,
          ProductionData::new
  );

  private final Holder<Lifespan> lifespan;
  private final Holder<WorkCycle> workCycle;
  private final Holder<Area> area;
  private final Holder<Speed> speed;
  private final Holder<Fertility> fertility;
  private final Holder<Pollination> pollination;

  public ProductionData(Holder<Lifespan> lifespan, Holder<WorkCycle> workCycle, Holder<Area> area, Holder<Speed> speed, Holder<Fertility> fertility, Holder<Pollination> pollination) {
    this.lifespan = lifespan;
    this.workCycle = workCycle;
    this.area = area;
    this.speed = speed;
    this.fertility = fertility;
    this.pollination = pollination;
  }

  @Override
  public String toString()
  {
    return super.toString() + " ProductionData{" + "lifespan=" + lifespan + ", workCycle=" + workCycle + ", area=" + area + ", speed=" + speed + ", fertility=" + fertility + ", pollination=" + pollination + '}';
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

  public Holder<Fertility> getFertility() {
    return fertility;
  }

  public Holder<Pollination> getPollination() {
    return pollination;
  }

  public Holder<WorkCycle> getWorkCycle() {
    return workCycle;
  }

  public static class Builder {

    private final BootstrapContext<BeeSpecies> context;

    private Holder<Lifespan> lifespan;
    private Holder<WorkCycle> workCycle;
    private Holder<Area> area;
    private Holder<Speed> speed;
    private Holder<Fertility> fertility;
    private Holder<Pollination> pollination;

    private Builder(BootstrapContext<BeeSpecies> context) {
      this.context = context;
      this.lifespan = context.lookup(ApicuriousRegistries.LIFESPANS).getOrThrow(Lifespan.AVERAGE);
      this.workCycle = context.lookup(ApicuriousRegistries.WORKCYCLES).getOrThrow(WorkCycle.DIURNAL);
      this.area = context.lookup(ApicuriousRegistries.AREAS).getOrThrow(Area.AVERAGE);
      this.speed = context.lookup(ApicuriousRegistries.SPEEDS).getOrThrow(Speed.AVERAGE);
      this.fertility = context.lookup(ApicuriousRegistries.FERTILITIES).getOrThrow(Fertility.AVERAGE_FERTILITY);
      this.pollination = context.lookup(ApicuriousRegistries.POLLINATIONS).getOrThrow(Pollination.AVERAGE);
    }

    public static Builder create(BootstrapContext<BeeSpecies> context) {
      return new Builder(context);
    }

    public Builder withLifespan(ResourceKey<Lifespan> lifespan) {
      this.lifespan = context.lookup(ApicuriousRegistries.LIFESPANS).getOrThrow(lifespan);
      return this;
    }

    public Builder withArea(Area area) {
      this.area = Holder.direct(area);
      return this;
    }

    public Builder withArea(ResourceKey<Area> area) {
      this.area = context.lookup(ApicuriousRegistries.AREAS).getOrThrow(area);
      return this;
    }

    public Builder withProductionSpeed(ResourceKey<Speed> speed) {
      this.speed = context.lookup(ApicuriousRegistries.SPEEDS).getOrThrow(speed);
      return this;
    }

    public Builder withPollinationRate(ResourceKey<Pollination> pollination) {
      this.pollination = context.lookup(ApicuriousRegistries.POLLINATIONS).getOrThrow(pollination);
      return this;
    }

    public Builder withWorkCycle(ResourceKey<WorkCycle> workCycle) {
      this.workCycle = context.lookup(ApicuriousRegistries.WORKCYCLES).getOrThrow(workCycle);
      return this;
    }

    public Builder withFertility(ResourceKey<Fertility> fertility) {
      this.fertility = context.lookup(ApicuriousRegistries.FERTILITIES).getOrThrow(fertility);
      return this;
    }

    public ProductionData build() {
      return new ProductionData(lifespan, workCycle, area, speed, fertility, pollination);
    }

  }
}
