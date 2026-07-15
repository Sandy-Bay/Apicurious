package sandybay.apicurious.common.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.common.menu.AnalyzerMenu;

public class BeeAnalyzerItem extends Item
{

  public BeeAnalyzerItem(Properties pProperties)
  {
    super(pProperties.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));
  }

  @Override
  public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand)
  {
    ItemStack stack = player.getItemInHand(hand);
    if (!stack.has(DataComponents.CONTAINER))
    {
      return InteractionResult.FAIL;
    }

    if (player instanceof ServerPlayer serverPlayer)
    {
      int analyzerSlot = hand == InteractionHand.MAIN_HAND ? player.getInventory().getSelectedSlot() : Inventory.SLOT_OFFHAND;

      serverPlayer.openMenu(new SimpleMenuProvider((containerId, playerInventory, pPlayer) -> new AnalyzerMenu(containerId, playerInventory, ContainerLevelAccess.NULL, analyzerSlot), Component.translatable("apicurious.menu.analyzer")), buffer -> buffer.writeVarInt(analyzerSlot));
      return InteractionResult.CONSUME;
    }
    return InteractionResult.SUCCESS;
  }
}