package sandybay.apicurious.api.bee.output;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

public record OutputResult(ItemStackTemplate output)
{
  public static final Codec<OutputResult> CODEC = RecordCodecBuilder.create(instance -> instance.group(ItemStackTemplate.CODEC.fieldOf("output").forGetter(OutputResult::output)).apply(instance, OutputResult::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, OutputResult> NETWORK_CODEC = StreamCodec.composite(ItemStackTemplate.STREAM_CODEC, OutputResult::output, OutputResult::new);

  public static Builder builder()
  {
    return new Builder();
  }

  public static OutputResult simple(Item item)
  {
    return builder().withItemstack(new ItemStackTemplate(item)).build();
  }

  public static OutputResult simple(Item item, int stackSize)
  {
    return builder().withItemstack(new ItemStackTemplate(item, stackSize)).build();
  }

  public static class Builder
  {
    private ItemStackTemplate stack;

    private Builder() {}

    public Builder withItemstack(ItemStackTemplate stack)
    {
      this.stack = stack;
      return this;
    }

    public OutputResult build()
    {
      return new OutputResult(this.stack);
    }
  }
}
