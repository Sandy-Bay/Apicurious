package sandybay.apicurious.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.transfer.item.ItemResource;
import sandybay.apicurious.api.bee.genetic.allele.AbstractClimatePreference;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.housing.HousingError;
import sandybay.apicurious.api.housing.blockentity.IApiaryErrorHandler;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.genetic.allele.HumidityPreference;
import sandybay.apicurious.common.bee.genetic.allele.HumidityTolerance;
import sandybay.apicurious.common.bee.genetic.allele.TemperaturePreference;
import sandybay.apicurious.common.bee.genetic.allele.TemperatureTolerance;

public class ClimateHelper
{

  private final Level level;
  private final IApiaryErrorHandler handler;

  public ClimateHelper(Level level, IApiaryErrorHandler handler)
  {
    this.level = level;
    this.handler = handler;
  }

  public boolean isCorrectTemperature(ItemResource bee, BlockPos pos)
  {
    Genome genome = bee.get(DataComponentRegistrar.GENOME);
    if (genome == null) return false;
    TemperaturePreference preference = (TemperaturePreference) genome.getTemperaturePreference(true).value();
    TemperatureTolerance tolerance = (TemperatureTolerance) genome.getTemperatureTolerance(true).value();
    return isCorrectClimate(TemperaturePreference.class, preference, tolerance.getToleranceModifier(), getTemperatureAtPosition(pos), pos, HousingError.TOO_COLD, HousingError.TOO_HOT);
  }

  public boolean isCorrectHumidity(ItemResource bee, BlockPos pos)
  {
    Genome genome = bee.get(DataComponentRegistrar.GENOME);
    if (genome == null) return false;
    HumidityPreference preference = (HumidityPreference) genome.getHumidityPreference(true).value();
    HumidityTolerance tolerance = (HumidityTolerance) genome.getHumidityTolerance(true).value();
    return isCorrectClimate(HumidityPreference.class, preference, tolerance.getToleranceModifier(), getHumidityAtPosition(pos), pos, HousingError.TOO_DRY, HousingError.TOO_HUMID);
  }

  public TagKey<Biome> getTemperatureAtPosition(BlockPos pos)
  {
    Holder<Biome> biome = level.getBiome(pos);
    return biome.tags()
            .filter(tag -> tag.location().getPath().contains("temperature"))
            .findFirst()
            .orElseGet(() -> defaultedTag(TemperaturePreference.class, biome, ApicuriousTags.BiomeTags.ICY_TEMPERATURE, biome.value().getModifiedClimateSettings().temperature()));
  }

  public TagKey<Biome> getHumidityAtPosition(BlockPos pos)
  {
    Holder<Biome> biome = level.getBiome(pos);
    return biome.tags()
            .filter(tag -> tag.location().getPath().contains("humidity"))
            .findFirst()
            .orElseGet(() -> defaultedTag(HumidityPreference.class, biome, ApicuriousTags.BiomeTags.HELLISH_HUMIDITY, biome.value().getModifiedClimateSettings().downfall()));
  }

  private <T extends AbstractClimatePreference<T>> boolean isCorrectClimate(Class<T> type, T preference, int toleranceModifier, TagKey<Biome> tagAtPos, BlockPos pos, HousingError tooLowError, HousingError tooHighError)
  {
    T tierAtPos = findByGroupTag(type, tagAtPos);
    int tierAtPosValue = tierAtPos == null ? 0 : tierAtPos.getValue();
    int preferenceValue = preference.getValue();
    boolean found = tierAtPos != null && Math.abs(tierAtPosValue - preferenceValue) <= toleranceModifier;
    if (handler != null)
    {
      if (found)
      {
        handler.removeError(tooLowError);
        handler.removeError(tooHighError);
      }
      else if (tierAtPos != null)
      {
        if (tierAtPos.getValue() > preference.getValue()) handler.addError(tooHighError);
        else handler.addError(tooLowError);
      }
    }
    return found;
  }

  private <T extends AbstractClimatePreference<T>> T findByGroupTag(Class<T> type, TagKey<Biome> tag)
  {
    RegistryAccess registryAccess = level.registryAccess();
    HolderLookup.RegistryLookup<IAllele<?>> lookup = registryAccess.lookupOrThrow(ApicuriousRegistries.ALLELES);
    return lookup.listElements()
            .map(Holder.Reference::value)
            .filter(type::isInstance)
            .map(type::cast)
            .filter(candidate -> candidate.getGroupTag().equals(tag))
            .findFirst()
            .orElse(null);
  }

  private <T extends AbstractClimatePreference<T>> TagKey<Biome> defaultedTag(Class<T> type, Holder<Biome> biome, TagKey<Biome> fallback, float rawValue)
  {
    if (!biome.isBound())
    {
      throw new IllegalArgumentException("Tried to get defaulted climate tag from unbound biome! REPORT THIS!");
    }
    RegistryAccess registryAccess = level.registryAccess();
    HolderLookup.RegistryLookup<IAllele<?>> lookup = registryAccess.lookupOrThrow(ApicuriousRegistries.ALLELES);
    return lookup.listElements()
            .map(Holder.Reference::value)
            .filter(type::isInstance)
            .map(type::cast)
            .min((a, b) -> Float.compare(Math.abs(a.getValue() - rawValue), Math.abs(b.getValue() - rawValue)))
            .map(AbstractClimatePreference::getGroupTag)
            .orElse(fallback);
  }
}