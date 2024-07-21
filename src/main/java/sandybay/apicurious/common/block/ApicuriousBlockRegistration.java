package sandybay.apicurious.common.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.bee.ApicuriousSpecies;

import java.util.function.Function;
import java.util.function.Supplier;

public class ApicuriousBlockRegistration {

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Apicurious.MODID);
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Apicurious.MODID);

  public static final BlockBehaviour.Properties HIVE_PROPS = BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE);

  public static final BlockItemHolder<HiveBlock, BlockItem> FOREST_HIVE = registerBlock("forest_hive", () -> new HiveBlock(ApicuriousSpecies.FOREST, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> MEADOW_HIVE = registerBlock("meadow_hive", () -> new HiveBlock(ApicuriousSpecies.MEADOW, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> MODEST_HIVE = registerBlock("modest_hive", () -> new HiveBlock(ApicuriousSpecies.MODEST, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> TROPICAL_HIVE = registerBlock("tropical_hive", () -> new HiveBlock(ApicuriousSpecies.TROPICAL, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> WINTRY_HIVE = registerBlock("wintry_hive", () -> new HiveBlock(ApicuriousSpecies.WINTRY, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> MARSHY_HIVE = registerBlock("marshy_hive", () -> new HiveBlock(ApicuriousSpecies.MARSHY, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> ROCKY_HIVE = registerBlock("rocky_hive", () -> new HiveBlock(ApicuriousSpecies.ROCKY, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> NETHER_HIVE = registerBlock("nether_hive", () -> new HiveBlock(ApicuriousSpecies.NETHER, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> ENDER_HIVE = registerBlock("ender_hive", () -> new HiveBlock(ApicuriousSpecies.ENDER, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));

  // TODO: Add Modest, Tropical, Ender, Wintry & Marshy Hives

  public static <BLOCK extends Block, BLOCKITEM extends BlockItem> BlockItemHolder<BLOCK, BLOCKITEM> registerBlock
          (String id, Supplier<BLOCK> block, Function<DeferredHolder<Block, BLOCK>, Supplier<BLOCKITEM>> item) {
    DeferredHolder<Block, BLOCK> b = BLOCKS.register(id, block);
    DeferredHolder<Item, BLOCKITEM> i = ITEMS.register(id, item.apply(b));
    return new BlockItemHolder<>(b, i);
  }

  public static void register(IEventBus bus) {
    BLOCKS.register(bus);
    ITEMS.register(bus);
  }

  public record BlockItemHolder<BLOCK extends Block, ITEM extends BlockItem>(DeferredHolder<Block, BLOCK> block, DeferredHolder<Item, ITEM> item) {}
}
