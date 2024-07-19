package sandybay.apicurious.common.datacomponent;

import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.bee.species.BeeSpecies;

public class ApicuriousDataComponents {

  public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Apicurious.MODID);

  public static final DeferredHolder<DataComponentType<?>, DataComponentType<BeeSpecies>> BEE_SPECIES = REGISTRAR.registerComponentType(
          "bee_species",
          builder -> builder
                  .persistent(BeeSpecies.CODEC)
                  .networkSynchronized(BeeSpecies.NETWORK_CODEC)
  );

  public static void register(IEventBus bus) {
    REGISTRAR.register(bus);
  }






}
