package sandybay.apicurious.common.block.centrifuge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.api.housing.ITicker;

public class CentrifugeBlock extends Block implements EntityBlock
{

  public CentrifugeBlock(Properties properties)
  {
    super(properties);
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
  {
    return null;
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
  {
    return (level, pos, state, blockEntity) ->
    {
      if (blockEntity instanceof ITicker tickable)
      {
        if (level.isClientSide())
        {
          tickable.clientTick(level, pos, state);
        } else
        {
          tickable.serverTick(level, pos, state);
        }
      }
    };
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
  {
    builder.add(BlockStateProperties.FACING);
  }
}
