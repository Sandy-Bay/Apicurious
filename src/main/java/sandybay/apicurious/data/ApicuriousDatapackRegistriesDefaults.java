package sandybay.apicurious.data;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.species.BeeColor;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.ApicuriousSpecies;

import java.util.List;

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
      bootstrap.register(ApicuriousSpecies.FOREST, speciesWithColor("forest", ApicuriousConstants.FOREST));
      bootstrap.register(ApicuriousSpecies.MEADOW, speciesWithColor("meadow", ApicuriousConstants.MEADOW));
      bootstrap.register(ApicuriousSpecies.MODEST, speciesWithColor("modest", ApicuriousConstants.MODEST));
      bootstrap.register(ApicuriousSpecies.TROPICAL, speciesWithColor("tropical", ApicuriousConstants.TROPICAL));
      bootstrap.register(ApicuriousSpecies.WINTRY, speciesWithColor("wintry", ApicuriousConstants.WINTRY));
      bootstrap.register(ApicuriousSpecies.MARSHY, speciesWithColor("marshy", ApicuriousConstants.MARSHY));
      bootstrap.register(ApicuriousSpecies.ROCKY, speciesWithColor("rocky", ApicuriousConstants.ROCKY));
      bootstrap.register(ApicuriousSpecies.NETHER, speciesWithColor("nether", ApicuriousConstants.NETHER));
      bootstrap.register(ApicuriousSpecies.ENDER, speciesWithColor("ender", ApicuriousConstants.ENDER));
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
  private static BeeSpecies speciesWithColor(String name, BeeColor color) {
    return BeeSpecies.Builder.create(name).withVisualData(visual -> visual.withBeeColor(color).build()).build();
  }

  private static BeeSpecies.Builder getSpeciesBuilder(String name) {
    return BeeSpecies.Builder.create(name);
  }



}
