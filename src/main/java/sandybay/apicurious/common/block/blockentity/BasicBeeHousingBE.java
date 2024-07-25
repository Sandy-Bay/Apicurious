package sandybay.apicurious.common.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.api.housing.blockentity.SimpleBlockHousingBE;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;

public class BasicBeeHousingBE extends SimpleBlockHousingBE {

  public BasicBeeHousingBE(BlockPos pos, BlockState blockState) {
    super(ApicuriousBlockRegistration.BEE_HOUSING.getType(), pos, blockState);
  }

}