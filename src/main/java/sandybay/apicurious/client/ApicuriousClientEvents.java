package sandybay.apicurious.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.client.gui.AnalyzerScreen;
import sandybay.apicurious.client.gui.ApiaryScreen;
import sandybay.apicurious.client.gui.BeeHousingScreen;
import sandybay.apicurious.client.gui.CentrifugeScreen;
import sandybay.apicurious.client.tinter.BeeCombItemTinter;
import sandybay.apicurious.client.tinter.BeeHiveItemTinter;
import sandybay.apicurious.client.tinter.BeeItemTinter;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.block.HiveBlock;
import sandybay.apicurious.common.registrar.BlockRegistrar;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.common.registrar.MenuRegistrar;

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
      public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer()
      {
        return new BeeItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
      }
    }, ItemRegistrar.DRONE.get(), ItemRegistrar.PRINCESS.get(), ItemRegistrar.QUEEN.get());
  }

  private static void handleBlockTint(final RegisterColorHandlersEvent.Block event)
  {
    event.register(ApicuriousClientEvents::registerHiveTintHandler, BlockRegistrar.FOREST_HIVE.asBlock(), BlockRegistrar.MEADOW_HIVE.asBlock(), BlockRegistrar.MODEST_HIVE.asBlock(), BlockRegistrar.TROPICAL_HIVE.asBlock(), BlockRegistrar.WINTRY_HIVE.asBlock(), BlockRegistrar.MARSHY_HIVE.asBlock(), BlockRegistrar.ROCKY_HIVE.asBlock(), BlockRegistrar.NETHER_HIVE.asBlock(), BlockRegistrar.ENDER_HIVE.asBlock(), BlockRegistrar.WATER_HIVE.asBlock());
  }

  private static void handleItemTint(final RegisterColorHandlersEvent.ItemTintSources event)
  {
    event.register(Apicurious.createIdentifier("bee_item_tinter"), BeeItemTinter.MAP_CODEC);
    event.register(Apicurious.createIdentifier("bee_hive_item_tinter"), BeeHiveItemTinter.MAP_CODEC);
    event.register(Apicurious.createIdentifier("bee_comb_item_tinter"), BeeCombItemTinter.MAP_CODEC);
  }

  private static void registerAlternativeBeeModels(final ModelEvent.RegisterStandalone event)
  {
    FileToIdConverter converter = FileToIdConverter.json("models/item/species");
    converter.listMatchingResources(Minecraft.getInstance().getResourceManager()).forEach((name, resource) ->
    {
      event.register(ModelResourceLocation.standalone(converter.fileToId(name).withPrefix("item/species/")));
    });
  }

  private static void registerScreens(RegisterMenuScreensEvent event)
  {
    event.register(MenuRegistrar.APIARY.get(), ApiaryScreen::new);
    event.register(MenuRegistrar.BEE_HOUSING.get(), BeeHousingScreen::new);
    event.register(MenuRegistrar.ANALYZER.get(), AnalyzerScreen::new);
    event.register(MenuRegistrar.CENTRIFUGE.get(), CentrifugeScreen::new);
  }

  private static int registerHiveTintHandler(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int tintIndex)
  {
    return getHiveTint(state.getBlock());
  }

  public static int getHiveTint(Block block)
  {
    ClientPacketListener connection = Minecraft.getInstance().getConnection();
    if (connection != null && block instanceof HiveBlock hiveBlock)
    {
      Optional<Holder.Reference<Registry<IAllele<?>>>> optional = connection.registryAccess().get(ApicuriousRegistries.ALLELES);
      if (optional.isPresent())
      {
        BeeSpecies species = (BeeSpecies) optional.get().get(hiveBlock.getSpecies());
        if (species == null) {return 0xFFFFFFFF;}
        return species.getVisualData().getBeeColor().getOutlineTint().getIntColor();
      }
    }
    return 0xFFFFFFFF;
  }


}
