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
import sandybay.apicurious.common.item.frame.FrameItem;
import sandybay.apicurious.common.item.SieveItem;
import sandybay.apicurious.common.item.frame.RestraintFrame;

import java.util.function.Function;
import java.util.function.Supplier;

public class ApicuriousItemRegistration
{

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Apicurious.MODID);

  public static Item.Properties SINGLETON_PROPS() {
    return new Item.Properties().stacksTo(1);
  }

  public static final DeferredHolder<Item, BaseBeeItem> DRONE = ITEMS.register("drone", () -> new BaseBeeItem(new Item.Properties(), EnumBeeType.DRONE));
  public static final DeferredHolder<Item, BaseBeeItem> PRINCESS = ITEMS.register("princess", () -> new BaseBeeItem(SINGLETON_PROPS(), EnumBeeType.PRINCESS));
  public static final DeferredHolder<Item, BaseBeeItem> QUEEN = ITEMS.register("queen", () -> new BaseBeeItem(SINGLETON_PROPS(), EnumBeeType.QUEEN));

  public static final DeferredHolder<Item, SieveItem> SIEVE = ITEMS.register("sieve", () -> new SieveItem(Tiers.WOOD, new Item.Properties().durability(32)));

  // Frames
  public static final DeferredHolder<Item, FrameItem> UNTREATED_FRAME = frame("untreated", 80, 1.0f, 0.897f, 1.0f);
  public static final DeferredHolder<Item, FrameItem> IMPREGNATED_FRAME = frame("impregnated", 240, 1.0f, 0.818f, 1.0f);
  public static final DeferredHolder<Item, FrameItem> HEALING_FRAME = frame("healing", 240, 1.5f, 1.25f, 0.5f);
  public static final DeferredHolder<Item, FrameItem> SOUL_FRAME = frame("soul", 240, 0.75f, 0.25f, 1.5f);
  public static final DeferredHolder<Item, FrameItem> RESTRAINT_FRAME = ITEMS.register(frame("restraint"), () -> new RestraintFrame(SINGLETON_PROPS().durability(240)));
  public static final DeferredHolder<Item, FrameItem> PROVEN_FRAME = frame("proven", 720, 1.0f, 0.714f, 1.0f);

  public static void register(IEventBus bus)
  {
    ITEMS.register(bus);
  }

  public static String frame(String name) {
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

}
