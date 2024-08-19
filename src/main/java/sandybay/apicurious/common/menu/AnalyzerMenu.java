package sandybay.apicurious.common.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerCopySlot;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.apache.commons.lang3.function.TriFunction;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.item.BeeAnalyzerItem;
import sandybay.apicurious.common.item.BeeItem;
import sandybay.apicurious.common.registrar.MenuRegistrar;

// TODO: Figure out how to sync the inventory data.
public class AnalyzerMenu extends AbstractContainerMenu
{
  private static final IdentificationListener listener = new IdentificationListener(
          (menu, slot, stack) -> {
            if (!(slot == 0 || slot == 1)) return false;
            ItemStack s1 = menu.getSlot(0).getItem();
            ItemStack s2 = menu.getSlot(1).getItem();
            if (!(s1.getItem() instanceof BeeItem && s2.is(ApicuriousTags.ItemTags.DROP_HONEY)) && !s1.has(DataComponentRegistrar.IDENTIFIED)) return false;
            if (s1.get(DataComponentRegistrar.IDENTIFIED)) return false;
            s2.shrink(1);
            s1.set(DataComponentRegistrar.IDENTIFIED, true);
            menu.getSlot(0).set(s1);
            menu.getSlot(1).set(s2);
            return true;
          },
          (menu, slot, data) -> true
  );

  private final ContainerLevelAccess access;
  private final Inventory inventory;
  private final int analyzerSlot;
  private final IItemHandler analyzerInventory;

  public AnalyzerMenu(int containerId, Inventory playerInventory)
  {
    this(containerId, playerInventory, ContainerLevelAccess.NULL, 0);
  }

  public AnalyzerMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf packetBuffer)
  {
    this(containerId, playerInventory, ContainerLevelAccess.NULL, packetBuffer.readVarInt());
  }

  public AnalyzerMenu(int containerId, Inventory playerInventory,
                      ContainerLevelAccess access,
                      int analyzerSlot)
  {
    super(MenuRegistrar.ANALYZER.get(), containerId);
    this.access = access;
    this.inventory = playerInventory;
    this.analyzerSlot = analyzerSlot;
    ItemStack analyzer = playerInventory.getItem(analyzerSlot);
    if (!(analyzer.getItem() instanceof BeeAnalyzerItem)) throw new IllegalArgumentException("Item was not Analyzer Item!");
    this.analyzerInventory = analyzer.getCapability(Capabilities.ItemHandler.ITEM);
    addAnalyzerSlots(analyzerInventory);
    addInventorySlots(playerInventory);
    addHotbarSlots(playerInventory);
    this.addSlotListener(listener);
  }

  private void addAnalyzerSlots(IItemHandler inventory)
  {
    this.addSlot(new ItemHandlerCopySlot(inventory, 0, 90, 111));
    this.addSlot(new ItemHandlerCopySlot(inventory, 1, 126, 111));
  }

  private void addInventorySlots(Inventory playerInventory)
  {
    //8,108
    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 9; j++)
      {
        if (j + i * 9 + 9 == 36) return;
        this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 36 + j * 18, 138 + i * 18));
      }
    }
  }

  private void addHotbarSlots(Inventory playerInventory)
  {
    //8, 166
    for (int k = 0; k < 9; k++)
    {
      this.addSlot(new Slot(playerInventory, k, 36 + k * 18, 196));
    }
  }

  @Override
  public ItemStack quickMoveStack(Player pPlayer, int pIndex)
  {
    return null;
  }

  @Override
  public boolean stillValid(Player pPlayer)
  {
    return inventory.getItem(analyzerSlot).getItem() instanceof BeeAnalyzerItem;
  }

  public boolean hasIdentifiedBee()
  {
    ItemStack stack = this.analyzerInventory.getStackInSlot(0);
    return stack.has(DataComponentRegistrar.IDENTIFIED) && stack.get(DataComponentRegistrar.IDENTIFIED);
  }

  public Genome getGenome()
  {
    return this.analyzerInventory.getStackInSlot(0).get(DataComponentRegistrar.GENOME);
  }

  public static class IdentificationListener implements ContainerListener
  {
    public TriFunction<AbstractContainerMenu, Integer, ItemStack, Boolean> slotChanged;
    public TriFunction<AbstractContainerMenu, Integer, Integer, Boolean> dataChanged;

    public IdentificationListener(TriFunction<AbstractContainerMenu, Integer, ItemStack, Boolean> slotChanged,
                                  TriFunction<AbstractContainerMenu, Integer, Integer, Boolean> dataChanged) {
      this.slotChanged = slotChanged;
      this.dataChanged = dataChanged;
    }

    @Override
    public void slotChanged(AbstractContainerMenu pContainerToSend, int pDataSlotIndex, ItemStack pStack)
    {
      this.slotChanged.apply(pContainerToSend, pDataSlotIndex, pStack);
    }

    @Override
    public void dataChanged(AbstractContainerMenu pContainerMenu, int pDataSlotIndex, int pValue)
    {
      this.dataChanged.apply(pContainerMenu, pDataSlotIndex, pValue);
    }
  }
}
