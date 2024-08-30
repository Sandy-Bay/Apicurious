package sandybay.apicurious.data.defaults.feature.configured;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import sandybay.apicurious.common.block.HiveBlock;
import sandybay.apicurious.common.registrar.BlockRegistrar;
import sandybay.apicurious.data.defaults.feature.FeatureKeys;

public class DefaultConfiguredFeatures
{
  public static void defaults(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap)
  {
    hive(bootstrap, FeatureKeys.HIVE.configured(), BlockRegistrar.FOREST_HIVE, 16);
  }

  public static void hive(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap, ResourceKey<ConfiguredFeature<?, ?>> key,
                          BlockRegistrar.BlockItemHolder<HiveBlock, BlockItem> hive, int tries)
  {
    feature(bootstrap, key, Feature.FLOWER,
            patch(
                    new WeightedStateProvider(
                            SimpleWeightedRandomList.<BlockState>builder().add(hive.asBlock().defaultBlockState(), 1)
                    ),
                    tries
            ));
  }

  public static <FC extends FeatureConfiguration, F extends Feature<FC>> void feature(
          BootstrapContext<ConfiguredFeature<?, ?>> bootstrap, ResourceKey<ConfiguredFeature<?, ?>> key,
          F feature, FC configuration)
  {
    bootstrap.register(key, new ConfiguredFeature<>(feature, configuration));
  }

  public static RandomPatchConfiguration patch(BlockStateProvider provider, int tries)
  {
    return FeatureUtils.simpleRandomPatchConfiguration(
            tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(provider))
    );
  }
}
