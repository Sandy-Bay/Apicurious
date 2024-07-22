package sandybay.apicurious.api.housing.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.api.item.IFrameItem;
import sandybay.apicurious.common.item.bee.DroneBeeItem;
import sandybay.apicurious.common.item.bee.PrincessBeeItem;
import sandybay.apicurious.common.item.bee.QueenBeeItem;

public class SimpleBlockHousingBE extends BaseHousingBE {

  // Server-sided Data
  private final ConfigurableItemStackHandler input;
  private final ConfigurableItemStackHandler frames;
  private final ConfigurableItemStackHandler output;

  public SimpleBlockHousingBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
    this.input = new ConfigurableItemStackHandler(2, this)
            .setInputFilter((stack, slot) -> {
              if (slot == 0 && (stack.getItem() instanceof PrincessBeeItem ||
                      stack.getItem() instanceof QueenBeeItem)) return true;
              return slot == 1 && stack.getItem() instanceof DroneBeeItem;
            })
            .setOutputFilter((stack, slot) -> !(slot == 1 && stack.getItem() instanceof QueenBeeItem))
            .setSlotLimit(0, 1);
    this.frames = new ConfigurableItemStackHandler(3, this)
            .setInputFilter((stack, slot) -> stack.getItem() instanceof IFrameItem)
            .setSlotLimit(1);
    this.output = new ConfigurableItemStackHandler(7, this)
            .setInputFilter((stack, slot) -> false);
  }

  @Override
  public void saveData(CompoundTag tag, HolderLookup.Provider registries, boolean clientOnly, boolean alwaysSave) {
    CompoundTag apiaryData = new CompoundTag();
    if (clientOnly) {
      // TODO: Add Client-data here
    } else {
      // TODO: Add Client-data here
      if (alwaysSave || input.hasChanged())  apiaryData.put("input",  input.serializeNBT(registries));
      if (alwaysSave || frames.hasChanged()) apiaryData.put("frames", frames.serializeNBT(registries));
      if (alwaysSave || output.hasChanged()) apiaryData.put("output", frames.serializeNBT(registries));
    }
    tag.put("apiary_data", apiaryData);
  }

  @Override
  public void readData(CompoundTag tag, HolderLookup.Provider registries, boolean clientOnly, boolean alwaysSave) {
    CompoundTag apiaryData = tag.getCompound("apiary_data");
    if (clientOnly) {
      // TODO: Add Client-data here
    } else {
      // TODO: Add Client-data here
      if (alwaysSave || apiaryData.contains("input"))  input.deserializeNBT(registries, apiaryData.getCompound("input"));
      if (alwaysSave || apiaryData.contains("frames")) frames.deserializeNBT(registries, apiaryData.getCompound("frames"));
      if (alwaysSave || apiaryData.contains("output")) output.deserializeNBT(registries, apiaryData.getCompound("output"));
    }
  }
}
