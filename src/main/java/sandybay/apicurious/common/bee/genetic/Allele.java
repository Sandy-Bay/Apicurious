package sandybay.apicurious.common.bee.genetic;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.bee.genetic.ITrait;
import sandybay.apicurious.api.bee.genetic.TraitType;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class Allele<T extends ITrait<T>> implements IAllele<T>
{


  // TODO: Figure out how this would even work.... I hate this already

  private final TraitType typeKey;
  private final boolean isDominantTrait;
  private T trait;

  private Allele(Holder<T> defaultTrait)
  {
    this.typeKey = defaultTrait.value().getTraitKey();
    this.trait = defaultTrait.value();
    this.isDominantTrait = defaultTrait.value().isDominantTrait();
  }

  @Override
  public TraitType getTraitKey()
  {
    return this.typeKey;
  }

  @Override
  public void setTrait(Holder<T> trait)
  {
    ITrait<T> held = trait.value();
    if (!held.getTraitKey().toString().equals(typeKey.toString()))
      throw new IllegalArgumentException("Attempted to set trait: %s of type %s, for Trait type: %s".formatted(
              held.getReadableName().getString(), held.getTraitKey().toString(), typeKey.toString())
      );
    this.trait = trait.value();
  }

  @Override
  public T getTrait()
  {
    return this.trait;
  }

  @Override
  public boolean isDominantTrait()
  {
    return this.isDominantTrait;
  }

  @Override
  public Component getRenderableName()
  {
    return Component.translatable("apicurious.genetics.allele").append(this.trait.getReadableName());
  }

  public static <T extends ITrait<T>> Allele<T> of(Holder<T> trait)
  {
    return new Allele<>(trait);
  }
}
