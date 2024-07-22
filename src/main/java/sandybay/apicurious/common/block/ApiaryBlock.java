package sandybay.apicurious.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.api.housing.BaseHousingBlock;
import sandybay.apicurious.common.block.blockentity.ApiaryHousingBE;

public class ApiaryBlock extends BaseHousingBlock {

  public ApiaryBlock(Properties properties) {
    super(properties, 1.0f);
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new ApiaryHousingBE(pos, state);
  }
}
