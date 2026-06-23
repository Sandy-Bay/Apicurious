package sandybay.apicurious.api.bee.genetic.mutation;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.item.IFrameItem;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

import java.util.List;

public interface IMutation
{
  Codec<IMutation> TYPED_CODEC = ApicuriousRegistries.MUTATION_TYPE_REGISTRY.byNameCodec().dispatch("type", IMutation::getType, MutationType::codec);

  MutationType getType();

  HolderSet<IAllele<?>> getFirst();

  HolderSet<IAllele<?>> getSecond();

  float getChance();

  Holder<IAllele<?>> getOutput();

  boolean test(SimpleBlockHousingBE housing);

  default boolean isValidMutation(List<ItemResource> frames, float baseChance, RandomSource random)
  {
    float mutationChance = baseChance;
    for (ItemResource frame : frames)
    {
      if (frame.isEmpty()) {continue;}
      IFrameItem item = (IFrameItem) frame.getItem();
      mutationChance = Math.clamp(mutationChance * item.getMutationChanceModifier(), 0.0f, 1.0f);
    }
    return random.nextFloat() <= mutationChance;
  }
}
