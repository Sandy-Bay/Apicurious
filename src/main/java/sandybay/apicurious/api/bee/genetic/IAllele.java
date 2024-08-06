package sandybay.apicurious.api.bee.genetic;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;

public interface IAllele<T extends ITrait<T>>
{

  TraitType getTraitKey();

  void setTrait(Holder<T> key);

  T getTrait();

  boolean isDominantTrait();

  Component getRenderableName();

}
