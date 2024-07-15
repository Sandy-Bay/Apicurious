package sandybay.apicurious.common.bee.traits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.bee.traits.ITrait;

public class Pollination implements ITrait<Pollination> {
  public static final Pollination SLOWEST = new Pollination(0.05f, "apicurious.pollination.slowest");
  public static final Pollination SLOWER = new Pollination(0.1f, "apicurious.pollination.slower");
  public static final Pollination SLOW = new Pollination(0.15f, "apicurious.pollination.slow");
  public static final Pollination AVERAGE = new Pollination(0.2f, "apicurious.pollination.average");
  public static final Pollination FAST = new Pollination(0.25f, "apicurious.pollination.fast");
  public static final Pollination FASTER = new Pollination(0.3f, "apicurious.pollination.faster");
  public static final Pollination FASTEST = new Pollination(0.35f, "apicurious.pollination.fastest");

  public static final Codec<Pollination> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.FLOAT.fieldOf("pollinationChance").forGetter(Pollination::getPollinationChance),
                  Codec.STRING.fieldOf("name").forGetter(Pollination::getName)
          ).apply(instance, Pollination::new)
  );

  public static final StreamCodec<ByteBuf, Pollination> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.FLOAT, Pollination::getPollinationChance,
          ByteBufCodecs.STRING_UTF8, Pollination::getName,
          Pollination::new
  );

  private final float pollinationChance;
  private final String name;
  private Component readableName;

  Pollination(float pollinationChance, String name) {
    this.pollinationChance = pollinationChance;
    this.name = name;
  }

  public float getPollinationChance() {
    return pollinationChance;
  }

  private String getName() {
    return name;
  }

  public Component getReadableName() {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public Codec<Pollination> getCodec() {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, Pollination> getStreamCodec() {
    return NETWORK_CODEC;
  }
}
