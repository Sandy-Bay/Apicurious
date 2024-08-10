package sandybay.apicurious.api.bee.genetic;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import sandybay.apicurious.common.bee.species.BeeSpecies;

import java.util.List;

public interface IMutation
{
  BeeSpecies getOutput();

  boolean test(Level level, List<ItemStack> frames, BeeSpecies first, BeeSpecies second, RandomSource random);
}
