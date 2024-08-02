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
import sandybay.apicurious.common.item.SieveItem;

public class ApicuriousItemRegistration
{

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Apicurious.MODID);

  public static final Item.Properties PRINCESS_QUEEN_PROPS = new Item.Properties().stacksTo(1);

  public static final DeferredHolder<Item, BaseBeeItem> DRONE = ITEMS.register("drone", () -> new BaseBeeItem(new Item.Properties(), EnumBeeType.DRONE));
  public static final DeferredHolder<Item, BaseBeeItem> PRINCESS = ITEMS.register("princess", () -> new BaseBeeItem(PRINCESS_QUEEN_PROPS, EnumBeeType.PRINCESS));
  public static final DeferredHolder<Item, BaseBeeItem> QUEEN = ITEMS.register("queen", () -> new BaseBeeItem(PRINCESS_QUEEN_PROPS, EnumBeeType.QUEEN));

  public static final DeferredHolder<Item, SieveItem> SIEVE = ITEMS.register("sieve", () -> new SieveItem(Tiers.WOOD, new Item.Properties().durability(32)));

  public static void register(IEventBus bus)
  {
    ITEMS.register(bus);
  }

}
