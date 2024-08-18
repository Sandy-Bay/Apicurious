package sandybay.apicurious.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.api.housing.handlers.item.ConfigurableItemStackHandler;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.api.util.ClimateHelper;
import sandybay.apicurious.common.menu.AnalyzerMenu;

public class BeeAnalyzerItem extends Item
{
  private ClimateHelper helper;
  private final ConfigurableItemStackHandler inventory;

  public BeeAnalyzerItem(Properties pProperties)
  {
    super(pProperties);
    this.inventory = new ConfigurableItemStackHandler(2)
            .setInputFilter((stack, slot) ->
            {
              if (slot == 0 && stack.getItem() instanceof BeeItem) return true;
              return slot == 1 && stack.is(ApicuriousTags.ItemTags.DROP_HONEY);
            })
            .setSlotLimit(0, 1);
  }

  @Override
  public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand)
  {
    if (this.helper == null) this.helper = new ClimateHelper(level, null);
    if (player instanceof ServerPlayer serverPlayer)
    {
      serverPlayer.openMenu(new SimpleMenuProvider(
              (containerId, playerInventory, pPlayer) -> new AnalyzerMenu(containerId, playerInventory),
              Component.translatable("menu.title.examplemod.mymenu")
      ));
    }
    return super.use(level, player, hand);
  }
}
