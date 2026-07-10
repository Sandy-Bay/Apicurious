package sandybay.apicurious.api.bee.output;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.compress.utils.Lists;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.function.IFunction;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A pool of output entries, generated {@code rolls} times per invocation.
 * <p>
 * <b>Placement of {@code ChanceCondition} matters:</b> conditions attached to this pool
 * (via {@link Builder#when(ICondition)}) are checked <em>once</em>, before any rolls happen —
 * so a {@code ChanceCondition} on the pool gates the entire batch of rolls together (all of
 * them happen, or none of them do). A {@code ChanceCondition} attached to an individual
 * {@link OutputPoolEntry} instead, is re-checked independently on every roll, since entries
 * re-evaluate their own conditions each time {@link OutputPoolEntry#generate} is called.
 * <p>
 * In practice: put a chance on the entry (not the pool) if you want each roll to
 * independently succeed or fail. Put a chance on the pool if you want an all-or-nothing gate
 * around a guaranteed set of rolls (e.g. "20% chance this pool produces anything at all,
 * but if it does, produce 3 guaranteed rolls").
 */
public record OutputPool(int rolls, List<OutputPoolEntry> entries, List<ICondition> conditions,
                         List<IFunction> functions)
{
  public static final Codec<OutputPool> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("rolls").forGetter(OutputPool::rolls), Codec.list(OutputPoolEntry.CODEC).fieldOf("entries").forGetter(OutputPool::entries), Codec.list(ICondition.TYPED_CODEC).fieldOf("conditions").forGetter(OutputPool::conditions), Codec.list(IFunction.TYPED_CODEC).fieldOf("functions").forGetter(OutputPool::functions)).apply(instance, OutputPool::new));

  public static final StreamCodec<RegistryFriendlyByteBuf, OutputPool> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.INT, OutputPool::rolls, ByteBufCodecs.collection(ArrayList::new, OutputPoolEntry.NETWORK_CODEC), OutputPool::entries, ByteBufCodecs.collection(ArrayList::new, ICondition.NETWORK_TYPED_CODEC), OutputPool::conditions, ByteBufCodecs.collection(ArrayList::new, IFunction.NETWORK_TYPED_CODEC), OutputPool::functions, OutputPool::new);

  public static Builder builder()
  {
    return new Builder();
  }

  public List<ItemStack> generate(SimpleBlockHousingBE housing)
  {
    List<ItemStack> output = Lists.newArrayList();
    if (conditions.stream().allMatch(c -> c.test(housing)))
    {
      for (int i = 0; i < rolls; i++)
      {
        List<List<ItemStack>> entryResults = entries.stream().map(e -> e.generate(housing)).toList();
        for (List<ItemStack> result : entryResults)
        {
          output.addAll(result);
        }
      }
      for (IFunction function : functions)
      {
        output = function.resolve(housing, output);
      }
    }
    return output;
  }

  public static class Builder
  {
    private final List<OutputPoolEntry> entries;
    private final List<ICondition> conditions;
    private final List<IFunction> functions;
    private int rolls;

    private Builder()
    {
      this.rolls = 1;
      this.entries = new ArrayList<>();
      this.conditions = new ArrayList<>();
      this.functions = new ArrayList<>();
    }

    public OutputPool.Builder withRolls(int rolls)
    {
      this.rolls = rolls;
      return this;
    }

    public OutputPool.Builder withResult(Consumer<OutputPoolEntry.Builder> output)
    {
      OutputPoolEntry.Builder builder = OutputPoolEntry.builder();
      output.accept(builder);
      this.entries.add(builder.build());
      return this;
    }

    public OutputPool.Builder when(ICondition condition)
    {
      this.conditions.add(condition);
      return this;
    }

    public OutputPool.Builder withFunction(IFunction function)
    {
      this.functions.add(function);
      return this;
    }

    public OutputPool build()
    {
      return new OutputPool(rolls, entries, conditions, functions);
    }

  }
}