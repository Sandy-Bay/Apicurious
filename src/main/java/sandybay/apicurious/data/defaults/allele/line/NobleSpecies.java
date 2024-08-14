package sandybay.apicurious.data.defaults.allele.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;
import sandybay.apicurious.data.defaults.tables.OutputTableKeys;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class NobleSpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.NOBLE.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.NOBLE.species(), "noble")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.NOBLE))
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
                    .withOutputData(OutputTableKeys.DRIPPING_OUTPUT)
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.MAJESTIC.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MAJESTIC.species(), "majestic")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MAJESTIC))
                    .withProductionData(builder -> {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.MAXIMUM_FERTILITY).withLifespan(Lifespan.SHORTENED)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.AVERAGE)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder -> {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(OutputTableKeys.DRIPPING_OUTPUT)
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.IMPERIAL.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.IMPERIAL.species(), "imperial")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.IMPERIAL))
                    .withProductionData(builder -> {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.AVERAGE)
                              .withPollinationRate(Pollination.SLOWEST).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder -> {
                      builder.withFlowers(Flowers.FLOWERS)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(OutputTableKeys.IMPERIAL_OUTPUT)
                    .build()
    );
  }
}
