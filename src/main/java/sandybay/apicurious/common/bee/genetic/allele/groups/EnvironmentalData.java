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

public class EnvironmentalData
{

  public static final Codec<EnvironmentalData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("flowers").forGetter(EnvironmentalData::getFlowersHolder),
                  HumidityData.CODEC.fieldOf("humidityData").forGetter(EnvironmentalData::getHumidityData),
                  TemperatureData.CODEC.fieldOf("temperatureData").forGetter(EnvironmentalData::getTemperatureData),
                  Codec.BOOL.fieldOf("ignoresRain").forGetter(EnvironmentalData::ignoresRain),
                  Codec.BOOL.fieldOf("ignoresSky").forGetter(EnvironmentalData::ignoresSky)
          ).apply(instance, EnvironmentalData::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, EnvironmentalData> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), EnvironmentalData::getFlowersHolder,
          HumidityData.NETWORK_CODEC, EnvironmentalData::getHumidityData,
          TemperatureData.NETWORK_CODEC, EnvironmentalData::getTemperatureData,
          ByteBufCodecs.BOOL, EnvironmentalData::ignoresRain,
          ByteBufCodecs.BOOL, EnvironmentalData::ignoresSky,
          EnvironmentalData::new
  );

  private final Holder<IAllele<?>> flowersHolder;
  private final HumidityData humidityData;
  private final TemperatureData temperatureData;
  private final boolean ignoresRain;
  private final boolean ignoresSky;
  private Flowers flowers;

  private EnvironmentalData(Holder<IAllele<?>> flowersHolder,
                            HumidityData humidityData, TemperatureData temperatureData,
                            boolean ignoresRain, boolean ignoresSky)
  {
    this.flowersHolder = flowersHolder;
    this.humidityData = humidityData;
    this.temperatureData = temperatureData;
    this.ignoresRain = ignoresRain;
    this.ignoresSky = ignoresSky;
  }

  @Override
  public String toString()
  {
    return super.toString() + " EnvironmentalData{" + "flowers=" + flowersHolder + ", humidityData=" + humidityData + ", temperatureData=" + temperatureData + ", ignoresRain=" + ignoresRain + ", ignoresSky=" + ignoresSky + '}';
  }

  private Holder<IAllele<?>> getFlowersHolder()
  {
    return flowersHolder;
  }

  public Flowers getFlowers()
  {
    if (flowers == null && flowersHolder.isBound()) flowers = (Flowers) flowersHolder.value();
    return flowers;
  }

  public HumidityData getHumidityData()
  {
    return humidityData;
  }

  public TemperatureData getTemperatureData()
  {
    return temperatureData;
  }

  public boolean ignoresRain()
  {
    return ignoresRain;
  }

  public boolean ignoresSky()
  {
    return ignoresSky;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EnvironmentalData that = (EnvironmentalData) o;
    return ignoresRain == that.ignoresRain && ignoresSky == that.ignoresSky && Objects.equals(flowersHolder, that.flowersHolder) && Objects.equals(humidityData, that.humidityData) && Objects.equals(temperatureData, that.temperatureData);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(flowersHolder, humidityData, temperatureData, ignoresRain, ignoresSky);
  }

  public static class Builder
  {

    private final BootstrapContext<IAllele<?>> context;
    private Holder<IAllele<?>> flowers;
    private Holder<IAllele<?>> temperaturePreference;
    private Holder<IAllele<?>> temperatureTolerance;
    private Holder<IAllele<?>> humidityPreference;
    private Holder<IAllele<?>> humidityTolerance;
    private boolean ignoresRain = false;
    private boolean ignoresSky = false;

    private Builder(BootstrapContext<IAllele<?>> context)
    {
      this.context = context;
      this.flowers = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(Flowers.NORMAL_FLOWERS);
      this.temperaturePreference = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(TemperaturePreference.AVERAGE);
      this.temperatureTolerance = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(TemperatureTolerance.NO_TOLERANCE);
      this.humidityPreference = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(HumidityPreference.AVERAGE);
      this.humidityTolerance = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(HumidityTolerance.NO_TOLERANCE);
    }

    public static Builder create(BootstrapContext<IAllele<?>> context)
    {
      return new Builder(context);
    }

    public Builder withFlowers(ResourceKey<IAllele<?>> flowers)
    {
      this.flowers = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(flowers);
      return this;
    }

    public Builder withTemperaturePreference(ResourceKey<IAllele<?>> temperaturePreference)
    {
      this.temperaturePreference = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(temperaturePreference);
      return this;
    }

    public Builder withTemperatureTolerance(ResourceKey<IAllele<?>> temperatureTolerance)
    {
      this.temperatureTolerance = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(temperatureTolerance);
      return this;
    }

    public Builder withHumidityPreference(ResourceKey<IAllele<?>> humidityPreference)
    {
      this.humidityPreference = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(humidityPreference);
      return this;
    }

    public Builder withHumidityTolerance(ResourceKey<IAllele<?>> humidityTolerance)
    {
      this.humidityTolerance = context.lookup(ApicuriousRegistries.ALLELES).getOrThrow(humidityTolerance);
      return this;
    }

    public Builder ignoresRain()
    {
      this.ignoresRain = true;
      return this;
    }

    public Builder ignoresSky()
    {
      this.ignoresSky = true;
      return this;
    }

    public EnvironmentalData build()
    {
      return new EnvironmentalData(
              flowers,
              new HumidityData(humidityPreference, humidityTolerance),
              new TemperatureData(temperaturePreference, temperatureTolerance),
              ignoresRain, ignoresSky
      );
    }
  }
}
