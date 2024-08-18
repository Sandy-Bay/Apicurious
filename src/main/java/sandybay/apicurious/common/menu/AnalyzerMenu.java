package sandybay.apicurious.common.menu;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.registrar.MenuRegistrar;

// TODO: Figure out how to sync the inventory data.
public class AnalyzerMenu extends AbstractContainerMenu
{
  private final ContainerLevelAccess access;
  private final ConfigurableItemStackHandler inventory;

  public AnalyzerMenu(int containerId, Inventory playerInventory)
  {
    this(containerId, playerInventory, ContainerLevelAccess.NULL, new ConfigurableItemStackHandler(2));
  }

  public AnalyzerMenu(int containerId, Inventory playerInventory, FriendlyByteBuf packetBuffer)
  {
    this(containerId, playerInventory, ContainerLevelAccess.NULL, new ConfigurableItemStackHandler(2));
  }

  public AnalyzerMenu(int containerId, Inventory playerInventory,
                      ContainerLevelAccess access,
                      ConfigurableItemStackHandler inventory)
  {
    super(MenuRegistrar.ANALYZER.get(), containerId);
    this.access = access;
    this.inventory = inventory;
    addAnalyzerSlots(inventory);
    addInventorySlots(playerInventory);
    addHotbarSlots(playerInventory);
  }

  private void addAnalyzerSlots(ConfigurableItemStackHandler inventory)
  {
    this.addSlot(new SlotItemHandler(inventory, 0, 90, 111));
    this.addSlot(new SlotItemHandler(inventory, 1, 126, 111));
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
    return true;
  }

  public boolean hasIdentifiedBee()
  {
    ItemStack stack = this.inventory.getStackInSlot(0);
    return stack.has(DataComponentRegistrar.IDENTIFIED) && stack.get(DataComponentRegistrar.IDENTIFIED);
  }

  public Genome getGenome()
  {
    return this.inventory.getStackInSlot(0).get(DataComponentRegistrar.GENOME);
  }
}
