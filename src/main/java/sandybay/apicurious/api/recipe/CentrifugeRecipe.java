package sandybay.apicurious.api.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import sandybay.apicurious.common.block.centrifuge.blockentity.CentrifugeBE;

import java.util.ArrayList;
import java.util.List;

public record CentrifugeRecipe(ItemStackTemplate input, int duration, List<CentrifugeOutput> outputs)
{
  public static final Codec<CentrifugeRecipe> TYPED_CODEC = RecordCodecBuilder.create(instance -> instance.group(ItemStackTemplate.CODEC.fieldOf("input").forGetter(CentrifugeRecipe::input), Codec.INT.fieldOf("duration").forGetter(CentrifugeRecipe::duration), Codec.list(CentrifugeOutput.CODEC).fieldOf("outputs").forGetter(CentrifugeRecipe::outputs)).apply(instance, CentrifugeRecipe::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, CentrifugeRecipe> STREAM_CODEC = StreamCodec.composite(ItemStackTemplate.STREAM_CODEC, CentrifugeRecipe::input, ByteBufCodecs.INT, CentrifugeRecipe::duration, ByteBufCodecs.collection(ArrayList::new, CentrifugeOutput.STREAM_CODEC), CentrifugeRecipe::outputs, CentrifugeRecipe::new);

  public static Builder recipe(Item item)
  {
    return new Builder(item);
  }

  public static Builder recipe(Holder<Item> item)
  {
    return new Builder(item);
  }

  public boolean matches(CentrifugeBE centrifuge)
  {
    return ItemStack.isSameItem(centrifuge.getInventory().getResource(0).toStack(), input);
  }

  public List<ItemStack> resolve(CentrifugeBE centrifuge)
  {
    List<ItemStack> outputs = new ArrayList<>();
    for (CentrifugeOutput output : this.outputs())
    {
      // getLevel() isn't null since we do that check in CentrifugeBE before calling this method.
      if (centrifuge.getLevel().getRandom().nextFloat() <= output.chance)
      {
        outputs.add(output.output().create());
      }
    }
    return outputs;
  }

  public static class Builder
  {
    private final List<CentrifugeOutput> outputs;
    private ItemStackTemplate input;
    private int duration;

    private Builder(Item input)
    {
      this.input = new ItemStackTemplate(input);
      this.duration = 100;
      this.outputs = new ArrayList<>();
    }

    private Builder(Holder<Item> input)
    {
      this.input = new ItemStackTemplate(input);
      this.duration = 100;
      this.outputs = new ArrayList<>();
    }

    public Builder withInput(ItemStackTemplate input)
    {
      this.input = input;
      return this;
    }

    public Builder withDuration(int duration)
    {
      this.duration = duration;
      return this;
    }

    public Builder withOutput(Item output, float chance)
    {
      this.outputs.add(new CentrifugeOutput(new ItemStackTemplate(output), chance));
      return this;
    }

    public Builder withOutput(Holder<Item> output, float chance)
    {
      this.outputs.add(new CentrifugeOutput(new ItemStackTemplate(output), chance));
      return this;
    }

    public Builder withOutput(ItemStackTemplate output, float chance)
    {
      if (this.outputs.size() > 9)
      {throw new IllegalArgumentException("There can be no more than 9 outputs for any given centrifuge recipe");}
      this.outputs.add(new CentrifugeOutput(output, chance));
      return this;
    }

    public CentrifugeRecipe build()
    {
      if (this.input == null) {throw new IllegalArgumentException("Input for Centrifuge Recipe was empty!");}
      if (this.duration <= 0)
      {throw new IllegalArgumentException("Duration must be equal to or above 1 for Centrifuge Recipe");}
      if (this.outputs.isEmpty() || this.outputs.size() > 9)
      {throw new IllegalArgumentException("The amount of outputs for any given Centrifuge Recipe should been between 1 and 9 possible outputs");}
      return new CentrifugeRecipe(input, duration, outputs);
    }
  }

  public record CentrifugeOutput(ItemStackTemplate output, float chance)
  {
    public static final Codec<CentrifugeOutput> CODEC = RecordCodecBuilder.create(instance -> instance.group(ItemStackTemplate.CODEC.fieldOf("output").forGetter(CentrifugeOutput::output), Codec.FLOAT.fieldOf("chance").forGetter(CentrifugeOutput::chance)).apply(instance, CentrifugeOutput::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, CentrifugeOutput> STREAM_CODEC = StreamCodec.composite(ItemStackTemplate.STREAM_CODEC, CentrifugeOutput::output, ByteBufCodecs.FLOAT, CentrifugeOutput::chance, CentrifugeOutput::new);
  }

}
