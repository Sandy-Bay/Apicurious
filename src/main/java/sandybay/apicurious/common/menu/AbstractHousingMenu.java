package sandybay.apicurious.common.menu;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.api.EnumApiaryError;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.bee.IBeeItem;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;

import java.util.List;

public abstract class AbstractHousingMenu extends AbstractContainerMenu
{

  // SLOT REFERENCE:
  // 0, 1        = Input
  // 2, 3, 4     = Frames
  // 5,  ..., 11 = Output
  // 12, ..., 38 = Player Inventory
  // 39, ..., 47 = Hotbar

  private static final Pair<Integer, Integer> baseInputCoords = Pair.of(29, 39);
  private static final int inputOffset = 26;
  private static final Pair<Integer, Integer> baseFrameCoords = Pair.of(66, 23);
  private static final int frameOffset = 29;

  private final ContainerLevelAccess access;

  public AbstractHousingMenu(MenuType<?> type, int containerId, Inventory playerInventory)
  {
    this(type, containerId, playerInventory, ContainerLevelAccess.NULL, new ConfigurableItemStackHandler(12));
  }

  public AbstractHousingMenu(MenuType<?> type, int containerId, Inventory playerInventory, ContainerLevelAccess access, ConfigurableItemStackHandler inventory)
  {
    super(type, containerId);
    this.access = access;
    addApiarySlots(inventory);
    addInventorySlots(playerInventory);
    addHotbarSlots(playerInventory);
  }

  /**
   * Determines if two @link {@link ItemStack} match and can be merged into a single slot
   */
  public static boolean canStacksMerge(ItemStack stack1, ItemStack stack2)
  {
    if (stack1.isEmpty() || stack2.isEmpty()) return false;
    return ItemStack.isSameItemSameComponents(stack1, stack2);
  }

  private void addApiarySlots(ConfigurableItemStackHandler inventory)
  {
    this.addSlot(new SlotItemHandler(inventory, 0, baseInputCoords.getFirst(), baseInputCoords.getSecond()));
    this.addSlot(new SlotItemHandler(inventory, 1, baseInputCoords.getFirst(), baseInputCoords.getSecond() + inputOffset));
    this.addSlot(new SlotItemHandler(inventory, 2, baseFrameCoords.getFirst(), baseFrameCoords.getSecond()));
    this.addSlot(new SlotItemHandler(inventory, 3, baseFrameCoords.getFirst(), baseFrameCoords.getSecond() + frameOffset));
    this.addSlot(new SlotItemHandler(inventory, 4, baseFrameCoords.getFirst(), baseFrameCoords.getSecond() + frameOffset * 2));

    // Because of the wierd offset of these, do the coordination positioning manually.
    this.addSlot(new SlotItemHandler(inventory, 5, 116, 52));
    this.addSlot(new SlotItemHandler(inventory, 6, 94, 39));
    this.addSlot(new SlotItemHandler(inventory, 7, 116, 25));
    this.addSlot(new SlotItemHandler(inventory, 8, 136, 39));
    this.addSlot(new SlotItemHandler(inventory, 9, 136, 65));
    this.addSlot(new SlotItemHandler(inventory, 10, 116, 78));
    this.addSlot(new SlotItemHandler(inventory, 11, 94, 65));
  }

  private void addInventorySlots(Inventory playerInventory)
  {
    //8,108
    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 9; j++)
      {
        if (j + i * 9 + 9 == 36) return;
        this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 108 + i * 18));
      }
    }
  }

  private void addHotbarSlots(Inventory playerInventory)
  {
    //8, 166
    for (int k = 0; k < 9; k++)
    {
      this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 166));
    }
  }

  /**
   * Logic to figure out where/if a shift-clicked slot can move its @link {@link ItemStack} to another @link {@link Slot}
   */
  @Override
  public @NotNull ItemStack quickMoveStack(@NotNull Player player, int slotIndex)
  {
    ItemStack originalStack = ItemStack.EMPTY;
    Slot slot = slots.get(slotIndex);
    int numSlots = slots.size();
    if (slot != null && slot.hasItem())
    {
      ItemStack stackInSlot = slot.getItem();
      originalStack = stackInSlot.copy();
      if (slotIndex >= numSlots - 9 * 4 && tryShiftItem(stackInSlot, numSlots))
      {
        // NOOP
      } else if (slotIndex >= numSlots - 9 * 4 && slotIndex < numSlots - 9)
      {
        if (!shiftItemStack(stackInSlot, numSlots - 9, numSlots))
        {
          return ItemStack.EMPTY;
        }
      } else if (slotIndex >= numSlots - 9 && slotIndex < numSlots)
      {
        if (!shiftItemStack(stackInSlot, numSlots - 9 * 4, numSlots - 9))
        {
          return ItemStack.EMPTY;
        }
      } else if (!shiftItemStack(stackInSlot, numSlots - 9 * 4, numSlots))
      {
        return ItemStack.EMPTY;
      }
      slot.onQuickCraft(stackInSlot, originalStack);
      if (stackInSlot.getCount() <= 0)
      {
        slot.set(ItemStack.EMPTY);
      } else
      {
        slot.setChanged();
      }
      if (stackInSlot.getCount() == originalStack.getCount())
      {
        return ItemStack.EMPTY;
      }
      slot.onTake(player, stackInSlot);
    }
    return originalStack;
  }

  public boolean shiftItemStack(ItemStack stackToShift, int start, int end)
  {
    boolean changed = false;
    if (stackToShift.isStackable())
    {
      for (int slotIndex = start; stackToShift.getCount() > 0 && slotIndex < end; slotIndex++)
      {
        Slot slot = slots.get(slotIndex);
        ItemStack stackInSlot = slot.getItem();
        if (!stackInSlot.isEmpty() && canStacksMerge(stackInSlot, stackToShift))
        {
          int resultingStackSize = stackInSlot.getCount() + stackToShift.getCount();
          int max = Math.min(stackToShift.getMaxStackSize(), slot.getMaxStackSize());
          if (resultingStackSize <= max)
          {
            stackToShift.setCount(0);
            stackInSlot.setCount(resultingStackSize);
            slot.setChanged();
            changed = true;
          } else if (stackInSlot.getCount() < max)
          {
            stackToShift.setCount(stackToShift.getCount() - (max - stackInSlot.getCount()));
            stackInSlot.setCount(max);
            slot.setChanged();
            changed = true;
          }
        }
      }
    }
    if (stackToShift.getCount() > 0)
    {
      for (int slotIndex = start; stackToShift.getCount() > 0 && slotIndex < end; slotIndex++)
      {
        Slot slot = slots.get(slotIndex);
        ItemStack stackInSlot = slot.getItem();
        if (stackInSlot.isEmpty())
        {
          int max = Math.min(stackToShift.getMaxStackSize(), slot.getMaxStackSize());
          stackInSlot = stackToShift.copy();
          stackInSlot.setCount(Math.min(stackToShift.getCount(), max));
          stackToShift.setCount(stackToShift.getCount() - stackInSlot.getCount());
          slot.set(stackInSlot);
          slot.setChanged();
          changed = true;
        }
      }
    }
    return changed;
  }

  public boolean tryShiftItem(ItemStack stackToShift, int numSlots)
  {
    for (int machineIndex = 0; machineIndex < numSlots - 9 * 4; machineIndex++)
    {
      Slot slot = slots.get(machineIndex);
      if (!slot.mayPlace(stackToShift)) continue;
      if (shiftItemStack(stackToShift, machineIndex, machineIndex + 1)) return true;
    }
    return false;
  }

  public boolean isHousingActive(ItemStack stack)
  {
    return stack.getItem() instanceof IBeeItem beeItem && beeItem.getBeeType() == EnumBeeType.QUEEN;
  }

  public ContainerLevelAccess getAccess()
  {
    return access;
  }

  public void receiveGuiData(List<EnumApiaryError> errors)
  {

  }
}
