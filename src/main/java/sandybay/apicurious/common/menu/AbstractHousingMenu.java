package sandybay.apicurious.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
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

  private final Inventory playerInventory;
  private final ContainerLevelAccess access;
  private final ConfigurableItemStackHandler input;
  private final ConfigurableItemStackHandler frames;
  private final ConfigurableItemStackHandler output;

  protected AbstractHousingMenu(int containerId, Inventory playerInventory) {
    super(menuType, containerId);
    this.playerInventory = playerInventory;
    this.access = ContainerLevelAccess.NULL;
    this.input = null;
    this.frames = null;
    this.output = null;
  }

  protected AbstractHousingMenu(
          int containerId, Inventory playerInventory, ContainerLevelAccess access,
          ConfigurableItemStackHandler input, ConfigurableItemStackHandler frames, ConfigurableItemStackHandler output) {
    super(menuType, containerId);
    this.playerInventory = playerInventory;
    this.access = access;
    this.input = input;
    this.frames = frames;
    this.output = output;
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

}
