package sandybay.apicurious.api.bee.genetic;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public interface ITrait<T extends ITrait<T>>
{

  ResourceLocation getTraitKey();

  Component getReadableName();

  Codec<T> getCodec();

  StreamCodec<ByteBuf, T> getStreamCodec();

  boolean isDominantTrait();
}
