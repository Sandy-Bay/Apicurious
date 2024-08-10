package sandybay.apicurious.api.bee;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.genetic.allele.groups.EnvironmentalData;
import sandybay.apicurious.common.bee.genetic.allele.groups.ProductionData;
import sandybay.apicurious.common.bee.genetic.allele.groups.VisualData;
import sandybay.apicurious.common.bee.output.OutputData;

public interface IBeeSpecies
{
  ResourceKey<IAllele<?>> getSpeciesKey();

  Component getReadableName();

  VisualData getVisualData();

  ProductionData getProductionData();

  EnvironmentalData getEnvironmentalData();

  OutputData getOutputData();

  Genome getSpeciesDefaultGenome(Level level);
}
