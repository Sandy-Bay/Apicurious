package sandybay.apicurious.data.defaults.feature.placed;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import sandybay.apicurious.data.defaults.feature.FeatureKeys;

import java.util.List;

public class DefaultPlacedFeatures
{
  public static void defaults(BootstrapContext<PlacedFeature> bootstrap)
  {
    HolderGetter<ConfiguredFeature<?, ?>> configured = bootstrap.lookup(Registries.CONFIGURED_FEATURE);
    Holder<ConfiguredFeature<?, ?>> forest_hive = configured.getOrThrow(VegetationFeatures.FLOWER_DEFAULT);
    bootstrap.register(FeatureKeys.HIVE.placed(), new PlacedFeature(forest_hive, List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
  }
}
