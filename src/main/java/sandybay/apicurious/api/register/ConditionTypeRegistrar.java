package sandybay.apicurious.api.register;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.checkerframework.checker.units.qual.C;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.condition.ConditionType;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.condition.*;
import sandybay.apicurious.common.bee.condition.combinatorial.AndCondition;
import sandybay.apicurious.common.bee.condition.combinatorial.NotCondition;
import sandybay.apicurious.common.bee.condition.combinatorial.OrCondition;
import sandybay.apicurious.common.bee.condition.combinatorial.XOrCondition;
import sandybay.apicurious.common.bee.condition.combinatorial.inverted.NAndCondition;
import sandybay.apicurious.common.bee.condition.combinatorial.inverted.NOrCondition;
import sandybay.apicurious.common.bee.condition.combinatorial.inverted.XNOrCondition;

/*
TODO: To Add
  - Entities in Area
  - Current Jukebox Record
  - Is affected by potion cloud
 */
public class ConditionTypeRegistrar
{
  private static final DeferredRegister<ConditionType> CONDITIONS = DeferredRegister.create(ApicuriousRegistries.CONDITION_TYPES, Apicurious.MODID);

  public static final DeferredHolder<ConditionType, ConditionType> IS_CORRECT_BIOME = CONDITIONS.register("is_correct_biome", () -> new ConditionType(BiomeCondition.CODEC, BiomeCondition.NETWORK_CODEC));
  public static final DeferredHolder<ConditionType, ConditionType> IS_CORRECT_DATE = CONDITIONS.register("is_correct_date", () -> new ConditionType(DateCondition.CODEC, DateCondition.NETWORK_CODEC));
  public static final DeferredHolder<ConditionType, ConditionType> IS_CORRECT_MOON_PHASE = CONDITIONS.register("is_correct_moon_phase", () -> new ConditionType(MoonPhaseCondition.CODEC, MoonPhaseCondition.NETWORK_CODEC));
  public static final DeferredHolder<ConditionType, ConditionType> IS_CORRECT_TEMPERATURE = CONDITIONS.register("is_correct_temperature", () -> new ConditionType(TemperatureCondition.CODEC, TemperatureCondition.NETWORK_CODEC));
  public static final DeferredHolder<ConditionType, ConditionType> IS_CORRECT_HUMIDITY = CONDITIONS.register("is_correct_humidity", () -> new ConditionType(HumidityCondition.CODEC, HumidityCondition.NETWORK_CODEC));
  public static final DeferredHolder<ConditionType, ConditionType> IS_CORRECT_DIMENSION = CONDITIONS.register("is_correct_dimension", () -> new ConditionType(DimensionCondition.CODEC, DimensionCondition.NETWORK_CODEC));
  public static final DeferredHolder<ConditionType, ConditionType> IS_CORRECT_WEATHER = CONDITIONS.register("is_correct_weather", () -> new ConditionType(WeatherCondition.CODEC, WeatherCondition.NETWORK_CODEC));
  public static final DeferredHolder<ConditionType, ConditionType> IS_CORRECT_HEIGHT = CONDITIONS.register("is_correct_height", () -> new ConditionType(HeightCondition.CODEC, HeightCondition.NETWORK_CODEC));
  public static final DeferredHolder<ConditionType, ConditionType> HAS_BLOCK_IN_VICINITY = CONDITIONS.register("has_block_in_vicinity", () -> new ConditionType(BlockInAreaCondition.CODEC, BlockInAreaCondition.NETWORK_CODEC));
  public static final DeferredHolder<ConditionType, ConditionType> CHANCE = CONDITIONS.register("chance", () -> new ConditionType(ChanceCondition.CODEC, ChanceCondition.NETWORK_CODEC));

  // Combinatorial
  public static final DeferredHolder<ConditionType, ConditionType> NOT = CONDITIONS.register("not", () -> new ConditionType(NotCondition.CODEC, NotCondition.NETWORK_CODEC));
  public static final DeferredHolder<ConditionType, ConditionType> AND = CONDITIONS.register("and", () -> new ConditionType(AndCondition.CODEC, AndCondition.NETWORK_CODEC));
  public static final DeferredHolder<ConditionType, ConditionType> NAND = CONDITIONS.register("nand", () -> new ConditionType(NAndCondition.CODEC, NAndCondition.NETWORK_CODEC));
  public static final DeferredHolder<ConditionType, ConditionType> OR = CONDITIONS.register("or", () -> new ConditionType(OrCondition.CODEC, OrCondition.NETWORK_CODEC));
  public static final DeferredHolder<ConditionType, ConditionType> NOR = CONDITIONS.register("nor", () -> new ConditionType(NOrCondition.CODEC, NOrCondition.NETWORK_CODEC));
  public static final DeferredHolder<ConditionType, ConditionType> XOR = CONDITIONS.register("xor", () -> new ConditionType(XOrCondition.CODEC, XOrCondition.NETWORK_CODEC));
  public static final DeferredHolder<ConditionType, ConditionType> XNOR = CONDITIONS.register("xnor", () -> new ConditionType(XNOrCondition.CODEC, XNOrCondition.NETWORK_CODEC));

  public static void init(IEventBus bus)
  {
    CONDITIONS.register(bus);
  }

}
