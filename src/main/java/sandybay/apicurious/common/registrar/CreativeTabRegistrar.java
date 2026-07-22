package sandybay.apicurious.common.registrar;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.config.ApicuriousMainConfig;
import sandybay.apicurious.common.item.BeeItem;

import java.util.List;

public class CreativeTabRegistrar
{

  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Apicurious.MODID);

  // TODO: Replace Healing frame with Sieve once Sieve is fixed
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GENERAL_TAB = CREATIVE_MODE_TABS.register("apicurious", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.apicurious.general")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> new ItemStack(ItemRegistrar.HEALING_FRAME.asItem())).displayItems((parameters, output) ->
  {
    output.accept(new ItemStack(ItemRegistrar.ANALYZER.asItem()));
    output.accept(new ItemStack(ItemRegistrar.SIEVE.asItem()));
    output.accept(BlockRegistrar.CENTRIFUGE.asItemStack());
    registerHousings(output);
    registerHives(output);
    registerProducts(output);
    registerCombs(output);
    registerDrops(output);
    registerPropolis(output);
    registerFrames(output);
  }).build());

  public static ItemStack bee_tab_icon = ItemStack.EMPTY;
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BEE_TAB = CREATIVE_MODE_TABS.register("apicurious_bee", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.apicurious.bee")).withTabsBefore(CreativeTabRegistrar.GENERAL_TAB.getKey()).icon(() -> bee_tab_icon).displayItems(CreativeTabRegistrar::registerBees).build());

  private static void registerHousings(CreativeModeTab.Output output)
  {
    output.acceptAll(List.of(BlockRegistrar.APIARY.asItemStack(), BlockRegistrar.BEE_HOUSING.asItemStack()));
  }

  public static void registerBees(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output)
  {
    bee_tab_icon = BeeItem.getBeeWithSpecies(parameters.holders(), ApicuriousSpecies.FOREST.species(), ItemRegistrar.QUEEN.item());
    parameters.holders().lookup(ApicuriousRegistries.ALLELES).ifPresent(registry ->
    {
      registry.listElements().forEach(allele ->
      {
        ResourceKey<IAllele<?>> rl = allele.key();
        if (rl.identifier().getPath().contains("species/"))
        {
          if (rl.identifier().getPath().equals("undefined"))
          {
            return;
          }
          if (rl.identifier().getPath().equals("debug") && !ApicuriousMainConfig.getDebug())
          {
            return;
          }
          List<ItemStack> bees = List.of(new ItemStack(ItemRegistrar.QUEEN.item()), new ItemStack(ItemRegistrar.PRINCESS.item()), new ItemStack(ItemRegistrar.DRONE.item()));
          BeeSpecies species = (BeeSpecies) allele.value();
          bees.forEach(stack ->
          {
            stack.set(DataComponentRegistrar.GENOME, species.getSpeciesDefaultGenome(parameters.holders()));
            stack.set(DataComponentRegistrar.IDENTIFIED, true);
          });
          output.acceptAll(bees);
        }
      });
    });
  }

  public static void registerHives(CreativeModeTab.Output output)
  {
    BlockRegistrar.HIVES.forEach(holder -> output.accept(holder.asBlock()));
  }

  private static void registerProducts(CreativeModeTab.Output output)
  {
    ItemRegistrar.PRODUCTS_LIST.forEach(product -> output.accept(product.get()));
  }

  private static void registerCombs(CreativeModeTab.Output output)
  {
    ItemRegistrar.COMBS_LIST.forEach(comb -> output.accept(comb.get()));
  }

  private static void registerDrops(CreativeModeTab.Output output)
  {
    ItemRegistrar.DROPS_LIST.forEach(drop -> output.accept(drop.get()));
  }

  private static void registerPropolis(CreativeModeTab.Output output)
  {
    ItemRegistrar.PROPOLIS_LIST.forEach(propolis -> output.accept(propolis.get()));
  }

  private static void registerFrames(CreativeModeTab.Output output)
  {
    ItemRegistrar.FRAMES_LIST.forEach(frame -> output.accept(frame.get()));
  }

  public static void register(IEventBus bus)
  {
    CREATIVE_MODE_TABS.register(bus);
  }

}
