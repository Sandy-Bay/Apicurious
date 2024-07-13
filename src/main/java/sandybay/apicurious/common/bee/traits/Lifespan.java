package sandybay.apicurious.common.bee.traits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.bee.traits.ITrait;

public class Lifespan implements ITrait<Lifespan> {
  public static final Lifespan SHORTEST = new Lifespan(400, "apicurious.lifespan.shortest");
  public static final Lifespan SHORTER = new Lifespan(900, "apicurious.lifespan.shorter");
  public static final Lifespan SHORT = new Lifespan(1200, "apicurious.lifespan.short");
  public static final Lifespan NORMAL = new Lifespan(1500, "apicurious.lifespan.normal");
  public static final Lifespan LONG = new Lifespan(1800, "apicurious.lifespan.long");
  public static final Lifespan LONGER = new Lifespan(2100, "apicurious.lifespan.longer");
  public static final Lifespan LONGEST = new Lifespan(2400, "apicurious.lifespan.longest");

  public static final Codec<Lifespan> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("lifespan").forGetter(Lifespan::getLifespan),
                  Codec.STRING.fieldOf("name").forGetter(Lifespan::getName)
          ).apply(instance, Lifespan::new)
  );

  private final int lifespan;
  private final String name;
  private Component readableName;

  public Lifespan(int lifespan, String name) {
    this.lifespan = lifespan;
    this.name = name;
  }

  public int getLifespan() {
    return lifespan;
  }

  private String getName() {
    return name;
  }

  public Component getReadableName() {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public Codec<Lifespan> getCodec() {
    return CODEC;
  }

  @Override
  public StreamCodec<FriendlyByteBuf, Lifespan> getStreamCodec() {
    return null;
  }
}
