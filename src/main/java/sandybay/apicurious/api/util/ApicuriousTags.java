package sandybay.apicurious.api.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class ApicuriousTags
{

  private static <T> TagKey<T> bind(ResourceKey<Registry<T>> registry, String path)
  {
    return TagKey.create(registry, Apicurious.createIdentifier(path));
  }

  public static class BlockTags
  {
    public static final TagKey<Block> HIVE = create("hive");
    public static final TagKey<Block> CACTUS = create("cactus");
    public static final TagKey<Block> JUNGLE = create("jungle");
    public static final TagKey<Block> MUSHROOM = create("mushroom");
    public static final TagKey<Block> WHEAT = create("wheat");
    public static final TagKey<Block> LILY_PAD = create("lily_pad");
    public static final TagKey<Block> REDSTONE = create("redstone");
    public static final TagKey<Block> DEAD_BUSH = create("dead_bush");
    public static final TagKey<Block> WOOD = create("wood");
    public static final TagKey<Block> SUGAR_CANE = create("sugar_cane");

    private static TagKey<Block> create(String path)
    {
      return bind(Registries.BLOCK, path);
    }
  }

  public static class BiomeTags
  {

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

    private static TagKey<Biome> create(String path)
    {
      return bind(Registries.BIOME, path);
    }
  }

  public static class ItemTags
  {

    public static final TagKey<Item> IS_SIEVE_TOOL = create("is_sieve_tool");
    public static final TagKey<Item> DROP_HONEY = create("drop/honey");

    private static TagKey<Item> create(String path)
    {
      return bind(Registries.ITEM, path);
    }
  }

  public static class AlleleTags
  {
    public static final TagKey<IAllele<?>> BASELINE_BEE = create("baseline_bee");

    private static TagKey<IAllele<?>> create(String path)
    {
      return bind(ApicuriousRegistries.ALLELES, path);
    }
  }
}
