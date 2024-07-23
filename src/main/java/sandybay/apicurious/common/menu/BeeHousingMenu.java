package sandybay.apicurious.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;
import sandybay.apicurious.common.register.ApicuriousMenuRegistration;

public class BeeHousingMenu extends AbstractHousingMenu {

  public BeeHousingMenu(int containerId, Inventory playerInventory) {
    super(ApicuriousMenuRegistration.BEE_HOUSING.get(), containerId, playerInventory);
  }

  public BeeHousingMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, ConfigurableItemStackHandler input, ConfigurableItemStackHandler frames, ConfigurableItemStackHandler output) {
    super(ApicuriousMenuRegistration.BEE_HOUSING.get(), containerId, playerInventory, access, input, frames, output);
  }

  @Override
  public boolean stillValid(Player player) {
    return AbstractContainerMenu.stillValid(getAccess(), player, ApicuriousBlockRegistration.BEE_HOUSING.asBlock());
  }
}
