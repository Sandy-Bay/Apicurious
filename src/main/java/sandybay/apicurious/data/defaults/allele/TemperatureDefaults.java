package sandybay.apicurious.data.defaults.allele;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.AlleleNaming;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.common.bee.genetic.allele.TemperaturePreference;
import sandybay.apicurious.common.bee.genetic.allele.TemperatureTolerance;

public class TemperatureDefaults
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(TemperaturePreference.HELLISH, temperaturePreference(5, ApicuriousTags.BiomeTags.HELLISH_TEMPERATURE, true, "hellish"));
    bootstrap.register(TemperaturePreference.HOT, temperaturePreference(4, ApicuriousTags.BiomeTags.HOT_TEMPERATURE, true, "hot"));
    bootstrap.register(TemperaturePreference.AVERAGE, temperaturePreference(3, ApicuriousTags.BiomeTags.AVERAGE_TEMPERATURE, true, "average"));
    bootstrap.register(TemperaturePreference.COLD, temperaturePreference(2, ApicuriousTags.BiomeTags.COLD_TEMPERATURE, true, "cold"));
    bootstrap.register(TemperaturePreference.ICY, temperaturePreference(1, ApicuriousTags.BiomeTags.ICY_TEMPERATURE, true, "icy"));

    // Tolerances
    bootstrap.register(TemperatureTolerance.NO_TOLERANCE, temperatureTolerance(0, true, "none"));
    bootstrap.register(TemperatureTolerance.LOWEST_TOLERANCE, temperatureTolerance(1, false, "lowest"));
    bootstrap.register(TemperatureTolerance.LOW_TOLERANCE, temperatureTolerance(2, false, "low"));
    bootstrap.register(TemperatureTolerance.AVERAGE_TOLERANCE, temperatureTolerance(3, false, "average"));
    bootstrap.register(TemperatureTolerance.HIGH_TOLERANCE, temperatureTolerance(4, false, "high"));
    bootstrap.register(TemperatureTolerance.MAXIMUM_TOLERANCE, temperatureTolerance(5, false, "maximum"));
  }

  private static TemperaturePreference temperaturePreference(int temperature, TagKey<Biome> groupTag,
                                                             boolean isDominantTrait, String name)
  {
    return new TemperaturePreference(temperature, groupTag, isDominantTrait, AlleleNaming.key("preference.temperature", name));
  }

  private static TemperatureTolerance temperatureTolerance(int toleranceModifier, boolean isDominantTrait, String name)
  {
    return new TemperatureTolerance(toleranceModifier, isDominantTrait, AlleleNaming.key("tolerance.temperature", name));
  }
}
