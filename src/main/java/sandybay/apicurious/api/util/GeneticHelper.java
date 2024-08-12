package sandybay.apicurious.api.util;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import sandybay.apicurious.api.bee.genetic.Genotype;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;

public class GeneticHelper
{

  public static <T extends IAllele<T>> Genotype getGenotypeFromParents(Genotype firstParent, Genotype secondParent, RandomSource random)
  {
    Holder<IAllele<?>> firstAllele = random.nextBoolean() ? firstParent.getActive() : firstParent.getInactive();
    Holder<IAllele<?>> secondAllele = random.nextBoolean() ? secondParent.getActive() : secondParent.getInactive();
    if (firstAllele.value().isDominantTrait() && secondAllele.value().isDominantTrait())
      return random.nextBoolean() ? Genotype.of(firstAllele, secondAllele) : Genotype.of(secondAllele, firstAllele);
    if (firstAllele.value().isDominantTrait()) return Genotype.of(firstAllele, secondAllele);
    if (secondAllele.value().isDominantTrait()) return Genotype.of(secondAllele, firstAllele);
    return Genotype.of(firstAllele, secondAllele);
  }

}
