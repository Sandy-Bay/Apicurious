package sandybay.apicurious.data.defaults.allele.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;
import sandybay.apicurious.data.defaults.tables.OutputTableKeys;

public class HeroicSpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    // TODO: Add Heroic effect
    bootstrap.register(ApicuriousSpecies.HEROIC.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.HEROIC.species(), "heroic")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.HEROIC))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.LONG)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOW)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE)
                              .ignoresSky();
                    })
                    .withOutputData(builder -> builder.withTable(OutputTableKeys.COCOA_OUTPUT))
                    .build()
    );
  }
}
