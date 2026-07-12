package sandybay.apicurious.data.defaults.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import sandybay.apicurious.Apicurious;

public class ApicuriousFeatureKeys
{
  public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_FOREST_HIVE = configured("forest_hive");
  public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_MEADOW_HIVE = configured("meadow_hive");
  public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_MODEST_HIVE = configured("modest_hive");
  public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_TROPICAL_HIVE = configured("tropical_hive");
  public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_WINTRY_HIVE = configured("wintry_hive");
  public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_MARSHY_HIVE = configured("marshy_hive");
  public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_ROCKY_HIVE = configured("rocky_hive");
  public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_ENDER_HIVE = configured("ender_hive");
  public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_WATER_HIVE = configured("water_hive");
  public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_NETHER_HIVE_HANGING = configured("nether_hive_hanging");
  public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_NETHER_HIVE_GROUND = configured("nether_hive_ground");

  public static final ResourceKey<PlacedFeature> FOREST_HIVE = placed("forest_hive");
  public static final ResourceKey<PlacedFeature> MEADOW_HIVE = placed("meadow_hive");
  public static final ResourceKey<PlacedFeature> MODEST_HIVE = placed("modest_hive");
  public static final ResourceKey<PlacedFeature> TROPICAL_HIVE = placed("tropical_hive");
  public static final ResourceKey<PlacedFeature> WINTRY_HIVE = placed("wintry_hive");
  public static final ResourceKey<PlacedFeature> MARSHY_HIVE = placed("marshy_hive");
  public static final ResourceKey<PlacedFeature> ROCKY_HIVE = placed("rocky_hive");
  public static final ResourceKey<PlacedFeature> ENDER_HIVE = placed("ender_hive");
  public static final ResourceKey<PlacedFeature> WATER_HIVE = placed("water_hive");
  public static final ResourceKey<PlacedFeature> NETHER_HIVE_HANGING = placed("nether_hive_hanging");
  public static final ResourceKey<PlacedFeature> NETHER_HIVE_GROUND = placed("nether_hive_ground");

  private static ResourceKey<ConfiguredFeature<?, ?>> configured(String name)
  {
    return ResourceKey.create(Registries.CONFIGURED_FEATURE, Apicurious.createIdentifier(name));
  }

  private static ResourceKey<PlacedFeature> placed(String name)
  {
    return ResourceKey.create(Registries.PLACED_FEATURE, Apicurious.createIdentifier(name));
  }
}