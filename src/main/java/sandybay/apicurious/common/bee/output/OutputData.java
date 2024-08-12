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
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.compress.utils.Lists;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.output.IBeeOutput;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public record OutputData(Holder<OutputTable> outputTable)
{
  public static Codec<OutputData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
          OutputTable.CODEC.fieldOf("outputTable").forGetter(OutputData::outputTable)
  ).apply(instance, OutputData::new));

  public static StreamCodec<RegistryFriendlyByteBuf, OutputData> NETWORK_CODEC = StreamCodec.composite(
          OutputTable.NETWORK_CODEC, OutputData::outputTable,
          OutputData::new
  );

  public List<ItemStack> generate(SimpleBlockHousingBE housing) {
    return outputTable.value().generate(housing);
  }

  public static class Builder
  {
    private final BootstrapContext<IAllele<?>> context;
    private OutputTable table = OutputTable.EMPTY;

    public Builder(BootstrapContext<IAllele<?>> context)
    {
      this.context = context;
    }

    public static OutputData.Builder create(BootstrapContext<IAllele<?>> context)
    {
      return new OutputData.Builder(context);
    }

    public OutputData.Builder withTable(Consumer<OutputTable.Builder> consumer)
    {
      OutputTable.Builder builder = OutputTable.builder();
      consumer.accept(builder);
      this.table = builder.build();
      return this;
    }

    public OutputData build()
    {
      return new OutputData(Holder.direct(table));
    }
  }
}
