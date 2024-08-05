package sandybay.apicurious.api.bee.genetic;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface IAllele<T extends ITrait<T>>
{

  ResourceLocation getTraitKey();

  void setTrait(Holder<ITrait<T>> key);

  Holder<ITrait<T>> getTrait();

  boolean isDominantTrait();

  Component getRenderableName();

}
