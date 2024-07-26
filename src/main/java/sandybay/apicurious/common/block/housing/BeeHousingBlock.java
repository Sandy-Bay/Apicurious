package sandybay.apicurious.common.block.housing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.api.housing.BaseHousingBlock;
import sandybay.apicurious.common.block.blockentity.BasicBeeHousingBE;

public class BeeHousingBlock extends BaseHousingBlock {

  public BeeHousingBlock(Properties properties) {
    super(properties, 3.0f);
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
    return new BasicBeeHousingBE(pos, state);
  }
}
