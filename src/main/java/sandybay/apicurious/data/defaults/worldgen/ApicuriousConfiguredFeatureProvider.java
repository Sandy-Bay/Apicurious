package sandybay.apicurious.data.defaults.worldgen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import sandybay.apicurious.common.registrar.BlockRegistrar;
import sandybay.apicurious.common.registrar.FeatureRegistrar;
import sandybay.apicurious.common.worldgen.feature.config.GroundHiveConfiguration;
import sandybay.apicurious.common.worldgen.feature.config.HangingHiveConfiguration;
import sandybay.apicurious.common.worldgen.feature.config.SubmergedHiveConfiguration;

public class ApicuriousConfiguredFeatureProvider
{
  public static void defaults(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap)
  {
    registerHanging(bootstrap, ApicuriousFeatureKeys.CONFIGURED_FOREST_HIVE, BlockRegistrar.FOREST_HIVE.asBlock());
    registerGround(bootstrap, ApicuriousFeatureKeys.CONFIGURED_MEADOW_HIVE, BlockRegistrar.MEADOW_HIVE.asBlock(), false);
    registerHanging(bootstrap, ApicuriousFeatureKeys.CONFIGURED_TROPICAL_HIVE, BlockRegistrar.TROPICAL_HIVE.asBlock());
    registerGround(bootstrap, ApicuriousFeatureKeys.CONFIGURED_WINTRY_HIVE, BlockRegistrar.WINTRY_HIVE.asBlock(), false);
    registerHanging(bootstrap, ApicuriousFeatureKeys.CONFIGURED_MARSHY_HIVE, BlockRegistrar.MARSHY_HIVE.asBlock());
    registerGround(bootstrap, ApicuriousFeatureKeys.CONFIGURED_MODEST_HIVE, BlockRegistrar.MODEST_HIVE.asBlock(), false);
    registerGround(bootstrap, ApicuriousFeatureKeys.CONFIGURED_ENDER_HIVE, BlockRegistrar.ENDER_HIVE.asBlock(), false);
    registerGround(bootstrap, ApicuriousFeatureKeys.CONFIGURED_ROCKY_HIVE, BlockRegistrar.ROCKY_HIVE.asBlock(), true);
    registerHanging(bootstrap, ApicuriousFeatureKeys.CONFIGURED_NETHER_HIVE_HANGING, BlockRegistrar.NETHER_HIVE.asBlock(), BlockTags.WART_BLOCKS, BlockTags.LOGS);
    registerGround(bootstrap, ApicuriousFeatureKeys.CONFIGURED_NETHER_HIVE_GROUND, BlockRegistrar.NETHER_HIVE.asBlock(), false);
    bootstrap.register(ApicuriousFeatureKeys.CONFIGURED_WATER_HIVE, new ConfiguredFeature<>(
            FeatureRegistrar.SUBMERGED_HIVE.get(), new SubmergedHiveConfiguration(BlockStateProvider.simple(BlockRegistrar.WATER_HIVE.asBlock()))
    ));
  }

  private static void registerHanging(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap,
                                      ResourceKey<ConfiguredFeature<?, ?>> key, Block hiveBlock)
  {
    registerHanging(bootstrap, key, hiveBlock, BlockTags.LEAVES, BlockTags.LOGS);
  }

  private static void registerHanging(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap,
                                      ResourceKey<ConfiguredFeature<?, ?>> key, Block hiveBlock,
                                      TagKey<Block> leafTag, TagKey<Block> logTag)
  {
    bootstrap.register(key, new ConfiguredFeature<>(
            FeatureRegistrar.HANGING_HIVE.get(), new HangingHiveConfiguration(BlockStateProvider.simple(hiveBlock), leafTag, logTag, true)
    ));
  }

  private static void registerGround(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap,
                                     ResourceKey<ConfiguredFeature<?, ?>> key, Block hiveBlock,
                                     boolean undergroundOnly)
  {
    bootstrap.register(key, new ConfiguredFeature<>(
            FeatureRegistrar.GROUND_HIVE.get(),
            new GroundHiveConfiguration(BlockStateProvider.simple(hiveBlock), undergroundOnly)
    ));
  }
}