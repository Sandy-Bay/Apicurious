package sandybay.apicurious.api.bee.output;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import org.apache.commons.compress.utils.Lists;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.function.IFunction;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public record OutputPoolEntry(List<OutputResult> outputs, List<ICondition> conditions, List<IFunction> functions)
{
  public static final Codec<OutputPoolEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.list(OutputResult.CODEC).fieldOf("outputs").forGetter(OutputPoolEntry::outputs), Codec.list(ICondition.TYPED_CODEC).fieldOf("conditions").forGetter(OutputPoolEntry::conditions), Codec.list(IFunction.TYPED_CODEC).fieldOf("functions").forGetter(OutputPoolEntry::functions)).apply(instance, OutputPoolEntry::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, OutputPoolEntry> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.collection(ArrayList::new, OutputResult.NETWORK_CODEC), OutputPoolEntry::outputs, ByteBufCodecs.collection(ArrayList::new, ICondition.NETWORK_TYPED_CODEC), OutputPoolEntry::conditions, ByteBufCodecs.collection(ArrayList::new, IFunction.NETWORK_TYPED_CODEC), OutputPoolEntry::functions, OutputPoolEntry::new);

  public static Builder builder()
  {
    return new Builder();
  }

  public List<ItemStack> generate(SimpleBlockHousingBE context)
  {
    List<ItemStack> stacks = Lists.newArrayList();
    if (conditions.stream().allMatch(c -> c.test(context)))
    {
      stacks = outputs.stream().map(OutputResult::output).map(ItemStackTemplate::create).toList();
      for (IFunction function : functions)
      {
        stacks = function.resolve(context, stacks);
      }
    }
    return stacks;
  }

  public static class Builder
  {

    private final List<OutputResult> outputs;
    private final List<ICondition> conditions;
    private final List<IFunction> functions;

    private Builder()
    {
      this.outputs = Lists.newArrayList();
      this.conditions = Lists.newArrayList();
      this.functions = Lists.newArrayList();
    }

    public Builder withResult(Item output)
    {
      this.outputs.add(OutputResult.simple(output));
      return this;
    }

    public Builder withResult(Item output, int stackSize)
    {
      this.outputs.add(OutputResult.simple(output, stackSize));
      return this;
    }

    public Builder withResult(Consumer<OutputResult.Builder> consumer)
    {
      OutputResult.Builder builder = OutputResult.builder();
      consumer.accept(builder);
      this.outputs.add(builder.build());
      return this;
    }

    public Builder when(ICondition condition)
    {
      this.conditions.add(condition);
      return this;
    }

    public Builder withFunction(IFunction function)
    {
      this.functions.add(function);
      return this;
    }

    public OutputPoolEntry build()
    {
      return new OutputPoolEntry(this.outputs, this.conditions, this.functions);
    }


  }
}
