//package sandybay.apicurious.common.old.traits.groups;
//
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import net.minecraft.core.Holder;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.ByteBufCodecs;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.resources.RegistryFixedCodec;
//import sandybay.apicurious.api.registry.ApicuriousRegistries;
//import sandybay.apicurious.common.old.traits.*;
//
//public class EnvironmentalData {
//
//  public static final Codec<EnvironmentalData> CODEC = RecordCodecBuilder.create(
//          instance -> instance.group(
//                  RegistryFixedCodec.create(ApicuriousRegistries.FLOWERS).fieldOf("flowers").forGetter(EnvironmentalData::getFlowers),
//                  HumidityData.CODEC.fieldOf("humidityData").forGetter(EnvironmentalData::getHumidityData),
//                  TemperatureData.CODEC.fieldOf("temperatureData").forGetter(EnvironmentalData::getTemperatureData),
//                  Codec.BOOL.fieldOf("ignoresRain").forGetter(EnvironmentalData::ignoresRain),
//                  Codec.BOOL.fieldOf("ignoresSky").forGetter(EnvironmentalData::ignoresSky)
//          ).apply(instance, EnvironmentalData::new)
//  );
//
//  public static final StreamCodec<RegistryFriendlyByteBuf, EnvironmentalData> NETWORK_CODEC = StreamCodec.composite(
//    ByteBufCodecs.holderRegistry(ApicuriousRegistries.FLOWERS), EnvironmentalData::getFlowers,
//    HumidityData.NETWORK_CODEC, EnvironmentalData::getHumidityData,
//    TemperatureData.NETWORK_CODEC, EnvironmentalData::getTemperatureData,
//    ByteBufCodecs.BOOL, EnvironmentalData::ignoresRain,
//    ByteBufCodecs.BOOL, EnvironmentalData::ignoresSky,
//    EnvironmentalData::new
//  );
//
//  private final Holder<Flowers> flowers;
//
//  private final HumidityData humidityData;
//  private final TemperatureData temperatureData;
//
//  private final boolean ignoresRain;
//  private final boolean ignoresSky;
//
//  private EnvironmentalData(Holder<Flowers> flowers,
//                            HumidityData humidityData, TemperatureData temperatureData,
//                            boolean ignoresRain, boolean ignoresSky) {
//    this.flowers = flowers;
//    this.humidityData = humidityData;
//    this.temperatureData = temperatureData;
//    this.ignoresRain = ignoresRain;
//    this.ignoresSky = ignoresSky;
//  }
//
//  public Holder<Flowers> getFlowers() {
//    return flowers;
//  }
//
//  public HumidityData getHumidityData() {
//    return humidityData;
//  }
//
//  public TemperatureData getTemperatureData() {
//    return temperatureData;
//  }
//
//  public boolean ignoresRain() {
//    return ignoresRain;
//  }
//
//  public boolean ignoresSky() {
//    return ignoresSky;
//  }
//
//  public static class Builder {
//
//    private Flowers flowers = Flowers.NORMAL_FLOWERS.value();
//    private TemperaturePreference temperaturePreference = TemperaturePreference.NORMAL.value();
//    private TemperatureTolerance temperatureTolerance = TemperatureTolerance.NO_TOLERANCE.value();
//    private HumidityPreference humidityPreference = HumidityPreference.NORMAL.value();
//    private HumidityTolerance humidityTolerance = HumidityTolerance.NO_TOLERANCE.value();
//    private boolean ignoresRain = false;
//    private boolean ignoresSky = false;
//
//    private Builder() {}
//
//    public static Builder create() {
//      return new Builder();
//    }
//
//    public Builder withFlowers(Flowers flowers) {
//      this.flowers = flowers;
//      return this;
//    }
//
//    public Builder withTemperaturePreference(TemperaturePreference temperaturePreference) {
//      this.temperaturePreference = temperaturePreference;
//      return this;
//    }
//
//    public Builder withTemperatureTolerance(TemperatureTolerance temperatureTolerance) {
//      this.temperatureTolerance = temperatureTolerance;
//      return this;
//    }
//
//    public Builder withHumidityPreference(HumidityPreference humidityPreference) {
//      this.humidityPreference = humidityPreference;
//      return this;
//    }
//
//    public Builder withHumidityTolerance(HumidityTolerance humidityTolerance) {
//      this.humidityTolerance = humidityTolerance;
//      return this;
//    }
//
//    public Builder ignoresRain() {
//      this.ignoresRain = true;
//      return this;
//    }
//
//    public Builder ignoresSky() {
//      this.ignoresSky = true;
//      return this;
//    }
//
//    public EnvironmentalData build() {
//      return new EnvironmentalData(
//              Holder.direct(flowers),
//              new HumidityData(Holder.direct(humidityPreference), Holder.direct(humidityTolerance)),
//              new TemperatureData(Holder.direct(temperaturePreference), Holder.direct(temperatureTolerance)),
//              ignoresRain, ignoresSky
//      );
//    }
//  }
//}
