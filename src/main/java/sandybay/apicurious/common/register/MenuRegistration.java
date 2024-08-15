package sandybay.apicurious.common.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.menu.ApiaryMenu;
import sandybay.apicurious.common.menu.BeeHousingMenu;

import java.util.function.Supplier;

public class MenuRegistration
{
  private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, Apicurious.MODID);

  public static final Supplier<MenuType<ApiaryMenu>> APIARY = MENU_TYPES.register("apiary", () -> IMenuTypeExtension.create(ApiaryMenu::new));
  public static final Supplier<MenuType<BeeHousingMenu>> BEE_HOUSING = MENU_TYPES.register("bee_housing", () -> IMenuTypeExtension.create(BeeHousingMenu::new));

  public static void register(IEventBus bus)
  {
    MENU_TYPES.register(bus);
  }
}
