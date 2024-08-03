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

public class ApicuriousMenuRegistration
{

  private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, Apicurious.MODID);

  public static void register(IEventBus bus)
  {
    MENU_TYPES.register(bus);
  }

  private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenu(String id, MenuType.MenuSupplier<T> supplier)
  {
    return MENU_TYPES.register(id, () -> new MenuType<>(supplier, FeatureFlags.DEFAULT_FLAGS));
  }

  public static final Supplier<MenuType<ApiaryMenu>> APIARY = MENU_TYPES.register("apiary", () -> IMenuTypeExtension.create(ApiaryMenu::new));


  public static final DeferredHolder<MenuType<?>, MenuType<BeeHousingMenu>> BEE_HOUSING = registerMenu("bee_housing", BeeHousingMenu::new);
}
