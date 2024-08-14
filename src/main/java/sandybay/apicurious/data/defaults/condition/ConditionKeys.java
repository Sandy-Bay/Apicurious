package sandybay.apicurious.data.defaults.condition;

import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.condition.ICondition;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.ApicuriousSpecies;

public class ConditionKeys
{
  public static final ResourceKey<ICondition> IS_CHRISTMAS = condition("is_christmas");

  public static ResourceKey<ICondition> condition(String name)
  {
    return ResourceKey.create(ApicuriousRegistries.CONDITIONS, Apicurious.createResourceLocation(name));
  }
}
