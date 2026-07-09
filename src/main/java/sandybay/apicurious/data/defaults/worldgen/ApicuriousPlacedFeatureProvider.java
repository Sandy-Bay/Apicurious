package sandybay.apicurious.data.defaults.worldgen;

import net.minecraft.core.HolderGetter;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.MatchingBiomesPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ApicuriousPlacedFeatureProvider
{

  public static void defaults(BootstrapContext<PlacedFeature> bootstrap)
  {
    HolderGetter<ConfiguredFeature<?, ?>> configuredGetter = bootstrap.lookup(Registries.CONFIGURED_FEATURE);
    HolderGetter<Biome> biomeGetter = bootstrap.lookup(Registries.BIOME);
    bootstrap.register(ApicuriousFeatureKeys.HANGING_FOREST_HIVE, new PlacedFeature(configuredGetter.getOrThrow(ApicuriousFeatureKeys.CONFIGURED_HANGING_FOREST_HIVE), List.of(
            BlockPredicateFilter.forPredicate(new MatchingBiomesPredicate(biomeGetter.getOrThrow(BiomeTags.IS_FOREST))),
            InSquarePlacement.spread(),
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
            CountPlacement.of(512)
    )));
  }
}
