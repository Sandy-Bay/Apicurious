package sandybay.apicurious.common.normal;

import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import sandybay.apicurious.common.config.ApicuriousMainConfig;

public class ApicuriousNormalEvents
{
  public static void registerTotallyNormalEvents(IEventBus bus)
  {
    bus.addListener(ApicuriousNormalEvents::whyAreTheHorsesOnFire);
  }

  /**
   * Credit goes to Thiakil :^I <3
   * @param event The entity tick event
   */
  public static void whyAreTheHorsesOnFire(EntityTickEvent.Post event)
  {
    if (ApicuriousMainConfig.main_config.whyAreTheHorsesOnFire.get() && event.getEntity() instanceof Horse horse)
    {
      Level level = horse.level();
      int time = Math.toIntExact(level.getOverworldClockTime() % 24000);
      if (time >= 1000 && time <= 12999 && level.canSeeSky(horse.getOnPos()))
      {
        horse.igniteForSeconds(5);
      }
    }
  }
}
