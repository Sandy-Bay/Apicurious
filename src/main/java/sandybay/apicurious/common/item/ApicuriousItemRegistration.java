package sandybay.apicurious.common.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.item.bee.DroneBeeItem;
import sandybay.apicurious.common.item.bee.PrincessBeeItem;
import sandybay.apicurious.common.item.bee.QueenBeeItem;

public class ApicuriousItemRegistration {

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Apicurious.MODID);

  public static final Item.Properties PRINCESS_QUEEN_PROPS = new Item.Properties().stacksTo(1);

  public static final DeferredHolder<Item, DroneBeeItem> DRONE = ITEMS.register("drone", () -> new DroneBeeItem(new Item.Properties()));
  public static final DeferredHolder<Item, PrincessBeeItem> PRINCESS = ITEMS.register("princess", () -> new PrincessBeeItem(PRINCESS_QUEEN_PROPS));
  public static final DeferredHolder<Item, QueenBeeItem> QUEEN = ITEMS.register("queen", () -> new QueenBeeItem(PRINCESS_QUEEN_PROPS));

  public static final DeferredHolder<Item, SieveItem> SIEVE = ITEMS.register("sieve", () -> new SieveItem(Tiers.WOOD, new Item.Properties().durability(32)));

  public static void register(IEventBus bus) {
    ITEMS.register(bus);
  }

}
