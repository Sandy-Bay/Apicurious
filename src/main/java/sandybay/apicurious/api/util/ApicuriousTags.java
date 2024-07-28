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

  private static <T> TagKey<T> bind(ResourceKey<Registry<T>> registry, String path) {
    return TagKey.create(registry, Apicurious.createResourceLocation(path));
  }

  public static class BlockTags {
    public static final TagKey<Block> HIVE = create("hive");

    private static TagKey<Block> create(String path) {
      return bind(Registries.BLOCK, path);
    }
  }

  public static class BiomeTags {

    public static final TagKey<Biome> IS_MEADOW = create("is_meadow");

    // HumidityPreference
    public static final TagKey<Biome> HELLISH_HUMIDITY = create("humidity/hellish");
    public static final TagKey<Biome> ARID_HUMIDITY = create("humidity/arid");
    public static final TagKey<Biome> AVERAGE_HUMIDITY = create("humidity/average");
    public static final TagKey<Biome> DAMP_HUMIDITY = create("humidity/damp");
    public static final TagKey<Biome> AQUATIC_HUMIDITY = create("humidity/aquatic");

    // TemperaturePreference
    public static final TagKey<Biome> HELLISH_TEMPERATURE = create("temperature/infernal");
    public static final TagKey<Biome> HOT_TEMPERATURE = create("temperature/hot");
    public static final TagKey<Biome> AVERAGE_TEMPERATURE = create("temperature/average");
    public static final TagKey<Biome> COLD_TEMPERATURE = create("temperature/cold");
    public static final TagKey<Biome> ICY_TEMPERATURE = create("temperature/icy");

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
}
