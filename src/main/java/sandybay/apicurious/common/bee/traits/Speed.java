package sandybay.apicurious.common.bee.traits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.bee.traits.ITrait;

/**
 * Speed is a trait inherited by Bees which alters the chance of a bee to produce output.
 * The faster the speed, the higher the chance of a bee creating a product per bee cycle update.
 */
public class Speed implements ITrait<Speed> {
  public static final Speed SLOWEST = new Speed(-0.7f, "apicurious.production_speed.slowest");
  public static final Speed SLOWER = new Speed(-0.4f, "apicurious.production_speed.slower");
  public static final Speed SLOW = new Speed(-0.2f, "apicurious.production_speed.slow");
  public static final Speed NORMAL = new Speed(0.0f, "apicurious.production_speed.normal");
  public static final Speed FAST = new Speed(0.2f, "apicurious.production_speed.fast");
  public static final Speed FASTER = new Speed(0.4f, "apicurious.production_speed.faster");
  public static final Speed FASTEST = new Speed(0.7f, "apicurious.production_speed.fastest");

  public static final Codec<Speed> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.FLOAT.fieldOf("productionModifier").forGetter(Speed::getProductionModifier),
                  Codec.STRING.fieldOf("name").forGetter(Speed::getName)
          ).apply(instance, Speed::new)
  );

  public static final StreamCodec<ByteBuf, Speed> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.FLOAT, Speed::getProductionModifier,
          ByteBufCodecs.STRING_UTF8, Speed::getName,
          Speed::new
  );

  private final float productionModifier;
  private final String name;
  private Component readableName;

  Speed(float productionModifier, String name) {
    this.productionModifier = productionModifier;
    this.name = name;
  }

  public float getProductionModifier() {
    return productionModifier;
  }

  private String getName() {
    return name;
  }

  public Component getReadableName() {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public Codec<Speed> getCodec() {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, Speed> getStreamCodec() {
    return NETWORK_CODEC;
  }
}
