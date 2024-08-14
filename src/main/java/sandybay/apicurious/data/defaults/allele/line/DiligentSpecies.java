package sandybay.apicurious.data.defaults.allele.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;
import sandybay.apicurious.data.defaults.tables.OutputTableKeys;

public class DiligentSpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.DILIGENT.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.DILIGENT.species(), "diligent")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.DILIGENT))
                    .withProductionData(builder -> {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORT)
                              .withPollinationRate(Pollination.SLOW).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder -> {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(OutputTableKeys.MERRY_OUTPUT)
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.UNWEARY.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.UNWEARY.species(), "unweary")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.UNWEARY))
                    .withProductionData(builder -> {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTENED)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.AVERAGE)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder -> {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(OutputTableKeys.MERRY_OUTPUT)
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.INDUSTRIOUS.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.INDUSTRIOUS.species(), "industrious")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.INDUSTRIOUS))
                    .withProductionData(builder -> {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.AVERAGE)
                              .withPollinationRate(Pollination.FAST).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder -> {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(OutputTableKeys.INDUSTRIOUS_OUTPUT)
                    .build()
    );
  }
}
