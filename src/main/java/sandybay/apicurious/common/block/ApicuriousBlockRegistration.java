package sandybay.apicurious.common.block;

import com.google.common.collect.Sets;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.block.blockentity.ApiaryHousingBE;
import sandybay.apicurious.common.block.blockentity.BasicBeeHousingBE;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ApicuriousBlockRegistration {

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Apicurious.MODID);
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Apicurious.MODID);
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Apicurious.MODID);

  public static void register(IEventBus bus) {
    BLOCKS.register(bus);
    ITEMS.register(bus);
    BLOCK_ENTITY_TYPES.register(bus);
  }

  private static final Item.Properties DEFAULT_ITEM_BLOCK_PROPERTIES = new Item.Properties();
  public static final BlockBehaviour.Properties HOUSING_PROPS = BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE);
  public static final BlockBehaviour.Properties HIVE_PROPS = BlockBehaviour.Properties.ofFullCopy(Blocks.BEE_NEST);

  // Hives
  public static final BlockItemHolder<HiveBlock, BlockItem> FOREST_HIVE = registerBlock("forest_hive", () -> new HiveBlock(ApicuriousSpecies.FOREST, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> MEADOW_HIVE = registerBlock("meadow_hive", () -> new HiveBlock(ApicuriousSpecies.MEADOW, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> MODEST_HIVE = registerBlock("modest_hive", () -> new HiveBlock(ApicuriousSpecies.MODEST, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> TROPICAL_HIVE = registerBlock("tropical_hive", () -> new HiveBlock(ApicuriousSpecies.TROPICAL, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> WINTRY_HIVE = registerBlock("wintry_hive", () -> new HiveBlock(ApicuriousSpecies.WINTRY, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> MARSHY_HIVE = registerBlock("marshy_hive", () -> new HiveBlock(ApicuriousSpecies.MARSHY, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> ROCKY_HIVE = registerBlock("rocky_hive", () -> new HiveBlock(ApicuriousSpecies.ROCKY, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> NETHER_HIVE = registerBlock("nether_hive", () -> new HiveBlock(ApicuriousSpecies.NETHER, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));
  public static final BlockItemHolder<HiveBlock, BlockItem> ENDER_HIVE = registerBlock("ender_hive", () -> new HiveBlock(ApicuriousSpecies.ENDER, HIVE_PROPS), (block) -> () -> new BlockItem(block.get(), new Item.Properties()));

  //Bee Housing
  public static BlockHolderWithTile<ApiaryBlock, BlockItem, ApiaryHousingBE> APIARY = registerBlockWithTile(
          "apiary",
          () -> new ApiaryBlock(HOUSING_PROPS),
          ApicuriousBlockRegistration::getDefaultBlockItem,
          ApiaryHousingBE::new,
          ApicuriousBlockRegistration::getDefaultType
  );

  public static BlockHolderWithTile<BeeHousingBlock, BlockItem, BasicBeeHousingBE> BEE_HOUSING = registerBlockWithTile(
          "bee_housing",
          () -> new BeeHousingBlock(HOUSING_PROPS),
          ApicuriousBlockRegistration::getDefaultBlockItem,
          BasicBeeHousingBE::new,
          ApicuriousBlockRegistration::getDefaultType
  );

  public static <BLOCK extends Block, BLOCKITEM extends BlockItem> BlockItemHolder<BLOCK, BLOCKITEM> registerBlock
          (String id, Supplier<BLOCK> block, Function<DeferredHolder<Block, BLOCK>, Supplier<BLOCKITEM>> item) {
    DeferredHolder<Block, BLOCK> b = BLOCKS.register(id, block);
    DeferredHolder<Item, BLOCKITEM> i = ITEMS.register(id, item.apply(b));
    return new BlockItemHolder<>(b, i);
  }

  public static <BLOCK extends Block, BLOCKITEM extends BlockItem, T extends BlockEntity>
  BlockHolderWithTile<BLOCK, BLOCKITEM, T> registerBlockWithTile(String id,
                                                                 Supplier<BLOCK> block,
                                                                 Function<DeferredHolder<Block, BLOCK>, Supplier<BLOCKITEM>> item,
                                                                 BlockEntityType.BlockEntitySupplier<T> factory,
                                                                 BiFunction<BlockEntityType.BlockEntitySupplier<T>, DeferredHolder<Block, BLOCK>, Supplier<BlockEntityType<T>>> type) {
    DeferredHolder<Block, BLOCK> b = BLOCKS.register(id, block);
    DeferredHolder<Item, BLOCKITEM> i = ITEMS.register(id, item.apply(b));
    DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> t = BLOCK_ENTITY_TYPES.register(id, type.apply(factory, b));
    return new BlockHolderWithTile<>(b, i, t);
  }

  private static <BLOCK extends Block> Supplier<BlockItem> getDefaultBlockItem(DeferredHolder<Block, BLOCK> block) {
    return () -> new BlockItem(block.get(), DEFAULT_ITEM_BLOCK_PROPERTIES);
  }

  private static <BLOCK extends Block, T extends BlockEntity, TYPE extends BlockEntityType<T>> Supplier<BlockEntityType<T>> getDefaultType
          (BlockEntityType.BlockEntitySupplier<T> factory, DeferredHolder<Block, BLOCK> block) {
    return () -> new BlockEntityType<>(factory, Sets.newHashSet(block.get()), null);
  }

  public record BlockItemHolder<BLOCK extends Block, ITEM extends BlockItem>(DeferredHolder<Block, BLOCK> block, DeferredHolder<Item, ITEM> item) {
    public ItemStack asItemStack() {
      return new ItemStack(item.get());
    }
  }
  public record BlockHolderWithTile<BLOCK extends Block, ITEM extends BlockItem, TYPE extends BlockEntity>
          (DeferredHolder<Block, BLOCK> block, DeferredHolder<Item, ITEM> item, DeferredHolder<BlockEntityType<?>, BlockEntityType<TYPE>> entityType) {
    public ItemStack asItemStack() {
      return new ItemStack(item.get());
    }
  }
}
