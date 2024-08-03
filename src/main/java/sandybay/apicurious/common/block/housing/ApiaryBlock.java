package sandybay.apicurious.common.block.housing;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.api.housing.BaseHousingBlock;
import sandybay.apicurious.api.housing.blockentity.BaseHousingBE;
import sandybay.apicurious.common.block.blockentity.ApiaryHousingBE;
import sandybay.apicurious.common.menu.ApiaryMenu;

public class ApiaryBlock extends BaseHousingBlock
{

  public ApiaryBlock(Properties properties)
  {
    super(properties, 1.0f);
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state)
  {
    return new ApiaryHousingBE(pos, state);
  }

  @Override
  protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult)
  {
    if (!level.isClientSide)
    {
      if (level.getBlockEntity(pos) instanceof BaseHousingBE apiaryHousingBE) player.openMenu(apiaryHousingBE, pos);
    }
    return InteractionResult.sidedSuccess(level.isClientSide);
  }
}
