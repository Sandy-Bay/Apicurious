package sandybay.apicurious.common.bee.traits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.bee.traits.ITrait;

public class TemperatureTolerance implements ITrait<TemperatureTolerance> {
  public static final TemperatureTolerance NO_TOLERANCE = new TemperatureTolerance(0, "apicurious.tolerance.temperature.none");
  public static final TemperatureTolerance LOW_TOLERANCE = new TemperatureTolerance(1, "apicurious.tolerance.temperature.low");
  public static final TemperatureTolerance HIGH_TOLERANCE = new TemperatureTolerance(2, "apicurious.tolerance.temperature.high");

  public static final Codec<TemperatureTolerance> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("toleranceModifier").forGetter(TemperatureTolerance::getToleranceModifier),
                  Codec.STRING.fieldOf("name").forGetter(TemperatureTolerance::getName)
          ).apply(instance, TemperatureTolerance::new)
  );

  public static final StreamCodec<ByteBuf, TemperatureTolerance> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, TemperatureTolerance::getToleranceModifier,
          ByteBufCodecs.STRING_UTF8, TemperatureTolerance::getName,
          TemperatureTolerance::new
  );

  private final int toleranceModifier;
  private final String name;
  private Component readableName;

  TemperatureTolerance(int toleranceModifier, String name) {
    this.toleranceModifier = toleranceModifier;
    this.name = name;
  }

  public int getToleranceModifier() {
    return toleranceModifier;
  }

  private String getName() {
    return name;
  }

  public Component getReadableName() {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public Codec<TemperatureTolerance> getCodec() {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, TemperatureTolerance> getStreamCodec() {
    return NETWORK_CODEC;
  }
}
