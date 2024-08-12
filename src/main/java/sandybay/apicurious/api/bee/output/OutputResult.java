package sandybay.apicurious.api.bee.output;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record OutputResult(ItemStack output)
{
  public static final Codec<OutputResult> CODEC = RecordCodecBuilder.create(instance ->
          instance.group(
                  ItemStack.CODEC.fieldOf("output").forGetter(OutputResult::output)
          ).apply(instance, OutputResult::new)
  );
  public static final StreamCodec<RegistryFriendlyByteBuf, OutputResult> NETWORK_CODEC = StreamCodec.composite(
          ItemStack.STREAM_CODEC, OutputResult::output,
          OutputResult::new
  );
}
