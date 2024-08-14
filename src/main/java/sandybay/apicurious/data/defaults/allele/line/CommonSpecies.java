package sandybay.apicurious.data.defaults.allele.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;
import sandybay.apicurious.data.defaults.tables.OutputTableKeys;

public class CommonSpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.COMMON.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.COMMON.species(), "common")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.COMMON))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(OutputTableKeys.STANDARD_OUTPUT)
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.CULTIVATED.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.CULTIVATED.species(), "cultivated")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.CULTIVATED))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTEST)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.FAST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(OutputTableKeys.STANDARD_OUTPUT)
                    .build()
    );
  }
}