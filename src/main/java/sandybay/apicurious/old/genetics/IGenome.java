package sandybay.apicurious.old.genetics;

import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.api.bee.genetic.ITrait;

import java.util.Set;

public interface IGenome
{
  Set<IAllele<?>> getAlleles();

  boolean addAllele();

  boolean removeAllele();

  <T extends ITrait<T>> IAllele<T> getAllele(ResourceKey<ITrait<?>> traitType);

  void combineGenomes(IGenome other);
}
