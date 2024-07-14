package sandybay.apicurious.common.bee.traits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.bee.traits.ITrait;

public class PollinationRate implements ITrait<PollinationRate> {
  public static final PollinationRate SLOWEST = new PollinationRate(600, "apicurious.pollination_rate.slowest");
  public static final PollinationRate SLOWER = new PollinationRate(500, "apicurious.pollination_rate.slower");
  public static final PollinationRate SLOW = new PollinationRate(400, "apicurious.pollination_rate.slow");
  public static final PollinationRate NORMAL = new PollinationRate(300, "apicurious.pollination_rate.normal");
  public static final PollinationRate FAST = new PollinationRate(200, "apicurious.pollination_rate.fast");
  public static final PollinationRate FASTER = new PollinationRate(100, "apicurious.pollination_rate.faster");
  public static final PollinationRate FASTEST = new PollinationRate(50, "apicurious.pollination_rate.fastest");

  public static final Codec<PollinationRate> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("pollinationDuration").forGetter(PollinationRate::getPollinationDuration),
                  Codec.STRING.fieldOf("name").forGetter(PollinationRate::getName)
          ).apply(instance, PollinationRate::new)
  );

  public static final StreamCodec<ByteBuf, PollinationRate> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, PollinationRate::getPollinationDuration,
          ByteBufCodecs.STRING_UTF8, PollinationRate::getName,
          PollinationRate::new
  );

  private final int pollinationDuration;
  private final String name;
  private Component readableName;

  PollinationRate(int pollinationDuration, String name) {
    this.pollinationDuration = pollinationDuration;
    this.name = name;
  }

  public int getPollinationDuration() {
    return pollinationDuration;
  }

  private String getName() {
    return name;
  }

  public Component getReadableName() {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public Codec<PollinationRate> getCodec() {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, PollinationRate> getStreamCodec() {
    return NETWORK_CODEC;
  }
}
