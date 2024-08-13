package sandybay.apicurious.common.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.item.TerritoryModifier;
import sandybay.apicurious.common.item.BaseBeeItem;
import sandybay.apicurious.common.item.SieveItem;
import sandybay.apicurious.common.item.frame.FrameItem;
import sandybay.apicurious.common.item.frame.RestraintFrame;

import java.util.function.Function;

public class ItemRegistration
{

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Apicurious.MODID);
  public static final DeferredHolder<Item, BaseBeeItem> DRONE = ITEMS.register("drone", () -> new BaseBeeItem(new Item.Properties(), EnumBeeType.DRONE));
  public static final DeferredHolder<Item, BaseBeeItem> PRINCESS = ITEMS.register("princess", () -> new BaseBeeItem(SINGLETON_PROPS(), EnumBeeType.PRINCESS));
  public static final DeferredHolder<Item, BaseBeeItem> QUEEN = ITEMS.register("queen", () -> new BaseBeeItem(SINGLETON_PROPS(), EnumBeeType.QUEEN));
  public static final DeferredHolder<Item, SieveItem> SIEVE = ITEMS.register("sieve", () -> new SieveItem(Tiers.WOOD, new Item.Properties().durability(32)));

  // Products
  // Combs
  /* TODO: Implement combs as needed
  public static final DeferredHolder<Item, Item> COCOA_COMB = comb("cocoa");
  public static final DeferredHolder<Item, Item> DRIPPING_COMB = comb("dripping");
  public static final DeferredHolder<Item, Item> FROZEN_COMB = comb("frozen");
  public static final DeferredHolder<Item, Item> MELLOW_COMB = comb("mellow");
  public static final DeferredHolder<Item, Item> MOSSY_COMB = comb("mossy");
  public static final DeferredHolder<Item, Item> MYSTERIOUS_COMB = comb("mysterious");
  public static final DeferredHolder<Item, Item> PARCHED_COMB = comb("parched");
  public static final DeferredHolder<Item, Item> POWDERY_COMB = comb("powdery");
  public static final DeferredHolder<Item, Item> SILKY_COMB = comb("silky");
  public static final DeferredHolder<Item, Item> SIMMERING_COMB = comb("simmering");
  public static final DeferredHolder<Item, Item> STRINGY_COMB = comb("stringy");
  public static final DeferredHolder<Item, Item> WHEATEN_COMB = comb("wheaten");
   */

  // Misc
  /* TODO: Implement as needed
  public static final DeferredHolder<Item, Item> BEESWAX = item("beeswax");
  public static final DeferredHolder<Item, Item> REFRACTORY_WAX = item("refractory_wax");
  public static final DeferredHolder<Item, Item> HONEY_DROP = item("honey_drop");
  public static final DeferredHolder<Item, Item> HONEY_DEW = item("honey_dew");
  public static final DeferredHolder<Item, Item> ROYAL_JELLY = item("royal_jelly");
  public static final DeferredHolder<Item, Item> PROPOLIS = item("propolis");
  public static final DeferredHolder<Item, Item> SILKEN_PROPOLIS = item("silken_propolis");
  public static final DeferredHolder<Item, Item> SILK_WISP = item("silk_wisp");
   */

  // Frames
  public static final DeferredHolder<Item, FrameItem> UNTREATED_FRAME = frame("untreated", 80, 1.0f, 0.897f, 1.0f);
  public static final DeferredHolder<Item, FrameItem> IMPREGNATED_FRAME = frame("impregnated", 240, 1.0f, 0.818f, 1.0f);
  public static final DeferredHolder<Item, FrameItem> HEALING_FRAME = frame("healing", 240, 1.5f, 1.25f, 0.5f);
  public static final DeferredHolder<Item, FrameItem> SOUL_FRAME = frame("soul", 240, 0.75f, 0.25f, 1.5f);
  public static final DeferredHolder<Item, FrameItem> RESTRAINT_FRAME = ITEMS.register(frame("restraint"), () -> new RestraintFrame(SINGLETON_PROPS().durability(240)));
  public static final DeferredHolder<Item, FrameItem> PROVEN_FRAME = frame("proven", 720, 1.0f, 0.714f, 1.0f);

  public static Item.Properties SINGLETON_PROPS()
  {
    return new Item.Properties().stacksTo(1);
  }

  public static void register(IEventBus bus)
  {
    ITEMS.register(bus);
  }

  public static String frame(String name)
  {
    return name + "_frame";
  }

  public static DeferredHolder<Item, FrameItem> frame(String name, int durability, float lifespanModifier, float productionModifier, float mutationModifier)
  {
    return ITEMS.register(name + "_frame", () -> new FrameItem(SINGLETON_PROPS().durability(durability), lifespanModifier, productionModifier, mutationModifier, new TerritoryModifier(xz -> xz, y -> y)));
  }

  public static DeferredHolder<Item, FrameItem> frame(String name, int durability, float lifespanModifier, float productionModifier, float mutationModifier, Function<Integer, Integer> xzMod, Function<Integer, Integer> ymod)
  {
    return ITEMS.register(name + "_frame", () -> new FrameItem(SINGLETON_PROPS().durability(durability), lifespanModifier, productionModifier, mutationModifier, new TerritoryModifier(xzMod, ymod)));
  }

  public static DeferredHolder<Item, Item> comb(String type)
  {
    return ITEMS.register(type + "_comb", () -> new Item(new Item.Properties()));
  }

  public static DeferredHolder<Item, Item> item(String name)
  {
    return ITEMS.register(name, () -> new Item(new Item.Properties()));
  }

  public static DeferredHolder<Item, Item> item(String name, Item.Properties properties)
  {
    return ITEMS.register(name, () -> new Item(properties));
  }

}
