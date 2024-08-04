package sandybay.apicurious.data;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.housing.HousingError;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;
import sandybay.apicurious.common.register.ApicuriousItemRegistration;

import java.util.Locale;

public class ApicuriousLangProvider extends LanguageProvider
{
  public ApicuriousLangProvider(PackOutput output)
  {
    super(output, Apicurious.MODID, "en_us");
  }

  @Override
  protected void addTranslations()
  {
    // Items
    add(ApicuriousItemRegistration.DRONE.get(), "Drone");
    add(ApicuriousItemRegistration.PRINCESS.get(), "Princess");
    add(ApicuriousItemRegistration.QUEEN.get(), "Queen");
    add(ApicuriousItemRegistration.SIEVE.get(), "Sieve");

    // Housing
    add(ApicuriousBlockRegistration.APIARY.asItem(), "Apiary");
    add(ApicuriousBlockRegistration.BEE_HOUSING.asItem(), "Bee Housing");

    // Frames
    add(ApicuriousItemRegistration.UNTREATED_FRAME.get(), "Untreated Frame");
    add(ApicuriousItemRegistration.IMPREGNATED_FRAME.get(), "Impregnated Frame");
    add(ApicuriousItemRegistration.HEALING_FRAME.get(), "Healing Frame");
    add(ApicuriousItemRegistration.SOUL_FRAME.get(), "Soul Frame");
    add(ApicuriousItemRegistration.RESTRAINT_FRAME.get(), "Restraint Frame");
    add(ApicuriousItemRegistration.PROVEN_FRAME.get(), "Proven Frame");

    // Misc
    add("apicurious.bee.shiftdown", "<Hold Shift for details>");

    // Hives
    addHives();
    addTabs();
    addMenus();
    addTraits();
    addSpecies();
    addErrors();
  }

  // Collection-methods
  public void addHives()
  {
    add(ApicuriousBlockRegistration.FOREST_HIVE.asItem(), "Forest Hive");
    add(ApicuriousBlockRegistration.MEADOW_HIVE.asItem(), "Meadow Hive");
    add(ApicuriousBlockRegistration.TROPICAL_HIVE.asItem(), "Tropical Hive");
    add(ApicuriousBlockRegistration.WINTRY_HIVE.asItem(), "Wintry Hive");
    add(ApicuriousBlockRegistration.MARSHY_HIVE.asItem(), "Marshy Hive");
    add(ApicuriousBlockRegistration.ROCKY_HIVE.asItem(), "Rocky Hive");
    add(ApicuriousBlockRegistration.NETHER_HIVE.asItem(), "Nether Hive");
    add(ApicuriousBlockRegistration.ENDER_HIVE.asItem(), "Ender Hive");
  }

  public void addTabs()
  {
    tab("general", "Apicurious");
    tab("bee", "Apicurious: Bee");
  }

  public void addMenus()
  {
    menu("apiary", "Apiary");
  }

  public void addTraits()
  {
    /// Area
    area("Smallest");
    area("Smaller");
    area("Small");
    area("Average");
    area("Large");
    area("Larger");
    area("Largest");

    /// Fertility
    fertility("Low");
    fertility("Average");
    fertility("High");
    fertility("Maximum");

    /// Flower
    flower("normal_flowers", "Flowers");
    flower("overworld_rocks", "Overworld Stone");
    flower("nether_rocks", "Nether Stone");

    /// Humidity Preference
    humidityPreference("Hellish");
    humidityPreference("Arid");
    humidityPreference("Average");
    humidityPreference("Damp");
    humidityPreference("Aquatic");

    /// Humidity Tolerance
    humidityTolerance("None");
    humidityTolerance("Lowest");
    humidityTolerance("Low");
    humidityTolerance("Average");
    humidityTolerance("High");
    humidityTolerance("Maximum");

    /// Lifespan
    lifespan("Shortest");
    lifespan("Shorter");
    lifespan("Short");
    lifespan("Shortened");
    lifespan("Average");
    lifespan("Elongated");
    lifespan("Long");
    lifespan("Longer");
    lifespan("Longest");

    /// Pollination
    pollination("Slowest");
    pollination("Slower");
    pollination("Slow");
    pollination("Average");
    pollination("Long");
    pollination("Longer");
    pollination("Longest");

    /// Speed
    speed("Slowest");
    speed("Slower");
    speed("Slow");
    speed("Average");
    speed("Fast");
    speed("Faster");
    speed("Fastest");

    /// Temperature Preference
    temperaturePreference("Infernal");
    temperaturePreference("Hot");
    temperaturePreference("Warm");
    temperaturePreference("Average");
    temperaturePreference("Chilly");
    temperaturePreference("Cold");
    temperaturePreference("Freezing");

    /// Temperature Tolerance
    temperatureTolerance("None");
    temperatureTolerance("Lowest");
    temperatureTolerance("Low");
    temperatureTolerance("Average");
    temperatureTolerance("High");
    temperatureTolerance("Maximum");

    /// WorkCycle
    workcycle("Always");
    workcycle("Diurnal");
    workcycle("Matutinal");
    workcycle("Nocturnal");
    workcycle("Vespertinal");
  }

  public void addSpecies()
  {
    species("Undefined");
    species("Debug");
    species("Forest");
    species("Meadow");
    species("Modest");
    species("Tropical");
    species("Wintry");
    species("Marshy");
    species("Rocky");
    species("Nether");
    species("Ender");
    species("Common");
    species("Cultivated");
    species("Industrious");
    species("Imperial");
    species("Austere");
  }

  // TODO: Write better messages and tooltips!
  public void addErrors()
  {
    error(HousingError.MISSING_QUEEN.getMessage(), "Missing Queen");
    error(HousingError.MISSING_PRINCESS.getMessage(), "Missing Princess");
    error(HousingError.MISSING_DRONE.getMessage(), "Missing Drone");
    error(HousingError.MISSING_FLOWER.getMessage(), "Missing Flowers");
    error(HousingError.TOO_DRY.getMessage(), "Too Dry");
    error(HousingError.TOO_HUMID.getMessage(), "Too Humid");
    error(HousingError.TOO_HOT.getMessage(), "Too Hot");
    error(HousingError.TOO_COLD.getMessage(), "Too Cold");
    error(HousingError.INVALID_TIME.getMessage(), "Wrong time of day");
    error(HousingError.IS_RAINING.getMessage(), "Is Raining");
    error(HousingError.NO_SKY.getMessage(), "No Sky");
    error(HousingError.FULL_INVENTORY.getMessage(), "Output Inventory is Full");
  }

  public void error(String message, String translation)
  {
    add(message, translation);
    add(message + "_tooltip", translation);
  }

  // Creative Tabs
  public void tab(String definition, String translation)
  {
    add("itemGroup.apicurious." + definition, translation);
  }

  // Menu
  public void menu(String definition, String translation)
  {
    add("apicurious.menu." + definition, translation);
  }

  // Traits
  public void area(String translation)
  {
    add("apicurious.area." + translation.toLowerCase(Locale.ROOT), translation);
  }

  public void fertility(String translation)
  {
    add("apicurious.fertility." + translation.toLowerCase(Locale.ROOT), translation);
  }

  public void flower(String definition, String translation)
  {
    add("apicurious.flower." + definition, translation);
  }

  public void humidityPreference(String translation)
  {
    add("apicurious.preference.humidity." + translation.toLowerCase(Locale.ROOT), translation);
  }

  public void humidityTolerance(String translation)
  {
    add("apicurious.tolerance.humidity." + translation.toLowerCase(Locale.ROOT), translation);
  }

  public void lifespan(String translation)
  {
    add("apicurious.lifespan." + translation.toLowerCase(Locale.ROOT), translation);
  }

  public void pollination(String translation)
  {
    add("apicurious.pollination." + translation.toLowerCase(Locale.ROOT), translation);
  }

  public void speed(String translation)
  {
    add("apicurious.speed." + translation.toLowerCase(Locale.ROOT), translation);
  }

  public void temperaturePreference(String translation)
  {
    add("apicurious.preference.temperature." + translation.toLowerCase(Locale.ROOT), translation);
  }

  public void temperatureTolerance(String translation)
  {
    add("apicurious.tolerance.temperature." + translation.toLowerCase(Locale.ROOT), translation);
  }

  public void workcycle(String translation)
  {
    add("apicurious.workcycle." + translation.toLowerCase(Locale.ROOT), translation);
  }

  // Species
  public void species(String translation)
  {
    species(translation.toLowerCase(Locale.ROOT), translation);
  }

  public void species(String definition, String translation)
  {
    add("apicurious.species." + definition, translation);
  }


}
