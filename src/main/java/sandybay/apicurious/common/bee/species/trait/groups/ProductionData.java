package sandybay.apicurious.common.bee.species.trait.groups;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.trait.*;

import java.util.Objects;

public class ProductionData
{

  public static final Codec<ProductionData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.LIFESPANS, Lifespan.CODEC).fieldOf("lifespan").forGetter(ProductionData::getLifespan),
                  RegistryFileCodec.create(ApicuriousRegistries.WORKCYCLES, WorkCycle.CODEC).fieldOf("workCycle").forGetter(ProductionData::getWorkCycle),
                  RegistryFileCodec.create(ApicuriousRegistries.AREAS, Area.CODEC).fieldOf("area").forGetter(ProductionData::getArea),
                  RegistryFileCodec.create(ApicuriousRegistries.SPEEDS, Speed.CODEC).fieldOf("speed").forGetter(ProductionData::getSpeed),
                  RegistryFileCodec.create(ApicuriousRegistries.FERTILITIES, Fertility.CODEC).fieldOf("fertility").forGetter(ProductionData::getFertility),
                  RegistryFileCodec.create(ApicuriousRegistries.POLLINATIONS, Pollination.CODEC).fieldOf("pollination").forGetter(ProductionData::getPollination)
          ).apply(instance, ProductionData::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, ProductionData> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holder(ApicuriousRegistries.LIFESPANS, Lifespan.NETWORK_CODEC), ProductionData::getLifespan,
          ByteBufCodecs.holder(ApicuriousRegistries.WORKCYCLES, WorkCycle.NETWORK_CODEC), ProductionData::getWorkCycle,
          ByteBufCodecs.holder(ApicuriousRegistries.AREAS, Area.NETWORK_CODEC), ProductionData::getArea,
          ByteBufCodecs.holder(ApicuriousRegistries.SPEEDS, Speed.NETWORK_CODEC), ProductionData::getSpeed,
          ByteBufCodecs.holder(ApicuriousRegistries.FERTILITIES, Fertility.NETWORK_CODEC), ProductionData::getFertility,
          ByteBufCodecs.holder(ApicuriousRegistries.POLLINATIONS, Pollination.NETWORK_CODEC), ProductionData::getPollination,
          ProductionData::new
  );

  private final Holder<Lifespan> lifespan;
  private final Holder<WorkCycle> workCycle;
  private final Holder<Area> area;
  private final Holder<Speed> speed;
  private final Holder<Fertility> fertility;
  private final Holder<Pollination> pollination;

  public ProductionData(Holder<Lifespan> lifespan, Holder<WorkCycle> workCycle, Holder<Area> area, Holder<Speed> speed, Holder<Fertility> fertility, Holder<Pollination> pollination)
  {
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

  public Holder<Lifespan> getLifespan()
  {
    return lifespan;
  }

  public Holder<Area> getArea()
  {
    return area;
  }

  public Holder<Speed> getSpeed()
  {
    return speed;
  }

  public Holder<Fertility> getFertility()
  {
    return fertility;
  }

  public Holder<Pollination> getPollination()
  {
    return pollination;
  }

  public Holder<WorkCycle> getWorkCycle()
  {
    return workCycle;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductionData that = (ProductionData) o;
    return Objects.equals(lifespan, that.lifespan) && Objects.equals(workCycle, that.workCycle) && Objects.equals(area, that.area) && Objects.equals(speed, that.speed) && Objects.equals(fertility, that.fertility) && Objects.equals(pollination, that.pollination);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(lifespan, workCycle, area, speed, fertility, pollination);
  }

  public static class Builder
  {

    private final BootstrapContext<BeeSpecies> context;

    private Holder<Lifespan> lifespan;
    private Holder<WorkCycle> workCycle;
    private Holder<Area> area;
    private Holder<Speed> speed;
    private Holder<Fertility> fertility;
    private Holder<Pollination> pollination;

    private Builder(BootstrapContext<BeeSpecies> context)
    {
      this.context = context;
      this.lifespan = context.lookup(ApicuriousRegistries.LIFESPANS).getOrThrow(Lifespan.AVERAGE);
      this.workCycle = context.lookup(ApicuriousRegistries.WORKCYCLES).getOrThrow(WorkCycle.DIURNAL);
      this.area = context.lookup(ApicuriousRegistries.AREAS).getOrThrow(Area.AVERAGE);
      this.speed = context.lookup(ApicuriousRegistries.SPEEDS).getOrThrow(Speed.AVERAGE);
      this.fertility = context.lookup(ApicuriousRegistries.FERTILITIES).getOrThrow(Fertility.AVERAGE_FERTILITY);
      this.pollination = context.lookup(ApicuriousRegistries.POLLINATIONS).getOrThrow(Pollination.AVERAGE);
    }

    public static Builder create(BootstrapContext<BeeSpecies> context)
    {
      return new Builder(context);
    }

    public Builder withLifespan(ResourceKey<Lifespan> lifespan)
    {
      this.lifespan = context.lookup(ApicuriousRegistries.LIFESPANS).getOrThrow(lifespan);
      return this;
    }

    public Builder withArea(ResourceKey<Area> area)
    {
      this.area = context.lookup(ApicuriousRegistries.AREAS).getOrThrow(area);
      return this;
    }

    public Builder withProductionSpeed(ResourceKey<Speed> speed)
    {
      this.speed = context.lookup(ApicuriousRegistries.SPEEDS).getOrThrow(speed);
      return this;
    }

    public Builder withPollinationRate(ResourceKey<Pollination> pollination)
    {
      this.pollination = context.lookup(ApicuriousRegistries.POLLINATIONS).getOrThrow(pollination);
      return this;
    }

    public Builder withWorkCycle(ResourceKey<WorkCycle> workCycle)
    {
      this.workCycle = context.lookup(ApicuriousRegistries.WORKCYCLES).getOrThrow(workCycle);
      return this;
    }

    public Builder withFertility(ResourceKey<Fertility> fertility)
    {
      this.fertility = context.lookup(ApicuriousRegistries.FERTILITIES).getOrThrow(fertility);
      return this;
    }

    public ProductionData build()
    {
      return new ProductionData(lifespan, workCycle, area, speed, fertility, pollination);
    }

  }
}
