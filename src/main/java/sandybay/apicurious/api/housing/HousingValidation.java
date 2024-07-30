package sandybay.apicurious.api.housing;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.checkerframework.checker.units.qual.C;
import sandybay.apicurious.api.util.ClimateHelper;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.trait.*;
import sandybay.apicurious.common.register.ApicuriousDataComponentRegistration;

import java.util.List;

public class HousingValidation {

  private ItemStack key;
  private ClimateHelper helper;
  private boolean isValid;

  public HousingValidation() {
    this.isValid = false;
  }

  public boolean validate(ItemStack key, Level level, BlockPos housingPosition, List<BlockPos> territory) {
    if (helper == null && level != null) helper = new ClimateHelper(level);
    if (key.isEmpty()) return false;
    if (this.key != key) {
      this.key = key;
      revalidate(level, housingPosition, territory);
    }
    return isValid;
  }

  private void revalidate(Level level, BlockPos housingPosition, List<BlockPos> territory) {
    this.isValid = validateFlowers(level, territory) && validateHumidity(housingPosition) && validateTemperature(housingPosition);
  }

  private boolean validateFlowers(Level level, List<BlockPos> territory) {
    boolean foundValid = false;
    if (!key.has(ApicuriousDataComponentRegistration.BEE_SPECIES)) return false;
    BeeSpecies species = key.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null) return false;
    Flowers flowers = species.getEnvironmentalData().getFlowers().value();
    for (BlockPos pos : territory) {
      if (level.getBlockState(pos).is(flowers.getFlowers())) {
        foundValid = true;
        break;
      }
    }
    return foundValid;
  }

  private boolean validateHumidity(BlockPos housingPosition) {
    return helper.isCorrectHumidity(key, housingPosition);
  }

  private boolean validateTemperature(BlockPos housingPosition) {
    return helper.isCorrectTemperature(key, housingPosition);
  }
}
