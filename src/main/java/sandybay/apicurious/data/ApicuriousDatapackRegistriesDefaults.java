package sandybay.apicurious.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.data.defaults.AlleleDefaults;
import sandybay.apicurious.data.defaults.mutations.MutationConditionsDefaults;
import sandybay.apicurious.data.defaults.mutations.MutationDefaults;

import java.util.concurrent.CompletableFuture;

public class ApicuriousDatapackRegistriesDefaults
{

  public static RegistrySetBuilder registerDataPackRegistryDefaults(CompletableFuture<HolderLookup.Provider> lookupProvider)
  {
    RegistrySetBuilder builder = new RegistrySetBuilder();
    builder.add(ApicuriousRegistries.ALLELES, AlleleDefaults::defaults);
    builder.add(ApicuriousRegistries.MUTATIONS, MutationDefaults::defaults);
    builder.add(ApicuriousRegistries.MUTATION_CONDITIONS, MutationConditionsDefaults::defaults);
    builder.add(Registries.CONFIGURED_FEATURE, bootstrap ->
    {
      // TODO: Implement generation for the bee hives
    });
    builder.add(Registries.PLACED_FEATURE, bootstrap ->
    {
      // TODO: Implement generation for the bee hives
    });

    return builder;
  }

  public static RegistrySetBuilder registerPostAlleleRegistryDefaults()
  {
    RegistrySetBuilder builder = new RegistrySetBuilder();

    return builder;
  }
}
