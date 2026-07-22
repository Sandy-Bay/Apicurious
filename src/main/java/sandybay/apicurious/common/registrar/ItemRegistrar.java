package sandybay.apicurious.common.registrar;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.item.TerritoryModifier;
import sandybay.apicurious.api.recipe.CentrifugeRecipe;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.Coloring;
import sandybay.apicurious.common.item.*;
import sandybay.apicurious.common.item.frame.FrameItem;
import sandybay.apicurious.common.item.frame.RestraintFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/*
  Todo: Needs new textures / models
    - Bees (Drone, Princess, Queen)
      - Needs new baseline textures
    - Sieve
    - Analyzer
      - Item + GUI
    - All Combs
    - All Products
    - All Frames
 */
public class ItemRegistrar
{

  public static final List<DeferredHolder<Item, Item>> PRODUCTS_LIST = new ArrayList<>();
  public static final List<DeferredHolder<Item, CombItem>> COMBS_LIST = new ArrayList<>();
  public static final List<DeferredHolder<Item, DropItem>> DROPS_LIST = new ArrayList<>();
  public static final List<DeferredHolder<Item, PropolisItem>> PROPOLIS_LIST = new ArrayList<>();
  public static final List<DeferredHolder<Item, FrameItem>> FRAMES_LIST = new ArrayList<>();
  public static final List<DeferredHolder<Item, PollenItem>> POLLEN_LIST = new ArrayList<>();

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Apicurious.MODID);
  public static final ItemHolder DRONE = bee("drone", EnumBeeType.DRONE, new Item.Properties(), BeeItem::new);
  public static final ItemHolder PRINCESS = bee("princess", EnumBeeType.PRINCESS, new Item.Properties().stacksTo(1), BeeItem::new);
  public static final ItemHolder QUEEN = bee("queen", EnumBeeType.QUEEN, new Item.Properties().stacksTo(1), BeeItem::new);
  public static final ItemHolder SIEVE = item("sieve", new Item.Properties().stacksTo(1), SieveItem::new);
  public static final ItemHolder ANALYZER = item("analyzer", new Item.Properties().stacksTo(1), BeeAnalyzerItem::new);

  // Products
  // Combs
  public static final CombHolder COCOA_COMB = comb("cocoa", Coloring.fromHex("d16200"), Coloring.fromHex("ffdc16"));
  public static final CombHolder DRIPPING_COMB = comb("dripping", Coloring.fromHex("D39728"), Coloring.fromHex("ffdc16"));
  public static final CombHolder FROZEN_COMB = comb("frozen", Coloring.fromHex("efffff"), Coloring.fromHex("daf5f3"));
  public static final CombHolder MELLOW_COMB = comb("mellow", Coloring.fromHex("7f0000"), Coloring.fromHex("ffdc16"));
  public static final CombHolder MOSSY_COMB = comb("mossy", Coloring.fromHex("698948"), Coloring.fromHex("ffdc16"));
  public static final CombHolder MYSTERIOUS_COMB = comb("mysterious", Coloring.fromHex("ec9a19"), Coloring.fromHex("d9de9e"));
  public static final CombHolder PARCHED_COMB = comb("parched", Coloring.fromHex("c5be86"), Coloring.fromHex("ffdc16"));
  public static final CombHolder POWDERY_COMB = comb("powdery", Coloring.fromHex("999999"), Coloring.fromHex("ffdc16"));
  public static final CombHolder SILKY_COMB = comb("silky", Coloring.fromHex("546626"), Coloring.fromHex("ffdc16"));
  public static final CombHolder SIMMERING_COMB = comb("simmering", Coloring.fromHex("ec9a19"), Coloring.fromHex("9a2323"));
  public static final CombHolder STRINGY_COMB = comb("stringy", Coloring.fromHex("19ec5a"), Coloring.fromHex("ffdc16"));
  public static final CombHolder WHEATEN_COMB = comb("wheaten", Coloring.fromHex("feff8f"), Coloring.fromHex("ffdc16"));
  public static final CombHolder ROCKY_COMB = comb("rocky", Coloring.fromHex("6e757d"), Coloring.fromHex("999999"));
  public static final CombHolder SEEDY_COMB = comb("seedy", Coloring.fromHex("D39728"), Coloring.fromHex("ffdc16"));
  public static final CombHolder DUSTY_COMB = comb("dusty", Coloring.fromHex("7a7648"), Coloring.fromHex("ffdc16"));
  public static final CombHolder DIAMOND_COMB = comb("diamond", Coloring.fromHex("7fbdfa"), Coloring.fromHex("999999"));
  public static final CombHolder EMERALD_COMB = comb("emerald", Coloring.fromHex("1cff03"), Coloring.fromHex("999999"));
  public static final CombHolder COPPER_COMB = comb("copper", Coloring.fromHex("d16308"), Coloring.fromHex("999999"));
  public static final CombHolder IRON_COMB = comb("iron", Coloring.fromHex("a87058"), Coloring.fromHex("999999"));
  public static final CombHolder GOLD_COMB = comb("gold", Coloring.fromHex("e6cc0b"), Coloring.fromHex("999999"));
  public static final CombHolder LAPIS_COMB = comb("lapis", Coloring.fromHex("3d2cdb"), Coloring.fromHex("999999"));
  public static final CombHolder DAMP_COMB = comb("damp", Coloring.fromHex("356933"), Coloring.fromHex("ffdc16"));
  public static final CombHolder ENERGETIC_COMB = comb("energetic", Coloring.fromHex("e835c7"), Coloring.fromHex("ffdc16"));
  public static final CombHolder STATIC_COMB = comb("static", Coloring.fromHex("af35e8"), Coloring.fromHex("ffdc16"));
  public static final CombHolder RED_TINTED_COMB = comb("red_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("ff0000"));
  public static final CombHolder YELLOW_TINTED_COMB = comb("yellow_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("ffdd00"));
  public static final CombHolder BLUE_TINTED_COMB = comb("blue_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("0022ff"));
  public static final CombHolder GREEN_TINTED_COMB = comb("green_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("009900"));
  public static final CombHolder BROWN_TINTED_COMB = comb("brown_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("5c350f"));
  public static final CombHolder WHITE_TINTED_COMB = comb("white_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("ffffff"));
  public static final CombHolder BLACK_TINTED_COMB = comb("black_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("575757"));
  public static final CombHolder ORANGE_TINTED_COMB = comb("orange_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("ff9d00"));
  public static final CombHolder CYAN_TINTED_COMB = comb("cyan_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("00ffe5"));
  public static final CombHolder PURPLE_TINTED_COMB = comb("purple_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("ae00ff"));
  public static final CombHolder GRAY_TINTED_COMB = comb("gray_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("bababa"));
  public static final CombHolder LIGHT_BLUE_TINTED_COMB = comb("light_blue_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("009dff"));
  public static final CombHolder PINK_TINTED_COMB = comb("pink_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("ff80df"));
  public static final CombHolder LIME_TINTED_COMB = comb("lime_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("00ff08"));
  public static final CombHolder LIGHT_GRAY_TINTED_COMB = comb("light_gray_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("c9c9c9"));
  public static final CombHolder MAGENTA_TINTED_COMB = comb("magenta_tinted", Coloring.fromHex("8cff00"), Coloring.fromHex("ff00cc"));
  public static final CombHolder UNSTABLE_COMB = comb("unstable", Coloring.fromHex("388020"), Coloring.fromHex("999999"));
  public static final CombHolder CLAY_COMB = comb("clay", Coloring.fromHex("999999"), Coloring.fromHex("83b3d4"));

  public static final CombHolder BARREN_COMB = comb("barren", Coloring.fromHex("ec9a19"), Coloring.fromHex("cbe374"));
  public static final CombHolder DECOMPOSED_COMB = comb("decomposed", Coloring.fromHex("523711"), Coloring.fromHex("ffffff"));
  public static final CombHolder ANCIENT_COMB = comb("ancient", Coloring.fromHex("f2db8f"), Coloring.fromHex("cbe374"));
  public static final CombHolder FOSSILISED_COMB = comb("fossilised", Coloring.fromHex("ec9a19"), Coloring.fromHex("cbe374"));
  public static final CombHolder GLACIAL_COMB = comb("glacial", Coloring.fromHex("7be3e3"), Coloring.fromHex("daf5f3"));
  public static final CombHolder FUNGAL_COMB = comb("fungal", Coloring.fromHex("ec9a19"), Coloring.fromHex("ffdc16"));
  public static final CombHolder BLAZING_COMB = comb("blazing", Coloring.fromHex("b06c28"), Coloring.fromHex("9a2323"));
  public static final CombHolder GLOWING_COMB = comb("glowing", Coloring.fromHex("ffd46c"), Coloring.fromHex("9a2323"));
  public static final CombHolder VENOMOUS_COMB = comb("venomous", Coloring.fromHex("f013ec"), Coloring.fromHex("069764"));
  public static final CombHolder BRIMSTONE_COMB = comb("brimstone", Coloring.fromHex("ec9a19"), Coloring.fromHex("9a2323"));
  public static final CombHolder MUCOUS_COMB = comb("mucous", Coloring.fromHex("17e328"), Coloring.fromHex("069764"));

  // Misc
  public static final ItemHolder BEESWAX = product("beeswax");
  public static final ItemHolder REFRACTORY_WAX = product("refractory_wax");
  public static final DropHolder HONEY_DROP = drop("honey_drop", Coloring.fromHex("ecb42d"), Coloring.fromHex("e8c814"));
  public static final ItemHolder HONEY_DEW = product("honey_dew");
  public static final ItemHolder ROYAL_JELLY = product("royal_jelly");
  public static final PropolisHolder PROPOLIS = propolis("propolis", Coloring.fromHex("c5b24e"));
  public static final PropolisHolder SILKEN_PROPOLIS = propolis("silken_propolis", Coloring.fromHex("ddff00"));
  public static final ItemHolder SILK_WISP = product("silk_wisp");
  public static final PollenHolder POLLEN = pollen("pollen", Coloring.fromHex("a28a25"), Coloring.fromHex("a28a25"));
  public static final ItemHolder ICE_SHARD = product("ice_shard");
  public static final ItemHolder ASH = product("ash");
  public static final ItemHolder PEAT = product("peat");
  public static final ItemHolder PHOSPHOR = product("phosphor");
  public static final ItemHolder COPPER_NUGGET = product("copper_nugget");
  public static final ItemHolder DIAMOND_NUGGET = product("diamond_nugget");
  public static final ItemHolder EMERALD_NUGGET = product("emerald_nugget");
  public static final PropolisHolder WATERY_PROPOLIS = propolis("watery_propolis", Coloring.fromHex("24B3C9"));
  public static final DropHolder RED_TINTED_DROP = drop("red_tinted_drop", Coloring.fromHex("FF0000"), Coloring.fromHex("CC4C4C"));
  public static final DropHolder YELLOW_TINTED_DROP = drop("yellow_tinted_drop", Coloring.fromHex("FFDD00"), Coloring.fromHex("E5E533"));
  public static final DropHolder BLUE_TINTED_DROP = drop("blue_tinted_drop", Coloring.fromHex("0022FF"), Coloring.fromHex("99B2F2"));
  public static final DropHolder GREEN_TINTED_DROP = drop("green_tinted_drop", Coloring.fromHex("009900"), Coloring.fromHex("667F33"));
  public static final DropHolder BROWN_TINTED_DROP = drop("brown_tinted_drop", Coloring.fromHex("5C350F"), Coloring.fromHex("7F664C"));
  public static final DropHolder WHITE_TINTED_DROP = drop("white_tinted_drop", Coloring.fromHex("FFFFFF"), Coloring.fromHex("D6D6D6"));
  public static final DropHolder BLACK_TINTED_DROP = drop("black_tinted_drop", Coloring.fromHex("191919"), Coloring.fromHex("575757"));
  public static final DropHolder ORANGE_TINTED_DROP = drop("orange_tinted_drop", Coloring.fromHex("FF9D00"), Coloring.fromHex("F2B233"));
  public static final DropHolder CYAN_TINTED_DROP = drop("cyan_tinted_drop", Coloring.fromHex("4C99B2"), Coloring.fromHex("00FFE5"));
  public static final DropHolder PURPLE_TINTED_DROP = drop("purple_tinted_drop", Coloring.fromHex("AE00FF"), Coloring.fromHex("B266E5"));
  public static final DropHolder GRAY_TINTED_DROP = drop("gray_tinted_drop", Coloring.fromHex("4C4C4C"), Coloring.fromHex("BABABA"));
  public static final DropHolder LIGHT_BLUE_TINTED_DROP = drop("light_blue_tinted_drop", Coloring.fromHex("009DFF"), Coloring.fromHex("99B2F2"));
  public static final DropHolder PINK_TINTED_DROP = drop("pink_tinted_drop", Coloring.fromHex("FF80DF"), Coloring.fromHex("F2B2CC"));
  public static final DropHolder LIME_TINTED_DROP = drop("lime_tinted_drop", Coloring.fromHex("00FF08"), Coloring.fromHex("7FCC19"));
  public static final DropHolder LIGHT_GRAY_TINTED_DROP = drop("light_gray_tinted_drop", Coloring.fromHex("999999"), Coloring.fromHex("C9C9C9"));
  public static final DropHolder MAGENTA_TINTED_DROP = drop("magenta_tinted_drop", Coloring.fromHex("FF00CC"), Coloring.fromHex("E57FD8"));
  public static final ItemHolder SALTPETER = product("saltpeter");
  public static final DropHolder ACIDIC_DROP = drop("acidic_drop", Coloring.fromHex("49DE3C"), Coloring.fromHex("4BB541"));
  public static final ItemHolder SULFUR = product("sulfur");

  // Frames
  public static final FrameHolder UNTREATED_FRAME = frame("untreated", 80, 1.0f, 0.897f, 1.0f, 1.0f);
  public static final ItemHolder  IMPREGNATED_STICK = item("impregnated_stick");
  public static final FrameHolder IMPREGNATED_FRAME = frame("impregnated", 240, 1.0f, 0.818f, 1.0f, 1.0f);
  public static final FrameHolder HEALING_FRAME = frame("healing", 240, 1.5f, 1.25f, 0.5f, 1.0f);
  public static final FrameHolder SOUL_FRAME = frame("soul", 240, 0.75f, 1.75f, 1.5f, 1.0f);
  public static final FrameHolder RESTRAINT_FRAME = frame("restraint", new Item.Properties().stacksTo(1).durability(240), RestraintFrame::new);
  public static final FrameHolder PROVEN_FRAME = frame("proven", 720, 1.0f, 0.714f, 1.0f, 1.0f);
  public static final FrameHolder ROYAL_FRAME = frame("royal", 120, 2.0f, 500.0f, 0.0f, 2.5f);
  public static final FrameHolder CREATIVE_FRAME = frame("creative", Integer.MAX_VALUE, 0.00001f, 1.0f, 1.0f, 1.0f);

  public static void register(IEventBus bus)
  {
    ITEMS.register(bus);
  }

  public static ItemHolder bee(String name, EnumBeeType beeType, Item.Properties props,
                               BiFunction<Item.Properties, EnumBeeType, BeeItem> func)
  {
    ResourceKey<Item> itemKey = create(name);
    DeferredHolder<Item, Item> bee = ITEMS.register(name, () -> func.apply(props.setId(itemKey), beeType));
    return new ItemHolder(itemKey, bee);
  }

  public static ItemHolder product(String name)
  {
    return product(name, new Item.Properties());
  }

  public static ItemHolder product(String name, Item.Properties properties)
  {
    ResourceKey<Item> itemKey = create(name);
    DeferredHolder<Item, Item> product = ITEMS.register(name, () -> new Item(properties.setId(itemKey)));
    PRODUCTS_LIST.add(product);
    return new ItemHolder(itemKey, product);
  }

  public static DropHolder drop(String name, Coloring dropTint, Coloring dropHighlight)
  {
    return drop(name, dropTint, dropHighlight, new Item.Properties());
  }

  public static DropHolder drop(String name, Coloring dropTint, Coloring dropHighlight, Item.Properties properties)
  {
    ResourceKey<Item> itemKey = create(name);
    DeferredHolder<Item, DropItem> product = ITEMS.register(name, () -> new DropItem(properties.setId(itemKey), dropTint, dropHighlight));
    DROPS_LIST.add(product);
    return new DropHolder(itemKey, product);
  }

  public static PropolisHolder propolis(String name, Coloring propolisTint)
  {
    return propolis(name, propolisTint, new Item.Properties());
  }

  public static PropolisHolder propolis(String name, Coloring propolisTint, Item.Properties properties)
  {
    ResourceKey<Item> itemKey = create(name);
    DeferredHolder<Item, PropolisItem> product = ITEMS.register(name, () -> new PropolisItem(properties.setId(itemKey), propolisTint));
    PROPOLIS_LIST.add(product);
    return new PropolisHolder(itemKey, product);
  }

  public static PollenHolder pollen(String name, Coloring pollenTint, Coloring pollenHighlight)
  {
    return pollen(name, pollenTint, pollenHighlight, new Item.Properties());
  }

  public static PollenHolder pollen(String name, Coloring pollenTint, Coloring pollenHighlight,
                                    Item.Properties properties)
  {
    ResourceKey<Item> itemKey = create(name);
    DeferredHolder<Item, PollenItem> product = ITEMS.register(name, () -> new PollenItem(properties.setId(itemKey), pollenTint, pollenHighlight));
    POLLEN_LIST.add(product);
    return new PollenHolder(itemKey, product);
  }

  public static String frame(String name)
  {
    return name + "_frame";
  }

  public static FrameHolder frame(String name, Item.Properties props, Function<Item.Properties, FrameItem> func)
  {
    ResourceKey<Item> frameKey = create(name + "_frame");
    DeferredHolder<Item, FrameItem> frame = ITEMS.register(name + "_frame", () -> func.apply(props.setId(frameKey)));
    FRAMES_LIST.add(frame);
    return new FrameHolder(frameKey, frame);
  }

  public static FrameHolder frame(String name, int durability, float lifespanModifier, float productionModifier,
                                  float mutationModifier, float additionalPrincessModifier)
  {
    ResourceKey<Item> itemKey = create(name + "_frame");
    DeferredHolder<Item, FrameItem> frame = ITEMS.register(name + "_frame", () -> new FrameItem(new Item.Properties().stacksTo(1).setId(itemKey).durability(durability), lifespanModifier, productionModifier, mutationModifier, additionalPrincessModifier, new TerritoryModifier(xz -> xz, y -> y)));
    FRAMES_LIST.add(frame);
    return new FrameHolder(itemKey, frame);
  }

  public static CombHolder comb(String type, Coloring outline, Coloring cells)
  {
    ResourceKey<Item> itemKey = create(type + "_comb");
    DeferredHolder<Item, CombItem> comb = ITEMS.register(type + "_comb", () -> new CombItem(new Item.Properties().setId(itemKey), outline, cells));
    COMBS_LIST.add(comb);
    return new CombHolder(itemKey, comb, ResourceKey.create(ApicuriousRegistries.CENTRIFUGE_RECIPES, Apicurious.createIdentifier(type)));
  }

  public static ItemHolder item(String name)
  {
    return item(name, new Item.Properties());
  }

  public static ItemHolder item(String name, Item.Properties properties)
  {
    return item(name, properties, Item::new);
  }

  public static ItemHolder item(String name, Item.Properties properties, Function<Item.Properties, Item> func)
  {
    ResourceKey<Item> itemKey = create(name);
    DeferredHolder<Item, Item> item = ITEMS.register(name, () -> func.apply(properties.setId(itemKey)));
    return new ItemHolder(itemKey, item);
  }

  public static ResourceKey<Item> create(String id)
  {
    return ResourceKey.create(Registries.ITEM, Apicurious.createIdentifier(id));
  }

  public record ItemHolder(ResourceKey<Item> itemKey, DeferredHolder<Item, Item> item)
  {
    public Item asItem() {
      return item.get();
    }
  }

  public record CombHolder(ResourceKey<Item> itemKey, DeferredHolder<Item, CombItem> comb,
                           ResourceKey<CentrifugeRecipe> recipe)
  {
    public Item asItem() {
      return comb.get();
    }
  }

  public record DropHolder(ResourceKey<Item> itemKey, DeferredHolder<Item, DropItem> drop)
  {
    public Item asItem() {
      return drop.get();
    }
  }

  public record FrameHolder(ResourceKey<Item> itemKey, DeferredHolder<Item, FrameItem> frame)
  {
    public Item asItem() {
      return frame.get();
    }
  }

  public record PollenHolder(ResourceKey<Item> itemKey, DeferredHolder<Item, PollenItem> pollen)
  {
    public Item asItem() {
      return pollen.get();
    }
  }

  public record PropolisHolder(ResourceKey<Item> itemKey, DeferredHolder<Item, PropolisItem> propolis)
  {
    public Item asItem() {
      return propolis.get();
    }
  }
}
