package sandybay.apicurious.api.util;

import net.minecraft.util.RandomSource;
import sandybay.apicurious.api.bee.genetic.Genotype;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.common.bee.genetic.Genome;

public class GeneticHelper
{

  public static <T extends IAllele<T>> Genotype getGenotypeFromParents(Genotype firstParent, Genotype secondParent, RandomSource random)
  {
    IAllele<?> firstAllele = random.nextBoolean() ? firstParent.getActive() : firstParent.getInactive();
    IAllele<?> secondAllele = random.nextBoolean() ? secondParent.getActive() : secondParent.getInactive();
    if (firstAllele.isDominantTrait() && secondAllele.isDominantTrait())
      return random.nextBoolean() ? Genotype.of(firstAllele, secondAllele) : Genotype.of(secondAllele, firstAllele);
    if (firstAllele.isDominantTrait()) return Genotype.of(firstAllele, secondAllele);
    if (secondAllele.isDominantTrait()) return Genotype.of(secondAllele, firstAllele);
    return Genotype.of(firstAllele, secondAllele);
  }

}
