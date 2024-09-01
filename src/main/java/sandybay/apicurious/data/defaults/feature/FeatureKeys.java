package sandybay.apicurious.data.defaults.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import sandybay.apicurious.Apicurious;

public class FeatureKeys
{
  // Feature Keys
  public static final ResourceKey<Feature<?>> HIVE = feature("apicurious_hive");

  // Configured / Placed Keys
  public static final FeatureKeyHolder FOREST_HIVE = hive("forest");
  public static final FeatureKeyHolder MEADOWS_HIVE = hive("meadows");
  public static final FeatureKeyHolder MODEST_HIVE = hive("modest");
  public static final FeatureKeyHolder TROPICAL_HIVE = hive("tropical");
  public static final FeatureKeyHolder WINTRY_HIVE = hive("wintry");
  public static final FeatureKeyHolder MARSHY_HIVE = hive("marshy");
  public static final FeatureKeyHolder ROCKY_HIVE = hive("rocky");
  public static final FeatureKeyHolder NETHER_HIVE = hive("nether");
  public static final FeatureKeyHolder ENDER_HIVE = hive("ender");
  public static final FeatureKeyHolder WATER_HIVE = hive("water");

  public static FeatureKeyHolder hive(String name)
  {
    return new FeatureKeyHolder(
            configured(name + "_hive"),
            placed(name + "_hive"),
            modifier(name + "hive")
    );
  }

  private static ResourceKey<Feature<?>> feature(String name)
  {
    return ResourceKey.create(Registries.FEATURE, Apicurious.createResourceLocation(name));
  }

  private static ResourceKey<ConfiguredFeature<?, ?>> configured(String name)
  {
    return ResourceKey.create(Registries.CONFIGURED_FEATURE, Apicurious.createResourceLocation("configured_" + name));
  }

  private static ResourceKey<PlacedFeature> placed(String name)
  {
    return ResourceKey.create(Registries.PLACED_FEATURE, Apicurious.createResourceLocation("placed_" + name));
  }

  private static ResourceKey<BiomeModifier> modifier(String name)
  {
    return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Apicurious.createResourceLocation("modifier_" + name));
  }

  public record FeatureKeyHolder(ResourceKey<ConfiguredFeature<?, ?>> configured, ResourceKey<PlacedFeature> placed, ResourceKey<BiomeModifier> modifier) { }
}
