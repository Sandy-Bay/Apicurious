package sandybay.apicurious.api.bee.genetic;

import net.minecraft.resources.ResourceLocation;
import sandybay.apicurious.api.bee.IBeeSpecies;

import java.util.Map;

public interface IGenome
{
  Map<ResourceLocation, IAllele<?>> getGenomeMap();

  boolean setAllele(IAllele<?> allele);

  <T extends ITrait<T>> IAllele<?> getAllele(ResourceLocation traitKey);

  IGenome combineGenomes(IGenome other);

  void getGenomeFromSpecies(IBeeSpecies species);
}
