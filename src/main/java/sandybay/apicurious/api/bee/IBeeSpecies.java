package sandybay.apicurious.api.bee;

import net.minecraft.network.chat.Component;
import sandybay.apicurious.api.bee.genetic.IGenome;
import sandybay.apicurious.common.bee.output.OutputData;
import sandybay.apicurious.common.bee.species.trait.groups.EnvironmentalData;
import sandybay.apicurious.common.bee.species.trait.groups.ProductionData;
import sandybay.apicurious.common.bee.species.trait.groups.VisualData;

public interface IBeeSpecies
{
  Component getReadableName();

  VisualData getVisualData();

  ProductionData getProductionData();

  EnvironmentalData getEnvironmentalData();

  OutputData getOutputData();

  IGenome getSpeciesDefaultGenome();
}
