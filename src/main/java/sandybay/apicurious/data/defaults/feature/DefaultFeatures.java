package sandybay.apicurious.data.defaults.feature;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class DefaultFeatures
{
  public static void defaults(BootstrapContext<Feature<?>> bootstrap)
  {
    bootstrap.register(FeatureKeys.HIVE.feature(), new RandomPatchFeature(RandomPatchConfiguration.CODEC));
  }
}
