package sandybay.apicurious.api.housing;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.api.register.ApicuriousDataComponentRegistration;
import sandybay.apicurious.api.util.ClimateHelper;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.trait.Flowers;

import java.util.List;

public class HousingValidation
{

  private ItemStack key;
  private ClimateHelper helper;
  private boolean isValid;

  public HousingValidation()
  {
    this.isValid = false;
  }

  public boolean validate(ItemStack key, Level level, BlockPos housingPosition, List<BlockPos> territory)
  {
    if (helper == null && level != null) helper = new ClimateHelper(level);
    if (key.isEmpty()) return false;
    if (!this.isValid || this.key != key)
    {
      this.key = key;
      revalidate(level, housingPosition, territory);
    }
    return isValid;
  }

  private void revalidate(Level level, BlockPos housingPosition, List<BlockPos> territory)
  {
    this.isValid = validateFlowers(level, territory) && validateHumidity(housingPosition) && validateTemperature(housingPosition);
  }

  private boolean validateFlowers(Level level, List<BlockPos> territory)
  {
    boolean foundValid = false;
    if (!key.has(ApicuriousDataComponentRegistration.BEE_SPECIES)) return false;
    BeeSpecies species = key.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null) return false;
    Holder<Flowers> flowersHolder = species.getEnvironmentalData().getFlowers();
    if (!flowersHolder.isBound()) throw new IllegalArgumentException("BeeSpecies flowers were unbound! REPORT THIS!");
    TagKey<Block> flowers = flowersHolder.value().getFlowers();
    for (BlockPos pos : territory)
    {
      BlockState state = level.getBlockState(pos);
      if (level.isLoaded(pos) && !state.isAir() && state.is(flowers))
      {
        foundValid = true;
        break;
      }
    }
    return foundValid;
  }

  private boolean validateHumidity(BlockPos housingPosition)
  {
    return helper.isCorrectHumidity(key, housingPosition);
  }

  private boolean validateTemperature(BlockPos housingPosition)
  {
    return helper.isCorrectTemperature(key, housingPosition);
  }
}
