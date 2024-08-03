package sandybay.apicurious.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;
import sandybay.apicurious.common.register.ApicuriousMenuRegistration;

public class BeeHousingMenu extends AbstractHousingMenu
{

  public BeeHousingMenu(int containerId, Inventory playerInventory)
  {
    super(ApicuriousMenuRegistration.BEE_HOUSING.get(), containerId, playerInventory);
  }

  public BeeHousingMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, ConfigurableItemStackHandler inventory)
  {
    super(ApicuriousMenuRegistration.BEE_HOUSING.get(), containerId, playerInventory);
  }

  @Override
  public boolean stillValid(@NotNull Player player)
  {
    return AbstractContainerMenu.stillValid(getAccess(), player, ApicuriousBlockRegistration.BEE_HOUSING.asBlock());
  }
}
