package sandybay.apicurious.data.defaults.allele.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

public class ResilientSpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.TOLERANT.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.TOLERANT.species(), "tolerant")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.TOLERANT))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORT)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.STONE)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.LOWEST_TOLERANCE)
                              .ignoresSky().ignoresRain();
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.TOLERANT.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.ROBUST.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ROBUST.species(), "robust")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ROBUST))
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
                              .ignoresRain().ignoresSky();
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.ROBUST.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.RESILIENT.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.RESILIENT.species(), "resilient")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.RESILIENT))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.LARGE).withFertility(Fertility.LOW_FERTILITY).withLifespan(Lifespan.SHORT)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.STONE)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.AVERAGE_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.AVERAGE_TOLERANCE)
                              .ignoresSky().ignoresRain();
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.RESILIENT.output()))
                    .build()
    );
  }
}
