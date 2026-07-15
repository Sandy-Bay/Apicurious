package sandybay.apicurious.data.defaults.allele;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.AlleleNaming;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.common.bee.genetic.allele.HumidityPreference;
import sandybay.apicurious.common.bee.genetic.allele.HumidityTolerance;

public class HumidityDefaults
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    // Preference
    bootstrap.register(HumidityPreference.HELLISH, humidityPreference(1, ApicuriousTags.BiomeTags.HELLISH_HUMIDITY, true, "hellish"));
    bootstrap.register(HumidityPreference.ARID, humidityPreference(2, ApicuriousTags.BiomeTags.ARID_HUMIDITY, true, "arid"));
    bootstrap.register(HumidityPreference.AVERAGE, humidityPreference(3, ApicuriousTags.BiomeTags.AVERAGE_HUMIDITY, true, "average"));
    bootstrap.register(HumidityPreference.DAMP, humidityPreference(4, ApicuriousTags.BiomeTags.DAMP_HUMIDITY, true, "damp"));
    bootstrap.register(HumidityPreference.AQUATIC, humidityPreference(5, ApicuriousTags.BiomeTags.AQUATIC_HUMIDITY, true, "aquatic"));

    // Tolerance
    bootstrap.register(HumidityTolerance.NO_TOLERANCE, humidityTolerance(0, true, "none"));
    bootstrap.register(HumidityTolerance.LOWEST_TOLERANCE, humidityTolerance(1, false, "lowest"));
    bootstrap.register(HumidityTolerance.LOW_TOLERANCE, humidityTolerance(2, false, "low"));
    bootstrap.register(HumidityTolerance.AVERAGE_TOLERANCE, humidityTolerance(3, false, "average"));
    bootstrap.register(HumidityTolerance.HIGH_TOLERANCE, humidityTolerance(4, false, "high"));
    bootstrap.register(HumidityTolerance.MAXIMUM_TOLERANCE, humidityTolerance(5, false, "maximum"));
  }

  private static HumidityPreference humidityPreference(int humidity, TagKey<Biome> groupTag, boolean isDominantTrait,
                                                       String name)
  {
    return new HumidityPreference(humidity, groupTag, isDominantTrait, AlleleNaming.key("preference.humidity", name));
  }

  private static HumidityTolerance humidityTolerance(int toleranceModifier, boolean isDominantTrait, String name)
  {
    return new HumidityTolerance(toleranceModifier, isDominantTrait, AlleleNaming.key("tolerance.humidity", name));
  }
}
