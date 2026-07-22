package sandybay.apicurious.data.defaults;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.loot.function.ApicuriousSpeciesFunction;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.server.ApicuriousRecipes;

import java.util.List;
import java.util.Optional;

public class VillagerTradesDefaults
{
  public static final ResourceKey<VillagerTrade> PROVEN_FRAME_L3 = tradeKey("proven_frame_l3");
  public static final ResourceKey<VillagerTrade> PROVEN_FRAME_L4 = tradeKey("proven_frame_l4");
  public static final ResourceKey<VillagerTrade> PROVEN_FRAME_L5 = tradeKey("proven_frame_l5");
  public static final ResourceKey<VillagerTrade> STEADFAST_DRONE_L3 = tradeKey("steadfast_drone_l3");
  public static final ResourceKey<VillagerTrade> STEADFAST_DRONE_L4 = tradeKey("steadfast_drone_l4");
  public static final ResourceKey<VillagerTrade> STEADFAST_DRONE_L5 = tradeKey("steadfast_drone_l5");

  /**
   * TODO: Replace this with a custom villager and building.
   */
  public static void defaults(BootstrapContext<VillagerTrade> context)
  {
    HolderGetter<IAllele<?>> allelesGetter = context.lookup(ApicuriousRegistries.ALLELES);
    register(context, PROVEN_FRAME_L3, new VillagerTrade(
            new TradeCost(Items.EMERALD, 3),
            Optional.of(new TradeCost(ItemRegistrar.IMPREGNATED_FRAME.asItem(), 1)),
            new ItemStackTemplate(ItemRegistrar.PROVEN_FRAME.asItem(), 1),
            2, 5, 0.2f, Optional.empty(), List.of()
    ));
    register(context, PROVEN_FRAME_L4, new VillagerTrade(
            new TradeCost(Items.EMERALD, 3),
            Optional.of(new TradeCost(ItemRegistrar.IMPREGNATED_FRAME.asItem(), 1)),
            new ItemStackTemplate(ItemRegistrar.PROVEN_FRAME.asItem(), 1),
            4, 5, 0.2f, Optional.empty(), List.of()
    ));
    register(context, PROVEN_FRAME_L5, new VillagerTrade(
            new TradeCost(Items.EMERALD, 3),
            Optional.of(new TradeCost(ItemRegistrar.IMPREGNATED_FRAME.asItem(), 1)),
            new ItemStackTemplate(ItemRegistrar.PROVEN_FRAME.asItem(), 1),
            8, 5, 0.2f, Optional.empty(), List.of()
    ));
    register(context, STEADFAST_DRONE_L3, new VillagerTrade(
            new TradeCost(Items.EMERALD, 8),
            Optional.of(new TradeCost(ItemRegistrar.DRONE.asItem(), 1)),
            new ItemStackTemplate(ItemRegistrar.DRONE.asItem(), 1),
            2, 10, 0.1f,
            Optional.empty(),
            List.of(
                    new ApicuriousSpeciesFunction(ApicuriousSpecies.STEADFAST.species())
            )
    ));
    register(context, STEADFAST_DRONE_L4, new VillagerTrade(
            new TradeCost(Items.EMERALD, 8),
            Optional.of(new TradeCost(ItemRegistrar.DRONE.asItem(), 1)),
            new ItemStackTemplate(ItemRegistrar.DRONE.asItem(), 1),
            4, 10, 0.1f,
            Optional.empty(),
            List.of(
                    new ApicuriousSpeciesFunction(ApicuriousSpecies.STEADFAST.species())
            )
    ));
    register(context, STEADFAST_DRONE_L5, new VillagerTrade(
            new TradeCost(Items.EMERALD, 8),
            Optional.of(new TradeCost(ItemRegistrar.DRONE.asItem(), 1)),
            new ItemStackTemplate(ItemRegistrar.DRONE.asItem(), 1),
            8, 10, 0.1f,
            Optional.empty(),
            List.of(
                    new ApicuriousSpeciesFunction(ApicuriousSpecies.STEADFAST.species())
            )
    ));
  }

  public static Holder.Reference<VillagerTrade> register(
          BootstrapContext<VillagerTrade> context, ResourceKey<VillagerTrade> resourceKey, VillagerTrade villagerTrade
  ) {
    return context.register(resourceKey, villagerTrade);
  }

  public static ResourceKey<VillagerTrade> tradeKey(String key) {
    return ResourceKey.create(Registries.VILLAGER_TRADE, Apicurious.createIdentifier(key));
  }
}
