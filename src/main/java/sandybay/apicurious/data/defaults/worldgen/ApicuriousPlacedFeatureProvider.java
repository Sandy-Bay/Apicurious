package sandybay.apicurious.data.defaults.worldgen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ApicuriousPlacedFeatureProvider
{
  private static final int GROUND_RARITY = 24;   // ~1 attempted chunk in 24 (~384 blocks apart on average)
  private static final int GROUND_COUNT = 1;

  private static final int HANGING_RARITY = 20;  // more frequent chunk selection...
  private static final int HANGING_COUNT = 2;    // ...but each attempt is far less likely to hit

  private static final int CAVE_RARITY = 16;
  private static final int CAVE_COUNT = 4;

  private static final int WATER_RARITY = 24;
  private static final int WATER_COUNT = 1;

  private static final int NETHER_HANGING_RARITY = 20;
  private static final int NETHER_HANGING_COUNT = 2;

  private static final int NETHER_GROUND_RARITY = 16;
  private static final int NETHER_GROUND_COUNT = 2;

  private static final int END_RARITY = 24;
  private static final int END_COUNT = 1;

  public static void defaults(BootstrapContext<PlacedFeature> bootstrap)
  {
    HolderGetter<ConfiguredFeature<?, ?>> configuredGetter = bootstrap.lookup(Registries.CONFIGURED_FEATURE);

    registerHeightmapHive(bootstrap, configuredGetter, ApicuriousFeatureKeys.FOREST_HIVE, ApicuriousFeatureKeys.CONFIGURED_FOREST_HIVE, Heightmap.Types.WORLD_SURFACE_WG, HANGING_RARITY, HANGING_COUNT);
    registerHeightmapHive(bootstrap, configuredGetter, ApicuriousFeatureKeys.TROPICAL_HIVE, ApicuriousFeatureKeys.CONFIGURED_TROPICAL_HIVE, Heightmap.Types.WORLD_SURFACE_WG, HANGING_RARITY, HANGING_COUNT);
    registerHeightmapHive(bootstrap, configuredGetter, ApicuriousFeatureKeys.MARSHY_HIVE, ApicuriousFeatureKeys.CONFIGURED_MARSHY_HIVE, Heightmap.Types.WORLD_SURFACE_WG, HANGING_RARITY, HANGING_COUNT);
    registerHeightmapHive(bootstrap, configuredGetter, ApicuriousFeatureKeys.MEADOW_HIVE, ApicuriousFeatureKeys.CONFIGURED_MEADOW_HIVE, Heightmap.Types.WORLD_SURFACE_WG, GROUND_RARITY, GROUND_COUNT);
    registerHeightmapHive(bootstrap, configuredGetter, ApicuriousFeatureKeys.WINTRY_HIVE, ApicuriousFeatureKeys.CONFIGURED_WINTRY_HIVE, Heightmap.Types.WORLD_SURFACE_WG, GROUND_RARITY, GROUND_COUNT);
    registerHeightmapHive(bootstrap, configuredGetter, ApicuriousFeatureKeys.MODEST_HIVE, ApicuriousFeatureKeys.CONFIGURED_MODEST_HIVE, Heightmap.Types.WORLD_SURFACE_WG, GROUND_RARITY, GROUND_COUNT);
    registerHeightmapHive(bootstrap, configuredGetter, ApicuriousFeatureKeys.ENDER_HIVE, ApicuriousFeatureKeys.CONFIGURED_ENDER_HIVE, Heightmap.Types.WORLD_SURFACE_WG, END_RARITY, END_COUNT);
    bootstrap.register(ApicuriousFeatureKeys.ROCKY_HIVE, new PlacedFeature(configuredGetter.getOrThrow(ApicuriousFeatureKeys.CONFIGURED_ROCKY_HIVE), List.of(RarityFilter.onAverageOnceEvery(CAVE_RARITY), CountPlacement.of(CAVE_COUNT), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(60)), BiomeFilter.biome())));
    registerHeightmapHive(bootstrap, configuredGetter, ApicuriousFeatureKeys.WATER_HIVE, ApicuriousFeatureKeys.CONFIGURED_WATER_HIVE, Heightmap.Types.OCEAN_FLOOR_WG, WATER_RARITY, WATER_COUNT);
    registerHeightRangeHive(bootstrap, configuredGetter, ApicuriousFeatureKeys.NETHER_HIVE_HANGING, ApicuriousFeatureKeys.CONFIGURED_NETHER_HIVE_HANGING, VerticalAnchor.absolute(32), VerticalAnchor.absolute(100), NETHER_HANGING_RARITY, NETHER_HANGING_COUNT);
    registerHeightRangeHive(bootstrap, configuredGetter, ApicuriousFeatureKeys.NETHER_HIVE_GROUND, ApicuriousFeatureKeys.CONFIGURED_NETHER_HIVE_GROUND, VerticalAnchor.absolute(32), VerticalAnchor.absolute(100), NETHER_GROUND_RARITY, NETHER_GROUND_COUNT);
  }

  private static void registerHeightmapHive(BootstrapContext<PlacedFeature> bootstrap,
                                            HolderGetter<ConfiguredFeature<?, ?>> configuredGetter,
                                            ResourceKey<PlacedFeature> placedKey,
                                            ResourceKey<ConfiguredFeature<?, ?>> configuredKey,
                                            Heightmap.Types heightmap, int rarity, int count)
  {
    bootstrap.register(placedKey, new PlacedFeature(configuredGetter.getOrThrow(configuredKey), List.of(RarityFilter.onAverageOnceEvery(rarity), CountPlacement.of(count), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(heightmap), BiomeFilter.biome())));
  }

  private static void registerHeightRangeHive(BootstrapContext<PlacedFeature> bootstrap,
                                              HolderGetter<ConfiguredFeature<?, ?>> configuredGetter,
                                              ResourceKey<PlacedFeature> placedKey,
                                              ResourceKey<ConfiguredFeature<?, ?>> configuredKey, VerticalAnchor minY,
                                              VerticalAnchor maxY, int rarity, int count)
  {
    bootstrap.register(placedKey, new PlacedFeature(configuredGetter.getOrThrow(configuredKey), List.of(RarityFilter.onAverageOnceEvery(rarity), CountPlacement.of(count), InSquarePlacement.spread(), HeightRangePlacement.uniform(minY, maxY), BiomeFilter.biome())));
  }
}