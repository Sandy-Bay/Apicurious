package sandybay.apicurious.data;

import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.util.StringUtil;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.apache.commons.lang3.StringUtils;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.housing.HousingError;
import sandybay.apicurious.common.register.BlockRegistration;
import sandybay.apicurious.common.register.ItemRegistration;

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
    add(ItemRegistration.DRONE.get(), "Drone");
    add(ItemRegistration.PRINCESS.get(), "Princess");
    add(ItemRegistration.QUEEN.get(), "Queen");
    add(ItemRegistration.SIEVE.get(), "Sieve");

    // Housing
    add(BlockRegistration.APIARY.asItem(), "Apiary");
    add(BlockRegistration.BEE_HOUSING.asItem(), "Bee Housing");

    // Frames
    add(ItemRegistration.UNTREATED_FRAME.get(), "Untreated Frame");
    add(ItemRegistration.IMPREGNATED_FRAME.get(), "Impregnated Frame");
    add(ItemRegistration.HEALING_FRAME.get(), "Healing Frame");
    add(ItemRegistration.SOUL_FRAME.get(), "Soul Frame");
    add(ItemRegistration.RESTRAINT_FRAME.get(), "Restraint Frame");
    add(ItemRegistration.PROVEN_FRAME.get(), "Proven Frame");

    // Products
    add(ItemRegistration.BEESWAX.get(), "Beeswax");
    add(ItemRegistration.REFRACTORY_WAX.get(), "Refractory Wax");
    add(ItemRegistration.HONEY_DROP.get(), "Honey Drop");
    add(ItemRegistration.HONEY_DEW.get(), "Honey Dew");
    add(ItemRegistration.ROYAL_JELLY.get(), "Royal Jelly");
    add(ItemRegistration.PROPOLIS.get(), "Propolis");
    add(ItemRegistration.SILKEN_PROPOLIS.get(), "Silken Propolis");
    add(ItemRegistration.SILK_WISP.get(), "Silk Wisp");
    add(ItemRegistration.POLLEN.get(), "Pollen");
    add(ItemRegistration.ICE_SHARD.get(), "Ice Shard");

    // Misc
    add("apicurious.bee.shiftdown", "<Hold Shift for details>");
    add("apicurious.tooltip.area", "Area: ");
    add("apicurious.tooltip.lifespan", "Lifespan: ");
    add("apicurious.tooltip.speed", "Speed: ");
    add("apicurious.tooltip.fertility", "Fertility: ");
    add("apicurious.tooltip.flowers", "Flowers: ");
    add("apicurious.tooltip.unidentified", "Unidentified");
    add("apicurious.genetics.active", "Active");
    add("apicurious.genetics.inactive", "Inactive");

    // Hives
    addHives();
    addTabs();
    addMenus();
    addTraits();
    addSpecies();
    addErrors();
    addCombs();
  }

  // Collection-methods
  public void addHives()
  {
    add(BlockRegistration.FOREST_HIVE.asItem(), "Forest Hive");
    add(BlockRegistration.MEADOW_HIVE.asItem(), "Meadow Hive");
    add(BlockRegistration.MODEST_HIVE.asItem(), "Modest Hive");
    add(BlockRegistration.TROPICAL_HIVE.asItem(), "Tropical Hive");
    add(BlockRegistration.WINTRY_HIVE.asItem(), "Wintry Hive");
    add(BlockRegistration.MARSHY_HIVE.asItem(), "Marshy Hive");
    add(BlockRegistration.ROCKY_HIVE.asItem(), "Rocky Hive");
    add(BlockRegistration.NETHER_HIVE.asItem(), "Nether Hive");
    add(BlockRegistration.ENDER_HIVE.asItem(), "Ender Hive");
  }

  public void addTabs()
  {
    tab("general", "Apicurious");
    tab("bee", "Apicurious: Bee");
  }

  public void addMenus()
  {
    menu("apiary", "Apiary");
    menu("bee_housing", "Bee Housing");
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

    /// Workcycle
    workcycle("Always");
    workcycle("Diurnal");
    workcycle("Matutinal");
    workcycle("Nocturnal");
    workcycle("Vespertinal");

    add("apicurious.genetics.allele", "Allele: ");
  }

  public void addSpecies()
  {
    // Debug
    species("Undefined");
    species("Debug");
    // Baseline
    species("Forest");
    species("Meadow");
    species("Modest");
    species("Tropical");
    species("Wintry");
    species("Marshy");
    species("Rocky");
    species("Nether");
    species("Ender");
    // Common
    species("Common");
    species("Cultivated");
    // Noble
    species("Noble");
    species("Majestic");
    species("Imperial");
    // Diligent
    species("Diligent");
    species("Unweary");
    species("Industrious");
    // Festive
    species("Leporine");
    species("Merry");
    // Agrarian
    species("Rural");
    species("Farmed");
  }

  // TODO: Write better messages and tooltips!
  public void addErrors()
  {
    error(HousingError.MISSING_QUEEN.getMessage(), "Missing Queen");
    error_tooltip(HousingError.MISSING_QUEEN.getMessage(), "The colony is missing its queen!");
    error(HousingError.MISSING_PRINCESS.getMessage(), "Missing Princess");
    error_tooltip(HousingError.MISSING_PRINCESS.getMessage(), "The colony requires a princess!");
    error(HousingError.MISSING_DRONE.getMessage(), "Missing Drone");
    error_tooltip(HousingError.MISSING_DRONE.getMessage(), "The colony can't run without its drones!");
    error(HousingError.MISSING_FLOWER.getMessage(), "Missing Flowers");
    error_tooltip(HousingError.MISSING_FLOWER.getMessage(), "No nearby flowers for nectar!");
    error(HousingError.TOO_DRY.getMessage(), "Too Dry");
    error_tooltip(HousingError.TOO_DRY.getMessage(), "The bees seem too agitated by the dryness to work.");
    error(HousingError.TOO_HUMID.getMessage(), "Too Humid");
    error_tooltip(HousingError.TOO_HUMID.getMessage(), "The bees seem too sluggish from the humidity to work.");
    error(HousingError.TOO_HOT.getMessage(), "Too Hot");
    error_tooltip(HousingError.TOO_HOT.getMessage(), "The scorching heat is making it hard for the bees to work.");
    error(HousingError.TOO_COLD.getMessage(), "Too Cold");
    error_tooltip(HousingError.TOO_COLD.getMessage(), "The frigid temperatures is proving too much for the poor bees.");
    error(HousingError.INVALID_TIME.getMessage(), "Wrong time of day");
    error_tooltip(HousingError.INVALID_TIME.getMessage(), "Shh... Your bees are resting!");
    error(HousingError.IS_RAINING.getMessage(), "Is Raining");
    error_tooltip(HousingError.IS_RAINING.getMessage(), "Maybe some raincoats are in order?");
    error(HousingError.NO_SKY.getMessage(), "No Sky");
    error_tooltip(HousingError.NO_SKY.getMessage(), "Your bees don't seem to appreciate the closed area.");
    error(HousingError.FULL_INVENTORY.getMessage(), "Output Inventory is Full");
    error_tooltip(HousingError.FULL_INVENTORY.getMessage(), "There is no more space in the apiary!");
  }

  public void addCombs()
  {
    comb(ItemRegistration.COCOA_COMB, "Cocoa");
    comb(ItemRegistration.DRIPPING_COMB, "Dripping");
    comb(ItemRegistration.FROZEN_COMB, "Frozen");
    comb(ItemRegistration.MELLOW_COMB, "Mellow");
    comb(ItemRegistration.MOSSY_COMB, "Mossy");
    comb(ItemRegistration.MYSTERIOUS_COMB, "Mysterious");
    comb(ItemRegistration.PARCHED_COMB, "Parched");
    comb(ItemRegistration.POWDERY_COMB, "Powdery");
    comb(ItemRegistration.SILKY_COMB, "Silky");
    comb(ItemRegistration.SIMMERING_COMB, "Simmering");
    comb(ItemRegistration.STRINGY_COMB, "Stringy");
    comb(ItemRegistration.WHEATEN_COMB, "Wheaten");
    comb(ItemRegistration.ROCKY_COMB, "Rocky");
    comb(ItemRegistration.SEEDY_COMB, "Seedy");
  }

  public void comb(Holder<Item> item, String name)
  {
    add(item.value(), name + " Comb");
  }

  public void error(String message, String translation)
  {
    add(message, translation);
  }

  public void error_tooltip(String message, String translation)
  {
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
    add("apicurious.preferenceHolder.humidity." + translation.toLowerCase(Locale.ROOT), translation);
  }

  public void humidityTolerance(String translation)
  {
    add("apicurious.toleranceHolder.humidity." + translation.toLowerCase(Locale.ROOT), translation);
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
    add("apicurious.preferenceHolder.temperature." + translation.toLowerCase(Locale.ROOT), translation);
  }

  public void temperatureTolerance(String translation)
  {
    add("apicurious.toleranceHolder.temperature." + translation.toLowerCase(Locale.ROOT), translation);
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
