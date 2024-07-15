package sandybay.apicurious.common.bee.traits.groups;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.common.bee.traits.TemperaturePreference;
import sandybay.apicurious.common.bee.traits.TemperatureTolerance;

public record TemperatureData(TemperaturePreference preference, TemperatureTolerance tolerance) {
  public static final Codec<TemperatureData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  TemperaturePreference.CODEC.fieldOf("preference").forGetter(TemperatureData::preference),
                  TemperatureTolerance.CODEC.fieldOf("tolerance").forGetter(TemperatureData::tolerance)
          ).apply(instance, TemperatureData::new)
  );

  public static final StreamCodec<ByteBuf, TemperatureData> NETWORK_CODEC = StreamCodec.composite(
          TemperaturePreference.NETWORK_CODEC, TemperatureData::preference,
          TemperatureTolerance.NETWORK_CODEC, TemperatureData::tolerance,
          TemperatureData::new
  );
}
