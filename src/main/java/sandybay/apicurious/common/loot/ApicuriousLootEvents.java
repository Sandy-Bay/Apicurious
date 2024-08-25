package sandybay.apicurious.common.loot;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.loot.function.ApicuriousSpeciesFunction;
import sandybay.apicurious.common.registrar.ItemRegistrar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EventBusSubscriber(modid = Apicurious.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ApicuriousLootEvents
{

  /**
   * Injects an entry into a loot pool
   *
   * @param event    Loot table event
   * @param poolName Pool name
   * @param entries  Entry to inject
   */
  private static void injectInto(LootTableLoadEvent event, String poolName, LootPoolEntryContainer... entries)
  {
    LootPool pool = event.getTable().getPool(poolName);
    if (pool != null)
    {
      List<LootPoolEntryContainer> mut = new ArrayList<>(pool.entries);
      mut.addAll(Arrays.asList(entries));
      pool.entries = mut;
    }
  }

  private static LootPoolEntryContainer addBee(Holder<Item> bee, ResourceKey<IAllele<?>> species, int weight)
  {
    return LootItem.lootTableItem(bee.value()).setWeight(weight).apply(ApicuriousSpeciesFunction.getBuilder(species)).build();
  }

  @SubscribeEvent
  public static void onLootTableLoad(LootTableLoadEvent event)
  {
    ResourceLocation name = event.getName();
    if ("minecraft".equals(name.getNamespace()))
    {
      switch (name.getPath())
      {
        case "chests/desert_pyramid", "chests/jungle_temple", "chests/stronghold_corridor", "chests/stronghold_crossing", "chests/stronghold_library", "chests/woodland_mansion":
          injectInto(event, "pool0", addBee(ItemRegistrar.DRONE, ApicuriousSpecies.STEADFAST.species(), 25));
          break;
        case "chests/abandoned_mineshaft", "chests/simple_dungeon":
          injectInto(event, "pool1", addBee(ItemRegistrar.DRONE, ApicuriousSpecies.STEADFAST.species(), 25));
          break;
        case "chests/pillager_outpost":
          injectInto(event, "pool3", addBee(ItemRegistrar.DRONE, ApicuriousSpecies.STEADFAST.species(), 25));
          break;
      }
    }
  }
}
