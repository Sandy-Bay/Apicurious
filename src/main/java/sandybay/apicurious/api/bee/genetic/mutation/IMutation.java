package sandybay.apicurious.api.bee.genetic.mutation;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.transfer.item.ItemResource;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.item.IFrameItem;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.SimpleBlockHousingHelper;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

import java.util.List;

public interface IMutation
{
  Codec<IMutation> TYPED_CODEC = ApicuriousRegistries.MUTATION_TYPE_REGISTRY.byNameCodec().dispatch("type", IMutation::getType, MutationType::codec);

  MutationType getType();

  HolderSet<IAllele<?>> first();

  HolderSet<IAllele<?>> second();

  float chance();

  Holder<IAllele<?>> output();

  default boolean matchesSpeciesPair(Holder<IAllele<?>> firstParent, Holder<IAllele<?>> secondParent)
  {
    return first().contains(firstParent) && second().contains(secondParent) || first().contains(secondParent) && second().contains(firstParent);
  }

  default boolean matches(SimpleBlockHousingBE housing)
  {
    if (housing.getLevel() == null)
    {
      return false;
    }
    Holder<IAllele<?>> first = SimpleBlockHousingHelper.getSpeciesInSlot(housing, 0, true);
    Holder<IAllele<?>> second = SimpleBlockHousingHelper.getSpeciesInSlot(housing, 1, true);
    if (first == null || second == null || first.is(second))
    {
      return false;
    }
    return matchesSpeciesPair(first, second);
  }

  /**
   * Rolls this mutation's chance, modified by any frames present in the housing.
   * Only call on a mutation that has already passed {@link #matches}.
   */
  default boolean rollSuccess(SimpleBlockHousingBE housing)
  {
    return isValidMutation(SimpleBlockHousingHelper.getFrames(housing), chance(), housing.getLevel().getRandom());
  }

  default boolean test(SimpleBlockHousingBE housing)
  {
    return matches(housing) && rollSuccess(housing);
  }

  default boolean isValidMutation(List<ItemResource> frames, float baseChance, RandomSource random)
  {
    float mutationChance = baseChance;
    for (ItemResource frame : frames)
    {
      if (frame.isEmpty())
      {
        continue;
      }
      IFrameItem item = (IFrameItem) frame.getItem();
      mutationChance = Math.clamp(mutationChance * item.getMutationChanceModifier(), 0.0f, 1.0f);
    }
    return random.nextFloat() <= mutationChance;
  }
}