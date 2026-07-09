package sandybay.apicurious.common.registrar;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.worldgen.feature.HangingHiveConfiguration;
import sandybay.apicurious.common.worldgen.feature.HangingHiveFeature;

public class FeatureRegistrar
{
  private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Apicurious.MODID);

  public static void register(IEventBus bus)
  {
    FEATURES.register(bus);
  }

  public static final DeferredHolder<Feature<?>, Feature<?>> HANGING_HIVE = FEATURES.register("hanging_hive", () -> new HangingHiveFeature(HangingHiveConfiguration.CODEC));

}
