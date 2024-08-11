package sandybay.apicurious.api.register;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.mutation.condition.MutationConditionType;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.genetic.mutation.condition.BiomeMutationCondition;

public class MutationConditionTypeRegistrar
{
  private static final DeferredRegister<MutationConditionType> CONDITIONS = DeferredRegister.create(ApicuriousRegistries.MUTATION_CONDITION_TYPES, Apicurious.MODID);

  public static final DeferredHolder<MutationConditionType, MutationConditionType> IN_BIOME = CONDITIONS.register("in_biome", () -> new MutationConditionType(BiomeMutationCondition.CODEC));

  public static void init(IEventBus bus)
  {
    CONDITIONS.register(bus);
  }

}
