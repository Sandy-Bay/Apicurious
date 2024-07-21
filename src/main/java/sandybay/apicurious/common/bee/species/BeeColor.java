package sandybay.apicurious.common.bee.species;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.bee.IBeeColor;
import sandybay.apicurious.api.util.Coloring;

import javax.annotation.Nullable;

public class BeeColor implements IBeeColor {

  public static final Codec<BeeColor> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.STRING.fieldOf("outlineTint").forGetter(BeeColor::getOutlineTintInternal),
                  Codec.STRING.optionalFieldOf("wingTint", "").forGetter(BeeColor::getWingTintInternal),
                  Codec.STRING.optionalFieldOf("bodyTint", "").forGetter(BeeColor::getBodyTintInternal)
          ).apply(instance, BeeColor::new)
  );
  public static final StreamCodec<ByteBuf, BeeColor> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.STRING_UTF8, BeeColor::getOutlineTintInternal,
          ByteBufCodecs.STRING_UTF8, BeeColor::getWingTintInternal,
          ByteBufCodecs.STRING_UTF8, BeeColor::getBodyTintInternal,
          BeeColor::new
  );

  private final String outlineTint;
  private final String wingTint;
  private final String bodyTint;

  public BeeColor(String outlineTint, @Nullable String wingTint, @Nullable String bodyTint) {
    this.outlineTint = outlineTint;
    this.wingTint = wingTint == null || wingTint.isEmpty() ? outlineTint : wingTint;
    this.bodyTint = bodyTint == null || bodyTint.isEmpty() ? "e9ce17" : bodyTint;
  }

  private String getOutlineTintInternal() {
    return outlineTint;
  }

  private String getWingTintInternal() {
    return wingTint;
  }

  private String getBodyTintInternal() {
    return bodyTint;
  }

  @Override
  public Coloring getOutlineTint() {
    return Coloring.fromHex(outlineTint);
  }

  @Override
  public Coloring getBodyTint() {
    return Coloring.fromHex(bodyTint);
  }

  @Override
  public Coloring getWingTint() {
    return Coloring.fromHex(wingTint);
  }

}