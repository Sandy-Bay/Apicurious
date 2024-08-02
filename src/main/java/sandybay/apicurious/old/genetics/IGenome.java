package sandybay.apicurious.old.genetics;

import sandybay.apicurious.api.bee.genetic.ITrait;

import java.util.Set;

public interface IGenome
{
  Set<IAllele<?>> getAlleles();

  boolean addAllele();

  boolean removeAllele();

  <T extends ITrait<T>> IAllele<T> getAllele(T traitType);

  void combineGenomes(IGenome other);
}
