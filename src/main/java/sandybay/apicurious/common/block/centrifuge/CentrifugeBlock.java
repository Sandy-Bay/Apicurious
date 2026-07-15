package sandybay.apicurious.common.block.centrifuge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import org.jetbrains.annotations.NotNull;
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
  protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos,
                                                      @NotNull Player player, @NotNull BlockHitResult hitResult)
  {
    if (level.isClientSide())
    {
      return InteractionResult.SUCCESS;
    }
    if (level.getBlockEntity(pos) instanceof CentrifugeBE centrifuge)
    {
      player.openMenu(centrifuge, pos);
      return InteractionResult.CONSUME;
    }
    return InteractionResult.PASS;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state)
  {
    return new CentrifugeBE(pos, state);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state,
                                                                @NotNull BlockEntityType<T> blockEntityType)
  {
    return (lvl, pos, blockState, blockEntity) ->
    {
      if (blockEntity instanceof ITicker tickable)
      {
        if (lvl.isClientSide())
        {
          tickable.clientTick(lvl, pos, blockState);
        }
        else
        {
          tickable.serverTick(lvl, pos, blockState);
        }
      }
    };
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
  {
    super.createBlockStateDefinition(builder);
    builder.add(BlockStateProperties.FACING);
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context)
  {
    return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getHorizontalDirection().getOpposite());
  }
}