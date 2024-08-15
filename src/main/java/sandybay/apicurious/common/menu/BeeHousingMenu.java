package sandybay.apicurious.common.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.common.block.blockentity.ApiaryHousingBE;
import sandybay.apicurious.common.block.blockentity.BeeHousingBE;
import sandybay.apicurious.common.register.BlockRegistration;
import sandybay.apicurious.common.register.MenuRegistration;

import java.util.Objects;

public class BeeHousingMenu extends AbstractHousingMenu
{
  private final BeeHousingBE housing;
  private final ContainerData containerData;
  private final Player player;

  public BeeHousingMenu(int containerId, Inventory playerInventory, FriendlyByteBuf packetBuffer)
  {
    super(MenuRegistration.BEE_HOUSING.get(), containerId, playerInventory);
    this.player = playerInventory.player;
    this.housing = (BeeHousingBE) Objects.requireNonNull(Minecraft.getInstance().level.getBlockEntity(packetBuffer.readBlockPos()));
    this.containerData = new SimpleContainerData(3);
    addDataSlots(containerData);
  }

  public BeeHousingMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, BeeHousingBE housing)
  {
    super(MenuRegistration.BEE_HOUSING.get(), containerId, playerInventory, access, housing.getInventory(), housing.getErrorList());
    this.player = playerInventory.player;
    this.housing = housing;
    this.containerData = housing.getContainerData();
    addDataSlots(containerData);
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

  public BeeHousingBE getHousing()
  {
    return housing;
  }

  @Override
  public boolean stillValid(@NotNull Player player)
  {
    return AbstractContainerMenu.stillValid(getAccess(), player, BlockRegistration.BEE_HOUSING.asBlock());
  }
}
