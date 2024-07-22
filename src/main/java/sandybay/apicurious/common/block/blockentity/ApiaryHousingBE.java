package sandybay.apicurious.common.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.api.housing.blockentity.SimpleBlockHousingBE;
import sandybay.apicurious.common.block.ApicuriousBlockRegistration;

public class ApiaryHousingBE extends SimpleBlockHousingBE {

  public ApiaryHousingBE(BlockPos pos, BlockState blockState) {
    super(ApicuriousBlockRegistration.APIARY.entityType().get(), pos, blockState);
  }



}
