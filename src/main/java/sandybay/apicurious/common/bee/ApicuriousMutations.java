package sandybay.apicurious.common.bee;

import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class ApicuriousMutations
{
  public static final ResourceKey<IMutation> FIRST_EXAMPLE = ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createIdentifier("first_example"));
  public static final ResourceKey<IMutation> SECOND_EXAMPLE = ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createIdentifier("second_example"));
  public static final ResourceKey<IMutation> THIRD_EXAMPLE = ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createIdentifier("third_example"));

  public static final ResourceKey<IMutation> SINGLE_CONDITIONAL_EXAMPLE = ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createIdentifier("single_conditional_example"));
  public static final ResourceKey<IMutation> MANY_CONDITIONAL_EXAMPLE = ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createIdentifier("many_conditional_example"));

}
