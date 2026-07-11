package sandybay.apicurious.common.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.common.block.housing.blockentity.BeeHousingBE;
import sandybay.apicurious.common.registrar.BlockRegistrar;
import sandybay.apicurious.common.registrar.MenuRegistrar;

import java.util.Objects;

public class BeeHousingMenu extends AbstractHousingMenu
{
  private final BeeHousingBE housing;
  private final ContainerData containerData;
  private final Player player;

  public BeeHousingMenu(int containerId, Inventory playerInventory, FriendlyByteBuf packetBuffer)
  {
    super(MenuRegistrar.BEE_HOUSING.get(), containerId, playerInventory);
    this.player = playerInventory.player;
    this.housing = (BeeHousingBE) Objects.requireNonNull(Minecraft.getInstance().level.getBlockEntity(packetBuffer.readBlockPos()));
    this.containerData = new SimpleContainerData(3);
    addDataSlots(containerData);
  }

  public BeeHousingMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, BeeHousingBE housing)
  {
    super(MenuRegistrar.BEE_HOUSING.get(), containerId, playerInventory, access, housing.getInventory(), housing.getErrorList());
    this.player = playerInventory.player;
    this.housing = housing;
    this.containerData = housing.getContainerData();
    addDataSlots(containerData);
    if (playerInventory.player instanceof ServerPlayer serverPlayer)
    {
      housing.addViewer(serverPlayer);
    }
  }

  @Override
  public void removed(Player player)
  {
    super.removed(player);
    if (player instanceof ServerPlayer serverPlayer)
    {
      housing.removeViewer(serverPlayer);
    }
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
    return AbstractContainerMenu.stillValid(getAccess(), player, BlockRegistrar.BEE_HOUSING.asBlock());
  }
}
