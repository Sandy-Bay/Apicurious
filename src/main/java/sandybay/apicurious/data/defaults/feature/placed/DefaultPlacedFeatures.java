package sandybay.apicurious.data.defaults.feature.placed;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import sandybay.apicurious.data.defaults.feature.FeatureKeys;

import java.util.List;

public class DefaultPlacedFeatures
{
  public static void defaults(BootstrapContext<PlacedFeature> bootstrap)
  {
    // Setup
    HolderGetter<ConfiguredFeature<?, ?>> configured = bootstrap.lookup(Registries.CONFIGURED_FEATURE);
    Holder<ConfiguredFeature<?, ?>> forest = configured.getOrThrow(FeatureKeys.FOREST_HIVE.configured());
    Holder<ConfiguredFeature<?, ?>> meadow = configured.getOrThrow(FeatureKeys.MEADOWS_HIVE.configured());
    Holder<ConfiguredFeature<?, ?>> modest = configured.getOrThrow(FeatureKeys.MODEST_HIVE.configured());
    Holder<ConfiguredFeature<?, ?>> tropical = configured.getOrThrow(FeatureKeys.TROPICAL_HIVE.configured());
    Holder<ConfiguredFeature<?, ?>> wintry = configured.getOrThrow(FeatureKeys.WINTRY_HIVE.configured());
    Holder<ConfiguredFeature<?, ?>> marshy = configured.getOrThrow(FeatureKeys.MARSHY_HIVE.configured());
    Holder<ConfiguredFeature<?, ?>> rocky = configured.getOrThrow(FeatureKeys.ROCKY_HIVE.configured());
    Holder<ConfiguredFeature<?, ?>> nether = configured.getOrThrow(FeatureKeys.NETHER_HIVE.configured());
    Holder<ConfiguredFeature<?, ?>> ender = configured.getOrThrow(FeatureKeys.ENDER_HIVE.configured());
    Holder<ConfiguredFeature<?, ?>> water = configured.getOrThrow(FeatureKeys.WATER_HIVE.configured());
    // Action
    bootstrap.register(FeatureKeys.FOREST_HIVE.placed(), new PlacedFeature(forest, List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            BiomeFilter.biome()
    )));
    bootstrap.register(FeatureKeys.MEADOWS_HIVE.placed(), new PlacedFeature(meadow, List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            BiomeFilter.biome()
    )));
    bootstrap.register(FeatureKeys.MODEST_HIVE.placed(), new PlacedFeature(modest, List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            BiomeFilter.biome()
    )));
    bootstrap.register(FeatureKeys.TROPICAL_HIVE.placed(), new PlacedFeature(tropical, List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            BiomeFilter.biome()
    )));
    bootstrap.register(FeatureKeys.WINTRY_HIVE.placed(), new PlacedFeature(wintry, List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            BiomeFilter.biome()
    )));
    bootstrap.register(FeatureKeys.MARSHY_HIVE.placed(), new PlacedFeature(marshy, List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            BiomeFilter.biome()
    )));
    bootstrap.register(FeatureKeys.ROCKY_HIVE.placed(), new PlacedFeature(rocky, List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(54)),
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            BiomeFilter.biome()
    )));
    bootstrap.register(FeatureKeys.NETHER_HIVE.placed(), new PlacedFeature(nether, List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            CountOnEveryLayerPlacement.of(4),
            BiomeFilter.biome()
    )));
    bootstrap.register(FeatureKeys.ENDER_HIVE.placed(), new PlacedFeature(ender, List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            BiomeFilter.biome()
    )));
    bootstrap.register(FeatureKeys.WATER_HIVE.placed(), new PlacedFeature(water, List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_TOP_SOLID,
            BiomeFilter.biome()
    )));
  }
}
