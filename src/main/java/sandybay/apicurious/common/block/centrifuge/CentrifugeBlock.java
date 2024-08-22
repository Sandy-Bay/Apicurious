package sandybay.apicurious.common.block.centrifuge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.api.housing.ITicker;
import sandybay.apicurious.common.block.centrifuge.blockentity.CentrifugeBE;

public class CentrifugeBlock extends Block implements EntityBlock
{

  public CentrifugeBlock(Properties properties)
  {
    super(properties);
  }

  @Override
  protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
    if (!level.isClientSide())
    {
      if (level.getBlockEntity(pos) instanceof CentrifugeBE centrifuge)
      {
        player.openMenu(centrifuge, pos);
      }
    }
    return InteractionResult.sidedSuccess(level.isClientSide());
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
  {
    return new CentrifugeBE(pos, state);
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
