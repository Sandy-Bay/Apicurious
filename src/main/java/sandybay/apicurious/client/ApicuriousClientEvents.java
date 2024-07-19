package sandybay.apicurious.client;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.datacomponent.ApicuriousDataComponents;
import sandybay.apicurious.common.item.ApicuriousItemRegistration;

public class ApicuriousClientEvents {

  public static void registerClientEvents(IEventBus bus) {
    bus.addListener(ApicuriousClientEvents::handleItemTint);
  }

  private static void handleItemTint(final RegisterColorHandlersEvent.Item event) {
    event.register(
            (stack, index) -> {
              int tint = 0xFFFFFFFF;
              if (index == 0) tint = getColor(stack, true, false);
              if (index == 1) tint = getColor(stack, false, true);
              if (index == 4) tint = getColor(stack, false, false);
              return tint;
            },
            ApicuriousItemRegistration.DRONE.get(),
            ApicuriousItemRegistration.PRINCESS.get(),
            ApicuriousItemRegistration.QUEEN.get()
    );
  }

  private static int getColor(ItemStack stack, boolean isOutline, boolean isBody) {
    BeeSpecies species = stack.get(ApicuriousDataComponents.BEE_SPECIES);
    if (species == null || species.getVisualData() == null) return 0xffffff;
    return isOutline ?
            species.getVisualData().getBeeColor().getOutlineTint().getIntColor() : isBody ?
            species.getVisualData().getBeeColor().getBodyTint().getIntColor() :
            species.getVisualData().getBeeColor().getWingTint().getIntColor();
  }


}
