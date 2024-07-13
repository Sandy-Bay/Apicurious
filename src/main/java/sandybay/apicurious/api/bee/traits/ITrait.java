package sandybay.apicurious.api.bee.traits;

import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;

public interface ITrait<T> {
  Component getReadableName();
  Codec<T> getCodec();
  StreamCodec<FriendlyByteBuf, T> getStreamCodec();
}
