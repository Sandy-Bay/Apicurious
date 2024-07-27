package sandybay.apicurious.api.housing.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.bee.IBeeItem;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.api.item.IFrameItem;

//This does not need to be in the api
public class SimpleBlockHousingBE extends BaseHousingBE {

  // Server-sided Data
  private final ConfigurableItemStackHandler inventory;

  public SimpleBlockHousingBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
    this.inventory = new ConfigurableItemStackHandler(12)
            .setInputFilter((stack, slot) -> {
              if (slot == 0 && (stack.getItem() instanceof IBeeItem beeItem && beeItem.getBeeType() != EnumBeeType.DRONE))
                return true;
              if (slot == 1 && stack.getItem() instanceof IBeeItem beeItem && beeItem.getBeeType() == EnumBeeType.DRONE)
                return true;
              return (slot >= 2 && slot <= 4) && stack.getItem() instanceof IFrameItem;
            })
            .setOutputFilter((stack, slot) -> !(slot == 0 && stack.getItem() instanceof IBeeItem beeItem && beeItem.getBeeType() == EnumBeeType.QUEEN))
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
    } else {
      if (alwaysSave || inventory.hasChanged()) apiaryData.put("inventory", inventory.serializeNBT(registries));
    }
    tag.put("apiary_data", apiaryData);
  }

  @Override
  public void readData(CompoundTag tag, HolderLookup.Provider registries, boolean clientOnly, boolean alwaysSave) {
    CompoundTag apiaryData = tag.getCompound("apiary_data");
    inventory.deserializeNBT(registries, apiaryData.getCompound("inventory"));

    if (clientOnly) {
    } else {
    }
  }

  public ConfigurableItemStackHandler getInventory() {
    return inventory;
  }
}
