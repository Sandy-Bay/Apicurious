package sandybay.apicurious.common.event;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.item.BeeItem;
import sandybay.apicurious.common.registrar.ItemRegistrar;

import java.util.List;

public class ApicuriousTrades
{
  public static void init(IEventBus bus)
  {
    bus.addListener(ApicuriousTrades::addMonasticTrades);
  }

  public static void addMonasticTrades(WandererTradesEvent event)
  {
    List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
    List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();
    rareTrades.add(emeraldForItem(event, 3, new ItemStack(ItemRegistrar.PRINCESS.get()), ApicuriousSpecies.MONASTIC.species(), 5, 10, 3));
  }

  private static VillagerTrades.ItemListing emeraldForItem(WandererTradesEvent event, int price, ItemStack stack, ResourceKey<IAllele<?>> species,
                                                           int maxTrades, int xp, int priceMultiplier)
  {
    ItemStack cost = new ItemStack(Items.EMERALD, price);
    Holder<IAllele<?>> spec = event.getRegistryAccess().registryOrThrow(ApicuriousRegistries.ALLELES).getHolderOrThrow(species);
    Genome genome = new Genome();
    genome.getDefaultGenome(spec);
    stack.set(DataComponentRegistrar.GENOME, genome);
    return new BasicItemListing(cost, stack, maxTrades, xp, priceMultiplier);
  }

  private static VillagerTrades.ItemListing itemForEmerald(ItemStack item, int salePrice, int maxTrades, int xp, int priceMultiplier)
  {
    return new BasicItemListing(item, new ItemStack(Items.EMERALD, salePrice), maxTrades, xp, priceMultiplier);
  }

  private static VillagerTrades.ItemListing itemForItem(ItemStack cost, ItemStack ret, int maxTrades, int xp, int priceMultiplier)
  {
    return new BasicItemListing(cost, ret, maxTrades, xp, priceMultiplier);
  }
}
