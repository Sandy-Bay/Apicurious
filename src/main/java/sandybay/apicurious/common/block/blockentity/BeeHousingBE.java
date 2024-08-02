package sandybay.apicurious.common.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.api.housing.blockentity.SimpleBlockHousingBE;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;

public class BeeHousingBE extends SimpleBlockHousingBE
{

  public BeeHousingBE(BlockPos pos, BlockState blockState)
  {
    super(ApicuriousBlockRegistration.BEE_HOUSING.getType(), pos, blockState);
  }

  @Override
  public void serverTick(Level level, BlockPos pos, BlockState state)
  {

  }

  @Override
  public void clientTick(Level level, BlockPos pos, BlockState state)
  {

  }
}
