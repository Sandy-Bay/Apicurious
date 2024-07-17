package sandybay.apicurious.data;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousHolder;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.traits.*;

public class ApicuriousDatapackRegistriesDefaults {

  public static RegistrySetBuilder registerDataPackRegistryDefaults() {
    init();
    RegistrySetBuilder builder = new RegistrySetBuilder();
    builder.add(ApicuriousRegistries.AREAS, bootstrap -> {
      register(bootstrap,
              Area.create(1,1,"smallest"),
              Area.create(2,2,"smaller"),
              Area.create(3,3,"small"),
              Area.create(4,4,"average"),
              Area.create(5,5,"large"),
              Area.create(6,6,"larger"),
              Area.create(7,7,"largest")
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
    });
    builder.add(ApicuriousRegistries.BEE_SPECIES, bootstrap -> {
      register(bootstrap,
              ApicuriousSpecies.FOREST,
              ApicuriousSpecies.MEADOW,
              ApicuriousSpecies.COMMON,
              ApicuriousSpecies.CULTIVATED
      );
    });
    return builder;
  }

  @SafeVarargs
  private static <T> void register(BootstrapContext<T> bootstrap, ApicuriousHolder<T>... holders) {
    for (ApicuriousHolder<T> holder : holders) {
      bootstrap.register(holder.key(), holder.value());
    }
  }

  public static void init() {
    Area.load();
    Flowers.load();
    HumidityPreference.load();
    HumidityTolerance.load();
    Lifespan.load();
    Pollination.load();
    Speed.load();
    TemperaturePreference.load();
    TemperatureTolerance.load();
    WorkCycle.load();
    ApicuriousSpecies.load();
  }


}
