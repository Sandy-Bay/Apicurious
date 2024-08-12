package sandybay.apicurious.common.bee.genetic.mutation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.genetic.mutation.MutationType;
import sandybay.apicurious.api.register.MutationTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.SimpleBlockHousingHelper;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

public record Mutation(HolderSet<IAllele<?>> first, HolderSet<IAllele<?>> second, float chance, Holder<IAllele<?>> output) implements IMutation
{

  public static final MapCodec<Mutation> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryCodecs.homogeneousList(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("first").forGetter(Mutation::first),
                  RegistryCodecs.homogeneousList(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("second").forGetter(Mutation::second),
                  Codec.floatRange(0.0f, 1.0f).fieldOf("chance").forGetter(Mutation::chance),
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("output").forGetter(Mutation::output)
          ).apply(instance, Mutation::new)
  );

  @Override
  public MutationType getType()
  {
    return MutationTypeRegistrar.BASE_MUTATION_TYPE.get();
  }

  @Override
  public Holder<IAllele<?>> getOutput()
  {
    return output();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    Holder<IAllele<?>> first = SimpleBlockHousingHelper.getSpeciesInSlot(housing, 0, true);
    Holder<IAllele<?>> second = SimpleBlockHousingHelper.getSpeciesInSlot(housing, 1, true);
    if (housing.getLevel() == null || first == null || second == null || first.is(second)) return false;
    if (first().contains(first) && second().contains(second) || first().contains(second) && second().contains(first))
    {
      return isValidMutation(SimpleBlockHousingHelper.getFrames(housing), chance(), housing.getLevel().getRandom());
    }
    return false;
  }
}