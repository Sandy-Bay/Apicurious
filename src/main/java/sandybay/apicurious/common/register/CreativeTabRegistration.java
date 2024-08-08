package sandybay.apicurious.common.register;

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
import sandybay.apicurious.api.register.DataComponentRegistration;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.item.BaseBeeItem;

import java.util.List;

public class CreativeTabRegistration
{

  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Apicurious.MODID);

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GENERAL_TAB = CREATIVE_MODE_TABS.register("apicurious", () -> CreativeModeTab.builder()
          .title(Component.translatable("itemGroup.apicurious.general"))
          .withTabsBefore(CreativeModeTabs.COMBAT)
          .icon(() -> new ItemStack(ItemRegistration.SIEVE.get()))
          .displayItems((parameters, output) ->
          {
            output.accept(new ItemStack(ItemRegistration.SIEVE.get()));
            output.acceptAll(List.of(
                    BlockRegistration.APIARY.asItemStack(),
                    BlockRegistration.BEE_HOUSING.asItemStack()
            ));
            output.acceptAll(List.of(
                    new ItemStack(ItemRegistration.UNTREATED_FRAME.get()),
                    new ItemStack(ItemRegistration.IMPREGNATED_FRAME.get()),
                    new ItemStack(ItemRegistration.HEALING_FRAME.get()),
                    new ItemStack(ItemRegistration.SOUL_FRAME.get()),
                    new ItemStack(ItemRegistration.RESTRAINT_FRAME.get()),
                    new ItemStack(ItemRegistration.PROVEN_FRAME.get())
            ));
            registerHives(output);
          }).build());

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BEE_TAB = CREATIVE_MODE_TABS.register("apicurious_bee", () -> CreativeModeTab.builder()
          .title(Component.translatable("itemGroup.apicurious.bee"))
          .withTabsBefore(CreativeTabRegistration.GENERAL_TAB.getKey())
          .icon(() -> BaseBeeItem.getBeeWithSpecies(null, ApicuriousSpecies.FOREST, ItemRegistration.QUEEN))
          .displayItems((parameters, output) -> registerBees(output)).build());

  public static void registerBees(CreativeModeTab.Output output)
  {
    ClientPacketListener connection = Minecraft.getInstance().getConnection();
    if (connection != null)
    {
      connection.registryAccess().registry(ApicuriousRegistries.ALLELES).ifPresent(registry ->
      {
        for (ResourceLocation rl : registry.keySet())
        {
          if (rl.getPath().contains("species/"))
          {
            if (rl.getPath().equals("undefined")) continue;
            List<ItemStack> bees = List.of(
                    new ItemStack(ItemRegistration.DRONE),
                    new ItemStack(ItemRegistration.PRINCESS)
            );
            BeeSpecies species = (BeeSpecies) registry.get(rl);
            bees.forEach(stack -> stack.set(DataComponentRegistration.BEE_SPECIES, species));
            output.acceptAll(bees);
          }
        }
      });
    }
  }

  public static void registerHives(CreativeModeTab.Output output)
  {
    output.acceptAll(List.of(
            BlockRegistration.FOREST_HIVE.asItemStack(),
            BlockRegistration.MEADOW_HIVE.asItemStack(),
            BlockRegistration.MODEST_HIVE.asItemStack(),
            BlockRegistration.TROPICAL_HIVE.asItemStack(),
            BlockRegistration.WINTRY_HIVE.asItemStack(),
            BlockRegistration.MARSHY_HIVE.asItemStack(),
            BlockRegistration.ROCKY_HIVE.asItemStack(),
            BlockRegistration.NETHER_HIVE.asItemStack(),
            BlockRegistration.ENDER_HIVE.asItemStack()
    ));
  }

  public static void register(IEventBus bus)
  {
    CREATIVE_MODE_TABS.register(bus);
  }

}
