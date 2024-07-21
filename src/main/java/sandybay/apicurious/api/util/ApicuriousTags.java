package sandybay.apicurious.api.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import sandybay.apicurious.Apicurious;

public class ApicuriousTags {

  public static class BlockTags {
    public static final TagKey<Block> HIVE = create("hive");

    private static TagKey<Block> create(String path) {
      return bind(Registries.BLOCK, path);
    }
  }

  public static class BiomeTags {

    public static final TagKey<Biome> IS_MEADOW = create("is_meadow");

    private static TagKey<Biome> create(String path) {
      return bind(Registries.BIOME, path);
    }
  }

  public static class ItemTags {

    public static final TagKey<Item> IS_SIEVE_TOOL = create("is_sieve_tool");

    private static TagKey<Item> create(String path) {
      return bind(Registries.ITEM, path);
    }
  }

  private static <T> TagKey<T> bind(ResourceKey<Registry<T>> registry, String path) {
    return TagKey.create(registry, Apicurious.createResourceLocation(path));
  }
}
