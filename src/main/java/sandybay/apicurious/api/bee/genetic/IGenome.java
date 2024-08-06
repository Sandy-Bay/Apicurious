package sandybay.apicurious.api.bee.genetic;

import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.species.BeeSpecies;

public interface IGenome
{

  boolean setAllelePair(Genome.AllelePair<?> allelePair);

  Genome.AllelePair<?> getAllelePair(AlleleType<?> traitKey);

  IGenome combineGenomes(IGenome other);

  void getDefaultGenome(BeeSpecies species);
}
