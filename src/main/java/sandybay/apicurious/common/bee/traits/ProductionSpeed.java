package sandybay.apicurious.common.bee.traits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.bee.traits.ITrait;

public class ProductionSpeed implements ITrait<ProductionSpeed> {
  public static final ProductionSpeed SLOWEST = new ProductionSpeed(600, "apicurious.production_speed.slowest");
  public static final ProductionSpeed SLOWER = new ProductionSpeed(500, "apicurious.production_speed.slower");
  public static final ProductionSpeed SLOW = new ProductionSpeed(400, "apicurious.production_speed.slow");
  public static final ProductionSpeed NORMAL = new ProductionSpeed(300, "apicurious.production_speed.normal");
  public static final ProductionSpeed FAST = new ProductionSpeed(200, "apicurious.production_speed.fast");
  public static final ProductionSpeed FASTER = new ProductionSpeed(100, "apicurious.production_speed.faster");
  public static final ProductionSpeed FASTEST = new ProductionSpeed(50, "apicurious.production_speed.fastest");

  public static final Codec<ProductionSpeed> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("productionDuration").forGetter(ProductionSpeed::getProductionDuration),
                  Codec.STRING.fieldOf("name").forGetter(ProductionSpeed::getName)
          ).apply(instance, ProductionSpeed::new)
  );

  public static final StreamCodec<ByteBuf, ProductionSpeed> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, ProductionSpeed::getProductionDuration,
          ByteBufCodecs.STRING_UTF8, ProductionSpeed::getName,
          ProductionSpeed::new
  );

  private final int productionDuration;
  private final String name;
  private Component readableName;

  ProductionSpeed(int productionDuration, String name) {
    this.productionDuration = productionDuration;
    this.name = name;
  }

  public int getProductionDuration() {
    return productionDuration;
  }

  private String getName() {
    return name;
  }

  public Component getReadableName() {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public Codec<ProductionSpeed> getCodec() {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, ProductionSpeed> getStreamCodec() {
    return NETWORK_CODEC;
  }
}
