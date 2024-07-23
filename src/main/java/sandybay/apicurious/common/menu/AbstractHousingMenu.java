package sandybay.apicurious.common.menu;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.api.item.IFrameItem;
import sandybay.apicurious.common.item.bee.DroneBeeItem;
import sandybay.apicurious.common.item.bee.PrincessBeeItem;
import sandybay.apicurious.common.item.bee.QueenBeeItem;

public abstract class AbstractHousingMenu extends AbstractContainerMenu {

  // SLOT REFERENCE:
  // 0, 1        = Input
  // 2, 3, 4     = Frames
  // 5,  ..., 11 = Output
  // 12, ..., 38 = Player Inventory
  // 39, ..., 47 = Hotbar

  // TODO: Figure out coordinates
  private static final Pair<Integer, Integer> baseInputCoords = Pair.of(29,39);
  private static final int inputOffset = 26;
  private static final Pair<Integer, Integer> baseFrameCoords = Pair.of(66,23);
  private static final int frameOffset = 29;

  private final ContainerLevelAccess access;
  public AbstractHousingMenu(MenuType<?> type, int containerId, Inventory playerInventory) {
    this(type, containerId, playerInventory, ContainerLevelAccess.NULL,
            new ConfigurableItemStackHandler(2, null),
            new ConfigurableItemStackHandler(3, null),
            new ConfigurableItemStackHandler(7, null)
    );
  }

  public AbstractHousingMenu(MenuType<?> type, int containerId, Inventory playerInventory, ContainerLevelAccess access,
                             ConfigurableItemStackHandler input,
                             ConfigurableItemStackHandler frames,
                             ConfigurableItemStackHandler output
  ) {
    super(type, containerId);
    this.access = access;
    addApiarySlots(input, frames, output);
    addInventorySlots(playerInventory);
    addHotbarSlots(playerInventory);
  }

  private void addApiarySlots(ConfigurableItemStackHandler input, ConfigurableItemStackHandler frames, ConfigurableItemStackHandler output) {
    this.slots.add(new SlotItemHandler(input, 0, baseInputCoords.getFirst(), baseInputCoords.getSecond()));
    this.slots.add(new SlotItemHandler(input, 1, baseInputCoords.getFirst(), baseInputCoords.getSecond() + inputOffset));
    this.slots.add(new SlotItemHandler(frames, 0, baseFrameCoords.getFirst(), baseFrameCoords.getSecond()));
    this.slots.add(new SlotItemHandler(frames, 1, baseFrameCoords.getFirst(), baseFrameCoords.getSecond() + frameOffset));
    this.slots.add(new SlotItemHandler(frames, 2, baseFrameCoords.getFirst(), baseFrameCoords.getSecond() + frameOffset * 2));

    // Because of the wierd offset of these, do the coordination positioning manually.
    this.slots.add(new SlotItemHandler(output, 0, 0, 0));
    this.slots.add(new SlotItemHandler(output, 1, 0, 0));
    this.slots.add(new SlotItemHandler(output, 2, 0, 0));
    this.slots.add(new SlotItemHandler(output, 3, 0, 0));
    this.slots.add(new SlotItemHandler(output, 4, 0, 0));
    this.slots.add(new SlotItemHandler(output, 5, 0, 0));
    this.slots.add(new SlotItemHandler(output, 6, 0, 0));
  }

  private void addInventorySlots(Inventory playerInventory) {
    //8,108
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 9; j++) {
        this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 108 + i * 18));
      }
    }
  }

  private void addHotbarSlots(Inventory playerInventory) {
    //8, 166
    for (int k = 0; k < 9; k++) {
      this.addSlot(new Slot(playerInventory, k + 38, 8 + k * 18, 166));
    }
  }

  @Override
  public ItemStack quickMoveStack(Player player, int index) {
    ItemStack quickMovedStack = ItemStack.EMPTY;
    Slot quickMovedSlot = this.slots.get(index);
    ItemStack primaryBeeItem = this.slots.get(0).getItem();
    // Check that slot exists and has an item
    if (quickMovedSlot != null && quickMovedSlot.hasItem()) {
      // Grab the raw item from the slot & make a copy
      ItemStack rawStack = quickMovedSlot.getItem();
      quickMovedStack = rawStack.copy();

      // If it's the primary Bee slot for Queens and Princess bees.
      if (index == 0) {
        // Fail to quick move if the Item in the slot is a Queen.
        // Since this indicates the housing is active.
        if (quickMovedStack.getItem() instanceof QueenBeeItem) return ItemStack.EMPTY;
        // Else return the result of 'moveToPlayerInventory'
        return moveToPlayerInventory(rawStack);

      // If it's the secondary Bee slot for Drones.
      } else if (index == 1) {
        // Return the result of 'moveToPlayerInventory'.
        return moveToPlayerInventory(rawStack);

      // If it's any of the frame slots.
      } else if (index >= 2 && index <= 4) {
        // Fail to quick move if the housing is active.
        if (isHousingActive(primaryBeeItem)) return ItemStack.EMPTY;
        // Else return the result of 'moveToPlayerInventory'
        return moveToPlayerInventory(rawStack);

      // If it's any of the output slots.
      } else if (index >= 5 && index <= 11) {
        // Return the result of 'moveToPlayerInventory'
        return moveToPlayerInventory(rawStack);

      // If it's any of the Inventory or Hotbar slots.
      } else if (index >= 12 && index <= 47) {
        // If the housing is Inactive & the moved stack is a Princess.
        if (!isHousingActive(primaryBeeItem) && rawStack.getItem() instanceof PrincessBeeItem) {
          // Then try to insert to the primary Bee slot.
          if (!moveItemStackTo(rawStack, 0,0, true)) {
            return ItemStack.EMPTY;
          }

        // If the moved stack is a Drone.
        } else if (rawStack.getItem() instanceof DroneBeeItem) {
          // Then try to insert to the secondary Bee slot.
          if (!moveItemStackTo(rawStack, 1,1, true)) {
            return ItemStack.EMPTY;
          }

        // If the housing is Inactive & the moved stack is a Frame.
        } else if (!isHousingActive(primaryBeeItem) && rawStack.getItem() instanceof IFrameItem) {
          // Then try to insert to the Frame slots.
          if (!moveItemStackTo(rawStack, 2, 4, true)) {
            return ItemStack.EMPTY;
          }
        }
      }
      // If the raw stack has completely been moved out of the slot.
      if (rawStack.isEmpty()) {
        // Set the slot to an empty stack.
        quickMovedSlot.set(ItemStack.EMPTY);
      } else {
        // Otherwise, notify the slot that the stack count has changed.
        quickMovedSlot.setChanged();
      }

      // If the raw stack was not able to be moved to another slot, no longer quick move
      if (rawStack.getCount() == quickMovedStack.getCount())
        return ItemStack.EMPTY;

      // Execute logic on what to do post move with the remaining stack.
      quickMovedSlot.onTake(player, rawStack);
    }
    return quickMovedStack;
  }

  private ItemStack moveToPlayerInventory(ItemStack stack) {
    if (!moveItemStackTo(stack, 12, 47, true)) {
      return ItemStack.EMPTY;
    }
    return stack;
  }

  public boolean isHousingActive(ItemStack stack) {
    return stack.getItem() instanceof QueenBeeItem;
  }

  public ContainerLevelAccess getAccess() {
    return access;
  }
}
