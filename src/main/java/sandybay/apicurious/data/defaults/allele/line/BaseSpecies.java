package sandybay.apicurious.data.defaults.allele.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

public class BaseSpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.FOREST.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.FOREST.species(), "forest")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.FOREST))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.HIGH_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWER).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.FOREST.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.MEADOW.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MEADOW.species(), "meadow")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MEADOW))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWER).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.MEADOW.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.MODEST.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MODEST.species(), "modest")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MODEST))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORT)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.CACTI)
                              .withHumidityPreference(HumidityPreference.ARID).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.MODEST.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.TROPICAL.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.TROPICAL.species(), "tropical")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.TROPICAL))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORT)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.JUNGLE)
                              .withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.HOT).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.TROPICAL.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.WINTRY.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.WINTRY.species(), "wintry")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.WINTRY))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.MAXIMUM_FERTILITY).withLifespan(Lifespan.SHORT)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.SNOW)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.ICY).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.WINTRY.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.MARSHY.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MARSHY.species(), "marshy")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MARSHY))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWER).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.MUSHROOM)
                              .withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.MARSHY.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.ROCKY.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ROCKY.species(), "rocky")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ROCKY))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.STONE)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.LOW_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)
                              .ignoresRain().ignoresSky();
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.ROCKY.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.WATER.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.WATER.species(), "water")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.WATER))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOW).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.LILY_PAD)
                              .withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOW_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE)
                              .ignoresRain().ignoresSky();
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.WATER.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.NETHER.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.NETHER.species(), "nether")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.NETHER))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.AVERAGE)
                              .withPollinationRate(Pollination.AVERAGE).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.NETHER_STONE)
                              .withHumidityPreference(HumidityPreference.ARID).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.HELLISH).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.NETHER.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.ENDER.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ENDER.species(), "ender")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ENDER))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.LARGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.LONGER)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.END_STONE)
                              .withHumidityPreference(HumidityPreference.ARID).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.COLD).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)
                              .ignoresSky().ignoresRain();
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.ENDER.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.VALIANT.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.VALIANT.species(), "valiant")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.VALIANT))
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
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.VALIANT.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.STEADFAST.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.STEADFAST.species(), "steadfast")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.STEADFAST).hasEffect())
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.AVERAGE)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE)
                              .ignoresSky();
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.STEADFAST.output()))
                    .build()
    );
  }
}
