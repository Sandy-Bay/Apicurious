package sandybay.apicurious.common.bee.genetic.mutation;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.bee.genetic.IMutation;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.item.frame.FrameItem;

import java.util.List;
import java.util.Optional;

// TODO: Make this support tags somehow...
public record Mutation(Holder<IAllele<?>> first, Holder<IAllele<?>> second, float chance, Holder<IAllele<?>> output) implements IMutation
{

  public static final Codec<Mutation> CODEC = RecordCodecBuilder.create(instance ->
          instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("first").forGetter(Mutation::first),
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("second").forGetter(Mutation::second),
                  Codec.floatRange(0.0f, 1.0f).fieldOf("chance").forGetter(Mutation::chance),
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("output").forGetter(Mutation::output)
          ).apply(instance, Mutation::new)
  );

  @Override
  public BeeSpecies getOutput()
  {
    return (BeeSpecies) output.value();
  }

  @Override
  public boolean test(Level level, List<ItemStack> frames, BeeSpecies first, BeeSpecies second, RandomSource random)
  {
    BeeSpecies firstLeft = (BeeSpecies) first().value();
    BeeSpecies secondLeft = (BeeSpecies) second().value();
    if (first.equals(firstLeft) && second.equals(secondLeft) || first.equals(secondLeft) && second.equals(firstLeft))
    {
      return isValidMutation(frames, random);
    }
    return false;
  }

  private boolean isValidMutation(List<ItemStack> frames, RandomSource random)
  {
    float mutationChance = chance();
    for (ItemStack frame : frames)
    {
      FrameItem item = (FrameItem) frame.getItem();
      mutationChance = Math.clamp(mutationChance * item.getMutationChanceModifier(), 0.0f, 1.0f);
    }
    return random.nextFloat() <= chance();
  }

  // TODO: Try and figure out how to make mutations support tags...
  private boolean validateAgainstTag(Level level, TagKey<IAllele<?>> key, BeeSpecies species)
  {
    Optional<Registry<IAllele<?>>> registryOptional = level.registryAccess().registry(ApicuriousRegistries.ALLELES);
    if (registryOptional.isPresent())
    {
      Registry<IAllele<?>> registry = registryOptional.get();
      return registry.getTags().filter(pair -> pair.getFirst().location().equals(key.location()))
              .map(Pair::getSecond)
              .anyMatch(named -> named.stream().anyMatch(holder -> holder.is(key) && holder.value().equals(species)));

    }
    return false;
  }
}
