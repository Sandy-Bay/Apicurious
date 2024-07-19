package sandybay.apicurious.common.creativetab;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.item.BaseBeeItem;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.datacomponent.ApicuriousDataComponents;
import sandybay.apicurious.common.item.ApicuriousItemRegistration;
import sandybay.apicurious.common.item.DroneBeeItem;

import java.util.List;

public class ApicuriousCreativeTabs {

  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Apicurious.MODID);

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BEE_TAB = CREATIVE_MODE_TABS.register("apicurious_bee", () -> CreativeModeTab.builder()
          .title(Component.translatable("itemGroup.apicurious.bee")) //The language key for the title of your CreativeModeTab
          .withTabsBefore(CreativeModeTabs.COMBAT)
          .icon(() -> BaseBeeItem.getBeeWithSpecies(null, ApicuriousSpecies.FOREST, ApicuriousItemRegistration.QUEEN))
          .displayItems((parameters, output) -> {
            registerBees(output);
          }).build());

  public static void registerBees(CreativeModeTab.Output output) {
    ClientPacketListener connection = Minecraft.getInstance().getConnection();
    if (connection != null) {
      connection.registryAccess().registry(ApicuriousRegistries.BEE_SPECIES).ifPresent(registry -> {
        for (ResourceLocation rl : registry.keySet()) {
          if (rl.getPath().equals("undefined")) continue;
          List<ItemStack> bees = List.of(
                  new ItemStack(ApicuriousItemRegistration.DRONE),
                  new ItemStack(ApicuriousItemRegistration.PRINCESS),
                  new ItemStack(ApicuriousItemRegistration.QUEEN)
          );
          BeeSpecies species = registry.get(rl);
          bees.forEach(stack -> stack.set(ApicuriousDataComponents.BEE_SPECIES, species));
          output.acceptAll(bees);
        }
      });
    }
  }

  public static void register(IEventBus bus) {
    CREATIVE_MODE_TABS.register(bus);
  }

}
