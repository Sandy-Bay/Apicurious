package sandybay.apicurious.old.genetics;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.api.bee.genetic.ITrait;

public interface IAllele<T extends ITrait<T>>
{
  ResourceKey<ITrait<T>> getTraitKey();

  T getTrait();

  boolean isDominant();

  Component getRenderableName();
}
