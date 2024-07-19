package sandybay.apicurious.api.bee.old.genetics;

import sandybay.apicurious.api.bee.old.traits.ITrait;
import sandybay.apicurious.common.bee.genetics.Allele;
import sandybay.apicurious.common.bee.genetics.Genome;

import java.util.Set;

public interface IGenome {
  Set<Allele<?>> getAlleles();

  boolean addAllele();
  boolean removeAllele();
  <T extends ITrait<T>> Allele<T> getAllele(T traitType);

  void combineGenomes(Genome other);
}
