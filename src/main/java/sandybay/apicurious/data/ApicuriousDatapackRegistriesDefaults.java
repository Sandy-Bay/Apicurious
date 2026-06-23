package sandybay.apicurious.data;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.data.defaults.CentrifugeRecipeDefaults;
import sandybay.apicurious.data.defaults.FunctionsDefaults;
import sandybay.apicurious.data.defaults.MutationDefaults;
import sandybay.apicurious.data.defaults.OutputTableDefaults;
import sandybay.apicurious.data.defaults.allele.AlleleDefaults;
import sandybay.apicurious.data.defaults.condition.ConditionsDefaults;

public class ApicuriousDatapackRegistriesDefaults
{

  public static RegistrySetBuilder registerDataPackRegistryDefaults()
  {
    RegistrySetBuilder builder = new RegistrySetBuilder();
    builder.add(ApicuriousRegistries.ALLELES, AlleleDefaults::defaults);
    builder.add(ApicuriousRegistries.MUTATIONS, MutationDefaults::registerDefaults);
    builder.add(ApicuriousRegistries.CONDITIONS, ConditionsDefaults::defaults);
    builder.add(ApicuriousRegistries.FUNCTIONS, FunctionsDefaults::defaults);
    builder.add(ApicuriousRegistries.OUTPUT_TABLES, OutputTableDefaults::defaults);
    builder.add(ApicuriousRegistries.CENTRIFUGE_RECIPES, CentrifugeRecipeDefaults::defaults);
    builder.add(Registries.CONFIGURED_FEATURE, bootstrap ->
    {
      // Todo: Implement generation for the bee hives
    });
    builder.add(Registries.PLACED_FEATURE, bootstrap ->
    {
      // Todo: Implement generation for the bee hives
    });

    return builder;
  }

  public static RegistrySetBuilder registerPostAlleleRegistryDefaults()
  {
    RegistrySetBuilder builder = new RegistrySetBuilder();

    return builder;
  }
}
