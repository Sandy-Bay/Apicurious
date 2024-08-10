package sandybay.apicurious.common.bee;

import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.genetic.mutation.Mutation;

public class ApicuriousMutations
{
  public static final ResourceKey<Mutation> FIRST_EXAMPLE = ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("first_example"));
  public static final ResourceKey<Mutation> SECOND_EXAMPLE = ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("second_example"));
  public static final ResourceKey<Mutation> THIRD_EXAMPLE = ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("third_example"));
  public static final ResourceKey<Mutation> FOURTH_EXAMPLE = ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("fourth_example"));

}
