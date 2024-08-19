package sandybay.apicurious.common.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.items.ComponentItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.api.util.ClimateHelper;
import sandybay.apicurious.common.menu.AnalyzerMenu;

// TODO: Figure out how to sync the inventory storage between client and server.
public class BeeAnalyzerItem extends Item
{
  private ClimateHelper helper;

  public BeeAnalyzerItem(Properties pProperties)
  {
    super(pProperties.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));
  }

  @Override
  public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand)
  {
    ItemStack stack = player.getItemInHand(hand);
    if (this.helper == null) this.helper = new ClimateHelper(level, null);
    if (!stack.has(DataComponents.CONTAINER)) return InteractionResultHolder.fail(stack);
    if (player instanceof ServerPlayer serverPlayer)
    {
      serverPlayer.openMenu(new SimpleMenuProvider(
              (containerId, playerInventory, pPlayer) -> new AnalyzerMenu(containerId, playerInventory),
              Component.translatable("apicurious.menu.analyzer")
      ), buffer -> buffer.writeVarInt(hand == InteractionHand.MAIN_HAND ? player.getInventory().selected : 40));
    }
    return super.use(level, player, hand);
  }
}
