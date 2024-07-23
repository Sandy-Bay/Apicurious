package sandybay.apicurious.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;
import sandybay.apicurious.common.register.ApicuriousItemRegistration;

import java.util.concurrent.CompletableFuture;

public class ApicuriousTagProvider {

  public ApicuriousTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
    new BlocksProvider(output, lookupProvider, existingFileHelper);
    new ItemsProvider(output, lookupProvider, existingFileHelper);
    new BiomesProvider(output, lookupProvider, existingFileHelper);
  }

  public static class BlocksProvider extends TagsProvider<Block> {

    public BlocksProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
      super(output, Registries.BLOCK, lookupProvider, Apicurious.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
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

  public static class ItemsProvider extends TagsProvider<Item> {

    public ItemsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
      super(output, Registries.ITEM, lookupProvider, Apicurious.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
      this.tag(ApicuriousTags.ItemTags.IS_SIEVE_TOOL).add(
              ApicuriousItemRegistration.SIEVE.getKey()
      );
    }
  }

  public static class BiomesProvider extends TagsProvider<Biome> {

    protected BiomesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
      super(output, Registries.BIOME, lookupProvider, Apicurious.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
      this.tag(ApicuriousTags.BiomeTags.IS_MEADOW).add(
              Biomes.MEADOW,
              Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS,
              Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU,
              Biomes.WINDSWEPT_HILLS, Biomes.CHERRY_GROVE
      );
    }
  }
}
