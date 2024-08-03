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
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;
import sandybay.apicurious.common.register.ApicuriousMenuRegistration;

import java.util.Objects;

public class ApiaryMenu extends AbstractHousingMenu
{
  private final ApiaryHousingBE apiary;
  private final ContainerData containerData;

  public ApiaryMenu(int containerId, Inventory playerInventory) {
    this(containerId, playerInventory, null);
  }

  public ApiaryMenu(int containerId, Inventory playerInventory, FriendlyByteBuf packetBuffer)
  {
    super(ApicuriousMenuRegistration.APIARY.get(), containerId, playerInventory);
    this.apiary = (ApiaryHousingBE) Objects.requireNonNull(Minecraft.getInstance().level.getBlockEntity(packetBuffer.readBlockPos()));
    this.containerData = new SimpleContainerData(3);
    addDataSlots(containerData);
  }

  public ApiaryMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, ApiaryHousingBE apiary)
  {
    super(ApicuriousMenuRegistration.APIARY.get(), containerId, playerInventory, access, apiary.getInventory(), apiary.getErrorList());
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

  @Override
  public boolean stillValid(@NotNull Player player)
  {
    return AbstractContainerMenu.stillValid(getAccess(), player, ApicuriousBlockRegistration.APIARY.asBlock());
  }
}
