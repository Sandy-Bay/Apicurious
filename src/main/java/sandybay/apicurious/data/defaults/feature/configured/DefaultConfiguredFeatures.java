package sandybay.apicurious.data.defaults.feature.configured;

import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import sandybay.apicurious.common.block.HiveBlock;
import sandybay.apicurious.common.registrar.BlockRegistrar;
import sandybay.apicurious.data.defaults.feature.FeatureKeys;

import java.util.List;

public class DefaultConfiguredFeatures
{

  public static final BlockPredicate HAS_SOLID_SIDE = BlockPredicate.anyOf(
          BlockPredicate.solid(new Vec3i(1, 0, 0)),
          BlockPredicate.solid(new Vec3i(-1, 0, 0)),
          BlockPredicate.solid(new Vec3i(0, 1, 0)),
          BlockPredicate.solid(new Vec3i(0, -1, 0)),
          BlockPredicate.solid(new Vec3i(0, 0, 1)),
          BlockPredicate.solid(new Vec3i(0, 0, -1))
  );

  public static List<BlockPredicate> DEFAULT_HIVE_PREDICATES = List.of(
          BlockPredicate.ONLY_IN_AIR_PREDICATE,
          DefaultConfiguredFeatures.HAS_SOLID_SIDE
  );

  public static void defaults(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap)
  {
    hive(bootstrap, FeatureKeys.FOREST_HIVE.configured(), BlockRegistrar.FOREST_HIVE, 8, 7, 3, DEFAULT_HIVE_PREDICATES);
    hive(bootstrap, FeatureKeys.MEADOWS_HIVE.configured(), BlockRegistrar.MEADOW_HIVE, 8, 7, 3, DEFAULT_HIVE_PREDICATES);
    hive(bootstrap, FeatureKeys.MODEST_HIVE.configured(), BlockRegistrar.MODEST_HIVE, 8, 7, 3, DEFAULT_HIVE_PREDICATES);
    hive(bootstrap, FeatureKeys.TROPICAL_HIVE.configured(), BlockRegistrar.TROPICAL_HIVE, 16, 7, 3, DEFAULT_HIVE_PREDICATES);
    hive(bootstrap, FeatureKeys.WINTRY_HIVE.configured(), BlockRegistrar.WINTRY_HIVE, 16, 7, 3, DEFAULT_HIVE_PREDICATES);
    hive(bootstrap, FeatureKeys.MARSHY_HIVE.configured(), BlockRegistrar.MARSHY_HIVE, 16, 7, 3, List.of(
            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE,
            DefaultConfiguredFeatures.HAS_SOLID_SIDE
    ));
    hive(bootstrap, FeatureKeys.ROCKY_HIVE.configured(), BlockRegistrar.ROCKY_HIVE, 64, 7, 3, DEFAULT_HIVE_PREDICATES);
    hive(bootstrap, FeatureKeys.NETHER_HIVE.configured(), BlockRegistrar.NETHER_HIVE, 16, 7, 3, DEFAULT_HIVE_PREDICATES);
    hive(bootstrap, FeatureKeys.ENDER_HIVE.configured(), BlockRegistrar.ENDER_HIVE, 8, 7, 3, DEFAULT_HIVE_PREDICATES);
    hive(bootstrap, FeatureKeys.WATER_HIVE.configured(), BlockRegistrar.WATER_HIVE, 8, 7, 3, List.of(
            BlockPredicate.matchesBlocks(Blocks.WATER),
            DefaultConfiguredFeatures.HAS_SOLID_SIDE
    ));
  }

  public static void hive(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap, ResourceKey<ConfiguredFeature<?, ?>> key,
                          BlockRegistrar.BlockItemHolder<HiveBlock, BlockItem> hive,
                          int tries, int xzSpread, int ySpread,
                          List<BlockPredicate> predicates)
  {
    feature(bootstrap, key, Feature.FLOWER,
            patch(
                    new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(hive.asBlock().defaultBlockState(), 1)),
                    tries,
                    xzSpread, ySpread,
                    predicates
            ));
  }

  public static <FC extends FeatureConfiguration, F extends Feature<FC>> void feature(
          BootstrapContext<ConfiguredFeature<?, ?>> bootstrap, ResourceKey<ConfiguredFeature<?, ?>> key,
          F feature, FC configuration)
  {
    bootstrap.register(key, new ConfiguredFeature<>(feature, configuration));
  }

  public static RandomPatchConfiguration patch(BlockStateProvider provider, int tries, int xzSpread, int ySpread, List<BlockPredicate> predicates)
  {
    return new RandomPatchConfiguration(
            tries,
            xzSpread,
            ySpread,
            PlacementUtils.filtered(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(provider),
                    BlockPredicate.allOf(predicates)
            )
    );
  }
}
