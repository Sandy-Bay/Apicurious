package sandybay.apicurious.api.bee.output;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public record OutputTable(List<OutputPool> pools)
{
  public static final OutputTable EMPTY = new OutputTable(Lists.newArrayList());
  public static final Codec<OutputTable> TYPED_CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.list(OutputPool.CODEC).fieldOf("pools").forGetter(OutputTable::pools)).apply(instance, OutputTable::new));
  public static final Codec<Holder<OutputTable>> CODEC = RegistryFileCodec.create(ApicuriousRegistries.OUTPUT_TABLES, TYPED_CODEC);
  public static final StreamCodec<RegistryFriendlyByteBuf, OutputTable> DIRECT_NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.collection(ArrayList::new, OutputPool.NETWORK_CODEC), OutputTable::pools, OutputTable::new);
  public static final StreamCodec<RegistryFriendlyByteBuf, Holder<OutputTable>> NETWORK_CODEC = ByteBufCodecs.holder(ApicuriousRegistries.OUTPUT_TABLES, DIRECT_NETWORK_CODEC);
  private static final Logger LOGGER = LogUtils.getLogger();

  public static Builder builder()
  {
    return new Builder();
  }

  public List<ItemStack> generate(SimpleBlockHousingBE housing)
  {
    List<ItemStack> generated = Lists.newArrayList();
    for (OutputPool pool : pools())
    {
      generated.addAll(pool.generate(housing));
    }
    return generated;
  }

  public static class Builder
  {
    private final List<OutputPool> pools;

    private Builder()
    {
      this.pools = new ArrayList<>();
    }

    public Builder withPool(Consumer<OutputPool.Builder> consumer)
    {
      OutputPool.Builder builder = OutputPool.builder();
      consumer.accept(builder);
      this.pools.add(builder.build());
      return this;
    }

    public OutputTable build()
    {
      return new OutputTable(pools);
    }
  }

}
