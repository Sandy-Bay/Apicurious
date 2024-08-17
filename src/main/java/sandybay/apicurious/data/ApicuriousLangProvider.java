package sandybay.apicurious.data;

import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.housing.HousingError;
import sandybay.apicurious.common.registrar.BlockRegistrar;
import sandybay.apicurious.common.registrar.ItemRegistrar;

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
    add(ItemRegistrar.DRONE.get(), "Drone");
    add(ItemRegistrar.PRINCESS.get(), "Princess");
    add(ItemRegistrar.QUEEN.get(), "Queen");
    add(ItemRegistrar.SIEVE.get(), "Sieve");

    // Housing
    add(BlockRegistrar.APIARY.asItem(), "Apiary");
    add(BlockRegistrar.BEE_HOUSING.asItem(), "Bee Housing");

    // Frames
    add(ItemRegistrar.UNTREATED_FRAME.get(), "Untreated Frame");
    add(ItemRegistrar.IMPREGNATED_FRAME.get(), "Impregnated Frame");
    add(ItemRegistrar.HEALING_FRAME.get(), "Healing Frame");
    add(ItemRegistrar.SOUL_FRAME.get(), "Soul Frame");
    add(ItemRegistrar.RESTRAINT_FRAME.get(), "Restraint Frame");
    add(ItemRegistrar.PROVEN_FRAME.get(), "Proven Frame");

    // Products
    add(ItemRegistrar.BEESWAX.get(), "Beeswax");
    add(ItemRegistrar.REFRACTORY_WAX.get(), "Refractory Wax");
    add(ItemRegistrar.HONEY_DROP.get(), "Honey Drop");
    add(ItemRegistrar.HONEY_DEW.get(), "Honey Dew");
    add(ItemRegistrar.ROYAL_JELLY.get(), "Royal Jelly");
    add(ItemRegistrar.PROPOLIS.get(), "Propolis");
    add(ItemRegistrar.SILKEN_PROPOLIS.get(), "Silken Propolis");
    add(ItemRegistrar.SILK_WISP.get(), "Silk Wisp");
    add(ItemRegistrar.POLLEN.get(), "Pollen");
    add(ItemRegistrar.ICE_SHARD.get(), "Ice Shard");

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
    add(BlockRegistrar.FOREST_HIVE.asItem(), "Forest Hive");
    add(BlockRegistrar.MEADOW_HIVE.asItem(), "Meadow Hive");
    add(BlockRegistrar.MODEST_HIVE.asItem(), "Modest Hive");
    add(BlockRegistrar.TROPICAL_HIVE.asItem(), "Tropical Hive");
    add(BlockRegistrar.WINTRY_HIVE.asItem(), "Wintry Hive");
    add(BlockRegistrar.MARSHY_HIVE.asItem(), "Marshy Hive");
    add(BlockRegistrar.ROCKY_HIVE.asItem(), "Rocky Hive");
    add(BlockRegistrar.NETHER_HIVE.asItem(), "Nether Hive");
    add(BlockRegistrar.ENDER_HIVE.asItem(), "Ender Hive");
    add(BlockRegistrar.WATER_HIVE.asItem(), "Water Hive");
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
    flower("cactus", "Cactus");
    flower("jungle", "Jungle");
    flower("mushroom", "Mushroom");
    flower("snow", "Snow");
    flower("wheat", "Wheat");
    flower("overworld_stone", "Stone");
    flower("nether_stone", "Nether Stone");


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
    temperaturePreference("Hellish");
    temperaturePreference("Hot");
    temperaturePreference("Warm");
    temperaturePreference("Average");
    temperaturePreference("Chilly");
    temperaturePreference("Cold");
    temperaturePreference("Icy");

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
    species("Water");
    species("Nether");
    species("Ender");
    species("Valiant");
    species("Steadfast");
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
    // Agrarian
    species("Rural");
    species("Farmed");
    species("Agrarian");
    // Festive
    species("Leporine");
    species("Merry");
    // Wooden
    species("Wooden");
    species("Lumbered");
    species("Timbered");
    // Heroic
    species("Heroic");
    // Resilient
    species("Tolerant");
    species("Robust");
    species("Resilient");
    // Metallic
    species("Cuprum");
    species("Ferrus");
    species("Aurum");
    // Mineral
    species("Lazuli");
    // Gemstone
    species("Diamantine");
    species("Emeraldine");
  }

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
    comb(ItemRegistrar.COCOA_COMB, "Cocoa");
    comb(ItemRegistrar.DRIPPING_COMB, "Dripping");
    comb(ItemRegistrar.FROZEN_COMB, "Frozen");
    comb(ItemRegistrar.MELLOW_COMB, "Mellow");
    comb(ItemRegistrar.MOSSY_COMB, "Mossy");
    comb(ItemRegistrar.MYSTERIOUS_COMB, "Mysterious");
    comb(ItemRegistrar.PARCHED_COMB, "Parched");
    comb(ItemRegistrar.POWDERY_COMB, "Powdery");
    comb(ItemRegistrar.SILKY_COMB, "Silky");
    comb(ItemRegistrar.SIMMERING_COMB, "Simmering");
    comb(ItemRegistrar.STRINGY_COMB, "Stringy");
    comb(ItemRegistrar.WHEATEN_COMB, "Wheaten");
    comb(ItemRegistrar.ROCKY_COMB, "Rocky");
    comb(ItemRegistrar.SEEDY_COMB, "Seedy");
    comb(ItemRegistrar.DUSTY_COMB, "Dusty");
    comb(ItemRegistrar.DIAMOND_COMB, "Diamantine");
    comb(ItemRegistrar.EMERALD_COMB, "Emeraldine");
    comb(ItemRegistrar.COPPER_COMB, "Copper");
    comb(ItemRegistrar.IRON_COMB, "Iron");
    comb(ItemRegistrar.GOLD_COMB, "Golden");
    comb(ItemRegistrar.LAPIS_COMB, "Lazulite");
    comb(ItemRegistrar.DAMP_COMB, "Damp");
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
    add("apicurious.flowers." + definition, translation);
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
