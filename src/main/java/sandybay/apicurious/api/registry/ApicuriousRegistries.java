package sandybay.apicurious.api.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.AlleleType;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.trait.*;

public class ApicuriousRegistries
{

  // Trait Registries
  public static final ResourceKey<Registry<AlleleType<?>>> TRAIT_TYPES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("trait_type"));
  public static final Registry<AlleleType<?>> TRAIT_TYPES_REGISTRY = new RegistryBuilder<>(TRAIT_TYPES).create();

  public static final ResourceKey<Registry<Area>> AREAS = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("area"));
  public static final ResourceKey<Registry<Fertility>> FERTILITIES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("fertility"));
  public static final ResourceKey<Registry<Flowers>> FLOWERS = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("flower"));
  public static final ResourceKey<Registry<HumidityPreference>> HUMIDITY_PREFERENCES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("humidity_preference"));
  public static final ResourceKey<Registry<HumidityTolerance>> HUMIDITY_TOLERANCES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("humidity_tolerance"));
  public static final ResourceKey<Registry<Lifespan>> LIFESPANS = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("lifespan"));
  public static final ResourceKey<Registry<Pollination>> POLLINATIONS = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("pollination"));
  public static final ResourceKey<Registry<Speed>> SPEEDS = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("speed"));
  public static final ResourceKey<Registry<TemperaturePreference>> TEMPERATURE_PREFERENCES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("temperature_preference"));
  public static final ResourceKey<Registry<TemperatureTolerance>> TEMPERATURE_TOLERANCES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("temperature_tolerance"));
  public static final ResourceKey<Registry<Workcycle>> WORKCYCLES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("workcycle"));

  // Species Registry
  public static final ResourceKey<Registry<BeeSpecies>> BEE_SPECIES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("bee_species"));

  public static void registerRegistries(final NewRegistryEvent event)
  {
    event.register(TRAIT_TYPES_REGISTRY);
  }

  public static void registerDatapackRegistries(final DataPackRegistryEvent.NewRegistry event)
  {
    // Traits
    event.dataPackRegistry(AREAS, Area.CODEC.codec(), Area.CODEC.codec());
    event.dataPackRegistry(FERTILITIES, Fertility.CODEC.codec(), Fertility.CODEC.codec());
    event.dataPackRegistry(FLOWERS, Flowers.CODEC.codec(), Flowers.CODEC.codec());
    event.dataPackRegistry(HUMIDITY_PREFERENCES, HumidityPreference.CODEC.codec(), HumidityPreference.CODEC.codec());
    event.dataPackRegistry(HUMIDITY_TOLERANCES, HumidityTolerance.CODEC.codec(), HumidityTolerance.CODEC.codec());
    event.dataPackRegistry(LIFESPANS, Lifespan.CODEC.codec(), Lifespan.CODEC.codec());
    event.dataPackRegistry(POLLINATIONS, Pollination.CODEC.codec(), Pollination.CODEC.codec());
    event.dataPackRegistry(SPEEDS, Speed.CODEC.codec(), Speed.CODEC.codec());
    event.dataPackRegistry(TEMPERATURE_PREFERENCES, TemperaturePreference.CODEC.codec(), TemperaturePreference.CODEC.codec());
    event.dataPackRegistry(TEMPERATURE_TOLERANCES, TemperatureTolerance.CODEC.codec(), TemperatureTolerance.CODEC.codec());
    event.dataPackRegistry(WORKCYCLES, Workcycle.CODEC.codec(), Workcycle.CODEC.codec());

    // Bee Species
    event.dataPackRegistry(BEE_SPECIES, BeeSpecies.CODEC.codec(), BeeSpecies.CODEC.codec());
  }
}
