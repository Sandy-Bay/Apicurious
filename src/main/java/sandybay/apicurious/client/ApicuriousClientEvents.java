package sandybay.apicurious.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import sandybay.apicurious.api.register.ApicuriousDataComponentRegistration;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.client.gui.ApiaryScreen;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.block.HiveBlock;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;
import sandybay.apicurious.common.register.ApicuriousItemRegistration;
import sandybay.apicurious.common.register.ApicuriousMenuRegistration;

import javax.annotation.Nullable;
import java.util.Optional;

public class ApicuriousClientEvents
{

  public static void registerClientEvents(IEventBus bus)
  {
    bus.addListener(ApicuriousClientEvents::handleItemTint);
    bus.addListener(ApicuriousClientEvents::handleBlockTint);
    bus.addListener(ApicuriousClientEvents::registerAlternativeBeeModels);
    bus.addListener(ApicuriousClientEvents::registerScreens);
  }

  private static void handleBlockTint(final RegisterColorHandlersEvent.Block event)
  {
    event.register(
            ApicuriousClientEvents::registerHiveTintHandler,
            ApicuriousBlockRegistration.FOREST_HIVE.asBlock(),
            ApicuriousBlockRegistration.MEADOW_HIVE.asBlock(),
            ApicuriousBlockRegistration.MODEST_HIVE.asBlock(),
            ApicuriousBlockRegistration.TROPICAL_HIVE.asBlock(),
            ApicuriousBlockRegistration.WINTRY_HIVE.asBlock(),
            ApicuriousBlockRegistration.MARSHY_HIVE.asBlock(),
            ApicuriousBlockRegistration.ROCKY_HIVE.asBlock(),
            ApicuriousBlockRegistration.NETHER_HIVE.asBlock(),
            ApicuriousBlockRegistration.ENDER_HIVE.asBlock()
    );
  }

  private static void handleItemTint(final RegisterColorHandlersEvent.Item event)
  {
    event.register(
            ApicuriousClientEvents::registerBeeTintHandler,
            ApicuriousItemRegistration.DRONE.get(),
            ApicuriousItemRegistration.PRINCESS.get(),
            ApicuriousItemRegistration.QUEEN.get()
    );
    event.register(
            ApicuriousClientEvents::registerHiveItemTintHandler,
            ApicuriousBlockRegistration.FOREST_HIVE.asItem(),
            ApicuriousBlockRegistration.MEADOW_HIVE.asItem(),
            ApicuriousBlockRegistration.MODEST_HIVE.asItem(),
            ApicuriousBlockRegistration.TROPICAL_HIVE.asItem(),
            ApicuriousBlockRegistration.WINTRY_HIVE.asItem(),
            ApicuriousBlockRegistration.MARSHY_HIVE.asItem(),
            ApicuriousBlockRegistration.ROCKY_HIVE.asItem(),
            ApicuriousBlockRegistration.NETHER_HIVE.asItem(),
            ApicuriousBlockRegistration.ENDER_HIVE.asItem()
    );
  }

  private static void registerAlternativeBeeModels(final ModelEvent.RegisterAdditional event)
  {
    FileToIdConverter converter = FileToIdConverter.json("models/item/species");
    converter.listMatchingResources(Minecraft.getInstance().getResourceManager()).forEach((name, resource) ->
    {
      event.register(ModelResourceLocation.standalone(converter.fileToId(name).withPrefix("item/species/")));
    });
  }

  private static void registerScreens(RegisterMenuScreensEvent event)
  {
    event.register(ApicuriousMenuRegistration.APIARY.get(), ApiaryScreen::new);
  }

  private static int registerBeeTintHandler(ItemStack stack, int tintIndex)
  {
    int tint = 0xFFFFFFFF;
    if (tintIndex == 0) tint = getColor(stack, true, false);
    if (tintIndex == 1) tint = getColor(stack, false, true);
    if (tintIndex == 4) tint = getColor(stack, false, false);
    return tint;
  }

  private static int registerHiveItemTintHandler(ItemStack stack, int tintIndex)
  {
    int tint = 0xFFFFFFFF;
    if (stack.getItem() instanceof BlockItem blockItem)
    {
      Block block = blockItem.getBlock();
      tint = getHiveTint(block);
    }
    return tint;
  }

  private static int registerHiveTintHandler(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int tintIndex)
  {
    return getHiveTint(state.getBlock());
  }

  private static int getHiveTint(Block block)
  {
    ClientPacketListener connection = Minecraft.getInstance().getConnection();
    if (connection != null && block instanceof HiveBlock hiveBlock)
    {
      Optional<Registry<BeeSpecies>> optional = connection.registryAccess().registry(ApicuriousRegistries.BEE_SPECIES);
      if (optional.isPresent())
      {
        BeeSpecies species = optional.get().get(hiveBlock.getSpecies());
        if (species == null) return 0xFFFFFFFF;
        return species.getVisualData().getBeeColor().getOutlineTint().getIntColor();
      }
    }
    return 0xFFFFFFFF;
  }

  private static int getColor(ItemStack stack, boolean isOutline, boolean isBody)
  {
    BeeSpecies species = stack.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null || species.getVisualData() == null || species.getVisualData().hasCustomRender())
      return 0xFFFFFFFF;
    return isOutline ?
            species.getVisualData().getBeeColor().getOutlineTint().getIntColor() :
            isBody ?
                    species.getVisualData().getBeeColor().getBodyTint().getIntColor() :
                    species.getVisualData().getBeeColor().getWingTint().getIntColor();
  }


}
