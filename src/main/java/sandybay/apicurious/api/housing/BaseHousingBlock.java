package sandybay.apicurious.api.housing;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.trait.Pollination;
import sandybay.apicurious.common.register.ApicuriousDataComponentRegistration;

public abstract class BaseHousingBlock extends Block implements EntityBlock {

  public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

  private final float basePollinationModifier;

  public BaseHousingBlock(Properties properties, float basePollinationModifier) {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false));
    this.basePollinationModifier = basePollinationModifier;
  }

  public boolean shouldPollinate(RandomSource random, ItemStack queen) {
    if (!queen.has(ApicuriousDataComponentRegistration.BEE_SPECIES)) return false;
    BeeSpecies species = queen.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null) return false;
    Pollination pollination = species.getProductionData().getPollination().value();
    return random.nextFloat() > Math.clamp(pollination.getPollinationChance() * basePollinationModifier, 0f, 1f);
  }

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
