package sandybay.apicurious.old.traits;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;

public interface ITrait<T> {
  Component getReadableName();
  Codec<T> getCodec();
  StreamCodec<ByteBuf, T> getStreamCodec();
}
