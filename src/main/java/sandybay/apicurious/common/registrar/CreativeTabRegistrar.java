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
            output.acceptAll(List.of(
                    BlockRegistrar.APIARY.asItemStack(),
                    BlockRegistrar.BEE_HOUSING.asItemStack()
            ));
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
                    new ItemStack(ItemRegistrar.DRONE),
                    new ItemStack(ItemRegistrar.PRINCESS)
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
            BlockRegistrar.ENDER_HIVE.asItemStack()
    ));
  }

  private static void registerCombs(CreativeModeTab.Output output)
  {
    output.acceptAll(List.of(
            new ItemStack(ItemRegistrar.COCOA_COMB),
            new ItemStack(ItemRegistrar.DRIPPING_COMB),
            new ItemStack(ItemRegistrar.FROZEN_COMB),
            new ItemStack(ItemRegistrar.MELLOW_COMB),
            new ItemStack(ItemRegistrar.MOSSY_COMB),
            new ItemStack(ItemRegistrar.MYSTERIOUS_COMB),
            new ItemStack(ItemRegistrar.PARCHED_COMB),
            new ItemStack(ItemRegistrar.POWDERY_COMB),
            new ItemStack(ItemRegistrar.SILKY_COMB),
            new ItemStack(ItemRegistrar.SIMMERING_COMB),
            new ItemStack(ItemRegistrar.STRINGY_COMB),
            new ItemStack(ItemRegistrar.WHEATEN_COMB),
            new ItemStack(ItemRegistrar.ROCKY_COMB),
            new ItemStack(ItemRegistrar.SEEDY_COMB)
    ));
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
    output.acceptAll(List.of(
            new ItemStack(ItemRegistrar.UNTREATED_FRAME),
            new ItemStack(ItemRegistrar.IMPREGNATED_FRAME),
            new ItemStack(ItemRegistrar.HEALING_FRAME),
            new ItemStack(ItemRegistrar.SOUL_FRAME),
            new ItemStack(ItemRegistrar.RESTRAINT_FRAME),
            new ItemStack(ItemRegistrar.PROVEN_FRAME)
    ));
  }

  public static void register(IEventBus bus)
  {
    CREATIVE_MODE_TABS.register(bus);
  }

}
