package sandybay.apicurious.api.util;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import sandybay.apicurious.api.bee.genetic.Genotype;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.common.config.ApicuriousMainConfig;

public class GeneticHelper
{

  public static <T extends IAllele<T>> Genotype getGenotypeFromParents(Genotype firstParent, Genotype secondParent,
                                                                       RandomSource random)
  {
    Holder<IAllele<?>> firstAllele = random.nextBoolean() ? firstParent.getActive() : firstParent.getInactive();
    Holder<IAllele<?>> secondAllele = random.nextBoolean() ? secondParent.getActive() : secondParent.getInactive();

    boolean firstDominant = firstAllele.value().isDominantTrait();
    boolean secondDominant = secondAllele.value().isDominantTrait();

    if (firstDominant == secondDominant)
    {
      return random.nextBoolean() ? Genotype.of(firstAllele, secondAllele) : Genotype.of(secondAllele, firstAllele);
    }

    Holder<IAllele<?>> dominant = firstDominant ? firstAllele : secondAllele;
    Holder<IAllele<?>> recessive = firstDominant ? secondAllele : firstAllele;

    boolean recessiveLeaksThrough = random.nextFloat() < ApicuriousMainConfig.getRecessiveLeakChance();
    return recessiveLeaksThrough ? Genotype.of(recessive, dominant) : Genotype.of(dominant, recessive);
  }

}