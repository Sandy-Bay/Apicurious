package sandybay.apicurious.common.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStacksResourceHandler;
import sandybay.apicurious.api.util.MenuHelper;
import sandybay.apicurious.common.block.centrifuge.blockentity.CentrifugeBE;
import sandybay.apicurious.common.registrar.BlockRegistrar;
import sandybay.apicurious.common.registrar.MenuRegistrar;

import java.util.Objects;

public class CentrifugeMenu extends AbstractContainerMenu
{
  // SLOT REFERENCE:
  // 0                         = Input
  // 1, 2, 3,                  = Output
  // 4, 5, 6,                  = Output
  // 7, 8, 9                   = Output

  private final ContainerLevelAccess access;
  private final ContainerData containerData;
  private final CentrifugeBE centrifuge;
  private final Player player;

  public CentrifugeMenu(int containerId, Inventory playerInventory, FriendlyByteBuf packetBuffer)
  {
    super(MenuRegistrar.CENTRIFUGE.get(), containerId);
    this.access = ContainerLevelAccess.NULL;
    this.containerData = new SimpleContainerData(2);
    this.centrifuge = (CentrifugeBE) Objects.requireNonNull(Minecraft.getInstance().level.getBlockEntity(packetBuffer.readBlockPos()));
    this.player = playerInventory.player;
    addCentrifugeSlots(new ConfigurableItemStacksResourceHandler(10).setInputFilter(((stack, slot) -> slot == 0)));
    addInventorySlots(playerInventory);
    addHotbarSlots(playerInventory);
    addDataSlots(containerData);
  }

  public CentrifugeMenu(int pContainerId, Inventory playerInventory, ContainerLevelAccess access, CentrifugeBE centrifuge)
  {
    super(MenuRegistrar.CENTRIFUGE.get(), pContainerId);
    this.access = access;
    this.centrifuge = centrifuge;
    this.containerData = centrifuge.getContainerData();
    this.player = playerInventory.player;
    addCentrifugeSlots(centrifuge.getInventory());
    addInventorySlots(playerInventory);
    addHotbarSlots(playerInventory);
    addDataSlots(containerData);
  }

  private void addCentrifugeSlots(ConfigurableItemStacksResourceHandler inventory)
  {
    this.addSlot(new ResourceHandlerSlot(inventory, inventory::set, 0, 44, 34));
    int slot = 1;
    int x = 97;
    int y = 18;
    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 3; j++)
      {
        this.addSlot(new ResourceHandlerSlot(inventory, inventory::set, slot, x + j * 18, y + i * 18));
        slot++;
      }
    }
  }

  private void addInventorySlots(Inventory playerInventory)
  {
    //8,108
    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 9; j++)
      {
        if (j + i * 9 + 9 == 36) {return;}
        this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
      }
    }
  }

  private void addHotbarSlots(Inventory playerInventory)
  {
    //8, 166
    for (int k = 0; k < 9; k++)
    {
      this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
    }
  }

  // Todo: Figure out how to do shift-clicking from inventory into centrifuge
  @Override
  public ItemStack quickMoveStack(Player pPlayer, int slotIndex)
  {
    ItemStack originalStack = ItemStack.EMPTY;
    Slot slot = slots.get(slotIndex);
    int numSlots = slots.size();
    if (slot.hasItem())
    {
      ItemStack stackInSlot = slot.getItem();
      originalStack = stackInSlot.copy();
      if (slotIndex < 10)
      {
        if (!shiftItemStack(stackInSlot, 10, numSlots))
        {
          return ItemStack.EMPTY;
        }
      }
      else
      {
        if (!shiftItemStack(stackInSlot, 0, 0))
        {
          return ItemStack.EMPTY;
        }
      }
      slot.onQuickCraft(stackInSlot, originalStack);
      if (stackInSlot.getCount() <= 0)
      {
        slot.set(ItemStack.EMPTY);
      }
      else
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
        if (!stackInSlot.isEmpty() && MenuHelper.canStacksMerge(stackInSlot, stackToShift))
        {
          int resultingStackSize = stackInSlot.getCount() + stackToShift.getCount();
          int max = Math.min(stackToShift.getMaxStackSize(), slot.getMaxStackSize());
          if (resultingStackSize <= max)
          {
            stackToShift.setCount(0);
            stackInSlot.setCount(resultingStackSize);
            slot.setChanged();
            changed = true;
          }
          else if (stackInSlot.getCount() < max)
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
      if (!slot.mayPlace(stackToShift)) {continue;}
      if (shiftItemStack(stackToShift, machineIndex, machineIndex + 1)) {return true;}
    }
    return false;
  }

  @Override
  public boolean stillValid(Player pPlayer)
  {
    return AbstractContainerMenu.stillValid(access, pPlayer, BlockRegistrar.CENTRIFUGE.asBlock());
  }

  public int getWork()
  {
    return this.containerData.get(0);
  }

  public int getMaxWork()
  {
    return this.containerData.get(1);
  }
}
