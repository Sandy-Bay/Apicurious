package sandybay.apicurious.common.bee;

import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class ApicuriousMutations
{
  public static final ResourceKey<IMutation> FIRST_EXAMPLE = ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("first_example"));
  public static final ResourceKey<IMutation> SECOND_EXAMPLE = ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("second_example"));
  public static final ResourceKey<IMutation> THIRD_EXAMPLE = ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("third_example"));

  public static final ResourceKey<IMutation> SINGLE_CONDITIONAL_EXAMPLE = ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("single_conditional_example"));
  public static final ResourceKey<IMutation> MANY_CONDITIONAL_EXAMPLE = ResourceKey.create(ApicuriousRegistries.MUTATIONS, Apicurious.createResourceLocation("many_conditional_example"));

}
