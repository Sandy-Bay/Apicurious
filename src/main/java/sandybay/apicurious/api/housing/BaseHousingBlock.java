package sandybay.apicurious.api.housing;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
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
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.bee.IBeeItem;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.trait.Area;
import sandybay.apicurious.common.bee.species.trait.Pollination;
import sandybay.apicurious.common.register.ApicuriousDataComponentRegistration;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseHousingBlock extends Block implements EntityBlock {

  public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

  private final float basePollinationModifier;

  public BaseHousingBlock(Properties properties, float basePollinationModifier) {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false));
    this.basePollinationModifier = basePollinationModifier;
  }

  public List<BlockPos> getTerritory(ItemStack queen, BlockPos housingPosition) {
    List<BlockPos> territory = new ArrayList<>();
    if (!queen.has(ApicuriousDataComponentRegistration.BEE_SPECIES)) return territory; // TODO: Replace this with Genome
    BeeSpecies species = queen.get(ApicuriousDataComponentRegistration.BEE_SPECIES);   // TODO: Replace this with Genome
    if (species == null) return territory;
    Holder<Area> areaHolder = species.getProductionData().getArea();
    if (!areaHolder.isBound()) throw new IllegalArgumentException("Area was unbound for BeeSpecies: %s, REPORT THIS!".formatted(species.getReadableName()));
    Area area = areaHolder.value();
    for (int x = housingPosition.getX() - area.getXZOffset(); x < housingPosition.getX() + area.getXZOffset(); x++) {
      for (int y = housingPosition.getY() - area.getYOffset(); y < housingPosition.getY() + area.getYOffset(); y++) {
        for (int z = housingPosition.getZ() - area.getXZOffset(); z < housingPosition.getZ() + area.getXZOffset(); z++) {
          if (x == housingPosition.getX() && y == housingPosition.getY() && z == housingPosition.getZ()) continue;
          territory.add(new BlockPos(x, y ,z));
        }
      }
    }
    return territory;
  }

  public boolean shouldPollinate(RandomSource random, ItemStack queen) {
    if (!queen.has(ApicuriousDataComponentRegistration.BEE_SPECIES)) return false;   // TODO: Replace this with Genome
    BeeSpecies species = queen.get(ApicuriousDataComponentRegistration.BEE_SPECIES); // TODO: Replace this with Genome
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
