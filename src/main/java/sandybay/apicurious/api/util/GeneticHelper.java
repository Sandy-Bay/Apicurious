package sandybay.apicurious.api.util;

import net.minecraft.util.RandomSource;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.common.bee.genetic.Genome;

public class GeneticHelper
{

  public static <T extends IAllele<T>> Genome.Genotype getGenotypeFromParents(Genome.Genotype firstParent, Genome.Genotype secondParent, RandomSource random)
  {
    IAllele<?> firstAllele = random.nextBoolean() ? firstParent.getActive() : firstParent.getInactive();
    IAllele<?> secondAllele = random.nextBoolean() ? secondParent.getActive() : secondParent.getInactive();
    if (firstAllele.isDominantTrait() && secondAllele.isDominantTrait())
      return random.nextBoolean() ? Genome.Genotype.of(firstAllele, secondAllele) : Genome.Genotype.of(secondAllele, firstAllele);
    if (firstAllele.isDominantTrait()) return Genome.Genotype.of(firstAllele, secondAllele);
    if (secondAllele.isDominantTrait()) return Genome.Genotype.of(secondAllele, firstAllele);
    return Genome.Genotype.of(firstAllele, secondAllele);
  }

}
