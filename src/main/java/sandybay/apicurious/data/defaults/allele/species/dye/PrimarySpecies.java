package sandybay.apicurious.data.defaults.allele.species.dye;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

public class PrimarySpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.MAROON.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MAROON.species(), "maroon")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MAROON))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.MAROON.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.SAFFRON.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.SAFFRON.species(), "saffron")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.SAFFRON))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.SAFFRON.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.PRUSSIAN.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.PRUSSIAN.species(), "prussian")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.PRUSSIAN))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.PRUSSIAN.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.NATURAL.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.NATURAL.species(), "natural")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.NATURAL))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.NATURAL.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.SEPIA.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.SEPIA.species(), "sepia")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.SEPIA))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.SEPIA.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.BLEACHED.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.BLEACHED.species(), "bleached")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.BLEACHED))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.BLEACHED.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.EBONY.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.EBONY.species(), "ebony")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.EBONY))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.EBONY.output()))
                    .build()
    );
  }
}
