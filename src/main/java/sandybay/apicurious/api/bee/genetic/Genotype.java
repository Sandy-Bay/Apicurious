package sandybay.apicurious.api.bee.genetic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

import java.util.Objects;

public record Genotype(Holder<IAllele<?>> first, Holder<IAllele<?>> second)
{
  public static Codec<Genotype> CODEC = RecordCodecBuilder.create(instance ->
          instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("first").forGetter(Genotype::first),
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("second").forGetter(Genotype::second)
          ).apply(instance, Genotype::new)
  );

  public static StreamCodec<RegistryFriendlyByteBuf, Genotype> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), Genotype::first,
          ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), Genotype::second,
          Genotype::new
  );

  public static <T extends IAllele<T>> Genotype defaultOf(Holder<IAllele<?>> trait)
  {
    return new Genotype(trait, trait);
  }

  public static <T extends IAllele<T>> Genotype of(Holder<IAllele<?>> active, Holder<IAllele<?>> inactive)
  {
    return new Genotype(active, inactive);
  }

  public Holder<IAllele<?>> getActive()
  {
    if (first.value().isDominantTrait()) return first;
    if (second.value().isDominantTrait()) return second;
    return first;
  }

  public Holder<IAllele<?>> getInactive()
  {
    Holder<IAllele<?>> active = getActive();
    return active == first ? second : first;
  }

  public Component getRenderableName()
  {
    // Output Example:
    // Active Allele: Average, Inactive Allele: Average
    return Component.translatable("apicurious.genetics.active")
            .append(getActive().value().getReadableName())
            .append(Component.literal(", "))
            .append(Component.translatable("apicurious.genetics.inactive"))
            .append(getInactive().value().getReadableName());
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Genotype genotype = (Genotype) o;
    return Objects.equals(first, genotype.first) && Objects.equals(second, genotype.second);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(first, second);
  }
}
