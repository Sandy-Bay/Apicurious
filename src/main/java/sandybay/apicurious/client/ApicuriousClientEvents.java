package sandybay.apicurious.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterItemModelsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.model.standalone.SimpleUnbakedStandaloneModel;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.client.gui.AnalyzerScreen;
import sandybay.apicurious.client.gui.ApiaryScreen;
import sandybay.apicurious.client.gui.BeeHousingScreen;
import sandybay.apicurious.client.gui.CentrifugeScreen;
import sandybay.apicurious.client.renderer.BeeItemModel;
import sandybay.apicurious.client.tinter.BeeCombItemTinter;
import sandybay.apicurious.client.tinter.BeeHiveBlockTinter;
import sandybay.apicurious.client.tinter.BeeHiveItemTinter;
import sandybay.apicurious.client.tinter.BeeItemTinter;
import sandybay.apicurious.common.registrar.BlockRegistrar;
import sandybay.apicurious.common.registrar.MenuRegistrar;

import java.util.List;

public class ApicuriousClientEvents
{

  public static void registerClientEvents(IEventBus bus)
  {
    bus.addListener(ApicuriousClientEvents::handleItemTint);
    bus.addListener(ApicuriousClientEvents::handleBlockTint);
    bus.addListener(ApicuriousClientEvents::registerScreens);
    bus.addListener(ApicuriousClientEvents::registerItemModels);
    bus.addListener(ApicuriousClientEvents::registerAlternativeBeeModels);
  }

  private static void registerItemModels(RegisterItemModelsEvent event)
  {
    event.register(Apicurious.createIdentifier("bee"), BeeItemModel.Unbaked.MAP_CODEC);
  }

  private static void handleBlockTint(final RegisterColorHandlersEvent.BlockTintSources event)
  {
    event.register(List.of(new BeeHiveBlockTinter()), BlockRegistrar.FOREST_HIVE.asBlock(), BlockRegistrar.MEADOW_HIVE.asBlock(), BlockRegistrar.MODEST_HIVE.asBlock(), BlockRegistrar.TROPICAL_HIVE.asBlock(), BlockRegistrar.WINTRY_HIVE.asBlock(), BlockRegistrar.MARSHY_HIVE.asBlock(), BlockRegistrar.ROCKY_HIVE.asBlock(), BlockRegistrar.NETHER_HIVE.asBlock(), BlockRegistrar.ENDER_HIVE.asBlock(), BlockRegistrar.WATER_HIVE.asBlock());
  }

  private static void handleItemTint(final RegisterColorHandlersEvent.ItemTintSources event)
  {
    event.register(Apicurious.createIdentifier("bee_item_tinter"), BeeItemTinter.MAP_CODEC);
    event.register(Apicurious.createIdentifier("bee_hive_item_tinter"), BeeHiveItemTinter.MAP_CODEC);
    event.register(Apicurious.createIdentifier("bee_comb_item_tinter"), BeeCombItemTinter.MAP_CODEC);
  }

  private static void registerScreens(RegisterMenuScreensEvent event)
  {
    event.register(MenuRegistrar.APIARY.get(), ApiaryScreen::new);
    event.register(MenuRegistrar.BEE_HOUSING.get(), BeeHousingScreen::new);
    event.register(MenuRegistrar.ANALYZER.get(), AnalyzerScreen::new);
    event.register(MenuRegistrar.CENTRIFUGE.get(), CentrifugeScreen::new);
  }

  private static void registerAlternativeBeeModels(final ModelEvent.RegisterStandalone event)
  {
    FileToIdConverter converter = FileToIdConverter.json("models/item/species");
    converter.listMatchingResources(Minecraft.getInstance().getResourceManager()).forEach((name, resource) ->
    {
      Identifier id = converter.fileToId(name).withPrefix("item/species/");
      StandaloneModelKey<QuadCollection> key = new StandaloneModelKey<>(id::toString);
      event.register(key, SimpleUnbakedStandaloneModel.quadCollection(id));
    });
  }
}
