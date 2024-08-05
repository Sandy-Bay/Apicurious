package sandybay.apicurious.common.bee.genetic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cod;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.bee.genetic.ITrait;

public class Allele<T extends ITrait<T>> implements IAllele<T>
{
  /*
  public static final Codec<Allele<?>> CODEC = RecordCodecBuilder.create(instance ->
    instance.group(



    ).apply(instance, Allele::new)
  );
  */

  // TODO: Figure out how this would even work.... I hate this already

  private final ResourceLocation typeKey;
  private final boolean isDominantTrait;
  private T trait;

  private Allele(Holder<T> defaultTrait)
  {
    this.typeKey = defaultTrait.value().getTraitKey();
    this.trait = defaultTrait.value();
    this.isDominantTrait = defaultTrait.value().isDominantTrait();
  }

  @Override
  public ResourceLocation getTraitKey()
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
