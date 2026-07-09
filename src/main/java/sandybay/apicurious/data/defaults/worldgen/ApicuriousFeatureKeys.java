package sandybay.apicurious.data.defaults.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import sandybay.apicurious.Apicurious;

public class ApicuriousFeatureKeys
{
  public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_HANGING_FOREST_HIVE = ResourceKey.create(Registries.CONFIGURED_FEATURE, Apicurious.createIdentifier("hanging_forest_hive"));
  public static final ResourceKey<PlacedFeature> HANGING_FOREST_HIVE = ResourceKey.create(Registries.PLACED_FEATURE, Apicurious.createIdentifier("hanging_forest_hive"));

}
