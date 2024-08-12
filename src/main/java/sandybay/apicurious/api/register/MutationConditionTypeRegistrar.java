package sandybay.apicurious.api.register;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.mutation.condition.MutationConditionType;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.genetic.mutation.condition.*;
import sandybay.apicurious.common.bee.genetic.mutation.condition.combinatorial.AndMutationCondition;
import sandybay.apicurious.common.bee.genetic.mutation.condition.combinatorial.NotMutationCondition;
import sandybay.apicurious.common.bee.genetic.mutation.condition.combinatorial.OrMutationCondition;
import sandybay.apicurious.common.bee.genetic.mutation.condition.combinatorial.XOrMutationCondition;
import sandybay.apicurious.common.bee.genetic.mutation.condition.combinatorial.inverted.NAndMutationCondition;
import sandybay.apicurious.common.bee.genetic.mutation.condition.combinatorial.inverted.NOrMutationCondition;
import sandybay.apicurious.common.bee.genetic.mutation.condition.combinatorial.inverted.XNOrMutationCondition;

/*
TODO: To Add
  - Entities in Area
  - Current Jukebox Record
  - Is affected by potion cloud
 */
public class MutationConditionTypeRegistrar
{
  private static final DeferredRegister<MutationConditionType> CONDITIONS = DeferredRegister.create(ApicuriousRegistries.MUTATION_CONDITION_TYPES, Apicurious.MODID);

  public static final DeferredHolder<MutationConditionType, MutationConditionType> IS_CORRECT_BIOME = CONDITIONS.register("is_correct_biome", () -> new MutationConditionType(BiomeMutationCondition.CODEC));
  public static final DeferredHolder<MutationConditionType, MutationConditionType> IS_CORRECT_DATE = CONDITIONS.register("is_correct_date", () -> new MutationConditionType(DateMutationCondition.CODEC));
  public static final DeferredHolder<MutationConditionType, MutationConditionType> IS_CORRECT_MOON_PHASE = CONDITIONS.register("is_correct_moon_phase", () -> new MutationConditionType(MoonPhaseCondition.CODEC));
  public static final DeferredHolder<MutationConditionType, MutationConditionType> IS_CORRECT_TEMPERATURE = CONDITIONS.register("is_correct_temperature", () -> new MutationConditionType(TemperatureMutationCondition.CODEC));
  public static final DeferredHolder<MutationConditionType, MutationConditionType> IS_CORRECT_HUMIDITY = CONDITIONS.register("is_correct_humidity", () -> new MutationConditionType(HumidityMutationCondition.CODEC));
  public static final DeferredHolder<MutationConditionType, MutationConditionType> IS_CORRECT_DIMENSION = CONDITIONS.register("is_correct_dimension", () -> new MutationConditionType(DimensionMutationCondition.CODEC));
  public static final DeferredHolder<MutationConditionType, MutationConditionType> IS_CORRECT_WEATHER = CONDITIONS.register("is_correct_weather", () -> new MutationConditionType(WeatherMutationCondition.CODEC));
  public static final DeferredHolder<MutationConditionType, MutationConditionType> IS_CORRECT_HEIGHT = CONDITIONS.register("is_correct_height", () -> new MutationConditionType(HeightMutationCondition.CODEC));
  public static final DeferredHolder<MutationConditionType, MutationConditionType> HAS_BLOCK_IN_VICINITY = CONDITIONS.register("has_block_in_vicinity", () -> new MutationConditionType(BlockInAreaMutationCondition.CODEC));

  // Combinatorial
  public static final DeferredHolder<MutationConditionType, MutationConditionType> NOT = CONDITIONS.register("not", () -> new MutationConditionType(NotMutationCondition.CODEC));
  public static final DeferredHolder<MutationConditionType, MutationConditionType> AND = CONDITIONS.register("and", () -> new MutationConditionType(AndMutationCondition.CODEC));
  public static final DeferredHolder<MutationConditionType, MutationConditionType> NAND = CONDITIONS.register("nand", () -> new MutationConditionType(NAndMutationCondition.CODEC));
  public static final DeferredHolder<MutationConditionType, MutationConditionType> OR = CONDITIONS.register("or", () -> new MutationConditionType(OrMutationCondition.CODEC));
  public static final DeferredHolder<MutationConditionType, MutationConditionType> NOR = CONDITIONS.register("nor", () -> new MutationConditionType(NOrMutationCondition.CODEC));
  public static final DeferredHolder<MutationConditionType, MutationConditionType> XOR = CONDITIONS.register("xor", () -> new MutationConditionType(XOrMutationCondition.CODEC));
  public static final DeferredHolder<MutationConditionType, MutationConditionType> XNOR = CONDITIONS.register("xnor", () -> new MutationConditionType(XNOrMutationCondition.CODEC));

  public static void init(IEventBus bus)
  {
    CONDITIONS.register(bus);
  }

}
