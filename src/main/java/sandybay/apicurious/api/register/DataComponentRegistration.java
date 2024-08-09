package sandybay.apicurious.api.register;

import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.IGenome;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.species.BeeSpecies;

public class DataComponentRegistration
{

  public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Apicurious.MODID);

  //This can go in the API so other mods can access it if needed

  public static final DeferredHolder<DataComponentType<?>, DataComponentType<Genome>> GENOME = REGISTRAR.registerComponentType(
          "genome",
          builder -> builder
                  .persistent(Genome.CODEC)
                  .networkSynchronized(Genome.NETWORK_CODEC)
  );

  public static void register(IEventBus bus)
  {
    REGISTRAR.register(bus);
  }

}
