package sandybay.apicurious.api.bee.genetic.mutation;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;
import sandybay.apicurious.common.item.frame.FrameItem;

import java.util.List;

public interface IMutation
{
  Codec<IMutation> TYPED_CODEC = ApicuriousRegistries.MUTATION_TYPE_REGISTRY
          .byNameCodec()
          .dispatch("type", IMutation::getType, MutationType::codec);

  MutationType getType();

  Holder<IAllele<?>> getOutput();

  boolean test(SimpleBlockHousingBE housing);

  default boolean isValidMutation(List<ItemStack> frames, float baseChance, RandomSource random)
  {
    float mutationChance = baseChance;
    for (ItemStack frame : frames)
    {
      FrameItem item = (FrameItem) frame.getItem();
      mutationChance = Math.clamp(mutationChance * item.getMutationChanceModifier(), 0.0f, 1.0f);
    }
    return random.nextFloat() <= mutationChance;
  }
}
