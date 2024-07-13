package sandybay.apicurious.common.bee.genetics;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import sandybay.apicurious.api.bee.genetics.IAllele;
import sandybay.apicurious.api.bee.traits.ITrait;

public class Allele<T extends ITrait<T>> implements IAllele<T> {

  private final T trait;
  private final boolean isDominant;

  public Allele(T trait, boolean isDominant) {
    this.trait = trait;
    this.isDominant = isDominant;
  }

  @Override
  public T getTrait() {
    return this.trait;
  }

  @Override
  public boolean isDominant() {
    return isDominant;
  }

  @Override
  public Component getRenderableName() {
    MutableComponent traitName = (MutableComponent) this.trait.getReadableName();
    return traitName.append(isDominant ? Component.translatable("apicurious.allele.dominant") : Component.translatable("apicurious.allele.recessive"));
  }

}
