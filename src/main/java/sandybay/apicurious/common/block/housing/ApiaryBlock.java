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
import sandybay.apicurious.common.block.blockentity.ApiaryHousingBE;
import sandybay.apicurious.common.menu.ApiaryMenu;

public class ApiaryBlock extends BaseHousingBlock implements MenuProvider
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
    if (!level.isClientSide && player instanceof ServerPlayer serverPlayer)
    {
      if (level.getBlockEntity(pos) instanceof ApiaryHousingBE)
      {
        serverPlayer.openMenu(state.getMenuProvider(level, pos));
      }
    }
    return InteractionResult.sidedSuccess(level.isClientSide);
  }

  //@Override
  public @NotNull Component getDisplayName()
  {
    return Component.translatable("apicurious.menu.apiary");
  }

  @Nullable
  @Override
  protected MenuProvider getMenuProvider(@NotNull BlockState state, Level level, @NotNull BlockPos pos)
  {
    if (level.getBlockEntity(pos) instanceof ApiaryHousingBE apiary)
    {
      return new SimpleMenuProvider(
              (id, inventory, player) -> new ApiaryMenu(
                      id,
                      inventory,
                      ContainerLevelAccess.create(level, pos),
                      apiary.containerData,
                      apiary.getInventory()
              ), getDisplayName());
    }
    return null;
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer)
  {
    return new SimpleMenuProvider(ApiaryMenu::new, getDisplayName()).createMenu(pContainerId, pPlayerInventory, pPlayer);
  }

}
