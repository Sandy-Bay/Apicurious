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
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.item.BeeItem;

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
            registerFrames(output);
            registerHives(output);
            registerCombs(output);
            registerProducts(output);
          }).build());

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BEE_TAB = CREATIVE_MODE_TABS.register("apicurious_bee", () -> CreativeModeTab.builder()
          .title(Component.translatable("itemGroup.apicurious.bee"))
          .withTabsBefore(CreativeTabRegistration.GENERAL_TAB.getKey())
          .icon(() -> BeeItem.getBeeWithSpecies(Minecraft.getInstance().level, ApicuriousSpecies.FOREST.species(), ItemRegistration.QUEEN))
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

  private static void registerCombs(CreativeModeTab.Output output)
  {
    output.acceptAll(List.of(
            new ItemStack(ItemRegistration.COCOA_COMB),
            new ItemStack(ItemRegistration.DRIPPING_COMB),
            new ItemStack(ItemRegistration.FROZEN_COMB),
            new ItemStack(ItemRegistration.MELLOW_COMB),
            new ItemStack(ItemRegistration.MOSSY_COMB),
            new ItemStack(ItemRegistration.MYSTERIOUS_COMB),
            new ItemStack(ItemRegistration.PARCHED_COMB),
            new ItemStack(ItemRegistration.POWDERY_COMB),
            new ItemStack(ItemRegistration.SILKY_COMB),
            new ItemStack(ItemRegistration.SIMMERING_COMB),
            new ItemStack(ItemRegistration.STRINGY_COMB),
            new ItemStack(ItemRegistration.WHEATEN_COMB),
            new ItemStack(ItemRegistration.ROCKY_COMB),
            new ItemStack(ItemRegistration.SEEDY_COMB)
    ));
  }

  private static void registerProducts(CreativeModeTab.Output output)
  {
    output.acceptAll(List.of(
            new ItemStack(ItemRegistration.BEESWAX),
            new ItemStack(ItemRegistration.REFRACTORY_WAX),
            new ItemStack(ItemRegistration.HONEY_DROP),
            new ItemStack(ItemRegistration.HONEY_DEW),
            new ItemStack(ItemRegistration.ROYAL_JELLY),
            new ItemStack(ItemRegistration.PROPOLIS),
            new ItemStack(ItemRegistration.SILKEN_PROPOLIS),
            new ItemStack(ItemRegistration.SILK_WISP),
            new ItemStack(ItemRegistration.POLLEN),
            new ItemStack(ItemRegistration.ICE_SHARD)
    ));
  }

  private static void registerFrames(CreativeModeTab.Output output)
  {
    output.acceptAll(List.of(
            new ItemStack(ItemRegistration.UNTREATED_FRAME),
            new ItemStack(ItemRegistration.IMPREGNATED_FRAME),
            new ItemStack(ItemRegistration.HEALING_FRAME),
            new ItemStack(ItemRegistration.SOUL_FRAME),
            new ItemStack(ItemRegistration.RESTRAINT_FRAME),
            new ItemStack(ItemRegistration.PROVEN_FRAME)
    ));
  }

  public static void register(IEventBus bus)
  {
    CREATIVE_MODE_TABS.register(bus);
  }

}
