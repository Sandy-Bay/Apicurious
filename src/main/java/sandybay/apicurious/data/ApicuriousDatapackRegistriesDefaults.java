package sandybay.apicurious.data;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.species.BeeColor;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.trait.*;

import java.util.List;

public class ApicuriousDatapackRegistriesDefaults {

  public static RegistrySetBuilder registerDataPackRegistryDefaults() {
    RegistrySetBuilder builder = new RegistrySetBuilder();

    builder.add(ApicuriousRegistries.AREAS, bootstrap -> {
      bootstrap.register(Area.SMALLEST, area(1, 1, "smallest"));
      bootstrap.register(Area.SMALLER, area(2, 2, "smaller"));
      bootstrap.register(Area.SMALL, area(3, 3, "small"));
      bootstrap.register(Area.AVERAGE, area(4, 4, "average"));
      bootstrap.register(Area.LARGE, area(5, 5, "large"));
      bootstrap.register(Area.LARGER, area(6, 6, "larger"));
      bootstrap.register(Area.LARGEST, area(7, 7, "largest"));
    });

    builder.add(ApicuriousRegistries.FERTILITIES, bootstrap -> {
      bootstrap.register(Fertility.LOW_FERTILITY, fertility(1, "low"));
      bootstrap.register(Fertility.AVERAGE_FERTILITY, fertility(2, "average"));
      bootstrap.register(Fertility.HIGH_FERTILITY, fertility(3, "high"));
      bootstrap.register(Fertility.MAXIMUM_FERTILITY, fertility(4, "maximum"));
    });

    builder.add(ApicuriousRegistries.FLOWERS, bootstrap -> {
      bootstrap.register(Flowers.NORMAL_FLOWERS, flowers(BlockTags.FLOWERS, "normal_flowers"));
      bootstrap.register(Flowers.ROCK, flowers(BlockTags.BASE_STONE_OVERWORLD, "overworld_rocks"));
      bootstrap.register(Flowers.NETHER_ROCK, flowers(BlockTags.BASE_STONE_NETHER, "nether_rocks"));
    });

    builder.add(ApicuriousRegistries.HUMIDITY_PREFERENCES, bootstrap -> {
      bootstrap.register(HumidityPreference.HELLISH, humidityPreference(1, ApicuriousTags.BiomeTags.HELLISH_HUMIDITY, "hellish"));
      bootstrap.register(HumidityPreference.ARID, humidityPreference(2, ApicuriousTags.BiomeTags.ARID_HUMIDITY, "arid"));
      bootstrap.register(HumidityPreference.AVERAGE, humidityPreference(3, ApicuriousTags.BiomeTags.AVERAGE_HUMIDITY, "average"));
      bootstrap.register(HumidityPreference.DAMP, humidityPreference(4, ApicuriousTags.BiomeTags.DAMP_HUMIDITY, "damp"));
      bootstrap.register(HumidityPreference.AQUATIC, humidityPreference(5, ApicuriousTags.BiomeTags.AQUATIC_HUMIDITY, "aquatic"));
    });

    builder.add(ApicuriousRegistries.HUMIDITY_TOLERANCES, bootstrap -> {
      bootstrap.register(HumidityTolerance.NO_TOLERANCE, humidityTolerance(0, "none"));
      bootstrap.register(HumidityTolerance.LOWEST_TOLERANCE, humidityTolerance(1, "lowest"));
      bootstrap.register(HumidityTolerance.LOW_TOLERANCE, humidityTolerance(2, "low"));
      bootstrap.register(HumidityTolerance.AVERAGE_TOLERANCE, humidityTolerance(3, "average"));
      bootstrap.register(HumidityTolerance.HIGH_TOLERANCE, humidityTolerance(4, "high"));
      bootstrap.register(HumidityTolerance.MAXIMUM_TOLERANCE, humidityTolerance(5, "maximum"));
    });

    builder.add(ApicuriousRegistries.LIFESPANS, bootstrap -> {
      bootstrap.register(Lifespan.SHOREST, lifespan(10, "shortest"));
      bootstrap.register(Lifespan.SHORTER, lifespan(20, "shortest"));
      bootstrap.register(Lifespan.SHORT, lifespan(30, "shortest"));
      bootstrap.register(Lifespan.SHORTENED, lifespan(35, "shortest"));
      bootstrap.register(Lifespan.AVERAGE, lifespan(40, "shortest"));
      bootstrap.register(Lifespan.ELONGATED, lifespan(45, "shortest"));
      bootstrap.register(Lifespan.LONG, lifespan(50, "shortest"));
      bootstrap.register(Lifespan.LONGER, lifespan(60, "shortest"));
      bootstrap.register(Lifespan.LONGEST, lifespan(70, "shortest"));
    });

    builder.add(ApicuriousRegistries.POLLINATIONS, bootstrap -> {
      bootstrap.register(Pollination.SLOWEST, pollination(0.05f, "slowest"));
      bootstrap.register(Pollination.SLOWER, pollination(0.1f, "slower"));
      bootstrap.register(Pollination.SLOW, pollination(0.15f, "slow"));
      bootstrap.register(Pollination.AVERAGE, pollination(0.2f, "average"));
      bootstrap.register(Pollination.FAST, pollination(0.25f, "fast"));
      bootstrap.register(Pollination.FASTER, pollination(0.3f, "faster"));
      bootstrap.register(Pollination.FASTEST, pollination(0.35f, "fastest"));
    });

    builder.add(ApicuriousRegistries.SPEEDS, bootstrap -> {
      bootstrap.register(Speed.SLOWEST, speed(-0.7f, "slowest"));
      bootstrap.register(Speed.SLOWER, speed(-0.4f, "slower"));
      bootstrap.register(Speed.SLOW, speed(-0.2f, "slow"));
      bootstrap.register(Speed.AVERAGE, speed(0.0f, "average"));
      bootstrap.register(Speed.FAST, speed(0.2f, "fast"));
      bootstrap.register(Speed.FASTER, speed(0.4f, "faster"));
      bootstrap.register(Speed.FASTEST, speed(0.7f, "fastest"));
    });

    builder.add(ApicuriousRegistries.TEMPERATURE_PREFERENCES, bootstrap -> {
      bootstrap.register(TemperaturePreference.INFERNAL, temperaturePreference(5, ApicuriousTags.BiomeTags.HELLISH_TEMPERATURE, "hellish"));
      bootstrap.register(TemperaturePreference.HOT, temperaturePreference(4, ApicuriousTags.BiomeTags.HOT_TEMPERATURE, "hot"));
      bootstrap.register(TemperaturePreference.AVERAGE, temperaturePreference(3, ApicuriousTags.BiomeTags.AVERAGE_TEMPERATURE, "average"));
      bootstrap.register(TemperaturePreference.COLD, temperaturePreference(2, ApicuriousTags.BiomeTags.COLD_TEMPERATURE, "cold"));
      bootstrap.register(TemperaturePreference.FREEZING, temperaturePreference(1, ApicuriousTags.BiomeTags.ICY_TEMPERATURE, "icy"));
    });

    builder.add(ApicuriousRegistries.TEMPERATURE_TOLERANCES, bootstrap -> {
      bootstrap.register(TemperatureTolerance.NO_TOLERANCE, temperatureTolerance(0, "none"));
      bootstrap.register(TemperatureTolerance.LOWEST_TOLERANCE, temperatureTolerance(1, "lowest"));
      bootstrap.register(TemperatureTolerance.LOW_TOLERANCE, temperatureTolerance(2, "low"));
      bootstrap.register(TemperatureTolerance.AVERAGE_TOLERANCE, temperatureTolerance(3, "average"));
      bootstrap.register(TemperatureTolerance.HIGH_TOLERANCE, temperatureTolerance(4, "high"));
      bootstrap.register(TemperatureTolerance.MAXIMUM_TOLERANCE, temperatureTolerance(5, "maximum"));
    });

    builder.add(ApicuriousRegistries.WORKCYCLES, bootstrap -> {
      bootstrap.register(WorkCycle.MATUTINAL, workcycle(List.of(new WorkCycle.Interval(4000, 10000)), "matutinal"));
      bootstrap.register(WorkCycle.DIURNAL, workcycle(List.of(new WorkCycle.Interval(6000, 18000)), "diurnal"));
      bootstrap.register(WorkCycle.VESPERTINAL, workcycle(List.of(new WorkCycle.Interval(14000, 20000)), "vespertinal"));
      bootstrap.register(WorkCycle.NOCTURNAL, workcycle(List.of(new WorkCycle.Interval(18000, 24000), new WorkCycle.Interval(0, 6000)), "nocturnal"));
      bootstrap.register(WorkCycle.ALWAYS, workcycle(List.of(new WorkCycle.Interval(0, 24000)), "always"));
    });

    builder.add(ApicuriousRegistries.BEE_SPECIES, bootstrap -> {
      bootstrap.register(ApicuriousSpecies.EMPTY, getSpeciesBuilder(bootstrap, "undefined").build());
      bootstrap.register(ApicuriousSpecies.FOREST, speciesWithColor(bootstrap, "forest", ApicuriousConstants.FOREST));
      bootstrap.register(ApicuriousSpecies.MEADOW, speciesWithColor(bootstrap, "meadow", ApicuriousConstants.MEADOW));
      bootstrap.register(ApicuriousSpecies.MODEST, speciesWithColor(bootstrap, "modest", ApicuriousConstants.MODEST));
      bootstrap.register(ApicuriousSpecies.TROPICAL, speciesWithColor(bootstrap, "tropical", ApicuriousConstants.TROPICAL));
      bootstrap.register(ApicuriousSpecies.WINTRY, speciesWithColor(bootstrap, "wintry", ApicuriousConstants.WINTRY));
      bootstrap.register(ApicuriousSpecies.MARSHY, speciesWithColor(bootstrap, "marshy", ApicuriousConstants.MARSHY));
      bootstrap.register(ApicuriousSpecies.ROCKY, speciesWithColor(bootstrap, "rocky", ApicuriousConstants.ROCKY));
      bootstrap.register(ApicuriousSpecies.NETHER, speciesWithColor(bootstrap, "nether", ApicuriousConstants.NETHER));
      bootstrap.register(ApicuriousSpecies.ENDER, speciesWithColor(bootstrap, "ender", ApicuriousConstants.ENDER));
      bootstrap.register(ApicuriousSpecies.DEBUG, getSpeciesBuilder(bootstrap, "debug")
              .withVisualData(visual -> {
                visual.hasEffect().hasCustomRender().build();
              }).build()
      );
    });
    builder.add(Registries.CONFIGURED_FEATURE, bootstrap -> {
      // TODO: Implement generation for the bee hives
    });
    builder.add(Registries.PLACED_FEATURE, bootstrap -> {
      // TODO: Implement generation for the bee hives
    });

    return builder;
  }

  // TODO: Build out more robust creation methods
  // Bee Species
  private static BeeSpecies speciesWithColor(BootstrapContext<BeeSpecies> context, String name, BeeColor color) {
    return BeeSpecies.Builder.create(context, name).withVisualData(visual -> visual.withBeeColor(color).build()).build();
  }

  private static BeeSpecies.Builder getSpeciesBuilder(BootstrapContext<BeeSpecies> context, String name) {
    return BeeSpecies.Builder.create(context, name);
  }

  // Area
  private static Area area(int xzOffset, int yOffset, String name) {
    return new Area(xzOffset, yOffset, "apicurious.area." + name);
  }

  // Fertility
  private static Fertility fertility(int offspring, String name) {
    return new Fertility(offspring, "apicurious.fertility." + name);
  }

  // Flowers
  private static Flowers flowers(TagKey<Block> flowers, String name) {
    return new Flowers(flowers, "apicurious.flowers." + name);
  }

  // Humidity Preference / Tolerance
  private static HumidityPreference humidityPreference(int humidity, TagKey<Biome> groupTag, String name) {
    return new HumidityPreference(humidity, groupTag, "apicurious.preference.humidity." + name);
  }

  private static HumidityTolerance humidityTolerance(int toleranceModifier, String name) {
    return new HumidityTolerance(toleranceModifier, "apicurious.tolerance.humidity." + name);
  }

  // Lifespan
  private static Lifespan lifespan(int cycles, String name) {
    return new Lifespan(cycles, "apicurious.lifespan." + name);
  }

  // Pollination
  private static Pollination pollination(float pollinationChance, String name) {
    return new Pollination(pollinationChance, "apicurious.pollination." + name);
  }

  // Speed
  private static Speed speed(float productionModifier, String name) {
    return new Speed(productionModifier, "apicurious.speed." + name);
  }

  // Temperature Preference / Tolerance
  private static TemperaturePreference temperaturePreference(int temperature, TagKey<Biome> groupTag, String name) {
    return new TemperaturePreference(temperature, groupTag, "apicurious.preference.temperature." + name);
  }

  private static TemperatureTolerance temperatureTolerance(int toleranceModifier, String name) {
    return new TemperatureTolerance(toleranceModifier, "apicurious.tolerance.temperature." + name);
  }

  // WorkCycle
  private static WorkCycle workcycle(List<WorkCycle.Interval> activeTimes, String name) {
    return new WorkCycle(activeTimes, "apicurious.workcycle." + name);
  }

}
