package sandybay.apicurious.data.defaults;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.data.defaults.feature.FeatureKeys;

/*
    TODO:
      - Figure out why marshy hives aren't generating
      - Validate that the following hives are spawning:
        - Rocky
        - Nether
 */
public class BiomeModifierDefaults
{
  public static void defaults(BootstrapContext<BiomeModifier> bootstrap)
  {
    bootstrap.register(FeatureKeys.FOREST_HIVE.modifier(), new BiomeModifiers.AddFeaturesBiomeModifier(
            bootstrap.lookup(Registries.BIOME).getOrThrow(Tags.Biomes.IS_FOREST),
            HolderSet.direct(bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(FeatureKeys.FOREST_HIVE.placed())),
            GenerationStep.Decoration.VEGETAL_DECORATION
    ));
    bootstrap.register(FeatureKeys.MEADOWS_HIVE.modifier(), new BiomeModifiers.AddFeaturesBiomeModifier(
            bootstrap.lookup(Registries.BIOME).getOrThrow(ApicuriousTags.BiomeTags.IS_MEADOW),
            HolderSet.direct(bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(FeatureKeys.MEADOWS_HIVE.placed())),
            GenerationStep.Decoration.VEGETAL_DECORATION
    ));
    bootstrap.register(FeatureKeys.MODEST_HIVE.modifier(), new BiomeModifiers.AddFeaturesBiomeModifier(
            bootstrap.lookup(Registries.BIOME).getOrThrow(Tags.Biomes.IS_DESERT),
            HolderSet.direct(bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(FeatureKeys.MODEST_HIVE.placed())),
            GenerationStep.Decoration.VEGETAL_DECORATION
    ));
    bootstrap.register(FeatureKeys.TROPICAL_HIVE.modifier(), new BiomeModifiers.AddFeaturesBiomeModifier(
            bootstrap.lookup(Registries.BIOME).getOrThrow(Tags.Biomes.IS_JUNGLE),
            HolderSet.direct(bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(FeatureKeys.TROPICAL_HIVE.placed())),
            GenerationStep.Decoration.VEGETAL_DECORATION
    ));
    bootstrap.register(FeatureKeys.WINTRY_HIVE.modifier(), new BiomeModifiers.AddFeaturesBiomeModifier(
            bootstrap.lookup(Registries.BIOME).getOrThrow(Tags.Biomes.IS_SNOWY),
            HolderSet.direct(bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(FeatureKeys.WINTRY_HIVE.placed())),
            GenerationStep.Decoration.VEGETAL_DECORATION
    ));
    bootstrap.register(FeatureKeys.MARSHY_HIVE.modifier(), new BiomeModifiers.AddFeaturesBiomeModifier(
            bootstrap.lookup(Registries.BIOME).getOrThrow(Tags.Biomes.IS_SWAMP),
            HolderSet.direct(bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(FeatureKeys.MARSHY_HIVE.placed())),
            GenerationStep.Decoration.VEGETAL_DECORATION
    ));
    bootstrap.register(FeatureKeys.ROCKY_HIVE.modifier(), new BiomeModifiers.AddFeaturesBiomeModifier(
            bootstrap.lookup(Registries.BIOME).getOrThrow(Tags.Biomes.IS_OVERWORLD),
            HolderSet.direct(bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(FeatureKeys.ROCKY_HIVE.placed())),
            GenerationStep.Decoration.VEGETAL_DECORATION
    ));
    bootstrap.register(FeatureKeys.NETHER_HIVE.modifier(), new BiomeModifiers.AddFeaturesBiomeModifier(
            bootstrap.lookup(Registries.BIOME).getOrThrow(Tags.Biomes.IS_NETHER),
            HolderSet.direct(bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(FeatureKeys.NETHER_HIVE.placed())),
            GenerationStep.Decoration.VEGETAL_DECORATION
    ));
    bootstrap.register(FeatureKeys.ENDER_HIVE.modifier(), new BiomeModifiers.AddFeaturesBiomeModifier(
            bootstrap.lookup(Registries.BIOME).getOrThrow(Tags.Biomes.IS_END),
            HolderSet.direct(bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(FeatureKeys.ENDER_HIVE.placed())),
            GenerationStep.Decoration.VEGETAL_DECORATION
    ));
    bootstrap.register(FeatureKeys.WATER_HIVE.modifier(), new BiomeModifiers.AddFeaturesBiomeModifier(
            bootstrap.lookup(Registries.BIOME).getOrThrow(Tags.Biomes.IS_OCEAN),
            HolderSet.direct(bootstrap.lookup(Registries.PLACED_FEATURE).getOrThrow(FeatureKeys.WATER_HIVE.placed())),
            GenerationStep.Decoration.VEGETAL_DECORATION
    ));
  }
}
