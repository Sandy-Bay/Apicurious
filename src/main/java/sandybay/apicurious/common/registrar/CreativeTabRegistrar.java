package sandybay.apicurious.common.registrar;

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
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.item.BeeItem;

import java.util.List;

public class CreativeTabRegistrar
{

  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Apicurious.MODID);

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GENERAL_TAB = CREATIVE_MODE_TABS.register("apicurious", () -> CreativeModeTab.builder()
          .title(Component.translatable("itemGroup.apicurious.general"))
          .withTabsBefore(CreativeModeTabs.COMBAT)
          .icon(() -> new ItemStack(ItemRegistrar.SIEVE.get()))
          .displayItems((parameters, output) ->
          {
            output.accept(new ItemStack(ItemRegistrar.SIEVE.get()));
            registerHousings(output);
            registerFrames(output);
            registerHives(output);
            registerCombs(output);
            registerProducts(output);
          }).build());

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BEE_TAB = CREATIVE_MODE_TABS.register("apicurious_bee", () -> CreativeModeTab.builder()
          .title(Component.translatable("itemGroup.apicurious.bee"))
          .withTabsBefore(CreativeTabRegistrar.GENERAL_TAB.getKey())
          .icon(() -> BeeItem.getBeeWithSpecies(Minecraft.getInstance().level, ApicuriousSpecies.FOREST.species(), ItemRegistrar.QUEEN))
          .displayItems((parameters, output) -> registerBees(output)).build());

  private static void registerHousings(CreativeModeTab.Output output)
  {
    output.acceptAll(List.of(
            BlockRegistrar.APIARY.asItemStack(),
            BlockRegistrar.BEE_HOUSING.asItemStack()
    ));
  }

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
                    new ItemStack(ItemRegistrar.QUEEN),
                    new ItemStack(ItemRegistrar.PRINCESS),
                    new ItemStack(ItemRegistrar.DRONE)
            );
            BeeSpecies species = (BeeSpecies) registry.get(rl);
            bees.forEach(stack ->
            {
              stack.set(DataComponentRegistrar.GENOME, species.getSpeciesDefaultGenome(Minecraft.getInstance().level));
              stack.set(DataComponentRegistrar.IDENTIFIED, true);
            });
            output.acceptAll(bees);
          }
        }
      });
    }
  }

  public static void registerHives(CreativeModeTab.Output output)
  {
    output.acceptAll(List.of(
            BlockRegistrar.FOREST_HIVE.asItemStack(),
            BlockRegistrar.MEADOW_HIVE.asItemStack(),
            BlockRegistrar.MODEST_HIVE.asItemStack(),
            BlockRegistrar.TROPICAL_HIVE.asItemStack(),
            BlockRegistrar.WINTRY_HIVE.asItemStack(),
            BlockRegistrar.MARSHY_HIVE.asItemStack(),
            BlockRegistrar.ROCKY_HIVE.asItemStack(),
            BlockRegistrar.NETHER_HIVE.asItemStack(),
            BlockRegistrar.ENDER_HIVE.asItemStack(),
            BlockRegistrar.WATER_HIVE.asItemStack()
    ));
  }

  private static void registerCombs(CreativeModeTab.Output output)
  {
    ItemRegistrar.COMBS.forEach(comb -> output.accept(comb.get()));
  }

  private static void registerProducts(CreativeModeTab.Output output)
  {
    output.acceptAll(List.of(
            new ItemStack(ItemRegistrar.BEESWAX),
            new ItemStack(ItemRegistrar.REFRACTORY_WAX),
            new ItemStack(ItemRegistrar.HONEY_DROP),
            new ItemStack(ItemRegistrar.HONEY_DEW),
            new ItemStack(ItemRegistrar.ROYAL_JELLY),
            new ItemStack(ItemRegistrar.PROPOLIS),
            new ItemStack(ItemRegistrar.SILKEN_PROPOLIS),
            new ItemStack(ItemRegistrar.SILK_WISP),
            new ItemStack(ItemRegistrar.POLLEN),
            new ItemStack(ItemRegistrar.ICE_SHARD)
    ));
  }

  private static void registerFrames(CreativeModeTab.Output output)
  {
    ItemRegistrar.FRAMES.forEach(frame -> output.accept(frame.get()));
  }

  public static void register(IEventBus bus)
  {
    CREATIVE_MODE_TABS.register(bus);
  }

}
