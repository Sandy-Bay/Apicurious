package sandybay.apicurious.old;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class HousingValidation {

  private ItemStack key;
  private boolean isValid;

  public HousingValidation() {
    this.isValid = false;
  }

  public boolean validate(ItemStack key, Level level, BlockPos housingPosition, List<BlockPos> territory) {
    if (key.isEmpty()) return false;
    if (this.key != key) {
      this.key = key;
      revalidate(level, housingPosition, territory);
    }
    return isValid;
  }

  private void revalidate(Level level, BlockPos housingPosition, List<BlockPos> territory) {
    this.isValid = validateFlowers(level, territory) && validateHumidity(level, housingPosition) && validateTemperature(level, housingPosition);
  }

  private boolean validateFlowers(Level level, List<BlockPos> territory) {
    boolean foundValid = false;
    // TODO: Fix this to support checking for flowers.
    /*
    for (TagKey<Block> flowerTag : key.get(ApicuriousDataComponents.GENOME).getFlowers().getFlowerTag()) {
      for (BlockPos pos : territory) {
        if (level.getBlockState(pos).is(flowerTag)) {
          foundValid = true;
          break;
        }
      }
    }
    */
    return foundValid;
  }

  private boolean validateHumidity(Level level, BlockPos housingPosition) {
    boolean foundValid = false;
    // TODO: Fix this to support checking for humidity.
    /*
    for (TagKey<Biome> biomeTag : key.get(ApicuriousDataComponents.GENOME).getHumidityPreference().getTagsWithTolerance(key.genome.humidityTolerance)) {
      if (level.getBlockState(apiaryPosition).is(biomeTag)) {
        foundValid = true;
        break;
      }
    }
    */
    return foundValid;
  }

  private boolean validateTemperature(Level level, BlockPos housingPosition) {
    boolean foundValid = false;
    // TODO: Fix this to support checking for humidity.
    /*
    for (TagKey<Biome> biomeTag : key.get(ApicuriousDataComponents.GENOME).getTemperaturePreference().getTagsWithTolerance(key.genome.temperatureTolerance)) {
      if (level.getBlockState(apiaryPosition).is(biomeTag)) {
        foundValid = true;
        break;
      }
    }
    */
    return foundValid;
  }
}
