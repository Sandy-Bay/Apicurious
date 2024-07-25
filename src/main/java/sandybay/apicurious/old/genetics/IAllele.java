package sandybay.apicurious.old.genetics;

import net.minecraft.network.chat.Component;
import sandybay.apicurious.old.traits.ITrait;

public interface IAllele<T extends ITrait<T>> {
  T getTrait();
  boolean isDominant();
  Component getRenderableName();
}