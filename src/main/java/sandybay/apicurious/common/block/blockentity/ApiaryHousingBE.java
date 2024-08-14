package sandybay.apicurious.common.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.api.housing.blockentity.SimpleBlockHousingBE;
import sandybay.apicurious.common.menu.ApiaryMenu;
import sandybay.apicurious.common.register.BlockRegistration;

public class ApiaryHousingBE extends SimpleBlockHousingBE
{

  private final ContainerData containerData = new ContainerData()
  {
    @Override
    public int get(int pIndex)
    {
      return switch (pIndex)
      {
        case 0 -> isActive ? 1 : 0;
        case 1 -> currentWork;
        case 2 -> maxWork;
        default -> throw new IllegalArgumentException("Invalid index: " + pIndex);
      };
    }

    @Override
    public void set(int pIndex, int pValue)
    {
      throw new IllegalStateException("Cannot set values through IIntArray");
    }

    @Override
    public int getCount()
    {
      return 3;
    }
  };

  public ApiaryHousingBE(BlockPos pos, BlockState blockState)
  {
    super(BlockRegistration.APIARY.getType(), pos, blockState);
  }

  public ContainerData getContainerData()
  {
    return containerData;
  }

  @Override
  public Component getDisplayName()
  {
    return Component.translatable("apicurious.menu.apiary");
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
  {
    if (getLevel() == null) return null;
    return new ApiaryMenu(id, inventory, ContainerLevelAccess.create(getLevel(), getBlockPos()), this);
  }
}
