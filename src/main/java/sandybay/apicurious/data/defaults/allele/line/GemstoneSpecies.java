package sandybay.apicurious.data.defaults.allele.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

public class GemstoneSpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.DIAMANTINE.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.DIAMANTINE.species(), "diamantine")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.DIAMANTINE))
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
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.DIAMANTINE.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.EMERALDINE.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.EMERALDINE.species(), "emeraldine")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.EMERALDINE))
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
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.EMERALDINE.output()))
                    .build()
    );
  }
}
