package sandybay.apicurious.data;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.data.defaults.*;
import sandybay.apicurious.data.defaults.allele.AlleleDefaults;
import sandybay.apicurious.data.defaults.condition.ConditionsDefaults;
import sandybay.apicurious.data.defaults.feature.configured.DefaultConfiguredFeatures;
import sandybay.apicurious.data.defaults.feature.placed.DefaultPlacedFeatures;

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
    builder.add(Registries.CONFIGURED_FEATURE, DefaultConfiguredFeatures::defaults);
    builder.add(Registries.PLACED_FEATURE, DefaultPlacedFeatures::defaults);
    builder.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, BiomeModifierDefaults::defaults);
    return builder;
  }
}
