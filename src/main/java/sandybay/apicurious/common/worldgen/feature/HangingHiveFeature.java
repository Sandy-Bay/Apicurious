package sandybay.apicurious.common.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
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
    if (canHangHere(config, level, pos))
    {
      level.setBlock(pos, config.hive().getState(level, level.getRandom(), pos), 2);
      Apicurious.LOGGER.info("Placed Hanging-Hive at {}", pos);
      return true;
    }
    return false;
  }

  private boolean canHangHere(HangingHiveConfiguration config, WorldGenLevel level, BlockPos pos)
  {
    return level.isEmptyBlock(pos) && level.getBlockState(pos.above()).is(config.leafTag());
  }
}
