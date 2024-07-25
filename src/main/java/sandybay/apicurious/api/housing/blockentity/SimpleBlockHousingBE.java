package sandybay.apicurious.api.housing.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.api.item.IFrameItem;
import sandybay.apicurious.common.block.housing.ApiaryBlock;
import sandybay.apicurious.common.item.bee.DroneBeeItem;
import sandybay.apicurious.common.item.bee.PrincessBeeItem;
import sandybay.apicurious.common.item.bee.QueenBeeItem;

public class SimpleBlockHousingBE extends BaseHousingBE {

  // Server-sided Data
  private final ConfigurableItemStackHandler inventory;
  public SimpleBlockHousingBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
    this.inventory = new ConfigurableItemStackHandler(12)
            .setInputFilter((stack, slot) -> {
              if (slot == 0 && (stack.getItem() instanceof PrincessBeeItem || stack.getItem() instanceof QueenBeeItem)) return true;
              if (slot == 1 && stack.getItem() instanceof DroneBeeItem) return true;
              return (slot >= 2 && slot <= 4) && stack.getItem() instanceof IFrameItem;
            })
            .setOutputFilter((stack, slot) -> !(slot == 0 && stack.getItem() instanceof QueenBeeItem))
            .setSlotLimit(0, 1)
            .setSlotLimit(2, 1)
            .setSlotLimit(3, 1)
            .setSlotLimit(4, 1)
            .setOnSlotChanged((stack, slot) -> this.setChanged());
  }

  @Override
  public void saveData(CompoundTag tag, HolderLookup.Provider registries, boolean clientOnly, boolean alwaysSave) {
    CompoundTag apiaryData = new CompoundTag();
    if (clientOnly) {
      // TODO: Add Client-data here
    } else {
      // TODO: Add Client-data here
      if (alwaysSave || inventory.hasChanged())  apiaryData.put("inventory",  inventory.serializeNBT(registries));
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
      if (alwaysSave || inventory.hasChanged())  apiaryData.put("inventory",  inventory.serializeNBT(registries));
    }
  }

  public ConfigurableItemStackHandler getInventory() {
    return inventory;
  }
}
