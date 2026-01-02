package sandybay.apicurious.api.register;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.mutation.MutationType;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.genetic.mutation.ConditionalMutation;
import sandybay.apicurious.common.bee.genetic.mutation.Mutation;

public class MutationTypeRegistrar
{
  private static final DeferredRegister<MutationType> MUTATION_TYPES = DeferredRegister.create(ApicuriousRegistries.MUTATION_TYPES, Apicurious.MODID);

  public static final DeferredHolder<MutationType, @NotNull MutationType> BASE_MUTATION_TYPE = MUTATION_TYPES.register("mutation", () -> new MutationType(Mutation.CODEC));
  public static final DeferredHolder<MutationType, @NotNull MutationType> CONDITONAL_MUTATION_TYPE = MUTATION_TYPES.register("conditional", () -> new MutationType(ConditionalMutation.CODEC));

  public static void init(IEventBus bus)
  {
    MUTATION_TYPES.register(bus);
  }
}
