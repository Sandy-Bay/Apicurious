package sandybay.apicurious.common.bee.traits.groups;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.common.bee.traits.HumidityPreference;
import sandybay.apicurious.common.bee.traits.HumidityTolerance;
import sandybay.apicurious.common.bee.traits.TemperaturePreference;
import sandybay.apicurious.common.bee.traits.TemperatureTolerance;

public class EnvironmentalData {

  public static final Codec<EnvironmentalData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  HumidityPreference.CODEC.fieldOf("humidityPreference").forGetter(EnvironmentalData::getHumidityPreference),
                  HumidityTolerance.CODEC.fieldOf("humidityTolerance").forGetter(EnvironmentalData::getHumidityTolerance),
                  TemperaturePreference.CODEC.fieldOf("temperaturePreference").forGetter(EnvironmentalData::getTemperaturePreference),
                  TemperatureTolerance.CODEC.fieldOf("temperatureTolerance").forGetter(EnvironmentalData::getTemperatureTolerance),
                  Codec.BOOL.fieldOf("ignoresRain").forGetter(EnvironmentalData::ignoresRain),
                  Codec.BOOL.fieldOf("ignoresSky").forGetter(EnvironmentalData::ignoresSky)
          ).apply(instance, EnvironmentalData::new)
  );

  public static final StreamCodec<ByteBuf, EnvironmentalData> NETWORK_CODEC = StreamCodec.composite(
    HumidityPreference.NETWORK_CODEC, EnvironmentalData::getHumidityPreference,
    HumidityTolerance.NETWORK_CODEC, EnvironmentalData::getHumidityTolerance,
    TemperaturePreference.NETWORK_CODEC, EnvironmentalData::getTemperaturePreference,
    TemperatureTolerance.NETWORK_CODEC, EnvironmentalData::getTemperatureTolerance,
    ByteBufCodecs.BOOL, EnvironmentalData::ignoresRain,
    ByteBufCodecs.BOOL, EnvironmentalData::ignoresSky,
    EnvironmentalData::new
  );

  private final HumidityPreference humidityPreference;
  private final HumidityTolerance humidityTolerance;

  private final TemperaturePreference temperaturePreference;
  private final TemperatureTolerance temperatureTolerance;

  private final boolean ignoresRain;
  private final boolean ignoresSky;

  private EnvironmentalData(HumidityPreference humidityPreference, HumidityTolerance humidityTolerance,
                            TemperaturePreference temperaturePreference, TemperatureTolerance temperatureTolerance,
                            boolean ignoresRain, boolean ignoresSky) {
    this.humidityPreference = humidityPreference;
    this.humidityTolerance = humidityTolerance;
    this.temperaturePreference = temperaturePreference;
    this.temperatureTolerance = temperatureTolerance;
    this.ignoresRain = ignoresRain;
    this.ignoresSky = ignoresSky;
  }

  public HumidityPreference getHumidityPreference() {
    return humidityPreference;
  }

  public HumidityTolerance getHumidityTolerance() {
    return humidityTolerance;
  }

  public TemperaturePreference getTemperaturePreference() {
    return temperaturePreference;
  }

  public TemperatureTolerance getTemperatureTolerance() {
    return temperatureTolerance;
  }

  public boolean ignoresRain() {
    return ignoresRain;
  }

  public boolean ignoresSky() {
    return ignoresSky;
  }

  public static class Builder {

    private TemperaturePreference temperaturePreference = TemperaturePreference.NORMAL;
    private TemperatureTolerance temperatureTolerance = TemperatureTolerance.NO_TOLERANCE;
    private HumidityPreference humidityPreference = HumidityPreference.NORMAL;
    private HumidityTolerance humidityTolerance = HumidityTolerance.NO_TOLERANCE;
    private boolean ignoresRain = false;
    private boolean ignoresSky = false;

    private Builder() {}

    public static Builder create() {
      return new Builder();
    }

    public Builder withTemperaturePreference(TemperaturePreference temperaturePreference) {
      this.temperaturePreference = temperaturePreference;
      return this;
    }

    public Builder withTemperatureTolerance(TemperatureTolerance temperatureTolerance) {
      this.temperatureTolerance = temperatureTolerance;
      return this;
    }

    public Builder withHumidityPreference(HumidityPreference humidityPreference) {
      this.humidityPreference = humidityPreference;
      return this;
    }

    public Builder withHumidityTolerance(HumidityTolerance humidityTolerance) {
      this.humidityTolerance = humidityTolerance;
      return this;
    }

    public Builder ignoresRain() {
      this.ignoresRain = true;
      return this;
    }

    public Builder ignoresSky() {
      this.ignoresSky = true;
      return this;
    }

    public EnvironmentalData build() {
      return new EnvironmentalData(
              humidityPreference, humidityTolerance,
              temperaturePreference, temperatureTolerance,
              ignoresRain, ignoresSky
      );
    }
  }
}
