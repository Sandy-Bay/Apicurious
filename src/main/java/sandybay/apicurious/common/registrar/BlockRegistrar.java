package sandybay.apicurious.common.registrar;

import com.google.common.collect.Sets;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.block.HiveBlock;
import sandybay.apicurious.common.block.centrifuge.CentrifugeBlock;
import sandybay.apicurious.common.block.centrifuge.blockentity.CentrifugeBE;
import sandybay.apicurious.common.block.housing.ApiaryBlock;
import sandybay.apicurious.common.block.housing.BeeHousingBlock;
import sandybay.apicurious.common.block.housing.blockentity.ApiaryHousingBE;
import sandybay.apicurious.common.block.housing.blockentity.BeeHousingBE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockRegistrar
{
  public static final List<BlockItemHolder<? extends HiveBlock, ? extends BlockItem>> HIVES = new ArrayList<>();

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Apicurious.MODID);
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Apicurious.MODID);
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Apicurious.MODID);
  public static final BlockBehaviour.Properties HOUSING_PROPS = BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE);
  public static final BlockBehaviour.Properties HIVE_PROPS = BlockBehaviour.Properties.ofFullCopy(Blocks.BEE_NEST);
  // Hives
  public static final BlockItemHolder<HiveBlock, BlockItem> FOREST_HIVE = registerHive("forest_hive", ApicuriousSpecies.FOREST.species(), (props, block) -> new BlockItem(block.get(), props));
  public static final BlockItemHolder<HiveBlock, BlockItem> MEADOW_HIVE = registerHive("meadow_hive", ApicuriousSpecies.MEADOW.species(), (props, block) -> new BlockItem(block.get(), props));
  public static final BlockItemHolder<HiveBlock, BlockItem> MODEST_HIVE = registerHive("modest_hive", ApicuriousSpecies.MODEST.species(), (props, block) -> new BlockItem(block.get(), props));
  public static final BlockItemHolder<HiveBlock, BlockItem> TROPICAL_HIVE = registerHive("tropical_hive", ApicuriousSpecies.TROPICAL.species(), (props, block) -> new BlockItem(block.get(), props));
  public static final BlockItemHolder<HiveBlock, BlockItem> WINTRY_HIVE = registerHive("wintry_hive", ApicuriousSpecies.WINTRY.species(), (props, block) -> new BlockItem(block.get(), props));
  public static final BlockItemHolder<HiveBlock, BlockItem> MARSHY_HIVE = registerHive("marshy_hive", ApicuriousSpecies.MARSHY.species(), (props, block) -> new BlockItem(block.get(), props));
  public static final BlockItemHolder<HiveBlock, BlockItem> ROCKY_HIVE = registerHive("rocky_hive", ApicuriousSpecies.ROCKY.species(), (props, block) -> new BlockItem(block.get(), props));
  public static final BlockItemHolder<HiveBlock, BlockItem> NETHER_HIVE = registerHive("nether_hive", ApicuriousSpecies.NETHER.species(), (props, block) -> new BlockItem(block.get(), props));
  public static final BlockItemHolder<HiveBlock, BlockItem> ENDER_HIVE = registerHive("ender_hive", ApicuriousSpecies.ENDER.species(), (props, block) -> new BlockItem(block.get(), props));
  public static final BlockItemHolder<HiveBlock, BlockItem> WATER_HIVE = registerHive("water_hive", ApicuriousSpecies.WATER.species(), (props, block) -> new BlockItem(block.get(), props));
  private static final Item.Properties DEFAULT_ITEM_BLOCK_PROPERTIES = new Item.Properties();

  public static void register(IEventBus bus)
  {
    BLOCKS.register(bus);
    ITEMS.register(bus);
    BLOCK_ENTITY_TYPES.register(bus);
  }
  public static BlockHolderWithTile<ApiaryBlock, BlockItem, ApiaryHousingBE> APIARY = registerBlockWithTile("apiary", HOUSING_PROPS, ApiaryBlock::new, BlockRegistrar::getDefaultBlockItem, ApiaryHousingBE::new, BlockRegistrar::getDefaultType);

  public static <BLOCK extends Block, BLOCKITEM extends BlockItem> BlockItemHolder<BLOCK, BLOCKITEM> registerBlock(String id, Supplier<BLOCK> block, Function<DeferredHolder<Block, BLOCK>, Supplier<BLOCKITEM>> item)
  {
    BlockItemId bii = createId(id);
    DeferredHolder<Block, BLOCK> b = BLOCKS.register(id, block);
    DeferredHolder<Item, BLOCKITEM> i = ITEMS.register(id, item.apply(b));
    return new BlockItemHolder<>(b, i, bii);
  }  public static BlockHolderWithTile<BeeHousingBlock, BlockItem, BeeHousingBE> BEE_HOUSING = registerBlockWithTile("bee_housing", HOUSING_PROPS, BeeHousingBlock::new, BlockRegistrar::getDefaultBlockItem, BeeHousingBE::new, BlockRegistrar::getDefaultType);

  public static BlockItemHolder<HiveBlock, BlockItem> registerHive(String id, ResourceKey<IAllele<?>> speciesKey, BiFunction<Item.Properties, DeferredHolder<Block, HiveBlock>, BlockItem> item)
  {
    BlockItemId bii = createId(id);
    DeferredHolder<Block, HiveBlock> b = BLOCKS.register(id, () -> new HiveBlock(speciesKey, HIVE_PROPS.setId(bii.block())));
    DeferredHolder<Item, BlockItem> i = ITEMS.register(id, () -> item.apply(DEFAULT_ITEM_BLOCK_PROPERTIES.setId(bii.item()), b));
    BlockItemHolder<HiveBlock, BlockItem> holder = new BlockItemHolder<>(b, i, bii);
    HIVES.add(holder);
    return holder;
  }
  public static BlockHolderWithTile<CentrifugeBlock, BlockItem, CentrifugeBE> CENTRIFUGE = registerBlockWithTile("centrifuge", HOUSING_PROPS, CentrifugeBlock::new, BlockRegistrar::getDefaultBlockItem, CentrifugeBE::new, BlockRegistrar::getDefaultType);

  public static <BLOCK extends Block, BLOCKITEM extends BlockItem, T extends BlockEntity> BlockHolderWithTile<BLOCK, BLOCKITEM, T> registerBlockWithTile(String id, BlockBehaviour.Properties props, Function<BlockBehaviour.Properties, BLOCK> block, BiFunction<Item.Properties, DeferredHolder<Block, BLOCK>, Supplier<BLOCKITEM>> item, BlockEntityType.BlockEntitySupplier<T> factory, BiFunction<BlockEntityType.BlockEntitySupplier<T>, DeferredHolder<Block, BLOCK>, Supplier<BlockEntityType<T>>> type)
  {
    BlockItemId bii = createId(id);
    DeferredHolder<Block, BLOCK> b = BLOCKS.register(id, () -> block.apply(props.setId(bii.block())));
    DeferredHolder<Item, BLOCKITEM> i = ITEMS.register(id, item.apply(DEFAULT_ITEM_BLOCK_PROPERTIES.setId(bii.item()), b));
    DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> t = BLOCK_ENTITY_TYPES.register(id, type.apply(factory, b));
    return new BlockHolderWithTile<>(b, i, t, bii);
  }

  private static <BLOCK extends Block> Supplier<BlockItem> getDefaultBlockItem(Item.Properties props, DeferredHolder<Block, BLOCK> block)
  {
    return () -> new BlockItem(block.get(), props);
  }

  private static <BLOCK extends Block, T extends BlockEntity, TYPE extends BlockEntityType<T>> Supplier<BlockEntityType<T>> getDefaultType(BlockEntityType.BlockEntitySupplier<T> factory, DeferredHolder<Block, BLOCK> block)
  {
    return () -> new BlockEntityType<>(factory, Sets.newHashSet(block.get()));
  }

  public record BlockItemHolder<BLOCK extends Block, ITEM extends BlockItem>(DeferredHolder<Block, BLOCK> block,
                                                                             DeferredHolder<Item, ITEM> item, BlockItemId id)
  {

    public BLOCK asBlock()
    {
      return block.get();
    }

    public ITEM asItem()
    {
      return item.get();
    }

    public ItemStack asItemStack()
    {
      return new ItemStack(item.get());
    }

  }

  public record BlockHolderWithTile<BLOCK extends Block, ITEM extends BlockItem, TYPE extends BlockEntity>
          (DeferredHolder<Block, BLOCK> block, DeferredHolder<Item, ITEM> item,
           DeferredHolder<BlockEntityType<?>, BlockEntityType<TYPE>> entityType, BlockItemId id)
  {
    public BLOCK asBlock()
    {
      return block.get();
    }

    public ITEM asItem()
    {
      return item.get();
    }

    public ItemStack asItemStack()
    {
      return new ItemStack(item.get());
    }

    public BlockEntityType<TYPE> getType()
    {
      return entityType.get();
    }
  }

  public static BlockItemId createId(String name) {
    Identifier id = Apicurious.createIdentifier(name);
    return createId(id, id);
  }

  public static BlockItemId createId(String blockName, String itemName) {
    return createId(Apicurious.createIdentifier(blockName), Apicurious.createIdentifier(itemName));
  }

  public static BlockItemId createId(Identifier blockId, Identifier itemId) {
    return new BlockItemId(ResourceKey.create(Registries.BLOCK, blockId), ResourceKey.create(Registries.ITEM, itemId));
  }
}
