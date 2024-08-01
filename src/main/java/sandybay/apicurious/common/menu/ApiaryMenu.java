package sandybay.apicurious.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;
import sandybay.apicurious.common.register.ApicuriousMenuRegistration;

public class ApiaryMenu extends AbstractHousingMenu {

  private final ContainerData containerData;

  public ApiaryMenu(int containerId, Inventory playerInventory) {
    super(ApicuriousMenuRegistration.APIARY.get(), containerId, playerInventory);
    containerData = new SimpleContainerData(3);
    addDataSlots(containerData);
  }

  public ApiaryMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, ContainerData containerData, ConfigurableItemStackHandler inventory) {
    super(ApicuriousMenuRegistration.APIARY.get(), containerId, playerInventory, access, inventory);

    this.containerData = containerData;
    addDataSlots(containerData);
  }

  public ApiaryMenu(int containedId, Inventory inventory, Player player) {
    this(containedId, inventory);
  }

  public boolean isActive()
  {
    return containerData.get(0) == 1;
  }

  public int getProgress()
  {
    return containerData.get(1);
  }

  public int getMaxProgress()
  {
    return containerData.get(2);
  }

  @Override
  public boolean stillValid(@NotNull Player player) {
    return AbstractContainerMenu.stillValid(getAccess(), player, ApicuriousBlockRegistration.APIARY.asBlock());
  }
}
