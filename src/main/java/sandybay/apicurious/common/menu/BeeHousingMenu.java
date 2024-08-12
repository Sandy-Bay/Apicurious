package sandybay.apicurious.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.common.register.BlockRegistration;
import sandybay.apicurious.common.register.MenuRegistration;

public class BeeHousingMenu extends AbstractHousingMenu
{

  public BeeHousingMenu(int containerId, Inventory playerInventory)
  {
    super(MenuRegistration.BEE_HOUSING.get(), containerId, playerInventory);
  }

  public BeeHousingMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, ConfigurableItemStackHandler inventory)
  {
    super(MenuRegistration.BEE_HOUSING.get(), containerId, playerInventory);
  }

  @Override
  public boolean stillValid(@NotNull Player player)
  {
    return AbstractContainerMenu.stillValid(getAccess(), player, BlockRegistration.BEE_HOUSING.asBlock());
  }
}
