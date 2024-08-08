package sandybay.apicurious.common.bee.output;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.compress.utils.Lists;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.bee.output.IBeeOutput;
import sandybay.apicurious.common.bee.species.BeeSpecies;

import java.util.ArrayList;
import java.util.List;

public class OutputData implements IBeeOutput
{
  public static Codec<OutputData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
          Codec.list(ItemStack.CODEC, 0, 7).fieldOf("outputs").forGetter(OutputData::getOutputs)
  ).apply(instance, OutputData::new));

  public static StreamCodec<RegistryFriendlyByteBuf, OutputData> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.collection(ArrayList::new, ItemStack.STREAM_CODEC, 7), OutputData::getOutputs,
          OutputData::new
  );

  private final List<ItemStack> outputs;

  public OutputData(List<ItemStack> outputs)
  {
    this.outputs = outputs;
  }

  @Override
  public List<ItemStack> getOutputs()
  {
    return outputs;
  }

  public static class Builder
  {
    private final BootstrapContext<IAllele<?>> context;
    private final List<ItemStack> outputs;

    public Builder(BootstrapContext<IAllele<?>> context)
    {
      this.context = context;
      this.outputs = Lists.newArrayList();
    }

    public static OutputData.Builder create(BootstrapContext<IAllele<?>> context)
    {
      return new OutputData.Builder(context);
    }

    public OutputData.Builder withStack(Item item, int count)
    {
      outputs.add(new ItemStack(item, count));
      return this;
    }

    public OutputData.Builder withStack(Holder<Item> item, int count)
    {
      outputs.add(new ItemStack(item, count));
      return this;
    }

    public OutputData.Builder withStack(ResourceKey<Item> item, int count)
    {
      HolderGetter<Item> provider = context.lookup(Registries.ITEM);
      Holder<Item> holder = provider.getOrThrow(item);
      outputs.add(new ItemStack(holder, count));
      return this;
    }

    public OutputData build()
    {
      return new OutputData(outputs);
    }
  }
}
