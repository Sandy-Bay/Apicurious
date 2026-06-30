package sandybay.apicurious.client.tinter.block;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.api.util.ClientHelper;

public record HiveBlockTinter() implements BlockTintSource
{
  @Override
  public int color(BlockState state)
  {
    return ClientHelper.getHiveTint(state.getBlock());
  }
}
