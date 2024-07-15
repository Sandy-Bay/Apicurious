package sandybay.apicurious.common.bee.traits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.bee.traits.ITrait;

public class Area implements ITrait<Area> {

  public static final Area SMALLEST = new Area(1, 1, "apicurious.area.smallest"); // 3x3x3
  public static final Area SMALLER = new Area(2, 2, "apicurious.area.smaller");   // 5x5x5
  public static final Area SMALL = new Area(3, 3, "apicurious.area.small");       // 7x7x7
  public static final Area AVERAGE = new Area(4, 4, "apicurious.area.average");   // 9x9x9
  public static final Area LARGE = new Area(5, 5, "apicurious.area.large");       // 11x11x11
  public static final Area LARGER = new Area(6, 6, "apicurious.area.larger");     // 13x13x13
  public static final Area LARGEST = new Area(7, 7, "apicurious.area.largest");   // 15x15x15

  public static final Codec<Area> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("xzOffset").forGetter(Area::getXZOffset),
                  Codec.INT.fieldOf("yOffset").forGetter(Area::getYOffset),
                  Codec.STRING.fieldOf("name").forGetter(Area::getName)
          ).apply(instance, Area::new)
  );
  public static final StreamCodec<ByteBuf, Area> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, Area::getXZOffset,
          ByteBufCodecs.INT, Area::getYOffset,
          ByteBufCodecs.STRING_UTF8, Area::getName,
          Area::new
  );

  private final int xzOffset;
  private final int yOffset;
  private final String name;
  private Component readableName;

  public Area(int xzOffset, int yOffset, String name) {
    this.xzOffset = xzOffset;
    this.yOffset = yOffset;
    this.name = name;
  }

  public int getXZOffset() {
    return xzOffset;
  }

  public int getYOffset() {
    return yOffset;
  }

  private String getName() {
    return name;
  }

  @Override
  public Component getReadableName() {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public Codec<Area> getCodec() {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, Area> getStreamCodec() {
    return NETWORK_CODEC;
  }
}
