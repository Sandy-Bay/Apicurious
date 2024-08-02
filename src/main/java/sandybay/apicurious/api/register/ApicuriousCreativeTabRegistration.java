package sandybay.apicurious.api.register;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.item.BaseBeeItem;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;
import sandybay.apicurious.common.register.ApicuriousItemRegistration;

import java.util.List;

public class ApicuriousCreativeTabRegistration
{

  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Apicurious.MODID);

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GENERAL_TAB = CREATIVE_MODE_TABS.register("apicurious", () -> CreativeModeTab.builder()
          .title(Component.translatable("itemGroup.apicurious.general"))
          .withTabsBefore(CreativeModeTabs.COMBAT)
          .icon(() -> new ItemStack(ApicuriousItemRegistration.SIEVE.get()))
          .displayItems((parameters, output) ->
          {
            output.accept(new ItemStack(ApicuriousItemRegistration.SIEVE.get()));
            output.acceptAll(List.of(
                    ApicuriousBlockRegistration.APIARY.asItemStack(),
                    ApicuriousBlockRegistration.BEE_HOUSING.asItemStack()
            ));
            registerHives(output);
          }).build());

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BEE_TAB = CREATIVE_MODE_TABS.register("apicurious_bee", () -> CreativeModeTab.builder()
          .title(Component.translatable("itemGroup.apicurious.bee"))
          .withTabsBefore(ApicuriousCreativeTabRegistration.GENERAL_TAB.getKey())
          .icon(() -> BaseBeeItem.getBeeWithSpecies(null, ApicuriousSpecies.FOREST, ApicuriousItemRegistration.QUEEN))
          .displayItems((parameters, output) ->
          {
            registerBees(output);
          }).build());

  public static void registerBees(CreativeModeTab.Output output)
  {
    ClientPacketListener connection = Minecraft.getInstance().getConnection();
    if (connection != null)
    {
      connection.registryAccess().registry(ApicuriousRegistries.BEE_SPECIES).ifPresent(registry ->
      {
        for (ResourceLocation rl : registry.keySet())
        {
          if (rl.getPath().equals("undefined")) continue;
          List<ItemStack> bees = List.of(
                  new ItemStack(ApicuriousItemRegistration.DRONE),
                  new ItemStack(ApicuriousItemRegistration.PRINCESS),
                  new ItemStack(ApicuriousItemRegistration.QUEEN)
          );
          BeeSpecies species = registry.get(rl);
          bees.forEach(stack -> stack.set(ApicuriousDataComponentRegistration.BEE_SPECIES, species));
          output.acceptAll(bees);
        }
      });
    }
  }

  public static void registerHives(CreativeModeTab.Output output)
  {
    output.acceptAll(List.of(
            ApicuriousBlockRegistration.FOREST_HIVE.asItemStack(),
            ApicuriousBlockRegistration.MEADOW_HIVE.asItemStack(),
            ApicuriousBlockRegistration.MODEST_HIVE.asItemStack(),
            ApicuriousBlockRegistration.TROPICAL_HIVE.asItemStack(),
            ApicuriousBlockRegistration.WINTRY_HIVE.asItemStack(),
            ApicuriousBlockRegistration.MARSHY_HIVE.asItemStack(),
            ApicuriousBlockRegistration.ROCKY_HIVE.asItemStack(),
            ApicuriousBlockRegistration.NETHER_HIVE.asItemStack(),
            ApicuriousBlockRegistration.ENDER_HIVE.asItemStack()
    ));
  }

  public static void register(IEventBus bus)
  {
    CREATIVE_MODE_TABS.register(bus);
  }

}
