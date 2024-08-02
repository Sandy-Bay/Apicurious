package sandybay.apicurious.api.register;

import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.bee.species.BeeSpecies;

public class ApicuriousDataComponentRegistration
{

  public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Apicurious.MODID);

  //This can go in the API so other mods can access it if needed
  public static final DeferredHolder<DataComponentType<?>, DataComponentType<BeeSpecies>> BEE_SPECIES = REGISTRAR.registerComponentType(
          "bee_species",
          builder -> builder
                  .persistent(BeeSpecies.CODEC)
                  .networkSynchronized(BeeSpecies.NETWORK_CODEC)
  );

  public static void register(IEventBus bus)
  {
    REGISTRAR.register(bus);
  }


}
