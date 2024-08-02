package sandybay.apicurious.data;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;
import sandybay.apicurious.common.register.ApicuriousItemRegistration;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ApicuriousTagProviders
{

  public ApicuriousTagProviders(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper)
  {
    new BlocksProvider(output, lookupProvider, existingFileHelper);
    new ItemsProvider(output, lookupProvider, existingFileHelper);
    new BiomesProvider(output, lookupProvider, existingFileHelper);
  }

  public static class BlocksProvider extends TagsProvider<Block>
  {

    public BlocksProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper)
    {
      super(output, Registries.BLOCK, lookupProvider, Apicurious.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
      this.tag(ApicuriousTags.BlockTags.HIVE).add(
              ApicuriousBlockRegistration.FOREST_HIVE.block().getKey(),
              ApicuriousBlockRegistration.MEADOW_HIVE.block().getKey(),
              ApicuriousBlockRegistration.MODEST_HIVE.block().getKey(),
              ApicuriousBlockRegistration.TROPICAL_HIVE.block().getKey(),
              ApicuriousBlockRegistration.WINTRY_HIVE.block().getKey(),
              ApicuriousBlockRegistration.MARSHY_HIVE.block().getKey(),
              ApicuriousBlockRegistration.ENDER_HIVE.block().getKey()
      );
    }
  }

  public static class ItemsProvider extends TagsProvider<Item>
  {

    public ItemsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper)
    {
      super(output, Registries.ITEM, lookupProvider, Apicurious.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
      this.tag(ApicuriousTags.ItemTags.IS_SIEVE_TOOL).add(
              ApicuriousItemRegistration.SIEVE.getKey()
      );
    }
  }

  public static class BiomesProvider extends TagsProvider<Biome>
  {

    protected BiomesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper)
    {
      super(output, Registries.BIOME, lookupProvider, Apicurious.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
      this.tag(ApicuriousTags.BiomeTags.IS_MEADOW).add(
              Biomes.MEADOW,
              Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS,
              Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU,
              Biomes.WINDSWEPT_HILLS, Biomes.CHERRY_GROVE
      );
      registerHumidityPreferences();
      registerTemperaturePreferences();
    }

    public void registerHumidityPreferences()
    {
      this.tag(ApicuriousTags.BiomeTags.HELLISH_HUMIDITY)
              .add(Biomes.BASALT_DELTAS, Biomes.CRIMSON_FOREST, Biomes.NETHER_WASTES, Biomes.SOUL_SAND_VALLEY, Biomes.WARPED_FOREST)
              .add(Biomes.THE_END, Biomes.SMALL_END_ISLANDS, Biomes.END_MIDLANDS, Biomes.END_HIGHLANDS, Biomes.END_BARRENS)
              .addTag(Tags.Biomes.IS_DRY_NETHER).addTag(Tags.Biomes.IS_END);
      this.tag(ApicuriousTags.BiomeTags.ARID_HUMIDITY)
              .add(
                      Biomes.DESERT,
                      Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS,
                      Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA
              ).addTag(Tags.Biomes.IS_WET_NETHER).addTag(Tags.Biomes.IS_WET_END);
      this.tag(ApicuriousTags.BiomeTags.AVERAGE_HUMIDITY)
              .add(
                      Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS,
                      Biomes.SNOWY_PLAINS, Biomes.ICE_SPIKES,
                      Biomes.MEADOW, Biomes.CHERRY_GROVE, Biomes.GROVE,
                      Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.DARK_FOREST,
                      Biomes.SNOWY_SLOPES,
                      Biomes.JAGGED_PEAKS, Biomes.FROZEN_PEAKS, Biomes.STONY_PEAKS,
                      Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_FOREST,
                      Biomes.TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA
              ).addTag(Tags.Biomes.IS_DRY_END);
      this.tag(ApicuriousTags.BiomeTags.DAMP_HUMIDITY)
              .add(
                      Biomes.RIVER, Biomes.FROZEN_RIVER,
                      Biomes.BEACH, Biomes.SNOWY_BEACH, Biomes.STONY_SHORE,
                      Biomes.SWAMP, Biomes.MANGROVE_SWAMP,
                      Biomes.DRIPSTONE_CAVES, Biomes.MUSHROOM_FIELDS,
                      Biomes.JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE,
                      Biomes.DEEP_DARK, Biomes.LUSH_CAVES
              );
      this.tag(ApicuriousTags.BiomeTags.AQUATIC_HUMIDITY)
              .add(
                      Biomes.OCEAN, Biomes.DEEP_OCEAN,
                      Biomes.WARM_OCEAN,
                      Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN,
                      Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN,
                      Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN
              );
    }

    @SuppressWarnings("unchecked")
    public void registerTemperaturePreferences()
    {
      this.tag(ApicuriousTags.BiomeTags.HELLISH_TEMPERATURE)
              .addTag(Tags.Biomes.IS_HOT_NETHER);
      this.tag(ApicuriousTags.BiomeTags.HOT_TEMPERATURE)
              .add(
                      Biomes.DESERT, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA,
                      Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS,
                      Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN,
                      Biomes.SPARSE_JUNGLE, Biomes.JUNGLE, Biomes.BAMBOO_JUNGLE
              ).addTag(Tags.Biomes.IS_COLD_NETHER);
      this.tag(ApicuriousTags.BiomeTags.AVERAGE_TEMPERATURE)
              .add(
                      Biomes.FOREST, Biomes.DARK_FOREST, Biomes.FLOWER_FOREST, Biomes.WINDSWEPT_FOREST,
                      Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.MEADOW, Biomes.CHERRY_GROVE, Biomes.OCEAN,
                      Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.RIVER, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.WINDSWEPT_HILLS,
                      Biomes.BEACH, Biomes.LUSH_CAVES, Biomes.SWAMP, Biomes.MANGROVE_SWAMP, Biomes.DRIPSTONE_CAVES, Biomes.MUSHROOM_FIELDS
              )
              .addTags(
                      Tags.Biomes.IS_HOT_END
              );
      this.tag(ApicuriousTags.BiomeTags.COLD_TEMPERATURE)
              .add(
                      Biomes.TAIGA, Biomes.SNOWY_TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA,
                      Biomes.SNOWY_PLAINS, Biomes.SNOWY_BEACH, Biomes.SNOWY_SLOPES, Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN,
                      Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.STONY_PEAKS, Biomes.JAGGED_PEAKS, Biomes.STONY_SHORE

              ).addTag(Tags.Biomes.IS_COLD_END);
      this.tag(ApicuriousTags.BiomeTags.ICY_TEMPERATURE)
              .add(
                      Biomes.ICE_SPIKES, Biomes.FROZEN_RIVER, Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.FROZEN_PEAKS
              );
    }

    public List<ResourceKey<Biome>> getBiomesWithHumidity(HolderLookup.RegistryLookup<Biome> lookup, float minHumidity, float maxHumidity)
    {
      return lookup.filterElements(biome ->
      {
        Biome.ClimateSettings climate = biome.getModifiedClimateSettings();
        return climate.downfall() > minHumidity && climate.downfall() < maxHumidity;
      }).listElements().map(Holder.Reference::getKey).toList();
    }
  }
}
