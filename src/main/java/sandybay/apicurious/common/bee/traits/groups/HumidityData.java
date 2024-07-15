package sandybay.apicurious.common.bee.traits.groups;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.common.bee.traits.HumidityPreference;
import sandybay.apicurious.common.bee.traits.HumidityTolerance;

public record HumidityData(HumidityPreference preference, HumidityTolerance tolerance) {
  public static final Codec<HumidityData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  HumidityPreference.CODEC.fieldOf("preference").forGetter(HumidityData::preference),
                  HumidityTolerance.CODEC.fieldOf("tolerance").forGetter(HumidityData::tolerance)
          ).apply(instance, HumidityData::new)
  );

  public static final StreamCodec<ByteBuf, HumidityData> NETWORK_CODEC = StreamCodec.composite(
          HumidityPreference.NETWORK_CODEC, HumidityData::preference,
          HumidityTolerance.NETWORK_CODEC, HumidityData::tolerance,
          HumidityData::new
  );
}
