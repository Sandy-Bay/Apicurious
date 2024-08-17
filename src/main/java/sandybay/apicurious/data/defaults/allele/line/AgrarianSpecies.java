package sandybay.apicurious.data.defaults.allele.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

public class AgrarianSpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.RURAL.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.RURAL.species(), "rural")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.RURAL))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.FASTER).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.WHEAT)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.RURAL.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.FARMERLY.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.FARMERLY.species(), "farmed")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.FARMED))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.FASTER).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.WHEAT)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.FARMERLY.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.AGRARIAN.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.AGRARIAN.species(), "agrarian")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.AGRARIAN).hasEffect())
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.LARGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.FASTER).withProductionSpeed(Speed.SLOW)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.WHEAT)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.AGRARIAN.output()))
                    .build()
    );
  }
}
