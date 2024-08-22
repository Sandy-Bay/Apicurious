package sandybay.apicurious.data.defaults.condition;

import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class ConditionKeys
{
  public static final ResourceKey<ICondition> IS_CHRISTMAS = condition("is_christmas");
  public static final ResourceKey<ICondition> IS_NEW_YEARS = condition("is_new_years");
  public static final ResourceKey<ICondition> IS_HALLOWEEN = condition("is_halloween");

  public static ResourceKey<ICondition> condition(String name)
  {
    return ResourceKey.create(ApicuriousRegistries.CONDITIONS, Apicurious.createResourceLocation(name));
  }
}
