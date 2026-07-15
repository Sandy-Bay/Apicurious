package sandybay.apicurious.common.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.worldgen.feature.config.GroundHiveConfiguration;

public class GroundHiveFeature extends Feature<GroundHiveConfiguration>
{
  public GroundHiveFeature(Codec<GroundHiveConfiguration> codec)
  {
    super(codec);
  }

  @Override
  public boolean place(FeaturePlaceContext<GroundHiveConfiguration> context)
  {
    GroundHiveConfiguration config = context.config();
    WorldGenLevel level = context.level();
    BlockPos pos = context.origin();

    if (!canRestHere(config, level, pos))
    {
      return false;
    }

    level.setBlock(pos, config.hive().getState(level, level.getRandom(), pos), 2);
    Apicurious.LOGGER.debug("Placed ground hive at {}", pos);
    return true;
  }

  /**
   * Deliberately generic: checks for an empty space above a sturdy block, rather than a specific
   * tag (sand/netherrack/end stone/etc). Which biomes this feature actually spawns in is already
   * controlled by the accompanying BiomeModifier, so we don't need to re-check surface material here.
   * When {@code undergroundOnly} is set, also rejects any position exposed to the sky, so a hive
   * meant for caves doesn't also pop out on an open mountainside or hilltop.
   */
  private boolean canRestHere(GroundHiveConfiguration config, WorldGenLevel level, BlockPos pos)
  {
    if (!level.isEmptyBlock(pos))
    {
      return false;
    }
    if (config.undergroundOnly() && level.canSeeSky(pos))
    {
      return false;
    }
    BlockPos below = pos.below();
    return level.getBlockState(below).isFaceSturdy(level, below, Direction.UP);
  }
}