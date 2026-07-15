package sandybay.apicurious.common.menu;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import org.apache.commons.lang3.function.TriFunction;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.item.BeeAnalyzerItem;
import sandybay.apicurious.common.item.BeeItem;
import sandybay.apicurious.common.registrar.MenuRegistrar;

public class AnalyzerMenu extends AbstractContainerMenu
{
  // Slot layout constants, referenced by index throughout this class and in quickMoveStack.
  private static final int BEE_SLOT = 0;
  private static final int HONEY_SLOT = 1;
  private static final int ANALYZER_SLOT_COUNT = 2; // slots 0-1

  private static final int INVENTORY_START = ANALYZER_SLOT_COUNT;      // 2
  private static final int INVENTORY_END = INVENTORY_START + 27;       // 29 (exclusive)
  private static final int HOTBAR_START = INVENTORY_END;               // 29
  private static final int HOTBAR_END = HOTBAR_START + 9;              // 38 (exclusive)

  private static final int PLAYER_HOTBAR_SIZE = 9;

  /**
   * Stateless shared listener: it captures no per-instance state (all context is passed in via
   * the menu argument on each callback), so a single static instance is safe to reuse across
   * every open AnalyzerMenu.
   */
  private static final IdentificationListener listener = new IdentificationListener((menu, slot, stack) ->
  {
    if (!(slot == BEE_SLOT || slot == HONEY_SLOT))
    {
      return false;
    }
    ItemStack s1 = menu.getSlot(BEE_SLOT).getItem();
    ItemStack s2 = menu.getSlot(HONEY_SLOT).getItem();

    // Only proceed when slot 0 holds a Bee and slot 1 holds an actual Honey Drop.
    if (!(s1.getItem() instanceof BeeItem) || s2.isEmpty() || !s2.is(ApicuriousTags.ItemTags.DROP_HONEY))
    {
      return false;
    }
    if (Boolean.TRUE.equals(s1.get(DataComponentRegistrar.IDENTIFIED)))
    {
      return false;
    }

    s2.shrink(1);
    s1.set(DataComponentRegistrar.IDENTIFIED, true);
    menu.getSlot(BEE_SLOT).set(s1);
    menu.getSlot(HONEY_SLOT).set(s2);
    return true;
  }, (menu, slot, data) -> true);

  private final ContainerLevelAccess access;
  private final Inventory inventory;
  private final int analyzerSlot;          // index into the raw player Inventory (0-35)
  private final int analyzerContainerSlot;  // corresponding index into this menu's slot list
  private final ItemStacksResourceHandler analyzerInventory;
  private final ItemStack analyzerStackRef; // the exact analyzer ItemStack this menu was opened for

  public AnalyzerMenu(int containerId, Inventory playerInventory)
  {
    this(containerId, playerInventory, ContainerLevelAccess.NULL, playerInventory.getSelectedSlot());
  }

  public AnalyzerMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf packetBuffer)
  {
    this(containerId, playerInventory, ContainerLevelAccess.NULL, packetBuffer.readVarInt());
  }

  public AnalyzerMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, int analyzerSlot)
  {
    this(containerId, playerInventory, access, analyzerSlot, validate(playerInventory, analyzerSlot));
  }

  private AnalyzerMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, int analyzerSlot,
                       ItemStacksResourceHandler capability)
  {
    super(MenuRegistrar.ANALYZER.get(), containerId);
    this.access = access;
    this.inventory = playerInventory;
    this.analyzerSlot = analyzerSlot;
    this.analyzerContainerSlot = toContainerSlotIndex(analyzerSlot);
    this.analyzerInventory = capability;
    this.analyzerStackRef = playerInventory.getItem(analyzerSlot);

    addAnalyzerSlots(analyzerInventory); // 0,1
    addInventorySlots(playerInventory);  // 2-28
    addHotbarSlots(playerInventory);     // 29-37
    this.addSlotListener(listener);
  }

  private static ItemStacksResourceHandler validate(Inventory playerInventory, int analyzerSlot)
  {
    ItemStack analyzer = playerInventory.getItem(analyzerSlot);
    if (!(analyzer.getItem() instanceof BeeAnalyzerItem))
    {
      throw new IllegalArgumentException("Item was not Analyzer Item!");
    }

    ItemStacksResourceHandler capability = (ItemStacksResourceHandler) analyzer.getCapability(Capabilities.Item.ITEM, null);
    if (capability == null)
    {
      throw new IllegalStateException("Analyzer Item had no ItemStacksResourceHandler capability!");
    }
    return capability;
  }

  /**
   * Converts an index into the raw player {@link Inventory} (0-8 hotbar, 9-35 main inventory)
   * into the corresponding index into this menu's slot list, matching the layout produced by
   * {@link #addInventorySlots} and {@link #addHotbarSlots}.
   */
  private static int toContainerSlotIndex(int playerInventoryIndex)
  {
    if (playerInventoryIndex < PLAYER_HOTBAR_SIZE)
    {
      // Player hotbar slot -> container hotbar slot
      return HOTBAR_START + playerInventoryIndex;
    }
    // Player main-inventory slot -> container main-inventory slot
    return INVENTORY_START + (playerInventoryIndex - PLAYER_HOTBAR_SIZE);
  }

  private static void returnOrDrop(Player player, ItemStack stack)
  {
    if (stack.isEmpty())
    {
      return;
    }

    boolean fullyAdded = player.getInventory().add(stack);
    if (!fullyAdded || !stack.isEmpty())
    {
      player.drop(stack, false);
    }
  }

  private void addAnalyzerSlots(ItemStacksResourceHandler inventory)
  {
    this.addSlot(new ResourceHandlerSlot(inventory, inventory::set, BEE_SLOT, 90, 111)
    {
    });
    this.addSlot(new ResourceHandlerSlot(inventory, inventory::set, HONEY_SLOT, 126, 111));
  }

  private void addInventorySlots(Inventory playerInventory)
  {
    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 9; j++)
      {
        this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 36 + j * 18, 138 + i * 18));
      }
    }
  }

  private void addHotbarSlots(Inventory playerInventory)
  {
    for (int k = 0; k < 9; k++)
    {
      this.addSlot(new Slot(playerInventory, k, 36 + k * 18, 196));
    }
  }

  @Override
  public boolean stillValid(Player pPlayer)
  {
    ItemStack current = inventory.getItem(analyzerSlot);
    return current.getItem() instanceof BeeAnalyzerItem && current == analyzerStackRef;
  }

  public boolean hasIdentifiedBee()
  {
    ItemResource stack = this.analyzerInventory.getResource(BEE_SLOT);
    return stack.has(DataComponentRegistrar.IDENTIFIED) && Boolean.TRUE.equals(stack.get(DataComponentRegistrar.IDENTIFIED));
  }

  public Genome getGenome()
  {
    return this.analyzerInventory.getResource(BEE_SLOT).get(DataComponentRegistrar.GENOME);
  }

  @Override
  public ItemStack quickMoveStack(Player player, int quickMovedSlotIndex)
  {
    Slot slot = this.slots.get(quickMovedSlotIndex);
    if (!slot.hasItem())
    {
      return ItemStack.EMPTY;
    }

    // Never let the analyzer item itself be shift-clicked out of the player's inventory.
    if (quickMovedSlotIndex == this.analyzerContainerSlot)
    {
      return ItemStack.EMPTY;
    }

    ItemStack rawStack = slot.getItem();
    ItemStack quickMovedStack = rawStack.copy();
    boolean moved;

    if (quickMovedSlotIndex >= INVENTORY_START)
    {
      // Moved from the player's inventory/hotbar into the analyzer.
      if (rawStack.is(ApicuriousTags.ItemTags.DROP_HONEY))
      {
        moved = this.moveItemStackTo(rawStack, HONEY_SLOT, HONEY_SLOT + 1, false);
      }
      else if (rawStack.getItem() instanceof BeeItem)
      {
        moved = this.moveItemStackTo(rawStack, BEE_SLOT, BEE_SLOT + 1, false);
      }
      else
      {
        // Not an item this menu accepts into the analyzer slots - nothing to quick-move.
        return ItemStack.EMPTY;
      }
    }
    else
    {
      // Moved from the Bee or Honey slot back into the player's inventory, hotbar first.
      moved = this.moveItemStackTo(rawStack, INVENTORY_START, HOTBAR_END, true);
    }

    if (!moved)
    {
      return ItemStack.EMPTY;
    }

    slot.onTake(player, rawStack);
    return quickMovedStack;
  }

  @Override
  public void removed(Player player)
  {
    returnOrDrop(player, this.analyzerInventory.getResource(BEE_SLOT).toStack());
    returnOrDrop(player, this.analyzerInventory.getResource(HONEY_SLOT).toStack());
    this.analyzerInventory.set(BEE_SLOT, ItemResource.of(ItemStack.EMPTY), 0);
    this.analyzerInventory.set(HONEY_SLOT, ItemResource.of(ItemStack.EMPTY), 0);
    super.removed(player);
  }

  public static class IdentificationListener implements ContainerListener
  {
    public TriFunction<AbstractContainerMenu, Integer, ItemStack, Boolean> slotChanged;
    public TriFunction<AbstractContainerMenu, Integer, Integer, Boolean> dataChanged;

    public IdentificationListener(TriFunction<AbstractContainerMenu, Integer, ItemStack, Boolean> slotChanged,
                                  TriFunction<AbstractContainerMenu, Integer, Integer, Boolean> dataChanged)
    {
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