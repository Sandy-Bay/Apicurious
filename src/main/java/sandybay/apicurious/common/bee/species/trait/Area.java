package sandybay.apicurious.common.bee.species.trait;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.ITrait;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class Area implements ITrait<Area> {


  public static final ResourceKey<Area> SMALLEST = ResourceKey.create(ApicuriousRegistries.AREAS, Apicurious.createResourceLocation("smallest"));
  public static final ResourceKey<Area> SMALLER = ResourceKey.create(ApicuriousRegistries.AREAS, Apicurious.createResourceLocation("smaller"));
  public static final ResourceKey<Area> SMALL = ResourceKey.create(ApicuriousRegistries.AREAS, Apicurious.createResourceLocation("small"));
  public static final ResourceKey<Area> AVERAGE = ResourceKey.create(ApicuriousRegistries.AREAS, Apicurious.createResourceLocation("average"));
  public static final ResourceKey<Area> LARGE = ResourceKey.create(ApicuriousRegistries.AREAS, Apicurious.createResourceLocation("large"));
  public static final ResourceKey<Area> LARGER = ResourceKey.create(ApicuriousRegistries.AREAS, Apicurious.createResourceLocation("larger"));
  public static final ResourceKey<Area> LARGEST = ResourceKey.create(ApicuriousRegistries.AREAS, Apicurious.createResourceLocation("largest"));


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
