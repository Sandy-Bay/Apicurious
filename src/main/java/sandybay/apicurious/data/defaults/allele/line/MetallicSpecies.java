package sandybay.apicurious.data.defaults.allele.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

public class MetallicSpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.CUPRUM.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.CUPRUM.species(), "cuprum")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.CUPRUM))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORT)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.STONE)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.LOW_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)
                              .ignoresSky().ignoresRain();
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.CUPRUM.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.FERRUS.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.FERRUS.species(), "ferrus")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.FERRUS))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORT)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.STONE)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.LOW_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)
                              .ignoresSky().ignoresRain();
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.FERRUS.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.AURUM.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.AURUM.species(), "aurum")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.AURUM))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORT)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.STONE)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.LOW_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)
                              .ignoresSky().ignoresRain();
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.AURUM.output()))
                    .build()
    );
  }
}
