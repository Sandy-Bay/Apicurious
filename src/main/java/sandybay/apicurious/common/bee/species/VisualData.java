package sandybay.apicurious.common.bee.species;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.util.ApicuriousConstants;

public class VisualData {

  public static final VisualData DEFAULT = VisualData.Builder.create().build();

  public static final Codec<VisualData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
            BeeColor.CODEC.optionalFieldOf("beeColor", ApicuriousConstants.UNDEFINED).forGetter(VisualData::getBeeColor),
            Codec.BOOL.optionalFieldOf("hasEffect", false).forGetter(VisualData::hasEffect),
            Codec.BOOL.optionalFieldOf("isTintable", true).forGetter(VisualData::isTintable)
          ).apply(instance, VisualData::new)
  );

  public static final StreamCodec<ByteBuf, VisualData> NETWORK_CODEC = StreamCodec.composite(
          BeeColor.NETWORK_CODEC, VisualData::getBeeColor,
          ByteBufCodecs.BOOL, VisualData::hasEffect,
          ByteBufCodecs.BOOL, VisualData::isTintable,
          VisualData::new
  );

  private final BeeColor beeColor;
  private final boolean hasEffect;
  private final boolean isTintable;

  public VisualData(BeeColor beeColor, boolean hasEffect, boolean isTintable) {
    this.beeColor = beeColor;
    this.hasEffect = hasEffect;
    this.isTintable = isTintable;
  }

  public BeeColor getBeeColor() {
    return beeColor;
  }

  public boolean hasEffect() {
    return hasEffect;
  }

  public boolean isTintable() {
    return isTintable;
  }

  public static class Builder {
    private String outlineTint = ApicuriousConstants.UNDEFIEND_OUTLINE;
    private String wingTint = ApicuriousConstants.UNDEFINED_WING;
    private String bodyTint = ApicuriousConstants.DEFAULT_BODY;
    private BeeColor beeColor;
    private boolean hasPredefinedColor;
    private boolean hasEffect = false;
    private boolean isTintable = true;

    private Builder() {}

    public static Builder create() {
      return new Builder();
    }

    public Builder withBeeColor(BeeColor predefined) {
      this.beeColor = predefined;
      this.hasPredefinedColor = true;
      return this;
    }

    public Builder withOutlineTint(String outlineTint) {
      this.outlineTint = outlineTint;
      return this;
    }

    public Builder withWingTint(String wingTint) {
      this.wingTint = wingTint;
      return this;
    }

    public Builder withBodyTint(String bodyTint) {
      this.bodyTint = bodyTint;
      return this;
    }

    public Builder hasEffect() {
      this.hasEffect = true;
      return this;
    }

    public Builder isNotTintable() {
      this.isTintable = false;
      return this;
    }

    public VisualData build() {
      return hasPredefinedColor ?
              new VisualData(beeColor, hasEffect, isTintable) :
              new VisualData(new BeeColor(outlineTint, wingTint, bodyTint), hasEffect, isTintable);
    }
  }
}
