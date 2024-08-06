package sandybay.apicurious.data;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
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

public class ApicuriousDatapackRegistriesDefaults
{

  public static RegistrySetBuilder registerDataPackRegistryDefaults()
  {
    RegistrySetBuilder builder = new RegistrySetBuilder();

    builder.add(ApicuriousRegistries.AREAS, bootstrap ->
    {
      bootstrap.register(Area.SMALLEST, area(1, 1, true, "smallest"));
      bootstrap.register(Area.SMALLER, area(2, 2, true, "smaller"));
      bootstrap.register(Area.SMALL, area(3, 3, true, "small"));
      bootstrap.register(Area.AVERAGE, area(4, 4, true, "average"));
      bootstrap.register(Area.LARGE, area(5, 5, false, "large"));
      bootstrap.register(Area.LARGER, area(6, 6, false, "larger"));
      bootstrap.register(Area.LARGEST, area(7, 7, false, "largest"));
    });

    builder.add(ApicuriousRegistries.FERTILITIES, bootstrap ->
    {
      bootstrap.register(Fertility.LOW_FERTILITY, fertility(1, true, "low"));
      bootstrap.register(Fertility.AVERAGE_FERTILITY, fertility(2, true, "average"));
      bootstrap.register(Fertility.HIGH_FERTILITY, fertility(3, false, "high"));
      bootstrap.register(Fertility.MAXIMUM_FERTILITY, fertility(4, false,"maximum"));
    });

    builder.add(ApicuriousRegistries.FLOWERS, bootstrap ->
    {
      bootstrap.register(Flowers.NORMAL_FLOWERS, flowers(BlockTags.FLOWERS, true, "normal_flowers"));
      bootstrap.register(Flowers.ROCK, flowers(BlockTags.BASE_STONE_OVERWORLD, true, "overworld_rocks"));
      bootstrap.register(Flowers.NETHER_ROCK, flowers(BlockTags.BASE_STONE_NETHER, true, "nether_rocks"));
    });

    builder.add(ApicuriousRegistries.HUMIDITY_PREFERENCES, bootstrap ->
    {
      bootstrap.register(HumidityPreference.HELLISH, humidityPreference(1, ApicuriousTags.BiomeTags.HELLISH_HUMIDITY, true,"hellish"));
      bootstrap.register(HumidityPreference.ARID, humidityPreference(2, ApicuriousTags.BiomeTags.ARID_HUMIDITY, true, "arid"));
      bootstrap.register(HumidityPreference.AVERAGE, humidityPreference(3, ApicuriousTags.BiomeTags.AVERAGE_HUMIDITY, true, "average"));
      bootstrap.register(HumidityPreference.DAMP, humidityPreference(4, ApicuriousTags.BiomeTags.DAMP_HUMIDITY, true, "damp"));
      bootstrap.register(HumidityPreference.AQUATIC, humidityPreference(5, ApicuriousTags.BiomeTags.AQUATIC_HUMIDITY, true, "aquatic"));
    });

    builder.add(ApicuriousRegistries.HUMIDITY_TOLERANCES, bootstrap ->
    {
      bootstrap.register(HumidityTolerance.NO_TOLERANCE, humidityTolerance(0, true, "none"));
      bootstrap.register(HumidityTolerance.LOWEST_TOLERANCE, humidityTolerance(1, false, "lowest"));
      bootstrap.register(HumidityTolerance.LOW_TOLERANCE, humidityTolerance(2, false, "low"));
      bootstrap.register(HumidityTolerance.AVERAGE_TOLERANCE, humidityTolerance(3, false, "average"));
      bootstrap.register(HumidityTolerance.HIGH_TOLERANCE, humidityTolerance(4, false, "high"));
      bootstrap.register(HumidityTolerance.MAXIMUM_TOLERANCE, humidityTolerance(5, false, "maximum"));
    });

    builder.add(ApicuriousRegistries.LIFESPANS, bootstrap ->
    {
      bootstrap.register(Lifespan.SHOREST, lifespan(10, false, "shortest"));
      bootstrap.register(Lifespan.SHORTER, lifespan(20, false,"shorter"));
      bootstrap.register(Lifespan.SHORT, lifespan(30, false,"short"));
      bootstrap.register(Lifespan.SHORTENED, lifespan(35, true,"shortened"));
      bootstrap.register(Lifespan.AVERAGE, lifespan(40, true,"average"));
      bootstrap.register(Lifespan.ELONGATED, lifespan(45, true,"elongated"));
      bootstrap.register(Lifespan.LONG, lifespan(50, false,"long"));
      bootstrap.register(Lifespan.LONGER, lifespan(60, false,"longer"));
      bootstrap.register(Lifespan.LONGEST, lifespan(70, false,"longest"));
    });

    builder.add(ApicuriousRegistries.POLLINATIONS, bootstrap ->
    {
      bootstrap.register(Pollination.SLOWEST, pollination(0.05f, false,"slowest"));
      bootstrap.register(Pollination.SLOWER, pollination(0.1f, false,"slower"));
      bootstrap.register(Pollination.SLOW, pollination(0.15f, true,"slow"));
      bootstrap.register(Pollination.AVERAGE, pollination(0.2f, true,"average"));
      bootstrap.register(Pollination.FAST, pollination(0.25f, true,"fast"));
      bootstrap.register(Pollination.FASTER, pollination(0.3f, false,"faster"));
      bootstrap.register(Pollination.FASTEST, pollination(0.35f, false,"fastest"));
    });

    builder.add(ApicuriousRegistries.SPEEDS, bootstrap ->
    {
      bootstrap.register(Speed.SLOWEST, speed(1.7f, false,"slowest"));
      bootstrap.register(Speed.SLOWER, speed(1.4f, false,"slower"));
      bootstrap.register(Speed.SLOW, speed(1.2f, true,"slow"));
      bootstrap.register(Speed.AVERAGE, speed(1.0f, true,"average"));
      bootstrap.register(Speed.FAST, speed(0.7f, true,"fast"));
      bootstrap.register(Speed.FASTER, speed(0.4f, false,"faster"));
      bootstrap.register(Speed.FASTEST, speed(0.2f, false,"fastest"));
    });

    builder.add(ApicuriousRegistries.TEMPERATURE_PREFERENCES, bootstrap ->
    {
      bootstrap.register(TemperaturePreference.HELLISH, temperaturePreference(5, ApicuriousTags.BiomeTags.HELLISH_TEMPERATURE, true,"hellish"));
      bootstrap.register(TemperaturePreference.HOT, temperaturePreference(4, ApicuriousTags.BiomeTags.HOT_TEMPERATURE, true,"hot"));
      bootstrap.register(TemperaturePreference.AVERAGE, temperaturePreference(3, ApicuriousTags.BiomeTags.AVERAGE_TEMPERATURE, true,"average"));
      bootstrap.register(TemperaturePreference.COLD, temperaturePreference(2, ApicuriousTags.BiomeTags.COLD_TEMPERATURE, true,"cold"));
      bootstrap.register(TemperaturePreference.ICY, temperaturePreference(1, ApicuriousTags.BiomeTags.ICY_TEMPERATURE, true,"icy"));
    });

    builder.add(ApicuriousRegistries.TEMPERATURE_TOLERANCES, bootstrap ->
    {
      bootstrap.register(TemperatureTolerance.NO_TOLERANCE, temperatureTolerance(0, true,"none"));
      bootstrap.register(TemperatureTolerance.LOWEST_TOLERANCE, temperatureTolerance(1, false,"lowest"));
      bootstrap.register(TemperatureTolerance.LOW_TOLERANCE, temperatureTolerance(2, false,"low"));
      bootstrap.register(TemperatureTolerance.AVERAGE_TOLERANCE, temperatureTolerance(3, false,"average"));
      bootstrap.register(TemperatureTolerance.HIGH_TOLERANCE, temperatureTolerance(4, false,"high"));
      bootstrap.register(TemperatureTolerance.MAXIMUM_TOLERANCE, temperatureTolerance(5, false,"maximum"));
    });

    builder.add(ApicuriousRegistries.WORKCYCLES, bootstrap ->
    {
      bootstrap.register(Workcycle.MATUTINAL, workcycle(List.of(new Workcycle.Interval(4000, 10000)), true,"matutinal"));
      bootstrap.register(Workcycle.DIURNAL, workcycle(List.of(new Workcycle.Interval(6000, 18000)), true,"diurnal"));
      bootstrap.register(Workcycle.VESPERTINAL, workcycle(List.of(new Workcycle.Interval(14000, 20000)), true,"vespertinal"));
      bootstrap.register(Workcycle.NOCTURNAL, workcycle(List.of(new Workcycle.Interval(18000, 24000), new Workcycle.Interval(0, 6000)), true,"nocturnal"));
      bootstrap.register(Workcycle.ALWAYS, workcycle(List.of(new Workcycle.Interval(0, 24000)), false,"always"));
    });

    builder.add(ApicuriousRegistries.BEE_SPECIES, bootstrap ->
    {
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
              .withVisualData(visual ->
              {
                visual.hasEffect().hasCustomRender().build();
              })
              .withProductionData(production ->
                      production.withArea(Area.LARGEST)
              ).withEnvironmentalData(environment ->
                      environment.withFlowers(Flowers.ROCK)
              )
              .withOutputData(outputs ->
                      outputs.withStack(Items.HONEYCOMB, 1)
              ).build()
      );
    });
    builder.add(Registries.CONFIGURED_FEATURE, bootstrap ->
    {
      // TODO: Implement generation for the bee hives
    });
    builder.add(Registries.PLACED_FEATURE, bootstrap ->
    {
      // TODO: Implement generation for the bee hives
    });

    return builder;
  }

  // TODO: Build out more robust creation methods
  // Bee Species
  private static BeeSpecies speciesWithColor(BootstrapContext<BeeSpecies> context, String name, BeeColor color)
  {
    return BeeSpecies.Builder.create(context, name).withVisualData(visual -> visual.withBeeColor(color).build()).build();
  }

  private static BeeSpecies.Builder getSpeciesBuilder(BootstrapContext<BeeSpecies> context, String name)
  {
    return BeeSpecies.Builder.create(context, name);
  }

  // Area
  private static Area area(int xzOffset, int yOffset, boolean isDominantTrait, String name)
  {
    return new Area(xzOffset, yOffset, isDominantTrait, "apicurious.area." + name);
  }

  // Fertility
  private static Fertility fertility(int offspring, boolean isDominantTrait, String name)
  {
    return new Fertility(offspring, isDominantTrait, "apicurious.fertility." + name);
  }

  // Flowers
  private static Flowers flowers(TagKey<Block> flowers, boolean isDominantTrait, String name)
  {
    return new Flowers(flowers, isDominantTrait, "apicurious.flowers." + name);
  }

  // Humidity Preference / Tolerance
  private static HumidityPreference humidityPreference(int humidity, TagKey<Biome> groupTag, boolean isDominantTrait, String name)
  {
    return new HumidityPreference(humidity, groupTag, isDominantTrait, "apicurious.preference.humidity." + name);
  }

  private static HumidityTolerance humidityTolerance(int toleranceModifier, boolean isDominantTrait, String name)
  {
    return new HumidityTolerance(toleranceModifier, isDominantTrait, "apicurious.tolerance.humidity." + name);
  }

  // Lifespan
  private static Lifespan lifespan(int cycles, boolean isDominantTrait, String name)
  {
    return new Lifespan(cycles, isDominantTrait, "apicurious.lifespan." + name);
  }

  // Pollination
  private static Pollination pollination(float pollinationChance, boolean isDominantTrait, String name)
  {
    return new Pollination(pollinationChance, isDominantTrait, "apicurious.pollination." + name);
  }

  // Speed
  private static Speed speed(float productionModifier, boolean isDominantTrait, String name)
  {
    return new Speed(productionModifier, isDominantTrait, "apicurious.speed." + name);
  }

  // Temperature Preference / Tolerance
  private static TemperaturePreference temperaturePreference(int temperature, TagKey<Biome> groupTag, boolean isDominantTrait, String name)
  {
    return new TemperaturePreference(temperature, groupTag, isDominantTrait, "apicurious.preference.temperature." + name);
  }

  private static TemperatureTolerance temperatureTolerance(int toleranceModifier, boolean isDominantTrait, String name)
  {
    return new TemperatureTolerance(toleranceModifier, isDominantTrait, "apicurious.tolerance.temperature." + name);
  }

  // Workcycle
  private static Workcycle workcycle(List<Workcycle.Interval> activeTimes, boolean isDominantTrait, String name)
  {
    return new Workcycle(activeTimes, isDominantTrait, "apicurious.workcycle." + name);
  }

}
