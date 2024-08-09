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
import sandybay.apicurious.common.block.blockentity.ApiaryHousingBE;
import sandybay.apicurious.common.register.BlockRegistration;
import sandybay.apicurious.common.register.MenuRegistration;

import java.util.Objects;

public class ApiaryMenu extends AbstractHousingMenu
{
  private final ApiaryHousingBE apiary;
  private final ContainerData containerData;
  private final Player player;

  public ApiaryMenu(int containerId, Inventory playerInventory, FriendlyByteBuf packetBuffer)
  {
    super(MenuRegistration.APIARY.get(), containerId, playerInventory);
    this.player = playerInventory.player;
    this.apiary = (ApiaryHousingBE) Objects.requireNonNull(Minecraft.getInstance().level.getBlockEntity(packetBuffer.readBlockPos()));
    this.containerData = new SimpleContainerData(3);
    addDataSlots(containerData);
  }

  public ApiaryMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, ApiaryHousingBE apiary)
  {
    super(MenuRegistration.APIARY.get(), containerId, playerInventory, access, apiary.getInventory(), apiary.getErrorList());
    this.player = playerInventory.player;
    this.apiary = apiary;
    this.containerData = apiary.getContainerData();
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

  public ApiaryHousingBE getApiary()
  {
    return apiary;
  }

  @Override
  public boolean stillValid(@NotNull Player player)
  {
    return AbstractContainerMenu.stillValid(getAccess(), player, BlockRegistration.APIARY.asBlock());
  }
}
