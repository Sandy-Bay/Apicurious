package sandybay.apicurious.old.genetics;

import sandybay.apicurious.old.traits.ITrait;

import java.util.Set;

public interface IGenome {
  Set<IAllele<?>> getAlleles();

  boolean addAllele();
  boolean removeAllele();
  <T extends ITrait<T>> IAllele<T> getAllele(T traitType);

  void combineGenomes(IGenome other);
}
