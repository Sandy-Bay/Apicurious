package sandybay.apicurious.data.defaults.allele.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

public class FestiveSpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.LEPORINE.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.LEPORINE.species(), "leporine")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.LEPORINE))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.AVERAGE)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.LEPORINE.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.MERRY.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MERRY.species(), "merry")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MERRY))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.AVERAGE)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.ALWAYS);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.ICY).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.MERRY.output()))
                    .build()
    );
  }
}
