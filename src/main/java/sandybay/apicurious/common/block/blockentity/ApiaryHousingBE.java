package sandybay.apicurious.common.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.api.housing.blockentity.SimpleBlockHousingBE;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;

public class ApiaryHousingBE extends SimpleBlockHousingBE {

  public ApiaryHousingBE(BlockPos pos, BlockState blockState) {
    super(ApicuriousBlockRegistration.APIARY.getType(), pos, blockState);
  }


}
