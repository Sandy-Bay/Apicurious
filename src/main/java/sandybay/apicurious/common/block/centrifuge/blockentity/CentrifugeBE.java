package sandybay.apicurious.common.block.centrifuge.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.api.housing.ITicker;
import sandybay.apicurious.common.registrar.BlockRegistrar;

public class CentrifugeBE extends BlockEntity implements ITicker, MenuProvider
{

  public CentrifugeBE(BlockPos pPos, BlockState pBlockState)
  {
    super(BlockRegistrar.CENTRIFUGE.getType(), pPos, pBlockState);
  }

  @Override
  public Component getDisplayName()
  {
    return Component.translatable("apicurious.menu.centrifuge");
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer)
  {
    return null;
  }

  @Override
  public void serverTick(Level level, BlockPos pos, BlockState state)
  {

  }

  @Override
  public void clientTick(Level level, BlockPos pos, BlockState state) { }
}
