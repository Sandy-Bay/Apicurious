package sandybay.apicurious.common.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluids;
import sandybay.apicurious.common.worldgen.feature.config.SubmergedHiveConfiguration;
import sandybay.apicurious.Apicurious;

public class SubmergedHiveFeature extends Feature<SubmergedHiveConfiguration>
{
  public SubmergedHiveFeature(Codec<SubmergedHiveConfiguration> codec)
  {
    super(codec);
  }

  @Override
  public boolean place(FeaturePlaceContext<SubmergedHiveConfiguration> context)
  {
    SubmergedHiveConfiguration config = context.config();
    WorldGenLevel level = context.level();
    BlockPos pos = context.origin();

    if (!canRestHere(level, pos))
    {
      return false;
    }

    BlockState state = config.hive().getState(level, level.getRandom(), pos);
    // If the hive block declares a waterlogged property, mark it wet so it doesn't look like it's
    // floating in an air pocket. If your hive block isn't waterloggable, this is a no-op.
    if (state.hasProperty(BlockStateProperties.WATERLOGGED))
    {
      state = state.setValue(BlockStateProperties.WATERLOGGED, true);
    }

    level.setBlock(pos, state, 2);
    Apicurious.LOGGER.debug("Placed submerged hive at {}", pos);
    return true;
  }

  private boolean canRestHere(WorldGenLevel level, BlockPos pos)
  {
    if (!level.getFluidState(pos).is(Fluids.WATER) || !level.getBlockState(pos).canBeReplaced())
    {
      return false;
    }
    BlockPos below = pos.below();
    return level.getBlockState(below).isFaceSturdy(level, below, Direction.UP);
  }
}