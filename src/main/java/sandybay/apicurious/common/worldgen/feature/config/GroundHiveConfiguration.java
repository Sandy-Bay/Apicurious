package sandybay.apicurious.common.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/**
 * Configuration for a hive that simply rests on solid ground - no leaves/logs required.
 * Suited for hives spawning in open biomes (desert/savanna/badlands, the Nether, the End) as well
 * as in caves underground (Rocky), toggled via {@code undergroundOnly}.
 */
public record GroundHiveConfiguration(BlockStateProvider hive, boolean undergroundOnly) implements FeatureConfiguration
{
  public static final Codec<GroundHiveConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(BlockStateProvider.CODEC.fieldOf("hive").forGetter(GroundHiveConfiguration::hive), Codec.BOOL.optionalFieldOf("underground_only", false).forGetter(GroundHiveConfiguration::undergroundOnly)).apply(instance, GroundHiveConfiguration::new));

  /**
   * Convenience constructor for the common surface case.
   */
  public GroundHiveConfiguration(BlockStateProvider hive)
  {
    this(hive, false);
  }
}