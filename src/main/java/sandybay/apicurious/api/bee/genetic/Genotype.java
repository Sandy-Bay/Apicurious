package sandybay.apicurious.api.bee.genetic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

import java.util.Objects;

public record Genotype(Holder<IAllele<?>> active, Holder<IAllele<?>> inactive)
{
  public static Codec<Genotype> CODEC = RecordCodecBuilder.create(instance -> instance.group(RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("active").forGetter(Genotype::active), RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("inactive").forGetter(Genotype::inactive)).apply(instance, Genotype::new));

  public static StreamCodec<RegistryFriendlyByteBuf, Genotype> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), Genotype::active, ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), Genotype::inactive, Genotype::new);

  public static Genotype defaultOf(Holder<IAllele<?>> trait)
  {
    return Genotype.of(trait, trait);
  }

  /**
   * Constructs a genotype with a fixed expressed/hidden ordering. Callers are
   * responsible for deciding which allele should be active before calling this.
   */
  public static Genotype of(Holder<IAllele<?>> active, Holder<IAllele<?>> inactive)
  {
    if (active.value().getTraitKey() != inactive.value().getTraitKey())
    {
      throw new IllegalArgumentException("Attempted to create Genotype with mismatched allele types: " + active.value().getTraitKey() + " vs " + inactive.value().getTraitKey());
    }
    return new Genotype(active, inactive);
  }

  public Holder<IAllele<?>> getActive()
  {
    return active;
  }

  public Holder<IAllele<?>> getInactive()
  {
    return inactive;
  }

  public Component getRenderableName()
  {
    return Component.translatable("apicurious.genetics.active").append(getActive().value().getReadableName()).append(Component.literal(", ")).append(Component.translatable("apicurious.genetics.inactive")).append(getInactive().value().getReadableName());
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }
    Genotype genotype = (Genotype) o;
    return Objects.equals(active, genotype.active) && Objects.equals(inactive, genotype.inactive);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(active, inactive);
  }
}