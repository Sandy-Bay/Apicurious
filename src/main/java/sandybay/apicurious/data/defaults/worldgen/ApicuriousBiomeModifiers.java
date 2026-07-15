package sandybay.apicurious.data.defaults.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.util.ApicuriousTags;

import java.util.List;

public class ApicuriousBiomeModifiers
{
  public static final ResourceKey<BiomeModifier> ADD_FOREST_HIVE = key("add_forest_hive");
  public static final ResourceKey<BiomeModifier> ADD_MEADOW_HIVE = key("add_meadow_hive");
  public static final ResourceKey<BiomeModifier> ADD_TROPICAL_HIVE = key("add_tropical_hive");
  public static final ResourceKey<BiomeModifier> ADD_WINTRY_HIVE = key("add_wintry_hive");
  public static final ResourceKey<BiomeModifier> ADD_MARSHY_HIVE = key("add_marshy_hive");
  public static final ResourceKey<BiomeModifier> ADD_ROCKY_HIVE = key("add_rocky_hive");
  public static final ResourceKey<BiomeModifier> ADD_ENDER_HIVE = key("add_ender_hive");

  public static final ResourceKey<BiomeModifier> ADD_NETHER_HIVE_HANGING = key("add_nether_hive_hanging");
  public static final ResourceKey<BiomeModifier> ADD_NETHER_HIVE_GROUND = key("add_nether_hive_ground");

  public static final ResourceKey<BiomeModifier> ADD_MODEST_HIVE_DESERT = key("add_modest_hive_desert");
  public static final ResourceKey<BiomeModifier> ADD_MODEST_HIVE_SAVANNA = key("add_modest_hive_savanna");
  public static final ResourceKey<BiomeModifier> ADD_MODEST_HIVE_BADLANDS = key("add_modest_hive_badlands");

  public static final ResourceKey<BiomeModifier> ADD_WATER_HIVE_OCEAN = key("add_water_hive_ocean");
  public static final ResourceKey<BiomeModifier> ADD_WATER_HIVE_RIVER = key("add_water_hive_river");

  public static void defaults(BootstrapContext<BiomeModifier> bootstrap)
  {
    HolderGetter<Biome> biomeGetter = bootstrap.lookup(Registries.BIOME);
    HolderGetter<PlacedFeature> placedFeatureGetter = bootstrap.lookup(Registries.PLACED_FEATURE);

    addFeature(bootstrap, ADD_FOREST_HIVE, biomeGetter.getOrThrow(BiomeTags.IS_FOREST), placedFeatureGetter.getOrThrow(ApicuriousFeatureKeys.FOREST_HIVE));

    addFeature(bootstrap, ADD_MEADOW_HIVE, biomeGetter.getOrThrow(ApicuriousTags.BiomeTags.IS_MEADOW), placedFeatureGetter.getOrThrow(ApicuriousFeatureKeys.MEADOW_HIVE));

    addFeature(bootstrap, ADD_TROPICAL_HIVE, biomeGetter.getOrThrow(BiomeTags.IS_JUNGLE), placedFeatureGetter.getOrThrow(ApicuriousFeatureKeys.TROPICAL_HIVE));

    addFeature(bootstrap, ADD_WINTRY_HIVE, biomeGetter.getOrThrow(Tags.Biomes.IS_COLD_OVERWORLD), placedFeatureGetter.getOrThrow(ApicuriousFeatureKeys.WINTRY_HIVE));

    addFeature(bootstrap, ADD_MARSHY_HIVE, biomeGetter.getOrThrow(Tags.Biomes.IS_SWAMP), placedFeatureGetter.getOrThrow(ApicuriousFeatureKeys.MARSHY_HIVE));

    Holder<PlacedFeature> modestHive = placedFeatureGetter.getOrThrow(ApicuriousFeatureKeys.MODEST_HIVE);
    addFeature(bootstrap, ADD_MODEST_HIVE_DESERT, biomeGetter.getOrThrow(Tags.Biomes.IS_DESERT), modestHive);
    addFeature(bootstrap, ADD_MODEST_HIVE_SAVANNA, biomeGetter.getOrThrow(Tags.Biomes.IS_SAVANNA), modestHive);
    addFeature(bootstrap, ADD_MODEST_HIVE_BADLANDS, biomeGetter.getOrThrow(Tags.Biomes.IS_BADLANDS), modestHive);
    addFeature(bootstrap, ADD_ROCKY_HIVE, biomeGetter.getOrThrow(BiomeTags.IS_OVERWORLD), placedFeatureGetter.getOrThrow(ApicuriousFeatureKeys.ROCKY_HIVE));
    Holder<Biome> warpedForest = biomeGetter.getOrThrow(Biomes.WARPED_FOREST);
    Holder<Biome> crimsonForest = biomeGetter.getOrThrow(Biomes.CRIMSON_FOREST);
    addFeature(bootstrap, ADD_NETHER_HIVE_HANGING, HolderSet.direct(List.of(warpedForest, crimsonForest)), placedFeatureGetter.getOrThrow(ApicuriousFeatureKeys.NETHER_HIVE_HANGING));

    Holder<Biome> netherWastes = biomeGetter.getOrThrow(Biomes.NETHER_WASTES);
    Holder<Biome> soulSandValley = biomeGetter.getOrThrow(Biomes.SOUL_SAND_VALLEY);
    Holder<Biome> basaltDeltas = biomeGetter.getOrThrow(Biomes.BASALT_DELTAS);
    addFeature(bootstrap, ADD_NETHER_HIVE_GROUND, HolderSet.direct(List.of(netherWastes, soulSandValley, basaltDeltas)), placedFeatureGetter.getOrThrow(ApicuriousFeatureKeys.NETHER_HIVE_GROUND));

    addFeature(bootstrap, ADD_ENDER_HIVE, biomeGetter.getOrThrow(BiomeTags.IS_END), placedFeatureGetter.getOrThrow(ApicuriousFeatureKeys.ENDER_HIVE));

    Holder<PlacedFeature> waterHive = placedFeatureGetter.getOrThrow(ApicuriousFeatureKeys.WATER_HIVE);
    addFeature(bootstrap, ADD_WATER_HIVE_OCEAN, biomeGetter.getOrThrow(BiomeTags.IS_OCEAN), waterHive);
    addFeature(bootstrap, ADD_WATER_HIVE_RIVER, biomeGetter.getOrThrow(BiomeTags.IS_RIVER), waterHive);
  }

  private static void addFeature(BootstrapContext<BiomeModifier> bootstrap, ResourceKey<BiomeModifier> key,
                                 HolderSet<Biome> biomes, Holder<PlacedFeature> feature)
  {
    bootstrap.register(key, new BiomeModifiers.AddFeaturesBiomeModifier(biomes, HolderSet.direct(feature), GenerationStep.Decoration.TOP_LAYER_MODIFICATION));
  }

  private static ResourceKey<BiomeModifier> key(String name)
  {
    return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Apicurious.createIdentifier(name));
  }
}