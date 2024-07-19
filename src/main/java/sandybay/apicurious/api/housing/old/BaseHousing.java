package sandybay.apicurious.api.housing.old;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import sandybay.apicurious.common.old.traits.Pollination;

import java.util.ArrayList;
import java.util.List;

public class BaseHousing extends Block {

  private final List<BlockPos> territory = new ArrayList<>();

  private final float pollinationBase;

  public BaseHousing(float pollinationBase) {
    super(Properties.ofFullCopy(Blocks.OAK_PLANKS));
    this.pollinationBase = pollinationBase;
  }

  public static List<BlockPos> getTerritory(BlockPos apiaryPosition, int xzOffset, int yOffset) {
    List<BlockPos> territory = new ArrayList<>();
    for (int x = apiaryPosition.getX() - xzOffset; x < apiaryPosition.getX() + xzOffset; x++) {
      for (int y = apiaryPosition.getY() - yOffset; y < apiaryPosition.getY() + yOffset; y++) {
        for (int z = apiaryPosition.getZ() - xzOffset; z < apiaryPosition.getZ() + xzOffset; z++) {
          territory.add(new BlockPos(x, y, z));
        }
      }
    }
    return territory;
  }

  public boolean shouldSpawnFlower(RandomSource random, Pollination pollination) {
    return random.nextFloat() > Math.clamp(pollinationBase * pollination.getPollinationChance(), 0.0f, 1.0f);
  }

}
