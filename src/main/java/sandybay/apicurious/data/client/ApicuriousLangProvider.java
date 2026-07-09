package sandybay.apicurious.data.client;

import net.minecraft.data.PackOutput;
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
    add(ItemRegistrar.DRONE.item().get(), "Drone");
    add("item.apicurious.drone.secret", "Bruno");
    add(ItemRegistrar.PRINCESS.item().get(), "Princess");
    add(ItemRegistrar.QUEEN.item().get(), "Queen");
    add(ItemRegistrar.SIEVE.item().get(), "Sieve");
    add(ItemRegistrar.ANALYZER.item().get(), "Bee Analyzer");

    // Blocks
    add(BlockRegistrar.APIARY.asBlock(), "Apiary");
    add(BlockRegistrar.APIARY.asItem(), "Apiary");
    add(BlockRegistrar.BEE_HOUSING.asBlock(), "Bee Housing");
    add(BlockRegistrar.BEE_HOUSING.asItem(), "Bee Housing");
    add(BlockRegistrar.CENTRIFUGE.asBlock(), "Centrifuge");
    add(BlockRegistrar.CENTRIFUGE.asItem(), "Centrifuge");

    // Frames
    add(ItemRegistrar.UNTREATED_FRAME.frame().get(), "Untreated Frame");
    add(ItemRegistrar.IMPREGNATED_FRAME.frame().get(), "Impregnated Frame");
    add(ItemRegistrar.HEALING_FRAME.frame().get(), "Healing Frame");
    add(ItemRegistrar.SOUL_FRAME.frame().get(), "Soul Frame");
    add(ItemRegistrar.RESTRAINT_FRAME.frame().get(), "Restraint Frame");
    add(ItemRegistrar.PROVEN_FRAME.frame().get(), "Proven Frame");
    add(ItemRegistrar.ROYAL_FRAME.frame().get(), "Royal Frame");
    add(ItemRegistrar.CREATIVE_FRAME.frame().get(), "Creative Frame");

    // Products
    add(ItemRegistrar.BEESWAX.item().get(), "Beeswax");
    add(ItemRegistrar.REFRACTORY_WAX.item().get(), "Refractory Wax");
    add(ItemRegistrar.HONEY_DROP.drop().get(), "Honey Drop");
    add(ItemRegistrar.HONEY_DEW.item().get(), "Honey Dew");
    add(ItemRegistrar.ROYAL_JELLY.item().get(), "Royal Jelly");
    add(ItemRegistrar.PROPOLIS.propolis().get(), "Propolis");
    add(ItemRegistrar.SILKEN_PROPOLIS.propolis().get(), "Silken Propolis");
    add(ItemRegistrar.SILK_WISP.item().get(), "Silk Wisp");
    add(ItemRegistrar.POLLEN.pollen().get(), "Pollen");
    add(ItemRegistrar.ICE_SHARD.item().get(), "Ice Shard");
    add(ItemRegistrar.ASH.item().get(), "Ash");
    add(ItemRegistrar.PEAT.item().get(), "Peat");
    add(ItemRegistrar.PHOSPHOR.item().get(), "Phosphor");
    add(ItemRegistrar.DIAMOND_NUGGET.item().get(), "Diamond Nugget");
    add(ItemRegistrar.EMERALD_NUGGET.item().get(), "Emerald Nugget");
    add(ItemRegistrar.COPPER_NUGGET.item().get(), "Copper Nugget");
    add(ItemRegistrar.WATERY_PROPOLIS.propolis().get(), "Watery Propolis");
    add(ItemRegistrar.RED_TINTED_DROP.drop().get(), "Red-Tinted Drop");
    add(ItemRegistrar.YELLOW_TINTED_DROP.drop().get(), "Yellow-Tinted Drop");
    add(ItemRegistrar.BLUE_TINTED_DROP.drop().get(), "Blue-Tinted Drop");
    add(ItemRegistrar.GREEN_TINTED_DROP.drop().get(), "Green-Tinted Drop");
    add(ItemRegistrar.BROWN_TINTED_DROP.drop().get(), "Brown-Tinted Drop");
    add(ItemRegistrar.WHITE_TINTED_DROP.drop().get(), "White-Tinted Drop");
    add(ItemRegistrar.BLACK_TINTED_DROP.drop().get(), "Black-Tinted Drop");
    add(ItemRegistrar.ORANGE_TINTED_DROP.drop().get(), "Orange-Tinted Drop");
    add(ItemRegistrar.CYAN_TINTED_DROP.drop().get(), "Cyan-Tinted Drop");
    add(ItemRegistrar.PURPLE_TINTED_DROP.drop().get(), "Purple-Tinted Drop");
    add(ItemRegistrar.GRAY_TINTED_DROP.drop().get(), "Gray-Tinted Drop");
    add(ItemRegistrar.LIGHT_BLUE_TINTED_DROP.drop().get(), "Light Blue-Tinted Drop");
    add(ItemRegistrar.PINK_TINTED_DROP.drop().get(), "Pink-Tinted Drop");
    add(ItemRegistrar.LIME_TINTED_DROP.drop().get(), "Lime-Tinted Drop");
    add(ItemRegistrar.LIGHT_GRAY_TINTED_DROP.drop().get(), "Light Gray-Tinted Drop");
    add(ItemRegistrar.MAGENTA_TINTED_DROP.drop().get(), "Magenta-Tinted Drop");
    add(ItemRegistrar.SALTPETER.item().get(), "Saltpeter");
    add(ItemRegistrar.ACIDIC_DROP.drop().get(), "Acidic Drop");
    add(ItemRegistrar.SULFUR.item().get(), "Sulfur");

    // Misc
    add("apicurious.bee.shiftdown", "<Hold Shift for details>");
    add("apicurious.tooltip.species", "Species: ");
    add("apicurious.tooltip.area", "Area: ");
    add("apicurious.tooltip.lifespan", "Lifespan: ");
    add("apicurious.tooltip.speed", "Speed: ");
    add("apicurious.tooltip.fertility", "Fertility: ");
    add("apicurious.tooltip.flowers", "Flowers: ");
    add("apicurious.tooltip.production", "Production: ");
    add("apicurious.tooltip.pollination", "Pollination: ");
    add("apicurious.tooltip.territory", "Territory: ");
    add("apicurious.tooltip.effect", "Effect: ");
    add("apicurious.tooltip.preference.temperature", "Temp: ");
    add("apicurious.tooltip.preference.humidity", "Humid: ");
    add("apicurious.tooltip.tolerance", "Tol: ");
    add("apicurious.tooltip.workcycle", "Workcycle: ");
    add("apicurious.tooltip.ignores_rain", "Flyer: ");
    add("apicurious.tooltip.ignores_sky", "Cave: ");
    add("apicurious.tooltip.unidentified", "Unidentified");
    add("apicurious.genetics.active", "Active");
    add("apicurious.genetics.inactive", "Inactive");

    // JEI
    addJEI();

    // Hives
    addHives();
    addTabs();
    addMenus();
    addTraits();
    addSpecies();
    addErrors();
    addCombs();
    addConditions();
  }

  private void addConditions()
  {
    add("apicurious.condition.chance", "- Chance: ");
    add("apicurious.condition.biome", "- In Biome:");
  }

  private void addJEI()
  {
    // Categories
    add("apicurious.jei.centrifuge.title", "Centrifuge");
    add("apicurious.jei.bee_outputs.title", "Bee Outputs");
    add("apicurious.jei.bee_mutations.title", "Bee Mutations");

    // Tooltips
    add("apicurious.jei.tooltip.conditions", "Conditions:");
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
    menu("analyzer", "Analyzer");
    menu("centrifuge", "Centrifuge");
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
    flower("cacti", "Cacti");
    flower("jungle", "Jungle");
    flower("snow", "Snow");
    flower("mushroom", "Mushroom");
    flower("wheat", "Wheat");
    flower("lily_pad", "Lily Pad");
    flower("overworld_stone", "Stone");
    flower("nether_stone", "Nether Stone");
    flower("end_stone", "End Stone");
    flower("redstone", "Redstone");
    flower("dead_bush", "Dead Bush");
    flower("wood", "Wood");
    flower("sugar_cane", "Sugar Cane");

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

    // Agrarian
    species("Rural");
    species("Farmed");
    species("Agrarian");
    // Apis
    species("Forest");
    species("Meadow");
    species("Common");
    species("Cultivated");
    // Aquatic
    species("Water");
    species("River");
    species("Ocean");
    species("Stained");
    // Austere
    species("Modest");
    species("Frugal");
    species("Austere");
    species("Hazardous");
    // Barren
    species("Arid");
    species("Barren");
    species("Desolate");
    species("Gnawing");
    species("Decomposing");
    // Boggy
    species("Marshy");
    species("Damp");
    species("Boggy");
    species("Fungal");
    species("Miry");
    // Caustic
    species("Corrosive");
    species("Caustic");
    species("Acidic");
    // Dye
    /// Primary
    species("Maroon");
    species("Saffron");
    species("Prussian");
    species("Natural");
    species("Sepia");
    species("Bleached");
    species("Ebony");
    /// Secondary
    species("Amber");
    species("Turquoise");
    species("Indigo");
    species("Slate");
    species("Azure");
    species("Lavender");
    species("Lime");
    /// Tertiary
    species("Ashen");
    species("Fuchsia");
    // Ender
    species("Ender");
    species("Spectral");
    species("Phantasmal");
    // Ecstatic
    species("Excited");
    species("Energetic");
    species("Ecstatic");
    // Festive
    species("Leporine");
    species("Merry");
    species("Tipsy");
    species("Celebratory");
    species("Tricky");
    // Fossilised
    species("Fossilised");
    species("Forgotten");
    // Frozen
    species("Wintry");
    species("Icy");
    species("Glacial");
    species("Frigid");
    species("Absolute");
    // Gemstone
    species("Diamantine");
    species("Emeraldine");
    // Heroic
    species("Valiant");
    species("Steadfast");
    species("Heroic");
    // Historic
    species("Ancient");
    species("Primeval");
    species("Prehistoric");
    species("Relic");
    // Imperial
    species("Noble");
    species("Majestic");
    species("Imperial");
    // Industrious
    species("Diligent");
    species("Unweary");
    species("Industrious");
    // Infernal
    species("Sinister");
    species("Fiendish");
    species("Demonic");
    // Metallic
    species("Cuprum");
    species("Ferrus");
    species("Aurum");
    // Mineral
    species("Lazuli");
    // Monastic
    species("Monastic");
    species("Secluded");
    species("Hermitic");
    // Resilient
    species("Rocky");
    species("Tolerant");
    species("Robust");
    species("Resilient");
    // Saccharine
    species("Sweetened");
    species("Sugary");
    // Timbered
    species("Wooden");
    species("Lumbered");
    species("Timbered");
    // Tropical
    species("Tropical");
    species("Exotic");
    species("Edenic");
    // Virulent
    species("Malicious");
    species("Infectious");
    species("Virulent");
    // Viscous
    species("Viscous");
    species("Glutinous");
    species("Sticky");
    // Volcanic
    species("Nether");
    species("Furious");
    species("Volcanic");
    species("Glowering");
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
    comb(ItemRegistrar.ENERGETIC_COMB, "Energetic");
    comb(ItemRegistrar.STATIC_COMB, "Static");
    comb(ItemRegistrar.RED_TINTED_COMB, "Red Tinted");
    comb(ItemRegistrar.YELLOW_TINTED_COMB, "Yellow Tinted");
    comb(ItemRegistrar.BLUE_TINTED_COMB, "Blue Tinted");
    comb(ItemRegistrar.GREEN_TINTED_COMB, "Green Tinted");
    comb(ItemRegistrar.BROWN_TINTED_COMB, "Brown Tinted");
    comb(ItemRegistrar.WHITE_TINTED_COMB, "White Tinted");
    comb(ItemRegistrar.BLACK_TINTED_COMB, "Black Tinted");
    comb(ItemRegistrar.ORANGE_TINTED_COMB, "Orange Tinted");
    comb(ItemRegistrar.CYAN_TINTED_COMB, "Cyan Tinted");
    comb(ItemRegistrar.PURPLE_TINTED_COMB, "Purple Tinted");
    comb(ItemRegistrar.GRAY_TINTED_COMB, "Gray Tinted");
    comb(ItemRegistrar.LIGHT_BLUE_TINTED_COMB, "Light Blue Tinted");
    comb(ItemRegistrar.PINK_TINTED_COMB, "Pink Tinted");
    comb(ItemRegistrar.LIME_TINTED_COMB, "Lime Tinted");
    comb(ItemRegistrar.LIGHT_GRAY_TINTED_COMB, "Light Gray Tinted");
    comb(ItemRegistrar.MAGENTA_TINTED_COMB, "Magenta Tinted");
    comb(ItemRegistrar.UNSTABLE_COMB, "Unstable");
    comb(ItemRegistrar.CLAY_COMB, "Clay");
    comb(ItemRegistrar.BARREN_COMB, "Barren");
    comb(ItemRegistrar.DECOMPOSED_COMB, "Decomposed");
    comb(ItemRegistrar.ANCIENT_COMB, "Ancient");
    comb(ItemRegistrar.FOSSILISED_COMB, "Fossilised");
    comb(ItemRegistrar.GLACIAL_COMB, "Glacial");
    comb(ItemRegistrar.FUNGAL_COMB, "Fungal");
    comb(ItemRegistrar.BLAZING_COMB, "Blazing");
    comb(ItemRegistrar.GLOWING_COMB, "Glowing");
    comb(ItemRegistrar.VENOMOUS_COMB, "Venomous");
    comb(ItemRegistrar.BRIMSTONE_COMB, "Brimstone");
    comb(ItemRegistrar.MUCOUS_COMB, "Mucous");
  }

  public void comb(ItemRegistrar.CombHolder item, String name)
  {
    add(item.comb().get(), name + " Comb");
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
