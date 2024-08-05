package sandybay.apicurious.common.bee.genetic;

import net.minecraft.resources.ResourceLocation;
import sandybay.apicurious.api.bee.IBeeSpecies;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.bee.genetic.IGenome;
import sandybay.apicurious.api.bee.genetic.ITrait;
import sandybay.apicurious.api.util.ApicuriousConstants;

import java.util.HashMap;
import java.util.Map;

public class Genome implements IGenome
{

  private final  Map<ResourceLocation, IAllele<?>> genome;

  public Genome()
  {
    this.genome = new HashMap<>();
  }

  @Override
  public Map<ResourceLocation, IAllele<?>> getGenomeMap()
  {
    return this.genome;
  }

  @Override
  public boolean setAllele(IAllele<?> allele)
  {
    IAllele<?> prev = this.genome.put(allele.getTraitKey(), allele);
    return  prev == null || prev != allele;
  }

  @Override
  public <T extends ITrait<T>> IAllele<?> getAllele(ResourceLocation traitKey)
  {
    return this.genome.get(traitKey);
  }

  @Override
  public IGenome combineGenomes(IGenome other)
  {
    return null;
  }

  @Override
  public void getGenomeFromSpecies(IBeeSpecies species)
  {
    //this.genome.put(ApicuriousConstants.AREA, new Allele<>(ApicuriousConstants.AREA, species.getProductionData().getArea(), false));
  }
}
