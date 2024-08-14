package sandybay.apicurious.data.defaults.allele;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.common.bee.genetic.allele.Flowers;

public class FlowersDefaults
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(Flowers.FLOWERS, flowers(BlockTags.FLOWERS, true, "normal_flowers"));
    bootstrap.register(Flowers.CACTI, flowers(ApicuriousTags.BlockTags.CACTUS, true, "cactus"));
    bootstrap.register(Flowers.JUNGLE, flowers(ApicuriousTags.BlockTags.JUNGLE, true, "jungle"));
    bootstrap.register(Flowers.MUSHROOM, flowers(ApicuriousTags.BlockTags.MUSHROOM, true, "mushroom"));
    bootstrap.register(Flowers.SNOW, flowers(BlockTags.SNOW, true, "snow"));
    bootstrap.register(Flowers.WHEAT, flowers(ApicuriousTags.BlockTags.WHEAT, true, "wheat"));
    bootstrap.register(Flowers.ROCK, flowers(BlockTags.BASE_STONE_OVERWORLD, true, "overworld_rocks"));
    bootstrap.register(Flowers.NETHER_ROCK, flowers(BlockTags.BASE_STONE_NETHER, true, "nether_rocks"));
  }

  private static Flowers flowers(TagKey<Block> flowers, boolean isDominantTrait, String name)
  {
    return new Flowers(flowers, isDominantTrait, "apicurious.flowers." + name);
  }
}
