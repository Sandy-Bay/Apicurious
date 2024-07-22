package sandybay.apicurious.api.housing;

import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public abstract class BaseHousingBlock extends Block implements EntityBlock {

  public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

  private final float basePollinationChance;

  public BaseHousingBlock(Properties properties, float basePollinationChance) {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false));
    this.basePollinationChance = basePollinationChance;
  }

  // TODO: Implement later
  // TODO: Replace '* 0f' with the pollination modifier of the Bee.
  //public boolean shouldPollinate(RandomSource random) {
  //  return random.nextFloat() > Math.clamp(basePollinationChance * 0f, 0f, 1f);
  //}

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level mLevel, BlockState mState, BlockEntityType<T> blockEntityType) {
    return (level, pos, state, blockEntity) -> {
      if (blockEntity instanceof ITickingApiary tickable) {
        if (level.isClientSide()) {
          tickable.clientTick(level, pos, state);
        } else {
          tickable.serverTick(level, pos, state);
        }
      }
    };
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(ACTIVE);
  }
}
