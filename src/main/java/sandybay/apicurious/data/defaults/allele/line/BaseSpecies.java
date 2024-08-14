package sandybay.apicurious.data.defaults.allele.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;
import sandybay.apicurious.data.defaults.tables.OutputTableKeys;

public class BaseSpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.FOREST.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.FOREST.species(), "forest")
                            .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.FOREST))
                            .withProductionData(builder -> {
                              builder.withArea(Area.AVERAGE).withFertility(Fertility.HIGH_FERTILITY).withLifespan(Lifespan.SHORTER)
                                      .withPollinationRate(Pollination.SLOWER).withProductionSpeed(Speed.SLOWEST)
                                      .withWorkCycle(Workcycle.DIURNAL);
                            })
                            .withEnvironmentalData(builder -> {
                              builder.withFlowers(Flowers.FLOWERS)
                                      .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                                      .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                            })
                            .withOutputData(OutputTableKeys.STANDARD_OUTPUT)
                            .build()
    );
    bootstrap.register(ApicuriousSpecies.MEADOW.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MEADOW.species(), "meadow")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MEADOW))
                    .withProductionData(builder -> {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWER).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder -> {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(OutputTableKeys.STANDARD_OUTPUT)
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.MODEST.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MODEST.species(), "modest")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MODEST))
                    .withProductionData(builder -> {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORT)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder -> {
                      builder.withFlowers(Flowers.CACTI)
                              .withHumidityPreference(HumidityPreference.ARID).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE);
                    })
                    .withOutputData(OutputTableKeys.PARCHED_OUTPUT)
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.TROPICAL.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.TROPICAL.species(), "tropical")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.TROPICAL))
                    .withProductionData(builder -> {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORT)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder -> {
                      builder.withFlowers(Flowers.JUNGLE)
                              .withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE);
                    })
                    .withOutputData(OutputTableKeys.SILKY_OUTPUT)
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.WINTRY.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.WINTRY.species(), "wintry")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.WINTRY))
                    .withProductionData(builder -> {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.MAXIMUM_FERTILITY).withLifespan(Lifespan.SHORT)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder -> {
                      builder.withFlowers(Flowers.SNOW)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.ICY).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE);
                    })
                    .withOutputData(OutputTableKeys.FROZEN_OUTPUT)
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.MARSHY.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MARSHY.species(), "marshy")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MARSHY))
                    .withProductionData(builder -> {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWER).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder -> {
                      builder.withFlowers(Flowers.MUSHROOM)
                              .withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE);
                    })
                    .withOutputData(OutputTableKeys.MOSSY_OUTPUT)
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.ROCKY.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ROCKY.species(), "rocky")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ROCKY))
                    .withProductionData(builder -> {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder -> {
                      builder.withFlowers(Flowers.ROCK)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.LOW_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)
                              .ignoresRain().ignoresSky();
                    })
                    .withOutputData(OutputTableKeys.ROCKY_OUTPUT)
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.NETHER.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.NETHER.species(), "nether")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.NETHER))
                    .withProductionData(builder -> {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.AVERAGE)
                              .withPollinationRate(Pollination.AVERAGE).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder -> {
                      builder.withFlowers(Flowers.NETHER_ROCK)
                              .withHumidityPreference(HumidityPreference.ARID).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.HELLISH).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE);
                    })
                    .withOutputData(OutputTableKeys.SIMMERING_OUTPUT)
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.ENDER.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ENDER.species(), "ender")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ENDER))
                    .withProductionData(builder -> {
                      builder.withArea(Area.LARGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.LONGER)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder -> {
                      builder.withFlowers(Flowers.NETHER_ROCK)
                              .withHumidityPreference(HumidityPreference.ARID).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.COLD).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)
                              .ignoresSky().ignoresRain();
                    })
                    .withOutputData(OutputTableKeys.MYSTERIOUS_OUTPUT)
                    .build()
    );
  }
}
