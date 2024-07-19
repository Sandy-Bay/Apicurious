package sandybay.apicurious.data;

import net.minecraft.core.RegistrySetBuilder;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.ApicuriousSpecies;

public class ApicuriousDatapackRegistriesDefaults {

  public static RegistrySetBuilder registerDataPackRegistryDefaults() {
    RegistrySetBuilder builder = new RegistrySetBuilder();
    /*
    builder.add(ApicuriousRegistries.AREAS, bootstrap -> {
      register(bootstrap,
              Area.SMALL,
              Area.SMALLER,
              Area.SMALLEST,
              Area.AVERAGE,
              Area.LARGE,
              Area.LARGER,
              Area.LARGEST
      );
    });
    builder.add(ApicuriousRegistries.FLOWERS, bootstrap -> {
      register(bootstrap,
              Flowers.NORMAL_FLOWERS,
              Flowers.ROCK,
              Flowers.NETHER_ROCK
      );
    });
    builder.add(ApicuriousRegistries.HUMIDITY_PREFERENCES, bootstrap -> {
      register(bootstrap,
              HumidityPreference.ARID,
              HumidityPreference.HELLISH,
              HumidityPreference.NORMAL,
              HumidityPreference.DAMP,
              HumidityPreference.AQUATIC
      );
    });
    builder.add(ApicuriousRegistries.HUMIDITY_TOLERANCES, bootstrap -> {
      register(bootstrap,
              HumidityTolerance.NO_TOLERANCE,
              HumidityTolerance.LOWEST_TOLERANCE,
              HumidityTolerance.LOW_TOLERANCE,
              HumidityTolerance.AVERAGE_TOLERANCE,
              HumidityTolerance.HIGH_TOLERANCE,
              HumidityTolerance.MAX_TOLERANCE
      );
    });
    builder.add(ApicuriousRegistries.LIFESPANS, bootstrap -> {
      register(bootstrap,
              Lifespan.SHORTEST,
              Lifespan.SHORTER,
              Lifespan.SHORT,
              Lifespan.SHORTENED,
              Lifespan.AVERAGE,
              Lifespan.ELONGATED,
              Lifespan.LONG,
              Lifespan.LONGER,
              Lifespan.LONGEST
      );
    });
    builder.add(ApicuriousRegistries.POLLINATIONS, bootstrap -> {
      register(bootstrap,
              Pollination.SLOW,
              Pollination.SLOWER,
              Pollination.SLOWEST,
              Pollination.AVERAGE,
              Pollination.FAST,
              Pollination.FASTER,
              Pollination.FASTEST
      );
    });
    builder.add(ApicuriousRegistries.SPEEDS, bootstrap -> {
      register(bootstrap,
              Speed.SLOW,
              Speed.SLOWER,
              Speed.SLOWEST,
              Speed.NORMAL,
              Speed.FAST,
              Speed.FASTER,
              Speed.FASTEST
      );
    });
    builder.add(ApicuriousRegistries.TEMPERATURE_PREFERENCES, bootstrap -> {
      register(bootstrap,
              TemperaturePreference.WARM,
              TemperaturePreference.HOT,
              TemperaturePreference.HELLISH,
              TemperaturePreference.NORMAL,
              TemperaturePreference.CHILLY,
              TemperaturePreference.COLD,
              TemperaturePreference.FREEZING
      );
    });
    builder.add(ApicuriousRegistries.TEMPERATURE_TOLERANCES, bootstrap -> {
      register(bootstrap,
              TemperatureTolerance.NO_TOLERANCE,
              TemperatureTolerance.LOW_TOLERANCE,
              TemperatureTolerance.LOWEST_TOLERANCE,
              TemperatureTolerance.AVERAGE_TOLERANCE,
              TemperatureTolerance.HIGH_TOLERANCE,
              TemperatureTolerance.MAX_TOLERANCE
      );
    });
    builder.add(ApicuriousRegistries.WORKCYCLES, bootstrap -> {
      register(bootstrap,
              WorkCycle.MATUTINAL,
              WorkCycle.DIURNAL,
              WorkCycle.VESPERTINAL,
              WorkCycle.NOCTURNAL,
              WorkCycle.ALWAYS
      );
    });*/
    builder.add(ApicuriousRegistries.BEE_SPECIES, bootstrap -> {
      bootstrap.register(ApicuriousSpecies.EMPTY, getSpeciesBuilder("undefined").build());
      bootstrap.register(ApicuriousSpecies.FOREST, getSpeciesBuilder("forest")
              .withVisualData(visual -> visual.withBeeColor(ApicuriousConstants.FOREST))
              .build());
      bootstrap.register(ApicuriousSpecies.MEADOW, getSpeciesBuilder("meadow")
              .withVisualData(visual -> visual.withBeeColor(ApicuriousConstants.MEADOW))
              .build());
      bootstrap.register(ApicuriousSpecies.COMMON, getSpeciesBuilder("common")
              .withVisualData(visual -> visual.withBeeColor(ApicuriousConstants.COMMON))
              .build());
      bootstrap.register(ApicuriousSpecies.CULTIVATED, getSpeciesBuilder("cultivated")
              .withVisualData(visual -> visual.withBeeColor(ApicuriousConstants.CULTIVATED))
              .build());
      bootstrap.register(ApicuriousSpecies.INDUSTRIOUS, getSpeciesBuilder("industrious")
              .withVisualData(visual -> visual
                      .withBeeColor(ApicuriousConstants.INDUSTRIOUS)
                      .hasEffect()
              ).build());
      bootstrap.register(ApicuriousSpecies.IMPERIAL, getSpeciesBuilder("imperial")
              .withVisualData(visual -> visual
                      .withBeeColor(ApicuriousConstants.IMPERIAL)
                      .hasEffect()
              ).build());
      bootstrap.register(ApicuriousSpecies.AUSTERE, getSpeciesBuilder("austere")
              .withVisualData(visual -> visual
                      .withBeeColor(ApicuriousConstants.AUSTERE)
                      .hasEffect()
              ).build());
    });
    return builder;
  }

  private static BeeSpecies.Builder getSpeciesBuilder(String name) {
    return BeeSpecies.Builder.create(name);
  }

}
