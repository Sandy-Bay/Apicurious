package sandybay.apicurious.api.housing;

import net.minecraft.core.BlockPos;
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
import sandybay.apicurious.api.item.IFrameItem;
import sandybay.apicurious.api.register.DataComponentRegistration;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.genetic.allele.Area;
import sandybay.apicurious.common.bee.genetic.allele.Pollination;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseHousingBlock extends Block implements EntityBlock
{

  public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

  private final float basePollinationModifier;

  public BaseHousingBlock(Properties properties, float basePollinationModifier)
  {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false));
    this.basePollinationModifier = basePollinationModifier;
  }

  public Set<BlockPos> getTerritory(ItemStack queen, BlockPos housingPosition, List<ItemStack> frames)
  {
    Set<BlockPos> territory = new HashSet<>();
    if (!queen.has(DataComponentRegistration.GENOME)) return territory;
    Genome genome = queen.get(DataComponentRegistration.GENOME);
    if (genome == null) return territory;
    Area area = genome.getArea(true);
    int xzOffset = area.getXZOffset();
    int yOffset = area.getYOffset();

    for (ItemStack stack : frames)
    {
      if (stack.getItem() instanceof IFrameItem frame)
      {
        // Make it so the minimum size is a 3x3x3, to avoid bricking the apiary
        xzOffset = Math.max(1, frame.getTerritoryModifier().xzMod().apply(xzOffset));
        yOffset = Math.max(1, frame.getTerritoryModifier().yMod().apply(yOffset));
      }
    }

    for (int x = housingPosition.getX() - xzOffset; x < housingPosition.getX() + xzOffset; x++)
    {
      for (int y = housingPosition.getY() - yOffset; y < housingPosition.getY() + yOffset; y++)
      {
        for (int z = housingPosition.getZ() - xzOffset; z < housingPosition.getZ() + xzOffset; z++)
        {
          if (x == housingPosition.getX() && y == housingPosition.getY() && z == housingPosition.getZ()) continue;
          territory.add(new BlockPos(x, y, z));
        }
      }
    }
    return territory;
  }

  public boolean shouldPollinate(RandomSource random, ItemStack queen)
  {
    if (!queen.has(DataComponentRegistration.GENOME)) return false;
    Genome genome = queen.get(DataComponentRegistration.GENOME);
    if (genome == null) return false;
    Pollination pollination = genome.getPollination(true);
    return random.nextFloat() < Math.clamp(pollination.getPollinationChance() * basePollinationModifier, 0f, 1f);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level mLevel, BlockState mState, BlockEntityType<T> blockEntityType)
  {
    return (level, pos, state, blockEntity) ->
    {
      if (blockEntity instanceof ITickingApiary tickable)
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
    builder.add(ACTIVE);
  }
}
