package sandybay.apicurious.common.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import sandybay.apicurious.common.worldgen.feature.config.HangingHiveConfiguration;
import sandybay.apicurious.Apicurious;

public class HangingHiveFeature extends Feature<HangingHiveConfiguration>
{
  public HangingHiveFeature(Codec<HangingHiveConfiguration> codec)
  {
    super(codec);
  }

  @Override
  public boolean place(FeaturePlaceContext<HangingHiveConfiguration> context)
  {
    HangingHiveConfiguration config = context.config();
    WorldGenLevel level = context.level();
    BlockPos pos = context.origin();

    if (!canHangHere(config, level, pos))
    {
      return false;
    }

    level.setBlock(pos, config.hive().getState(level, level.getRandom(), pos), 2);
    Apicurious.LOGGER.debug("Placed hanging hive at {}", pos);
    return true;
  }

  private boolean canHangHere(HangingHiveConfiguration config, WorldGenLevel level, BlockPos pos)
  {
    if (!level.isEmptyBlock(pos))
    {
      return false;
    }
    if (!level.getBlockState(pos.above()).is(config.leafTag()))
    {
      return false;
    }
    return !config.requireLog() || hasNearbyLog(config, level, pos);
  }

  /**
   * Searches a small cube around the hive position for a log/stem block, so the hive only hangs
   * near an actual trunk rather than under isolated / decorative leaves.
   */
  private boolean hasNearbyLog(HangingHiveConfiguration config, WorldGenLevel level, BlockPos pos)
  {
    int radius = config.logSearchRadius();
    BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
    for (int dx = -radius; dx <= radius; dx++)
    {
      for (int dy = -radius; dy <= radius; dy++)
      {
        for (int dz = -radius; dz <= radius; dz++)
        {
          cursor.set(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);
          if (level.getBlockState(cursor).is(config.logTag()))
          {
            return true;
          }
        }
      }
    }
    return false;
  }
}