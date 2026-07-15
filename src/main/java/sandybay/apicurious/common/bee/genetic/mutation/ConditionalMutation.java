// common/bee/genetic/mutation/ConditionalMutation.java
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
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.register.MutationTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

import java.util.List;

public record ConditionalMutation(HolderSet<IAllele<?>> first, HolderSet<IAllele<?>> second, float chance,
                                  Holder<IAllele<?>> output, List<Holder<ICondition>> conditions) implements IMutation
{
  public static final MapCodec<ConditionalMutation> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(RegistryCodecs.homogeneousList(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("first").forGetter(ConditionalMutation::first), RegistryCodecs.homogeneousList(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("second").forGetter(ConditionalMutation::second), Codec.floatRange(0.0f, 1.0f).fieldOf("chance").forGetter(ConditionalMutation::chance), RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("output").forGetter(ConditionalMutation::output), Codec.list(RegistryFileCodec.create(ApicuriousRegistries.CONDITIONS, ICondition.TYPED_CODEC)).fieldOf("conditions").forGetter(ConditionalMutation::conditions)).apply(instance, ConditionalMutation::new));

  @Override
  public MutationType getType()
  {
    return MutationTypeRegistrar.CONDITONAL_MUTATION_TYPE.get();
  }

  @Override
  public boolean matches(SimpleBlockHousingBE housing)
  {
    return IMutation.super.matches(housing) && conditions.stream().map(Holder::value).allMatch(condition -> condition.test(housing));
  }
}