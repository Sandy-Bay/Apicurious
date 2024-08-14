package sandybay.apicurious.common.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.api.housing.blockentity.SimpleBlockHousingBE;
import sandybay.apicurious.common.register.BlockRegistration;

public class BeeHousingBE extends SimpleBlockHousingBE
{

  public BeeHousingBE(BlockPos pos, BlockState blockState)
  {
    super(BlockRegistration.BEE_HOUSING.getType(), pos, blockState);
  }

  @Override
  public void serverTick(Level level, BlockPos pos, BlockState state)
  {

  }

  @Override
  public void clientTick(Level level, BlockPos pos, BlockState state)
  {

  }

  @Override
  public Component getDisplayName()
  {
    return Component.translatable("apicurious.menu.bee_housing");
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer)
  {
    return null; // TODO: Implement this
  }
}
