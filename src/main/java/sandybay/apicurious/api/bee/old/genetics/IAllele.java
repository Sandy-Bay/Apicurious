package sandybay.apicurious.api.bee.old.genetics;

import net.minecraft.network.chat.Component;
import sandybay.apicurious.api.bee.old.traits.ITrait;

public interface IAllele<T extends ITrait<T>> {
  T getTrait();
  boolean isDominant();
  Component getRenderableName();
}
