package sandybay.apicurious.common.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/**
 * Configuration for a hive that rests underwater on the ocean/river floor.
 */
public record SubmergedHiveConfiguration(BlockStateProvider hive) implements FeatureConfiguration
{
  public static final Codec<SubmergedHiveConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(BlockStateProvider.CODEC.fieldOf("hive").forGetter(SubmergedHiveConfiguration::hive)).apply(instance, SubmergedHiveConfiguration::new));
}