package sandybay.apicurious.common.registrar;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.item.TerritoryModifier;
import sandybay.apicurious.api.recipe.CentrifugeRecipe;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.item.BeeAnalyzerItem;
import sandybay.apicurious.common.item.BeeItem;
import sandybay.apicurious.common.item.SieveItem;
import sandybay.apicurious.common.item.frame.FrameItem;
import sandybay.apicurious.common.item.frame.RestraintFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/*
  TODO: Needs new textures / models
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
  public static final List<DeferredHolder<Item, Item>> COMBS = new ArrayList<>();
  public static final List<DeferredHolder<Item, Item>> PRODUCTS = new ArrayList<>();
  public static final List<DeferredHolder<Item, FrameItem>> FRAMES = new ArrayList<>();

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Apicurious.MODID);
  public static final DeferredHolder<Item, BeeItem> DRONE = ITEMS.register("drone", () -> new BeeItem(new Item.Properties(), EnumBeeType.DRONE));
  public static final DeferredHolder<Item, BeeItem> PRINCESS = ITEMS.register("princess", () -> new BeeItem(SINGLETON_PROPS(), EnumBeeType.PRINCESS));
  public static final DeferredHolder<Item, BeeItem> QUEEN = ITEMS.register("queen", () -> new BeeItem(SINGLETON_PROPS(), EnumBeeType.QUEEN));
  public static final DeferredHolder<Item, SieveItem> SIEVE = ITEMS.register("sieve", () -> new SieveItem(Tiers.WOOD, new Item.Properties().durability(32)));
  public static final DeferredHolder<Item, BeeAnalyzerItem> ANALYZER = ITEMS.register("analyzer", () -> new BeeAnalyzerItem(SINGLETON_PROPS()));

  // Products
  // Combs
  public static final CombHolder COCOA_COMB = comb("cocoa");
  public static final CombHolder DRIPPING_COMB = comb("dripping");
  public static final CombHolder FROZEN_COMB = comb("frozen");
  public static final CombHolder MELLOW_COMB = comb("mellow");
  public static final CombHolder MOSSY_COMB = comb("mossy");
  public static final CombHolder MYSTERIOUS_COMB = comb("mysterious");
  public static final CombHolder PARCHED_COMB = comb("parched");
  public static final CombHolder POWDERY_COMB = comb("powdery");
  public static final CombHolder SILKY_COMB = comb("silky");
  public static final CombHolder SIMMERING_COMB = comb("simmering");
  public static final CombHolder STRINGY_COMB = comb("stringy");
  public static final CombHolder WHEATEN_COMB = comb("wheaten");
  public static final CombHolder ROCKY_COMB = comb("rocky");
  public static final CombHolder SEEDY_COMB = comb("seedy");
  public static final CombHolder DUSTY_COMB = comb("dusty");
  public static final CombHolder DIAMOND_COMB = comb("diamond");
  public static final CombHolder EMERALD_COMB = comb("emerald");
  public static final CombHolder COPPER_COMB = comb("copper");
  public static final CombHolder IRON_COMB = comb("iron");
  public static final CombHolder GOLD_COMB = comb("gold");
  public static final CombHolder LAPIS_COMB = comb("lapis");
  public static final CombHolder DAMP_COMB = comb("damp");
  public static final CombHolder ENERGETIC_COMB = comb("energetic");
  public static final CombHolder STATIC_COMB = comb("static");
  public static final CombHolder RED_TINTED_COMB = comb("red_tinted");
  public static final CombHolder YELLOW_TINTED_COMB = comb("yellow_tinted");
  public static final CombHolder BLUE_TINTED_COMB = comb("blue_tinted");
  public static final CombHolder GREEN_TINTED_COMB = comb("green_tinted");
  public static final CombHolder BROWN_TINTED_COMB = comb("brown_tinted");
  public static final CombHolder WHITE_TINTED_COMB = comb("white_tinted");
  public static final CombHolder BLACK_TINTED_COMB = comb("black_tinted");
  public static final CombHolder ORANGE_TINTED_COMB = comb("orange_tinted");
  public static final CombHolder CYAN_TINTED_COMB = comb("cyan_tinted");
  public static final CombHolder PURPLE_TINTED_COMB = comb("purple_tinted");
  public static final CombHolder GRAY_TINTED_COMB = comb("gray_tinted");
  public static final CombHolder LIGHT_BLUE_TINTED_COMB = comb("light_blue_tinted");
  public static final CombHolder PINK_TINTED_COMB = comb("pink_tinted");
  public static final CombHolder LIME_TINTED_COMB = comb("lime_tinted");
  public static final CombHolder LIGHT_GRAY_TINTED_COMB = comb("light_gray_tinted");
  public static final CombHolder MAGENTA_TINTED_COMB = comb("magenta_tinted");
  public static final CombHolder UNSTABLE_COMB = comb("unstable");
  public static final CombHolder CLAY_COMB = comb("clay");
  public static final CombHolder BARREN_COMB = comb("barren");
  public static final CombHolder DECOMPOSED_COMB = comb("decomposed");
  public static final CombHolder ANCIENT_COMB = comb("ancient");
  public static final CombHolder FOSSILISED_COMB = comb("fossilised");
  public static final CombHolder GLACIAL_COMB = comb("glacial");
  public static final CombHolder FUNGAL_COMB = comb("fungal");
  public static final CombHolder BLAZING_COMB = comb("blazing");
  public static final CombHolder GLOWING_COMB = comb("glowing");
  public static final CombHolder VENOMOUS_COMB = comb("venomous");
  public static final CombHolder BRIMSTONE_COMB = comb("brimstone");
  public static final CombHolder MUCOUS_COMB = comb("mucous");

  // Misc
  public static final DeferredHolder<Item, Item> BEESWAX = product("beeswax");
  public static final DeferredHolder<Item, Item> REFRACTORY_WAX = product("refractory_wax");
  public static final DeferredHolder<Item, Item> HONEY_DROP = product("honey_drop");
  public static final DeferredHolder<Item, Item> HONEY_DEW = product("honey_dew");
  public static final DeferredHolder<Item, Item> ROYAL_JELLY = product("royal_jelly");
  public static final DeferredHolder<Item, Item> PROPOLIS = product("propolis");
  public static final DeferredHolder<Item, Item> SILKEN_PROPOLIS = product("silken_propolis");
  public static final DeferredHolder<Item, Item> SILK_WISP = product("silk_wisp");
  public static final DeferredHolder<Item, Item> POLLEN = product("pollen");
  public static final DeferredHolder<Item, Item> ICE_SHARD = product("ice_shard");
  public static final DeferredHolder<Item, Item> ASH = product("ash");
  public static final DeferredHolder<Item, Item> PEAT = product("peat");
  public static final DeferredHolder<Item, Item> PHOSPHOR = product("phosphor");
  public static final DeferredHolder<Item, Item> DIAMOND_NUGGET = product("diamond_nugget");
  public static final DeferredHolder<Item, Item> EMERALD_NUGGET = product("emerald_nugget");
  public static final DeferredHolder<Item, Item> COPPER_NUGGET = product("copper_nugget");
  public static final DeferredHolder<Item, Item> WATERY_PROPOLIS = product("watery_propolis");
  public static final DeferredHolder<Item, Item> RED_TINTED_DROP = product("red_tinted_drop");
  public static final DeferredHolder<Item, Item> YELLOW_TINTED_DROP = product("yellow_tinted_drop");
  public static final DeferredHolder<Item, Item> BLUE_TINTED_DROP = product("blue_tinted_drop");
  public static final DeferredHolder<Item, Item> GREEN_TINTED_DROP = product("green_tinted_drop");
  public static final DeferredHolder<Item, Item> BROWN_TINTED_DROP = product("brown_tinted_drop");
  public static final DeferredHolder<Item, Item> WHITE_TINTED_DROP = product("white_tinted_drop");
  public static final DeferredHolder<Item, Item> BLACK_TINTED_DROP = product("black_tinted_drop");
  public static final DeferredHolder<Item, Item> ORANGE_TINTED_DROP = product("orange_tinted_drop");
  public static final DeferredHolder<Item, Item> CYAN_TINTED_DROP = product("cyan_tinted_drop");
  public static final DeferredHolder<Item, Item> PURPLE_TINTED_DROP = product("purple_tinted_drop");
  public static final DeferredHolder<Item, Item> GRAY_TINTED_DROP = product("gray_tinted_drop");
  public static final DeferredHolder<Item, Item> LIGHT_BLUE_TINTED_DROP = product("light_blue_tinted_drop");
  public static final DeferredHolder<Item, Item> PINK_TINTED_DROP = product("pink_tinted_drop");
  public static final DeferredHolder<Item, Item> LIME_TINTED_DROP = product("lime_tinted_drop");
  public static final DeferredHolder<Item, Item> LIGHT_GRAY_TINTED_DROP = product("light_gray_tinted_drop");
  public static final DeferredHolder<Item, Item> MAGENTA_TINTED_DROP = product("magenta_tinted_drop");
  public static final DeferredHolder<Item, Item> SALTPETER = product("saltpeter");
  public static final DeferredHolder<Item, Item> ACIDIC_DROP = product("acidic_drop");
  public static final DeferredHolder<Item, Item> SULFUR = product("sulfur");

  // Frames
  public static final DeferredHolder<Item, FrameItem> UNTREATED_FRAME = frame("untreated", 80, 1.0f, 0.897f, 1.0f, 1.0f);
  public static final DeferredHolder<Item, FrameItem> IMPREGNATED_FRAME = frame("impregnated", 240, 1.0f, 0.818f, 1.0f, 1.0f);
  public static final DeferredHolder<Item, FrameItem> HEALING_FRAME = frame("healing", 240, 1.5f, 1.25f, 0.5f, 1.0f);
  public static final DeferredHolder<Item, FrameItem> SOUL_FRAME = frame("soul", 240, 0.75f, 0.25f, 1.5f, 1.0f);
  public static final DeferredHolder<Item, FrameItem> RESTRAINT_FRAME = ITEMS.register(frame("restraint"), () -> new RestraintFrame(SINGLETON_PROPS().durability(240)));
  public static final DeferredHolder<Item, FrameItem> PROVEN_FRAME = frame("proven", 720, 1.0f, 0.714f, 1.0f, 1.0f);
  public static final DeferredHolder<Item, FrameItem> ROYAL_FRAME = frame("royal", 120, 2.0f, 500.0f, 0.0f, 2.5f);
  public static final DeferredHolder<Item, FrameItem> CREATIVE_FRAME = frame("creative", Integer.MAX_VALUE, 0.00001f, 1.0f, 1.0f, 1.0f);

  public static Item.Properties SINGLETON_PROPS()
  {
    return new Item.Properties().stacksTo(1);
  }

  public static void register(IEventBus bus)
  {
    ITEMS.register(bus);
  }

  public static DeferredHolder<Item, Item> product(String name)
  {
    DeferredHolder<Item, Item> holder = ITEMS.register(name, () -> new Item(new Item.Properties()));
    PRODUCTS.add(holder);
    return holder;
  }

  public static DeferredHolder<Item, Item> product(String name, Item.Properties properties)
  {
    DeferredHolder<Item, Item> holder = ITEMS.register(name, () -> new Item(properties));
    PRODUCTS.add(holder);
    return holder;
  }

  public static String frame(String name)
  {
    return name + "_frame";
  }

  public static DeferredHolder<Item, FrameItem> frame(String name, int durability, float lifespanModifier, float productionModifier, float mutationModifier, float additionalPrincessModifier)
  {
    DeferredHolder<Item, FrameItem> frame = ITEMS.register(name + "_frame", () -> new FrameItem(SINGLETON_PROPS().durability(durability), lifespanModifier, productionModifier, mutationModifier, additionalPrincessModifier, new TerritoryModifier(xz -> xz, y -> y)));
    FRAMES.add(frame);
    return frame;
  }

  public static DeferredHolder<Item, FrameItem> frame(String name, int durability, float lifespanModifier, float productionModifier, float mutationModifier, float additionalPrincessModifier, Function<Integer, Integer> xzMod, Function<Integer, Integer> ymod)
  {
    DeferredHolder<Item, FrameItem> frame = ITEMS.register(name + "_frame", () -> new FrameItem(SINGLETON_PROPS().durability(durability), lifespanModifier, productionModifier, mutationModifier, additionalPrincessModifier, new TerritoryModifier(xzMod, ymod)));
    FRAMES.add(frame);
    return frame;
  }

  public static CombHolder comb(String type)
  {
    DeferredHolder<Item, Item> comb = ITEMS.register(type + "_comb", () -> new Item(new Item.Properties()));
    COMBS.add(comb);
    return new CombHolder(comb, ResourceKey.create(ApicuriousRegistries.CENTRIFUGE_RECIPES, Apicurious.createResourceLocation(type)));
  }

  public static DeferredHolder<Item, Item> item(String name)
  {
    return ITEMS.register(name, () -> new Item(new Item.Properties()));
  }

  public static DeferredHolder<Item, Item> item(String name, Item.Properties properties)
  {
    return ITEMS.register(name, () -> new Item(properties));
  }

  public record CombHolder(DeferredHolder<Item, Item> comb, ResourceKey<CentrifugeRecipe> recipe) {}
}
