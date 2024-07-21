package sandybay.apicurious.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.block.ApicuriousBlockRegistration;
import sandybay.apicurious.common.block.HiveBlock;
import sandybay.apicurious.common.datacomponent.ApicuriousDataComponents;
import sandybay.apicurious.common.item.ApicuriousItemRegistration;

import javax.annotation.Nullable;
import javax.swing.text.html.Option;
import java.util.Objects;
import java.util.Optional;

public class ApicuriousClientEvents {

  public static void registerClientEvents(IEventBus bus) {
    bus.addListener(ApicuriousClientEvents::handleItemTint);
    bus.addListener(ApicuriousClientEvents::handleBlockTint);
  }

  private static void handleBlockTint(final RegisterColorHandlersEvent.Block event) {
    event.register(
            ApicuriousClientEvents::registerHiveTintHandler,
            ApicuriousBlockRegistration.FOREST_HIVE.block().get(),
            ApicuriousBlockRegistration.MEADOW_HIVE.block().get(),
            ApicuriousBlockRegistration.MODEST_HIVE.block().get(),
            ApicuriousBlockRegistration.TROPICAL_HIVE.block().get(),
            ApicuriousBlockRegistration.WINTRY_HIVE.block().get(),
            ApicuriousBlockRegistration.MARSHY_HIVE.block().get(),
            ApicuriousBlockRegistration.ROCKY_HIVE.block().get(),
            ApicuriousBlockRegistration.NETHER_HIVE.block().get(),
            ApicuriousBlockRegistration.ENDER_HIVE.block().get()
    );
  }

  private static void handleItemTint(final RegisterColorHandlersEvent.Item event) {
    event.register(
            ApicuriousClientEvents::registerBeeTintHandler,
            ApicuriousItemRegistration.DRONE.get(),
            ApicuriousItemRegistration.PRINCESS.get(),
            ApicuriousItemRegistration.QUEEN.get()
    );
    event.register(
            ApicuriousClientEvents::registerHiveItemTintHandler,
            ApicuriousBlockRegistration.FOREST_HIVE.item().get(),
            ApicuriousBlockRegistration.MEADOW_HIVE.item().get(),
            ApicuriousBlockRegistration.MODEST_HIVE.item().get(),
            ApicuriousBlockRegistration.TROPICAL_HIVE.item().get(),
            ApicuriousBlockRegistration.WINTRY_HIVE.item().get(),
            ApicuriousBlockRegistration.MARSHY_HIVE.item().get(),
            ApicuriousBlockRegistration.ROCKY_HIVE.item().get(),
            ApicuriousBlockRegistration.NETHER_HIVE.item().get(),
            ApicuriousBlockRegistration.ENDER_HIVE.item().get()
    );
  }

  private static int registerBeeTintHandler(ItemStack stack, int tintIndex) {
    int tint = 0xFFFFFFFF;
    if (tintIndex == 0) tint = getColor(stack, true, false);
    if (tintIndex == 1) tint = getColor(stack, false, true);
    if (tintIndex == 4) tint = getColor(stack, false, false);
    return tint;
  }

  private static int registerHiveItemTintHandler(ItemStack stack, int tintIndex) {
    int tint = 0xFFFFFFFF;
    if (stack.getItem() instanceof BlockItem blockItem) {
      Block block = blockItem.getBlock();
      ClientPacketListener connection = Minecraft.getInstance().getConnection();
      if (connection != null && block instanceof HiveBlock hiveBlock) {
        Optional<Registry<BeeSpecies>> optional = connection.registryAccess().registry(ApicuriousRegistries.BEE_SPECIES);
        if (optional.isPresent()) {
          BeeSpecies species = optional.get().get(hiveBlock.getSpecies());
          if (species == null) return tint;
          tint = species.getVisualData().getBeeColor().getOutlineTint().getIntColor();
        }
      }
    }
    return tint;
  }

  private static int registerHiveTintHandler(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int tintIndex) {
    int tint = 0xFFFFFFFF;
    ClientPacketListener connection = Minecraft.getInstance().getConnection();
    if (connection != null && state.getBlock() instanceof HiveBlock hiveBlock) {
      Optional<Registry<BeeSpecies>> optional = connection.registryAccess().registry(ApicuriousRegistries.BEE_SPECIES);
      if (optional.isPresent()) {
        BeeSpecies species = optional.get().get(hiveBlock.getSpecies());
        if (species == null) return tint;
        tint = species.getVisualData().getBeeColor().getOutlineTint().getIntColor();
      }
    }
    return tint;
  }

  private static int getColor(ItemStack stack, boolean isOutline, boolean isBody) {
    BeeSpecies species = stack.get(ApicuriousDataComponents.BEE_SPECIES);
    if (species == null || species.getVisualData() == null) return 0xffffff;
    return isOutline ?
            species.getVisualData().getBeeColor().getOutlineTint().getIntColor() : isBody ?
            species.getVisualData().getBeeColor().getBodyTint().getIntColor() :
            species.getVisualData().getBeeColor().getWingTint().getIntColor();
  }


}
