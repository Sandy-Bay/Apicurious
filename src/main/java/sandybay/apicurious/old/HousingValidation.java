package sandybay.apicurious.old;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.trait.*;
import sandybay.apicurious.common.register.ApicuriousDataComponentRegistration;

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

  private boolean validateHumidity(Level level, BlockPos housingPosition) {
    boolean foundValid = false;
    if (!key.has(ApicuriousDataComponentRegistration.BEE_SPECIES)) return false;
    BeeSpecies species = key.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null) return false;
    HumidityPreference preference = species.getEnvironmentalData().getHumidityData().preference().value();
    HumidityTolerance tolerance = species.getEnvironmentalData().getHumidityData().tolerance().value();
    List<TagKey<Biome>> toleratedTags = preference.getHumidityWithTolerance(tolerance);
    for (TagKey<Biome> humidityTag : toleratedTags) {
      if (level.getBiome(housingPosition).is(humidityTag)) {
        foundValid = true;
        break;
      }
    }
    return foundValid;
  }

  private boolean validateTemperature(Level level, BlockPos housingPosition) {
    boolean foundValid = false;
    if (!key.has(ApicuriousDataComponentRegistration.BEE_SPECIES)) return false;
    BeeSpecies species = key.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null) return false;
    TemperaturePreference preference = species.getEnvironmentalData().getTemperatureData().preference().value();
    TemperatureTolerance tolerance = species.getEnvironmentalData().getTemperatureData().tolerance().value();
    List<TagKey<Biome>> toleratedTags = preference.getTemperatureWithTolerance(tolerance);
    for (TagKey<Biome> biomeTag : toleratedTags) {
      if (level.getBiome(housingPosition).is(biomeTag)) {
        foundValid = true;
        break;
      }
    }
    return foundValid;
  }
}
