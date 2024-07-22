//package sandybay.apicurious.common.old.traits;
//
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import io.netty.buffer.ByteBuf;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.codec.ByteBufCodecs;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.resources.ResourceKey;
//import sandybay.apicurious.Apicurious;
//import sandybay.apicurious.old.traits.ITrait;
//import sandybay.apicurious.api.registry.ApicuriousRegistries;
//
//public class Area implements ITrait<Area> {
//
//  public static void load() {}
//
//  public static final ApicuriousHolder<Area> SMALLEST = create(1,1,"smallest"); // 3x3x3,
//  public static final ApicuriousHolder<Area> SMALLER = create(2,2,"smaller");   // 5x5x5
//  public static final ApicuriousHolder<Area> SMALL = create(3,3,"small");       // 7x7x7
//  public static final ApicuriousHolder<Area> AVERAGE = create(4,4,"average");   // 9x9x9
//  public static final ApicuriousHolder<Area> LARGE = create(5,5,"large");       // 11x11x11
//  public static final ApicuriousHolder<Area> LARGER = create(6,6,"larger");     // 13x13x13
//  public static final ApicuriousHolder<Area> LARGEST = create(7,7,"largest");   // 15x15x15
//
//  private static ApicuriousHolder<Area> create(int xz, int y, String name) {
//    ResourceKey<Area> key = ResourceKey.create(ApicuriousRegistries.AREAS, Apicurious.createResourceLocation(name));
//    Area area = new Area(xz, y, "apicurious.area." + name);
//    return new ApicuriousHolder<>(key, area);
//  }
//
//  public static final Codec<Area> CODEC = RecordCodecBuilder.create(
//          instance -> instance.group(
//                  Codec.INT.fieldOf("xzOffset").forGetter(Area::getXZOffset),
//                  Codec.INT.fieldOf("yOffset").forGetter(Area::getYOffset),
//                  Codec.STRING.fieldOf("name").forGetter(Area::getName)
//          ).apply(instance, Area::new)
//  );
//  public static final StreamCodec<ByteBuf, Area> NETWORK_CODEC = StreamCodec.composite(
//          ByteBufCodecs.INT, Area::getXZOffset,
//          ByteBufCodecs.INT, Area::getYOffset,
//          ByteBufCodecs.STRING_UTF8, Area::getName,
//          Area::new
//  );
//
//  private final int xzOffset;
//  private final int yOffset;
//  private final String name;
//  private Component readableName;
//
//  public Area(int xzOffset, int yOffset, String name) {
//    this.xzOffset = xzOffset;
//    this.yOffset = yOffset;
//    this.name = name;
//  }
//
//  public int getXZOffset() {
//    return xzOffset;
//  }
//
//  public int getYOffset() {
//    return yOffset;
//  }
//
//  private String getName() {
//    return name;
//  }
//
//  @Override
//  public Component getReadableName() {
//    if (readableName == null) readableName = Component.translatable(this.name);
//    return readableName;
//  }
//
//  @Override
//  public Codec<Area> getCodec() {
//    return CODEC;
//  }
//
//  @Override
//  public StreamCodec<ByteBuf, Area> getStreamCodec() {
//    return NETWORK_CODEC;
//  }
//}
