package sandybay.apicurious.data.defaults.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import sandybay.apicurious.Apicurious;

public class FeatureKeys
{
  public static final FeatureKeyHolder HIVE = key("hive");

  public static FeatureKeyHolder key(String name) {
    return new FeatureKeyHolder(feature(name), configured(name), placed(name));
  }

  private static ResourceKey<Feature<?>> feature(String name)
  {
    return ResourceKey.create(Registries.FEATURE, Apicurious.createResourceLocation("feature_" + name));
  }

  private static ResourceKey<ConfiguredFeature<?, ?>> configured(String name)
  {
    return ResourceKey.create(Registries.CONFIGURED_FEATURE, Apicurious.createResourceLocation("configured_" + name));
  }

  private static ResourceKey<PlacedFeature> placed(String name)
  {
    return ResourceKey.create(Registries.PLACED_FEATURE, Apicurious.createResourceLocation("placed_" + name));
  }

  public record FeatureKeyHolder(ResourceKey<Feature<?>> feature, ResourceKey<ConfiguredFeature<?, ?>> configured, ResourceKey<PlacedFeature> placed) { }
}
