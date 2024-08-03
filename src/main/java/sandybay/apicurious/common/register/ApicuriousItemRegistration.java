package sandybay.apicurious.common.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.common.item.BaseBeeItem;
import sandybay.apicurious.common.item.FrameItem;
import sandybay.apicurious.common.item.SieveItem;

public class ApicuriousItemRegistration
{

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Apicurious.MODID);

  public static final Item.Properties SINGLETON_PROPS = new Item.Properties().stacksTo(1);

  public static final DeferredHolder<Item, BaseBeeItem> DRONE = ITEMS.register("drone", () -> new BaseBeeItem(new Item.Properties(), EnumBeeType.DRONE));
  public static final DeferredHolder<Item, BaseBeeItem> PRINCESS = ITEMS.register("princess", () -> new BaseBeeItem(SINGLETON_PROPS, EnumBeeType.PRINCESS));
  public static final DeferredHolder<Item, BaseBeeItem> QUEEN = ITEMS.register("queen", () -> new BaseBeeItem(SINGLETON_PROPS, EnumBeeType.QUEEN));

  public static final DeferredHolder<Item, SieveItem> SIEVE = ITEMS.register("sieve", () -> new SieveItem(Tiers.WOOD, new Item.Properties().durability(32)));

  // Frames
  public static final DeferredHolder<Item, FrameItem> UNTREATED_FRAME = frame("untreated", 80, 1.0f, 0.5f, 1.0f);
  public static final DeferredHolder<Item, FrameItem> IMPREGNATED_FRAME = frame("impregnated", 240, 1.0f, 0.5f, 1.0f);
  public static final DeferredHolder<Item, FrameItem> PROVEN_FRAME = frame("proven", 720, 1.0f, 0.5f, 1.0f);

  public static void register(IEventBus bus)
  {
    ITEMS.register(bus);
  }

  public static DeferredHolder<Item, FrameItem> frame(String name, int durability, float lifespanModifier, float productionModifier, float mutationModifier)
  {
    return ITEMS.register(name + "_frame", () -> new FrameItem(SINGLETON_PROPS.durability(durability), lifespanModifier, productionModifier, mutationModifier));
  }

}
