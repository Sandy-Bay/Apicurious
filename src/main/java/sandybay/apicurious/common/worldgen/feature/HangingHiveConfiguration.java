package sandybay.apicurious.common.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record HangingHiveConfiguration(BlockStateProvider hive, TagKey<Block> leafTag, TagKey<Block> logTag, boolean requireLog) implements FeatureConfiguration
{
  public static final Codec<HangingHiveConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
          BlockStateProvider.CODEC.fieldOf("block").forGetter(HangingHiveConfiguration::hive),
          TagKey.codec(Registries.BLOCK).fieldOf("leafTag").forGetter(HangingHiveConfiguration::leafTag),
          TagKey.codec(Registries.BLOCK).fieldOf("logTag").forGetter(HangingHiveConfiguration::logTag),
          Codec.BOOL.fieldOf("requireLog").forGetter(HangingHiveConfiguration::requireLog)
  ).apply(instance, HangingHiveConfiguration::new));
}
