package sandybay.apicurious.data.defaults.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.util.ApicuriousTags;

import java.util.ArrayList;
import java.util.List;

public class ApicuriousBiomeModifiers
{
  public static final ResourceKey<BiomeModifier> ADD_HANGING_FOREST_HIVE = ResourceKey.create(
          NeoForgeRegistries.Keys.BIOME_MODIFIERS, Apicurious.createIdentifier("add_hanging_forest_hive")
  );
  public static final ResourceKey<BiomeModifier> ADD_MEADOW_HIVE = ResourceKey.create(
          NeoForgeRegistries.Keys.BIOME_MODIFIERS, Apicurious.createIdentifier("add_meadow_hive")
  );
  public static final ResourceKey<BiomeModifier> ADD_MODEST_HIVE = ResourceKey.create(
          NeoForgeRegistries.Keys.BIOME_MODIFIERS, Apicurious.createIdentifier("add_modest_hive")
  );

  public static void defaults(BootstrapContext<BiomeModifier> bootstrap)
  {
    HolderGetter<Biome> biomeGetter = bootstrap.lookup(Registries.BIOME);
    HolderGetter<PlacedFeature> placedFeatureGetter = bootstrap.lookup(Registries.PLACED_FEATURE);
    bootstrap.register(ADD_HANGING_FOREST_HIVE,
            new BiomeModifiers.AddFeaturesBiomeModifier(
                    biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                    HolderSet.direct(placedFeatureGetter.getOrThrow(ApicuriousFeatureKeys.HANGING_FOREST_HIVE)),
                    GenerationStep.Decoration.TOP_LAYER_MODIFICATION
            )
    );
    bootstrap.register(ADD_MEADOW_HIVE,
            new BiomeModifiers.AddFeaturesBiomeModifier(
                    biomeGetter.getOrThrow(ApicuriousTags.BiomeTags.IS_MEADOW),
                    HolderSet.direct(placedFeatureGetter.getOrThrow(ApicuriousFeatureKeys.HANGING_FOREST_HIVE)),
                    GenerationStep.Decoration.TOP_LAYER_MODIFICATION
            )
    );
    List<Holder<Biome>> MODEST_BIOMES = new ArrayList<>();
    MODEST_BIOMES.addAll(biomeGetter.getOrThrow(Tags.Biomes.IS_DESERT).stream().toList());
    MODEST_BIOMES.addAll(biomeGetter.getOrThrow(Tags.Biomes.IS_SAVANNA).stream().toList());
    MODEST_BIOMES.addAll(biomeGetter.getOrThrow(Tags.Biomes.IS_BADLANDS).stream().toList());
    bootstrap.register(ADD_MODEST_HIVE,
            new BiomeModifiers.AddFeaturesBiomeModifier(
                    HolderSet.direct(MODEST_BIOMES),
                    HolderSet.direct(placedFeatureGetter.getOrThrow(ApicuriousFeatureKeys.HANGING_FOREST_HIVE)),
                    GenerationStep.Decoration.TOP_LAYER_MODIFICATION
            )
    );
    /**
     * bootstrap.register(ADD_MEADOW_HIVE,
     *             new BiomeModifiers.AddFeaturesBiomeModifier()
     *     );
     */
  }
}
