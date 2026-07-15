package sandybay.apicurious.common.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/**
 * Configuration for a hive that hangs beneath leaves. If {@code requireLog} is true, a log/stem
 * block must also be found within {@code logSearchRadius} blocks, so the hive only spawns near an
 * actual trunk rather than under any stray or decorative leaf block.
 */
public record HangingHiveConfiguration(BlockStateProvider hive, TagKey<Block> leafTag, TagKey<Block> logTag,
                                       boolean requireLog, int logSearchRadius) implements FeatureConfiguration
{
  public static final Codec<HangingHiveConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(BlockStateProvider.CODEC.fieldOf("hive").forGetter(HangingHiveConfiguration::hive), TagKey.codec(Registries.BLOCK).fieldOf("leaf_tag").forGetter(HangingHiveConfiguration::leafTag), TagKey.codec(Registries.BLOCK).fieldOf("log_tag").forGetter(HangingHiveConfiguration::logTag), Codec.BOOL.fieldOf("require_log").forGetter(HangingHiveConfiguration::requireLog), Codec.INT.optionalFieldOf("log_search_radius", 3).forGetter(HangingHiveConfiguration::logSearchRadius)).apply(instance, HangingHiveConfiguration::new));

  /**
   * Convenience constructor for datagen call-sites that don't need a custom search radius.
   */
  public HangingHiveConfiguration(BlockStateProvider hive, TagKey<Block> leafTag, TagKey<Block> logTag,
                                  boolean requireLog)
  {
    this(hive, leafTag, logTag, requireLog, 3);
  }
}