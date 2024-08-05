package sandybay.apicurious.common.bee.genetic;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.bee.genetic.ITrait;

public class Allele<T extends ITrait<T>> implements IAllele<T>
{
  private final ResourceLocation typeKey;
  private final boolean isDominantTrait;
  private Holder<ITrait<T>> trait;

  public Allele(ResourceLocation typeKey, Holder<ITrait<T>> defaultTrait, boolean isDominantTrait)
  {
    this.typeKey = typeKey;
    this.trait = defaultTrait;
    this.isDominantTrait = isDominantTrait;
  }

  @Override
  public ResourceLocation getTraitKey()
  {
    return this.typeKey;
  }

  @Override
  public void setTrait(Holder<ITrait<T>> trait)
  {
    ITrait<T> held = trait.value();
    if (!held.getTraitKey().toString().equals(typeKey.toString()))
      throw new IllegalArgumentException("Attempted to set trait: %s of type %s, for Trait type: %s".formatted(
              held.getReadableName().getString(), held.getTraitKey().toString(), typeKey.toString())
      );
    this.trait = trait;
  }

  @Override
  public Holder<ITrait<T>> getTrait()
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
    return Component.translatable("apicurious.genetics.allele").append(this.trait.value().getReadableName());
  }
}
