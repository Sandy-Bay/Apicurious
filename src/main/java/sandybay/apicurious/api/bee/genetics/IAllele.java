package sandybay.apicurious.api.bee.genetics;

import net.minecraft.network.chat.Component;
import sandybay.apicurious.api.bee.traits.ITrait;

public interface IAllele<T extends ITrait<T>> {
  T getTrait();
  boolean isDominant();
  Component getRenderableName();
}
