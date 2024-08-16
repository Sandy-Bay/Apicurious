package sandybay.apicurious.common.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.common.menu.BeeHousingMenu;
import sandybay.apicurious.common.registrar.BlockRegistrar;

public class BeeHousingBE extends SimpleBlockHousingBE
{

  public BeeHousingBE(BlockPos pos, BlockState blockState)
  {
    super(BlockRegistrar.BEE_HOUSING.getType(), pos, blockState);
  }

  @Override
  public Component getDisplayName()
  {
    return Component.translatable("apicurious.menu.bee_housing");
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
  {
    if (getLevel() == null) return null;
    return new BeeHousingMenu(id, inventory, ContainerLevelAccess.create(getLevel(), getBlockPos()), this);
  }
}
