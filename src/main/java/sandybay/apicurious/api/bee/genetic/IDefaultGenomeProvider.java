package sandybay.apicurious.api.bee.genetic;

import net.minecraft.core.Holder;
import sandybay.apicurious.api.bee.genetic.allele.AlleleType;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;

import java.util.Map;

public interface IDefaultGenomeProvider
{
  Map<AlleleType<? extends IAllele<?>>, Holder<IAllele<?>>> getDefaultTraits();
}