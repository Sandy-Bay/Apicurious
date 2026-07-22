package sandybay.apicurious.data.server;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.VillagerTradeTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.registrar.BlockRegistrar;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.VillagerTradesDefaults;
import sandybay.apicurious.data.providers.AlleleTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ApicuriousTagProviders
{

  public ApicuriousTagProviders(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
  {
    new BlocksProvider(output, lookupProvider);
    new ItemsProvider(output, lookupProvider);
    new BiomesProvider(output, lookupProvider);
  }

  public static class BlocksProvider extends TagsProvider<Block>
  {

    public BlocksProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
      super(output, Registries.BLOCK, lookupProvider, Apicurious.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
      this.getOrCreateRawBuilder(ApicuriousTags.BlockTags.HIVE).addElement(BlockRegistrar.FOREST_HIVE.block().getId()).addElement(BlockRegistrar.MEADOW_HIVE.block().getId()).addElement(BlockRegistrar.MODEST_HIVE.block().getId()).addElement(BlockRegistrar.TROPICAL_HIVE.block().getId()).addElement(BlockRegistrar.WINTRY_HIVE.block().getId()).addElement(BlockRegistrar.MARSHY_HIVE.block().getId()).addElement(BlockRegistrar.ENDER_HIVE.block().getId()).addElement(BlockRegistrar.WATER_HIVE.block().getId()).addElement(BlockRegistrar.NETHER_HIVE.block().getId()).addElement(BlockRegistrar.ROCKY_HIVE.block().getId());
      this.getOrCreateRawBuilder(ApicuriousTags.BlockTags.CACTUS).addElement(Blocks.CACTUS.builtInRegistryHolder().getKey().identifier());
      this.getOrCreateRawBuilder(ApicuriousTags.BlockTags.JUNGLE).addElement(Blocks.COCOA.builtInRegistryHolder().getKey().identifier()).addElement(Blocks.VINE.builtInRegistryHolder().getKey().identifier());
      this.getOrCreateRawBuilder(ApicuriousTags.BlockTags.MUSHROOM).addElement(Blocks.RED_MUSHROOM.builtInRegistryHolder().getKey().identifier()).addElement(Blocks.RED_MUSHROOM_BLOCK.builtInRegistryHolder().getKey().identifier()).addElement(Blocks.BROWN_MUSHROOM.builtInRegistryHolder().getKey().identifier()).addElement(Blocks.BROWN_MUSHROOM_BLOCK.builtInRegistryHolder().getKey().identifier()).addElement(Blocks.MUSHROOM_STEM.builtInRegistryHolder().getKey().identifier());
      this.getOrCreateRawBuilder(ApicuriousTags.BlockTags.WHEAT).addElement(Blocks.WHEAT.builtInRegistryHolder().getKey().identifier());
      this.getOrCreateRawBuilder(ApicuriousTags.BlockTags.LILY_PAD).addElement(Blocks.LILY_PAD.builtInRegistryHolder().getKey().identifier());
      this.getOrCreateRawBuilder(ApicuriousTags.BlockTags.REDSTONE).addElement(Blocks.REDSTONE_ORE.builtInRegistryHolder().getKey().identifier()).addElement(Blocks.REDSTONE_WIRE.builtInRegistryHolder().getKey().identifier()).addElement(Blocks.REDSTONE_TORCH.builtInRegistryHolder().getKey().identifier()).addElement(Blocks.REDSTONE_WALL_TORCH.builtInRegistryHolder().getKey().identifier()).addElement(Blocks.REDSTONE_BLOCK.builtInRegistryHolder().getKey().identifier());
      this.getOrCreateRawBuilder(ApicuriousTags.BlockTags.DEAD_BUSH).addElement(Blocks.DEAD_BUSH.builtInRegistryHolder().getKey().identifier()).addElement(Blocks.POTTED_DEAD_BUSH.builtInRegistryHolder().getKey().identifier());
      this.getOrCreateRawBuilder(ApicuriousTags.BlockTags.WOOD).addTag(BlockTags.LOGS.location()).addTag(BlockTags.PLANKS.location());
      this.getOrCreateRawBuilder(ApicuriousTags.BlockTags.SUGAR_CANE).addElement(Blocks.SUGAR_CANE.builtInRegistryHolder().getKey().identifier());
    }
  }

  public static class ItemsProvider extends TagsProvider<Item>
  {

    public ItemsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
      super(output, Registries.ITEM, lookupProvider, Apicurious.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
      this.getOrCreateRawBuilder(ApicuriousTags.ItemTags.IS_SIEVE_TOOL).addElement(ItemRegistrar.SIEVE.itemKey().identifier());
      this.getOrCreateRawBuilder(ApicuriousTags.ItemTags.DROP_HONEY).addElement(ItemRegistrar.HONEY_DROP.itemKey().identifier()).addElement(ItemRegistrar.HONEY_DEW.itemKey().identifier());
      this.getOrCreateRawBuilder(Tags.Items.HIDDEN_FROM_RECIPE_VIEWERS).addElement(ItemRegistrar.DRONE.itemKey().identifier()).addElement(ItemRegistrar.PRINCESS.itemKey().identifier());
    }
  }

  public static class BiomesProvider extends TagsProvider<Biome>
  {

    public BiomesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
      super(output, Registries.BIOME, lookupProvider, Apicurious.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
      this.getOrCreateRawBuilder(ApicuriousTags.BiomeTags.IS_MEADOW).addElement(Biomes.MEADOW.identifier()).addElement(Biomes.PLAINS.identifier()).addElement(Biomes.SUNFLOWER_PLAINS.identifier()).addElement(Biomes.SAVANNA.identifier()).addElement(Biomes.SAVANNA_PLATEAU.identifier()).addElement(Biomes.WINDSWEPT_HILLS.identifier()).addElement(Biomes.CHERRY_GROVE.identifier());
      registerHumidityPreferences();
      registerTemperaturePreferences();
    }

    public void registerHumidityPreferences()
    {
      this.getOrCreateRawBuilder(ApicuriousTags.BiomeTags.HELLISH_HUMIDITY).addElement(Biomes.BASALT_DELTAS.identifier()).addElement(Biomes.CRIMSON_FOREST.identifier()).addElement(Biomes.NETHER_WASTES.identifier()).addElement(Biomes.SOUL_SAND_VALLEY.identifier()).addElement(Biomes.WARPED_FOREST.identifier()).addElement(Biomes.THE_END.identifier()).addElement(Biomes.SMALL_END_ISLANDS.identifier()).addElement(Biomes.END_MIDLANDS.identifier()).addElement(Biomes.END_HIGHLANDS.identifier()).addElement(Biomes.END_BARRENS.identifier()).addTag(Tags.Biomes.IS_DRY_NETHER.location()).addTag(Tags.Biomes.IS_END.location());

      this.getOrCreateRawBuilder(ApicuriousTags.BiomeTags.ARID_HUMIDITY).addElement(Biomes.DESERT.identifier()).addElement(Biomes.BADLANDS.identifier()).addElement(Biomes.ERODED_BADLANDS.identifier()).addElement(Biomes.WOODED_BADLANDS.identifier()).addElement(Biomes.SAVANNA.identifier()).addElement(Biomes.SAVANNA_PLATEAU.identifier()).addElement(Biomes.WINDSWEPT_SAVANNA.identifier()).addTag(Tags.Biomes.IS_WET_NETHER.location()).addTag(Tags.Biomes.IS_WET_END.location());

      this.getOrCreateRawBuilder(ApicuriousTags.BiomeTags.AVERAGE_HUMIDITY).addElement(Biomes.PLAINS.identifier()).addElement(Biomes.SUNFLOWER_PLAINS.identifier()).addElement(Biomes.SNOWY_PLAINS.identifier()).addElement(Biomes.ICE_SPIKES.identifier()).addElement(Biomes.MEADOW.identifier()).addElement(Biomes.CHERRY_GROVE.identifier()).addElement(Biomes.GROVE.identifier()).addElement(Biomes.BIRCH_FOREST.identifier()).addElement(Biomes.OLD_GROWTH_BIRCH_FOREST.identifier()).addElement(Biomes.DARK_FOREST.identifier()).addElement(Biomes.SNOWY_SLOPES.identifier()).addElement(Biomes.JAGGED_PEAKS.identifier()).addElement(Biomes.FROZEN_PEAKS.identifier()).addElement(Biomes.STONY_PEAKS.identifier()).addElement(Biomes.WINDSWEPT_HILLS.identifier()).addElement(Biomes.WINDSWEPT_GRAVELLY_HILLS.identifier()).addElement(Biomes.WINDSWEPT_FOREST.identifier()).addElement(Biomes.TAIGA.identifier()).addElement(Biomes.OLD_GROWTH_PINE_TAIGA.identifier()).addElement(Biomes.OLD_GROWTH_SPRUCE_TAIGA.identifier()).addTag(Tags.Biomes.IS_DRY_END.location());

      this.getOrCreateRawBuilder(ApicuriousTags.BiomeTags.DAMP_HUMIDITY).addElement(Biomes.RIVER.identifier()).addElement(Biomes.FROZEN_RIVER.identifier()).addElement(Biomes.BEACH.identifier()).addElement(Biomes.SNOWY_BEACH.identifier()).addElement(Biomes.STONY_SHORE.identifier()).addElement(Biomes.SWAMP.identifier()).addElement(Biomes.MANGROVE_SWAMP.identifier()).addElement(Biomes.DRIPSTONE_CAVES.identifier()).addElement(Biomes.MUSHROOM_FIELDS.identifier()).addElement(Biomes.JUNGLE.identifier()).addElement(Biomes.SPARSE_JUNGLE.identifier()).addElement(Biomes.BAMBOO_JUNGLE.identifier()).addElement(Biomes.DEEP_DARK.identifier()).addElement(Biomes.LUSH_CAVES.identifier());

      this.getOrCreateRawBuilder(ApicuriousTags.BiomeTags.AQUATIC_HUMIDITY).addElement(Biomes.OCEAN.identifier()).addElement(Biomes.DEEP_OCEAN.identifier()).addElement(Biomes.WARM_OCEAN.identifier()).addElement(Biomes.LUKEWARM_OCEAN.identifier()).addElement(Biomes.DEEP_LUKEWARM_OCEAN.identifier()).addElement(Biomes.COLD_OCEAN.identifier()).addElement(Biomes.DEEP_COLD_OCEAN.identifier()).addElement(Biomes.FROZEN_OCEAN.identifier()).addElement(Biomes.DEEP_FROZEN_OCEAN.identifier());
    }

    @SuppressWarnings("unchecked")
    public void registerTemperaturePreferences()
    {
      this.getOrCreateRawBuilder(ApicuriousTags.BiomeTags.HELLISH_TEMPERATURE).addTag(Tags.Biomes.IS_HOT_NETHER.location());

      this.getOrCreateRawBuilder(ApicuriousTags.BiomeTags.HOT_TEMPERATURE).addElement(Biomes.DESERT.identifier()).addElement(Biomes.SAVANNA.identifier()).addElement(Biomes.SAVANNA_PLATEAU.identifier()).addElement(Biomes.WINDSWEPT_SAVANNA.identifier()).addElement(Biomes.BADLANDS.identifier()).addElement(Biomes.ERODED_BADLANDS.identifier()).addElement(Biomes.WOODED_BADLANDS.identifier()).addElement(Biomes.WARM_OCEAN.identifier()).addElement(Biomes.LUKEWARM_OCEAN.identifier()).addElement(Biomes.DEEP_LUKEWARM_OCEAN.identifier()).addElement(Biomes.SPARSE_JUNGLE.identifier()).addElement(Biomes.JUNGLE.identifier()).addElement(Biomes.BAMBOO_JUNGLE.identifier()).addTag(Tags.Biomes.IS_COLD_NETHER.location());

      this.getOrCreateRawBuilder(ApicuriousTags.BiomeTags.AVERAGE_TEMPERATURE).addElement(Biomes.FOREST.identifier()).addElement(Biomes.DARK_FOREST.identifier()).addElement(Biomes.FLOWER_FOREST.identifier()).addElement(Biomes.WINDSWEPT_FOREST.identifier()).addElement(Biomes.BIRCH_FOREST.identifier()).addElement(Biomes.OLD_GROWTH_BIRCH_FOREST.identifier()).addElement(Biomes.MEADOW.identifier()).addElement(Biomes.CHERRY_GROVE.identifier()).addElement(Biomes.OCEAN.identifier()).addElement(Biomes.PLAINS.identifier()).addElement(Biomes.SUNFLOWER_PLAINS.identifier()).addElement(Biomes.RIVER.identifier()).addElement(Biomes.OLD_GROWTH_PINE_TAIGA.identifier()).addElement(Biomes.WINDSWEPT_HILLS.identifier()).addElement(Biomes.BEACH.identifier()).addElement(Biomes.LUSH_CAVES.identifier()).addElement(Biomes.SWAMP.identifier()).addElement(Biomes.MANGROVE_SWAMP.identifier()).addElement(Biomes.DRIPSTONE_CAVES.identifier()).addElement(Biomes.MUSHROOM_FIELDS.identifier()).addTag(Tags.Biomes.IS_HOT_END.location());

      this.getOrCreateRawBuilder(ApicuriousTags.BiomeTags.COLD_TEMPERATURE).addElement(Biomes.TAIGA.identifier()).addElement(Biomes.SNOWY_TAIGA.identifier()).addElement(Biomes.OLD_GROWTH_PINE_TAIGA.identifier()).addElement(Biomes.OLD_GROWTH_SPRUCE_TAIGA.identifier()).addElement(Biomes.SNOWY_PLAINS.identifier()).addElement(Biomes.SNOWY_BEACH.identifier()).addElement(Biomes.SNOWY_SLOPES.identifier()).addElement(Biomes.COLD_OCEAN.identifier()).addElement(Biomes.DEEP_COLD_OCEAN.identifier()).addElement(Biomes.WINDSWEPT_GRAVELLY_HILLS.identifier()).addElement(Biomes.STONY_PEAKS.identifier()).addElement(Biomes.JAGGED_PEAKS.identifier()).addElement(Biomes.STONY_SHORE.identifier()).addTag(Tags.Biomes.IS_COLD_END.location());

      this.getOrCreateRawBuilder(ApicuriousTags.BiomeTags.ICY_TEMPERATURE).addElement(Biomes.ICE_SPIKES.identifier()).addElement(Biomes.FROZEN_RIVER.identifier()).addElement(Biomes.FROZEN_OCEAN.identifier()).addElement(Biomes.DEEP_FROZEN_OCEAN.identifier()).addElement(Biomes.FROZEN_PEAKS.identifier());
    }
  }

  public static class AlleleProvider extends AlleleTagsProvider
  {

    public AlleleProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider)
    {
      super(pOutput, pLookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider)
    {
      this.getOrCreateRawBuilder(ApicuriousTags.AlleleTags.BASELINE_BEE).addElement(ApicuriousSpecies.FOREST.species().identifier()).addElement(ApicuriousSpecies.MEADOW.species().identifier()).addElement(ApicuriousSpecies.MODEST.species().identifier()).addElement(ApicuriousSpecies.TROPICAL.species().identifier()).addElement(ApicuriousSpecies.WINTRY.species().identifier()).addElement(ApicuriousSpecies.MARSHY.species().identifier()).addElement(ApicuriousSpecies.ROCKY.species().identifier()).addElement(ApicuriousSpecies.WATER.species().identifier()).addElement(ApicuriousSpecies.NETHER.species().identifier()).addElement(ApicuriousSpecies.ENDER.species().identifier());
    }
  }

  /**
   * TODO: Replace this with a custom villager and building.
   */
  public static class VillagerTradeTagsProvider extends TagsProvider<VillagerTrade> {

    public VillagerTradeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
      super(output, Registries.VILLAGER_TRADE, lookupProvider, Apicurious.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
      this.tag(VillagerTradeTags.FARMER_LEVEL_3).addOptional(VillagerTradesDefaults.PROVEN_FRAME_L3).addOptional(VillagerTradesDefaults.STEADFAST_DRONE_L3);
      this.tag(VillagerTradeTags.FARMER_LEVEL_4).addOptional(VillagerTradesDefaults.PROVEN_FRAME_L4).addOptional(VillagerTradesDefaults.STEADFAST_DRONE_L4);
      this.tag(VillagerTradeTags.FARMER_LEVEL_5).addOptional(VillagerTradesDefaults.PROVEN_FRAME_L5).addOptional(VillagerTradesDefaults.STEADFAST_DRONE_L5);
    }
  }
}
