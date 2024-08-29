package sandybay.apicurious.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.client.gui.AnalyzerScreen;
import sandybay.apicurious.client.gui.ApiaryScreen;
import sandybay.apicurious.client.gui.BeeHousingScreen;
import sandybay.apicurious.client.gui.CentrifugeScreen;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.block.HiveBlock;
import sandybay.apicurious.common.item.CombItem;
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

  private static void handleItemTint(final RegisterColorHandlersEvent.Item event)
  {
    event.register(ApicuriousClientEvents::registerBeeTintHandler, ItemRegistrar.DRONE.get(), ItemRegistrar.PRINCESS.get(), ItemRegistrar.QUEEN.get());
    event.register(ApicuriousClientEvents::registerHiveItemTintHandler, BlockRegistrar.FOREST_HIVE.asItem(), BlockRegistrar.MEADOW_HIVE.asItem(), BlockRegistrar.MODEST_HIVE.asItem(), BlockRegistrar.TROPICAL_HIVE.asItem(), BlockRegistrar.WINTRY_HIVE.asItem(), BlockRegistrar.MARSHY_HIVE.asItem(), BlockRegistrar.ROCKY_HIVE.asItem(), BlockRegistrar.NETHER_HIVE.asItem(), BlockRegistrar.ENDER_HIVE.asItem(), BlockRegistrar.WATER_HIVE.asItem());
    ItemLike[] combs = ItemRegistrar.COMBS.stream().map(Holder::value).toArray(ItemLike[]::new);
    event.register(ApicuriousClientEvents::registerCombTintHandler, combs);
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
    event.register(MenuRegistrar.APIARY.get(), ApiaryScreen::new);
    event.register(MenuRegistrar.BEE_HOUSING.get(), BeeHousingScreen::new);
    event.register(MenuRegistrar.ANALYZER.get(), AnalyzerScreen::new);
    event.register(MenuRegistrar.CENTRIFUGE.get(), CentrifugeScreen::new);
  }

  private static int registerBeeTintHandler(ItemStack stack, int tintIndex)
  {
    int tint = 0xFFFFFFFF;
    if (tintIndex == 0) {tint = getColor(stack, true, false);}
    if (tintIndex == 1) {tint = getColor(stack, false, true);}
    if (tintIndex == 4) {tint = getColor(stack, false, false);}
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

  private static int registerCombTintHandler(ItemStack stack, int tintIndex)
  {
    if (stack.getItem() instanceof CombItem comb)
    {
      if (tintIndex == 0) return comb.getOutline().getIntColor();
      else return comb.getCells().getIntColor();
    }
    return 0xFFFFFFFF;
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
        if (species == null) {return 0xFFFFFFFF;}
        return species.getVisualData().getBeeColor().getOutlineTint().getIntColor();
      }
    }
    return 0xFFFFFFFF;
  }

  private static int getColor(ItemStack stack, boolean isOutline, boolean isBody)
  {
    Genome genome = stack.get(DataComponentRegistrar.GENOME);
    if (genome == null) {return 0xFFFFFFFF;}
    BeeSpecies species = (BeeSpecies) genome.getSpecies(true).value();
    if (species.getVisualData() == null || species.getVisualData().hasCustomRender()) {return 0xFFFFFFFF;}
    return isOutline ? species.getVisualData().getBeeColor().getOutlineTint().getIntColor() : isBody ? species.getVisualData().getBeeColor().getBodyTint().getIntColor() : species.getVisualData().getBeeColor().getWingTint().getIntColor();
  }


}
