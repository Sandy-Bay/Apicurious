package sandybay.apicurious.api.register;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.AlleleType;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.trait.*;

public class AlleleTypeRegistration
{
  private static final DeferredRegister<AlleleType<?>> TRAIT_TYPES = DeferredRegister.create(ApicuriousRegistries.TRAIT_TYPES, Apicurious.MODID);

  public static final DeferredHolder<AlleleType<?>, AlleleType<BeeSpecies>> SPECIES_TYPE = TRAIT_TYPES.register("species", () -> new AlleleType<>(BeeSpecies.CODEC, BeeSpecies.NETWORK_CODEC));
  public static final DeferredHolder<AlleleType<?>, AlleleType<Area>> AREA_TYPE = TRAIT_TYPES.register("area", () -> new AlleleType<>(Area.CODEC, Area.NETWORK_CODEC));
  public static final DeferredHolder<AlleleType<?>, AlleleType<Fertility>> FERTILITY_TYPE = TRAIT_TYPES.register("fertility", () -> new AlleleType<>(Fertility.CODEC, Fertility.NETWORK_CODEC));
  public static final DeferredHolder<AlleleType<?>, AlleleType<Flowers>> FLOWERS_TYPE = TRAIT_TYPES.register("flowers", () -> new AlleleType<>(Flowers.CODEC, Flowers.NETWORK_CODEC));
  public static final DeferredHolder<AlleleType<?>, AlleleType<HumidityPreference>> HUMIDITY_PREFERENCE_TYPE = TRAIT_TYPES.register("humidity_preference", () -> new AlleleType<>(HumidityPreference.CODEC, HumidityPreference.NETWORK_CODEC));
  public static final DeferredHolder<AlleleType<?>, AlleleType<HumidityTolerance>> HUMIDITY_TOLERANCE_TYPE = TRAIT_TYPES.register("humidity_tolerance", () -> new AlleleType<>(HumidityTolerance.CODEC, HumidityTolerance.NETWORK_CODEC));
  public static final DeferredHolder<AlleleType<?>, AlleleType<Lifespan>> LIFESPAN_TYPE = TRAIT_TYPES.register("lifespan", () -> new AlleleType<>(Lifespan.CODEC, Lifespan.NETWORK_CODEC));
  public static final DeferredHolder<AlleleType<?>, AlleleType<Pollination>> POLLINATION_TYPE = TRAIT_TYPES.register("pollination", () -> new AlleleType<>(Pollination.CODEC, Pollination.NETWORK_CODEC));
  public static final DeferredHolder<AlleleType<?>, AlleleType<Speed>> SPEED_TYPE = TRAIT_TYPES.register("speed", () -> new AlleleType<>(Speed.CODEC, Speed.NETWORK_CODEC));
  public static final DeferredHolder<AlleleType<?>, AlleleType<TemperaturePreference>> TEMPERATURE_PREFERENCE_TYPE = TRAIT_TYPES.register("temperature_preference", () -> new AlleleType<>(TemperaturePreference.CODEC, TemperaturePreference.NETWORK_CODEC));
  public static final DeferredHolder<AlleleType<?>, AlleleType<TemperatureTolerance>> TEMPERATURE_TOLERANCE_TYPE = TRAIT_TYPES.register("temperature_tolerance", () -> new AlleleType<>(TemperatureTolerance.CODEC, TemperatureTolerance.NETWORK_CODEC));
  public static final DeferredHolder<AlleleType<?>, AlleleType<Workcycle>> WORKCYCLE_TYPE = TRAIT_TYPES.register("workcycle", () -> new AlleleType<>(Workcycle.CODEC, Workcycle.NETWORK_CODEC));

  public static void init(IEventBus bus) {
    TRAIT_TYPES.register(bus);
  }
}
