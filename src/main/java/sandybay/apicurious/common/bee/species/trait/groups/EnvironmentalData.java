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

import java.util.Objects;

public class EnvironmentalData
{

  public static final Codec<EnvironmentalData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  RegistryFixedCodec.create(ApicuriousRegistries.FLOWERS).fieldOf("flowers").forGetter(EnvironmentalData::getFlowers),
                  HumidityData.CODEC.fieldOf("humidityData").forGetter(EnvironmentalData::getHumidityData),
                  TemperatureData.CODEC.fieldOf("temperatureData").forGetter(EnvironmentalData::getTemperatureData),
                  Codec.BOOL.fieldOf("ignoresRain").forGetter(EnvironmentalData::ignoresRain),
                  Codec.BOOL.fieldOf("ignoresSky").forGetter(EnvironmentalData::ignoresSky)
          ).apply(instance, EnvironmentalData::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, EnvironmentalData> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.FLOWERS), EnvironmentalData::getFlowers,
          HumidityData.NETWORK_CODEC, EnvironmentalData::getHumidityData,
          TemperatureData.NETWORK_CODEC, EnvironmentalData::getTemperatureData,
          ByteBufCodecs.BOOL, EnvironmentalData::ignoresRain,
          ByteBufCodecs.BOOL, EnvironmentalData::ignoresSky,
          EnvironmentalData::new
  );

  private final Holder<Flowers> flowers;

  private final HumidityData humidityData;
  private final TemperatureData temperatureData;

  private final boolean ignoresRain;
  private final boolean ignoresSky;

  private EnvironmentalData(Holder<Flowers> flowers,
                            HumidityData humidityData, TemperatureData temperatureData,
                            boolean ignoresRain, boolean ignoresSky)
  {
    this.flowers = flowers;
    this.humidityData = humidityData;
    this.temperatureData = temperatureData;
    this.ignoresRain = ignoresRain;
    this.ignoresSky = ignoresSky;
  }

  @Override
  public String toString()
  {
    return super.toString() + " EnvironmentalData{" + "flowers=" + flowers + ", humidityData=" + humidityData + ", temperatureData=" + temperatureData + ", ignoresRain=" + ignoresRain + ", ignoresSky=" + ignoresSky + '}';
  }

  public Holder<Flowers> getFlowers()
  {
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
    return ignoresRain == that.ignoresRain && ignoresSky == that.ignoresSky && Objects.equals(flowers, that.flowers) && Objects.equals(humidityData, that.humidityData) && Objects.equals(temperatureData, that.temperatureData);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(flowers, humidityData, temperatureData, ignoresRain, ignoresSky);
  }

  public static class Builder
  {

    private final BootstrapContext<BeeSpecies> context;
    private Holder<Flowers> flowers;
    private Holder<TemperaturePreference> temperaturePreference;
    private Holder<TemperatureTolerance> temperatureTolerance;
    private Holder<HumidityPreference> humidityPreference;
    private Holder<HumidityTolerance> humidityTolerance;
    private boolean ignoresRain = false;
    private boolean ignoresSky = false;

    private Builder(BootstrapContext<BeeSpecies> context)
    {
      this.context = context;
      this.flowers = context.lookup(ApicuriousRegistries.FLOWERS).getOrThrow(Flowers.NORMAL_FLOWERS);
      this.temperaturePreference = context.lookup(ApicuriousRegistries.TEMPERATURE_PREFERENCES).getOrThrow(TemperaturePreference.AVERAGE);
      this.temperatureTolerance = context.lookup(ApicuriousRegistries.TEMPERATURE_TOLERANCES).getOrThrow(TemperatureTolerance.NO_TOLERANCE);
      this.humidityPreference = context.lookup(ApicuriousRegistries.HUMIDITY_PREFERENCES).getOrThrow(HumidityPreference.AVERAGE);
      this.humidityTolerance = context.lookup(ApicuriousRegistries.HUMIDITY_TOLERANCES).getOrThrow(HumidityTolerance.NO_TOLERANCE);
    }

    public static Builder create(BootstrapContext<BeeSpecies> context)
    {
      return new Builder(context);
    }

    public Builder withFlowers(ResourceKey<Flowers> flowers)
    {
      this.flowers = context.lookup(ApicuriousRegistries.FLOWERS).getOrThrow(flowers);
      return this;
    }

    public Builder withTemperaturePreference(ResourceKey<TemperaturePreference> temperaturePreference)
    {
      this.temperaturePreference = context.lookup(ApicuriousRegistries.TEMPERATURE_PREFERENCES).getOrThrow(temperaturePreference);
      return this;
    }

    public Builder withTemperatureTolerance(ResourceKey<TemperatureTolerance> temperatureTolerance)
    {
      this.temperatureTolerance = context.lookup(ApicuriousRegistries.TEMPERATURE_TOLERANCES).getOrThrow(temperatureTolerance);
      return this;
    }

    public Builder withHumidityPreference(ResourceKey<HumidityPreference> humidityPreference)
    {
      this.humidityPreference = context.lookup(ApicuriousRegistries.HUMIDITY_PREFERENCES).getOrThrow(humidityPreference);
      return this;
    }

    public Builder withHumidityTolerance(ResourceKey<HumidityTolerance> humidityTolerance)
    {
      this.humidityTolerance = context.lookup(ApicuriousRegistries.HUMIDITY_TOLERANCES).getOrThrow(humidityTolerance);
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
