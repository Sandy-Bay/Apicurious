package sandybay.apicurious.common.registrar;

import net.minecraft.client.Minecraft;
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

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GENERAL_TAB = CREATIVE_MODE_TABS.register("apicurious", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.apicurious.general")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> new ItemStack(ItemRegistrar.SIEVE.get())).displayItems((parameters, output) ->
  {
    output.accept(new ItemStack(ItemRegistrar.ANALYZER.get()));
    output.accept(new ItemStack(ItemRegistrar.SIEVE.get()));
    output.accept(BlockRegistrar.CENTRIFUGE.asItemStack());
    registerHousings(output);
    registerFrames(output);
    registerHives(output);
    registerCombs(output);
    registerProducts(output);
  }).build());

  public static ItemStack bee_tab_icon = ItemStack.EMPTY;
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BEE_TAB = CREATIVE_MODE_TABS.register("apicurious_bee", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.apicurious.bee")).withTabsBefore(CreativeTabRegistrar.GENERAL_TAB.getKey()).icon(() -> bee_tab_icon).displayItems(CreativeTabRegistrar::registerBees).build());

  private static void registerHousings(CreativeModeTab.Output output)
  {
    output.acceptAll(List.of(BlockRegistrar.APIARY.asItemStack(), BlockRegistrar.BEE_HOUSING.asItemStack()));
  }

  public static void registerBees(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output)
  {
    bee_tab_icon = BeeItem.getBeeWithSpecies(parameters.holders(), ApicuriousSpecies.FOREST.species(), ItemRegistrar.QUEEN);
    parameters.holders().lookup(ApicuriousRegistries.ALLELES).ifPresent(registry ->
    {
      registry.listElements().forEach(allele ->
      {
        ResourceKey<IAllele<?>> rl = allele.key();
        if (rl.location().getPath().contains("species/"))
        {
          if (rl.location().getPath().equals("undefined")) {return;}
          if (rl.location().getPath().equals("debug") && !ApicuriousMainConfig.main_config.debug.get()) {return;}
          List<ItemStack> bees = List.of(new ItemStack(ItemRegistrar.QUEEN), new ItemStack(ItemRegistrar.PRINCESS), new ItemStack(ItemRegistrar.DRONE));
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

  private static void registerCombs(CreativeModeTab.Output output)
  {
    ItemRegistrar.COMBS.forEach(comb -> output.accept(comb.get()));
  }

  private static void registerProducts(CreativeModeTab.Output output)
  {
    ItemRegistrar.PRODUCTS.forEach(product -> output.accept(product.get()));
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
