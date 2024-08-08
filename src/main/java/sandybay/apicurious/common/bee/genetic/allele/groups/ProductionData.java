package sandybay.apicurious.common.bee.genetic.allele.groups;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.genetic.allele.*;

import java.util.Objects;

public class ProductionData
{

  public static final Codec<ProductionData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("lifespan").forGetter(ProductionData::getLifespanHolder),
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("workCycle").forGetter(ProductionData::getWorkcycleHolder),
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("area").forGetter(ProductionData::getAreaHolder),
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("speed").forGetter(ProductionData::getSpeedHolder),
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("fertility").forGetter(ProductionData::getFertilityHolder),
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("pollination").forGetter(ProductionData::getPollinationHolder)
          ).apply(instance, ProductionData::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, ProductionData> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), ProductionData::getLifespanHolder,
          ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), ProductionData::getWorkcycleHolder,
          ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), ProductionData::getAreaHolder,
          ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), ProductionData::getSpeedHolder,
          ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), ProductionData::getFertilityHolder,
          ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), ProductionData::getPollinationHolder,
          ProductionData::new
  );

  private final Holder<IAllele<?>> lifespanHolder;
  private Lifespan lifespan;
  private final Holder<IAllele<?>> workcycleHolder;
  private Workcycle workcycle;
  private final Holder<IAllele<?>> areaHolder;
  private Area area;
  private final Holder<IAllele<?>> speedHolder;
  private Speed speed;
  private final Holder<IAllele<?>> fertilityHolder;
  private Fertility fertility;
  private final Holder<IAllele<?>> pollinationHolder;
  private Pollination pollination;

  public ProductionData(Holder<IAllele<?>> lifespanHolder, Holder<IAllele<?>> workcycleHolder, Holder<IAllele<?>> areaHolder,
                        Holder<IAllele<?>> speedHolder, Holder<IAllele<?>> fertilityHolder, Holder<IAllele<?>> pollinationHolder)
  {
    this.lifespanHolder = lifespanHolder;
    this.workcycleHolder = workcycleHolder;
    this.areaHolder = areaHolder;
    this.speedHolder = speedHolder;
    this.fertilityHolder = fertilityHolder;
    this.pollinationHolder = pollinationHolder;
  }

  @Override
  public String toString()
  {
    return super.toString() + " ProductionData{" + "lifespan=" + lifespan + ", workCycle=" + workcycleHolder + ", area=" + areaHolder + ", speed=" + speed + ", fertility=" + fertility + ", pollination=" + pollination + '}';
  }

  private Holder<IAllele<?>> getLifespanHolder()
  {
    return lifespanHolder;
  }

  public Lifespan getLifespan()
  {
    if (lifespan == null && lifespanHolder.isBound()) lifespan = (Lifespan) lifespanHolder.value();
    return lifespan;
  }

  private Holder<IAllele<?>> getAreaHolder()
  {
    return areaHolder;
  }

  public Area getArea()
  {
    if (area == null && areaHolder.isBound()) area = (Area) areaHolder.value();
    return area;
  }

  private Holder<IAllele<?>> getSpeedHolder()
  {
    return speedHolder;
  }

  public Speed getSpeed()
  {
    if (speed == null && speedHolder.isBound()) speed = (Speed) speedHolder.value();
    return speed;
  }

  private Holder<IAllele<?>> getFertilityHolder()
  {
    return fertilityHolder;
  }

  public Fertility getFertility()
  {
    if (fertility == null && fertilityHolder.isBound()) fertility = (Fertility) fertilityHolder.value();
    return fertility;
  }

  private Holder<IAllele<?>> getPollinationHolder()
  {
    return pollinationHolder;
  }

  public Pollination getPollination()
  {
    if (pollination == null && pollinationHolder.isBound()) pollination = (Pollination) pollinationHolder.value();
    return pollination;
  }

  private Holder<IAllele<?>> getWorkcycleHolder()
  {
    return workcycleHolder;
  }

  public Workcycle getWorkcycle()
  {
    if (workcycle == null && workcycleHolder.isBound()) workcycle = (Workcycle) workcycleHolder.value();
    return workcycle;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductionData that = (ProductionData) o;
    return Objects.equals(getLifespan(), that.getLifespan()) && Objects.equals(getWorkcycle(), that.getWorkcycle()) &&
            Objects.equals(getArea(), that.getArea()) && Objects.equals(getSpeed(), that.getSpeed()) &&
            Objects.equals(getFertility(), that.getFertility()) && Objects.equals(getPollination(), that.getPollination());
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(lifespan, workcycle, area, speed, fertility, pollination);
  }

  public static class Builder
  {

    private final BootstrapContext<IAllele<?>> context;

    private Holder<IAllele<?>> lifespan;
    private Holder<IAllele<?>> workCycle;
    private Holder<IAllele<?>> area;
    private Holder<IAllele<?>> speed;
    private Holder<IAllele<?>> fertility;
    private Holder<IAllele<?>> pollination;

    private Builder(BootstrapContext<IAllele<?>> context)
    {
      this.context = context;
      this.lifespan = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(Lifespan.AVERAGE);
      this.workCycle = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(Workcycle.DIURNAL);
      this.area = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(Area.AVERAGE);
      this.speed = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(Speed.AVERAGE);
      this.fertility = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(Fertility.AVERAGE_FERTILITY);
      this.pollination = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(Pollination.AVERAGE);
    }

    public static Builder create(BootstrapContext<IAllele<?>> context)
    {
      return new Builder(context);
    }

    public Builder withLifespan(ResourceKey<IAllele<?>> lifespan)
    {
      this.lifespan = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(lifespan);
      return this;
    }

    public Builder withArea(ResourceKey<IAllele<?>> area)
    {
      this.area = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(area);
      return this;
    }

    public Builder withProductionSpeed(ResourceKey<IAllele<?>> speed)
    {
      this.speed = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(speed);
      return this;
    }

    public Builder withPollinationRate(ResourceKey<IAllele<?>> pollination)
    {
      this.pollination = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(pollination);
      return this;
    }

    public Builder withWorkCycle(ResourceKey<IAllele<?>> workCycle)
    {
      this.workCycle = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(workCycle);
      return this;
    }

    public Builder withFertility(ResourceKey<IAllele<?>> fertility)
    {
      this.fertility = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(fertility);
      return this;
    }

    public ProductionData build()
    {
      return new ProductionData(lifespan, workCycle, area, speed, fertility, pollination);
    }

  }
}
