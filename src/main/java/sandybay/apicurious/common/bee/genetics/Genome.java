package sandybay.apicurious.common.bee.genetics;

import sandybay.apicurious.api.bee.genetics.IGenome;
import sandybay.apicurious.api.bee.traits.ITrait;

import java.util.HashSet;
import java.util.Set;

public class Genome implements IGenome {

  private final Set<Allele<?>> alleles = new HashSet<>();

  @Override
  public Set<Allele<?>> getAlleles() {
    return this.alleles;
  }

  @Override
  public boolean addAllele() {
    return false;
  }

  @Override
  public boolean removeAllele() {
    return false;
  }

  @Override
  public <T extends ITrait<T>> Allele<T> getAllele(T traitType) {
    for (Allele<?> allele : this.alleles) {
      if (allele.getTrait().getClass().equals(traitType.getClass())) {
        return (Allele<T>) allele; // Cast to Allele<T>
      }
    }
    return null; // Return null if no matching allele is found
  }


  @Override
  public void combineGenomes(Genome other) {

  }
}
