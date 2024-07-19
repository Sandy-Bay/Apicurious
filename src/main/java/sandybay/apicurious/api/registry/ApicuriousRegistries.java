package sandybay.apicurious.api.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.old.traits.*;

public class ApicuriousRegistries {

  // Trait Registries
  public static final ResourceKey<Registry<Area>> AREAS = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("area"));
  public static final ResourceKey<Registry<Flowers>> FLOWERS = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("flower"));
  public static final ResourceKey<Registry<HumidityPreference>> HUMIDITY_PREFERENCES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("humidity_preference"));
  public static final ResourceKey<Registry<HumidityTolerance>> HUMIDITY_TOLERANCES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("humidity_tolerance"));
  public static final ResourceKey<Registry<Lifespan>> LIFESPANS = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("lifespan"));
  public static final ResourceKey<Registry<Pollination>> POLLINATIONS = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("pollination"));
  public static final ResourceKey<Registry<Speed>> SPEEDS = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("speed"));
  public static final ResourceKey<Registry<TemperaturePreference>> TEMPERATURE_PREFERENCES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("temperature_preference"));
  public static final ResourceKey<Registry<TemperatureTolerance>> TEMPERATURE_TOLERANCES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("temperature_tolerance"));
  public static final ResourceKey<Registry<WorkCycle>> WORKCYCLES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("workcycle"));

  // Species Registry
  public static final ResourceKey<Registry<BeeSpecies>> BEE_SPECIES = ResourceKey.createRegistryKey(Apicurious.createResourceLocation("bee_species"));

  public static void registerDatapackRegistries(final DataPackRegistryEvent.NewRegistry event) {
    // Traits
    event.dataPackRegistry(AREAS, Area.CODEC, Area.CODEC);
    event.dataPackRegistry(FLOWERS, Flowers.CODEC, Flowers.CODEC);
    event.dataPackRegistry(HUMIDITY_PREFERENCES, HumidityPreference.CODEC, HumidityPreference.CODEC);
    event.dataPackRegistry(HUMIDITY_TOLERANCES, HumidityTolerance.CODEC, HumidityTolerance.CODEC);
    event.dataPackRegistry(LIFESPANS, Lifespan.CODEC, Lifespan.CODEC);
    event.dataPackRegistry(POLLINATIONS, Pollination.CODEC, Pollination.CODEC);
    event.dataPackRegistry(SPEEDS, Speed.CODEC, Speed.CODEC);
    event.dataPackRegistry(TEMPERATURE_PREFERENCES, TemperaturePreference.CODEC, TemperaturePreference.CODEC);
    event.dataPackRegistry(TEMPERATURE_TOLERANCES, TemperatureTolerance.CODEC, TemperatureTolerance.CODEC);
    event.dataPackRegistry(WORKCYCLES, WorkCycle.CODEC, WorkCycle.CODEC);

    // Bee Species
    event.dataPackRegistry(BEE_SPECIES, BeeSpecies.CODEC, BeeSpecies.CODEC);

  }


}
