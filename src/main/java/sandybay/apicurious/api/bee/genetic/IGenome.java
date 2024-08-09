package sandybay.apicurious.api.bee.genetic;

import net.minecraft.util.RandomSource;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.species.BeeSpecies;

public interface IGenome
{
  <T extends IAllele<T>> Genome.Genotype getGenotype(AlleleType<T> traitKey);

  IGenome combineGenomes(IGenome other, RandomSource random);

  void getDefaultGenome(BeeSpecies species);
}
