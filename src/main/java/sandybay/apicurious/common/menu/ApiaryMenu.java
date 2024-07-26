package sandybay.apicurious.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;
import sandybay.apicurious.common.register.ApicuriousMenuRegistration;

public class ApiaryMenu extends AbstractHousingMenu {

  public ApiaryMenu(int containerId, Inventory playerInventory) {
    super(ApicuriousMenuRegistration.APIARY.get(), containerId, playerInventory);
  }

  public ApiaryMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, ConfigurableItemStackHandler inventory) {
    super(ApicuriousMenuRegistration.APIARY.get(), containerId, playerInventory, access, inventory);
  }

  public ApiaryMenu(int containedId, Inventory inventory, Player player) {
    this(containedId, inventory);
  }


  @Override
  public boolean stillValid(@NotNull Player player) {
    return AbstractContainerMenu.stillValid(getAccess(), player, ApicuriousBlockRegistration.APIARY.asBlock());
  }
}
