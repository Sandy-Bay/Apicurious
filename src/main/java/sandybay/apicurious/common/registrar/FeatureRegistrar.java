package sandybay.apicurious.common.registrar;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.worldgen.feature.GroundHiveFeature;
import sandybay.apicurious.common.worldgen.feature.HangingHiveFeature;
import sandybay.apicurious.common.worldgen.feature.SubmergedHiveFeature;
import sandybay.apicurious.common.worldgen.feature.config.GroundHiveConfiguration;
import sandybay.apicurious.common.worldgen.feature.config.HangingHiveConfiguration;
import sandybay.apicurious.common.worldgen.feature.config.SubmergedHiveConfiguration;

public class FeatureRegistrar
{
  private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Apicurious.MODID);

  // Declared with their concrete feature type (not Feature<?>) so datagen can call .get() directly
  // without an unsafe downcast.
  public static final DeferredHolder<Feature<?>, HangingHiveFeature> HANGING_HIVE = FEATURES.register("hanging_hive", () -> new HangingHiveFeature(HangingHiveConfiguration.CODEC));

  public static final DeferredHolder<Feature<?>, GroundHiveFeature> GROUND_HIVE = FEATURES.register("ground_hive", () -> new GroundHiveFeature(GroundHiveConfiguration.CODEC));

  public static final DeferredHolder<Feature<?>, SubmergedHiveFeature> SUBMERGED_HIVE = FEATURES.register("submerged_hive", () -> new SubmergedHiveFeature(SubmergedHiveConfiguration.CODEC));

  public static void register(IEventBus bus)
  {
    FEATURES.register(bus);
  }
}