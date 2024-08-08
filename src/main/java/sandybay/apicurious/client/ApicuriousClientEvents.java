package sandybay.apicurious.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
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
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.register.DataComponentRegistration;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.client.gui.ApiaryScreen;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.block.HiveBlock;
import sandybay.apicurious.common.register.BlockRegistration;
import sandybay.apicurious.common.register.ItemRegistration;
import sandybay.apicurious.common.register.MenuRegistration;

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
    bus.addListener(ApicuriousClientEvents::registerClientExtensions);
  }

  private static void registerClientExtensions(final RegisterClientExtensionsEvent event)
  {
    event.registerItem(new IClientItemExtensions()
    {
      @Override
      public BlockEntityWithoutLevelRenderer getCustomRenderer()
      {
        return new BeeItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
      }
    }, ItemRegistration.DRONE.get(), ItemRegistration.PRINCESS.get(), ItemRegistration.QUEEN.get());
  }

  private static void handleBlockTint(final RegisterColorHandlersEvent.Block event)
  {
    event.register(
            ApicuriousClientEvents::registerHiveTintHandler,
            BlockRegistration.FOREST_HIVE.asBlock(),
            BlockRegistration.MEADOW_HIVE.asBlock(),
            BlockRegistration.MODEST_HIVE.asBlock(),
            BlockRegistration.TROPICAL_HIVE.asBlock(),
            BlockRegistration.WINTRY_HIVE.asBlock(),
            BlockRegistration.MARSHY_HIVE.asBlock(),
            BlockRegistration.ROCKY_HIVE.asBlock(),
            BlockRegistration.NETHER_HIVE.asBlock(),
            BlockRegistration.ENDER_HIVE.asBlock()
    );
  }

  private static void handleItemTint(final RegisterColorHandlersEvent.Item event)
  {
    event.register(
            ApicuriousClientEvents::registerBeeTintHandler,
            ItemRegistration.DRONE.get(),
            ItemRegistration.PRINCESS.get(),
            ItemRegistration.QUEEN.get()
    );
    event.register(
            ApicuriousClientEvents::registerHiveItemTintHandler,
            BlockRegistration.FOREST_HIVE.asItem(),
            BlockRegistration.MEADOW_HIVE.asItem(),
            BlockRegistration.MODEST_HIVE.asItem(),
            BlockRegistration.TROPICAL_HIVE.asItem(),
            BlockRegistration.WINTRY_HIVE.asItem(),
            BlockRegistration.MARSHY_HIVE.asItem(),
            BlockRegistration.ROCKY_HIVE.asItem(),
            BlockRegistration.NETHER_HIVE.asItem(),
            BlockRegistration.ENDER_HIVE.asItem()
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
    event.register(MenuRegistration.APIARY.get(), ApiaryScreen::new);
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
      Optional<Registry<IAllele<?>>> optional = connection.registryAccess().registry(ApicuriousRegistries.ALLELES);
      if (optional.isPresent())
      {
        BeeSpecies species = (BeeSpecies) optional.get().get(hiveBlock.getSpecies());
        if (species == null) return 0xFFFFFFFF;
        return species.getVisualData().getBeeColor().getOutlineTint().getIntColor();
      }
    }
    return 0xFFFFFFFF;
  }

  private static int getColor(ItemStack stack, boolean isOutline, boolean isBody)
  {
    BeeSpecies species = stack.get(DataComponentRegistration.BEE_SPECIES);
    if (species == null || species.getVisualData() == null || species.getVisualData().hasCustomRender())
      return 0xFFFFFFFF;
    return isOutline ?
            species.getVisualData().getBeeColor().getOutlineTint().getIntColor() :
            isBody ?
                    species.getVisualData().getBeeColor().getBodyTint().getIntColor() :
                    species.getVisualData().getBeeColor().getWingTint().getIntColor();
  }


}
