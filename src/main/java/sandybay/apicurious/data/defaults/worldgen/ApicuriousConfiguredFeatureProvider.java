package sandybay.apicurious.data.defaults.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import sandybay.apicurious.common.registrar.BlockRegistrar;
import sandybay.apicurious.common.registrar.FeatureRegistrar;
import sandybay.apicurious.common.worldgen.feature.HangingHiveConfiguration;
import sandybay.apicurious.common.worldgen.feature.HangingHiveFeature;

public class ApicuriousConfiguredFeatureProvider
{
  public static final HangingHiveConfiguration FOREST_CONFIG = new HangingHiveConfiguration(
          BlockStateProvider.simple(BlockRegistrar.FOREST_HIVE.asBlock()),
          BlockTags.LEAVES,
          BlockTags.LOGS,
          true
  );

  public static Holder<ConfiguredFeature<?, ?>> FOREST_HIVE;

  public static void defaults(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap)
  {
    HolderGetter<Feature<?>> featureGetter = bootstrap.lookup(Registries.FEATURE);
    Holder.Reference<Feature<?>> hanging_hive = featureGetter.getOrThrow(FeatureRegistrar.HANGING_HIVE.getKey());
    FOREST_HIVE = bootstrap.register(ApicuriousFeatureKeys.CONFIGURED_HANGING_FOREST_HIVE, new ConfiguredFeature<>((HangingHiveFeature) hanging_hive.value(), FOREST_CONFIG));
  }
}
