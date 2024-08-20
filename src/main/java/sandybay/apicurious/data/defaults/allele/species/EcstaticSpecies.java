package sandybay.apicurious.data.defaults.allele.species;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

public class EcstaticSpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.EXCITED.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.EXCITED.species(), "excited")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.EXCITED))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.REDSTONE)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE)
                              .ignoresSky();
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.EXCITED.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.ENERGETIC.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ENERGETIC.species(), "energetic")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ENERGETIC))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.REDSTONE)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE)
                              .ignoresSky();
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.EXCITED.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.ECSTATIC.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.ECSTATIC.species(), "ecstatic")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.ECSTATIC))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWEST)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.REDSTONE)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE)
                              .ignoresSky();
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.EXCITED.output()))
                    .build()
    );
  }
}
