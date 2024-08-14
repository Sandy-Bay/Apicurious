package sandybay.apicurious.api.util;

import sandybay.apicurious.common.bee.species.BeeColor;

public class ApicuriousConstants
{
  // Color-values
  //// Bodies
  public static final String DEFAULT_BODY = "ffdc16";
  public static final String WINTRY_BODY = "daf5f3";
  public static final String ROCKY_BODY = "999999";
  public static final String NETHER_BODY = "9a2323";
  public static final String ENDER_BODY = "d9de9e";

  //// Outlines & Wings
  // Debug
  public static final String UNDEFIEND_OUTLINE = "999999";
  public static final String UNDEFINED_WING = "bcbcbc";
  // Baseline
  public static final String FOREST_OUTLINE = "19d0ec";
  public static final String FOREST_WING = "ffdc16";
  public static final String MEADOW_OUTLINE = "ef131e";
  public static final String MEADOW_WING = "ffdc16";
  public static final String MODEST_OUTLINE = "c5be86";
  public static final String MODEST_WING = "ffdc16";
  public static final String TROPICAL_OUTLINE = "388020";
  public static final String TROPICAL_WING = "ffdc16";
  public static final String WINTRY_OUTLINE = "a0ffc8";
  public static final String WINTRY_WING = "daf5f3";
  public static final String MARSHY_OUTLINE = "546626";
  public static final String MARSHY_WING = "ffdc16";
  public static final String ROCKY_OUTLINE = "a8a8a8";
  public static final String ROCKY_WING = "999999";
  public static final String NETHER_OUTLINE = "8c6969";
  public static final String NETHER_WING = "9a2323";
  public static final String ENDER_OUTLINE = "e079fa";
  public static final String ENDER_WING = "d9de9e";
  // Common
  public static final String COMMON_OUTLINE = "b2b2b2";
  public static final String COMMON_WING = "ffdc16";
  public static final String CULTIVATED_OUTLINE = "5734ec";
  public static final String CULTIVATED_WING = "ffdc16";
  // Noble
  public static final String NOBLE_OUTLINE = "ec9a19";
  public static final String NOBLE_WING = "ffdc16";
  public static final String MAJESTIC_OUTLINE = "7f0000";
  public static final String MAJESTIC_WING = "ffdc16";
  public static final String IMPERIAL_OUTLINE = "a3e02f";
  public static final String IMPERIAL_WING = "ffdc16";
  // Diligent
  public static final String DILIGENT_OUTLINE = "c219ec";
  public static final String DILIGENT_WING = "ffdc16";
  public static final String UNWEARY_OUTLINE = "19ec5a";
  public static final String UNWEARY_WING = "ffdc16";
  public static final String INDUSTRIOUS_OUTLINE = "ffffff";
  public static final String INDUSTIROUS_WING = "ffdc16";
  // Festive
  public static final String LEPORINE_OUTLINE = "0feff8f";
  public static final String LEPORINE_WING = "3cd757";
  public static final String MERRY_OUTLINE = "ffffff";
  public static final String MERRY_WING = "d40000";
  // Agrarian
  public static final String RURAL_OUTLINE = "";
  public static final String RURAL_WING = "";
  public static final String FARMED_OUTLINE = "";
  public static final String FARMED_WING = "";

  //// BeeColor(s)
  // Debug
  public static final BeeColor UNDEFINED = new BeeColor(UNDEFIEND_OUTLINE, UNDEFINED_WING, DEFAULT_BODY);
  // Baseline
  public static final BeeColor FOREST = new BeeColor(FOREST_OUTLINE, FOREST_WING, DEFAULT_BODY);
  public static final BeeColor MEADOW = new BeeColor(MEADOW_OUTLINE, MEADOW_WING, DEFAULT_BODY);
  public static final BeeColor MODEST = new BeeColor(MODEST_OUTLINE, MODEST_WING, DEFAULT_BODY);
  public static final BeeColor TROPICAL = new BeeColor(TROPICAL_OUTLINE, TROPICAL_WING, DEFAULT_BODY);
  public static final BeeColor WINTRY = new BeeColor(WINTRY_OUTLINE, WINTRY_WING, WINTRY_BODY);
  public static final BeeColor MARSHY = new BeeColor(MARSHY_OUTLINE, MARSHY_WING, DEFAULT_BODY);
  public static final BeeColor ROCKY = new BeeColor(ROCKY_OUTLINE, ROCKY_WING, ROCKY_BODY);
  public static final BeeColor NETHER = new BeeColor(NETHER_OUTLINE, NETHER_WING, NETHER_BODY);
  public static final BeeColor ENDER = new BeeColor(ENDER_OUTLINE, ENDER_WING, ENDER_BODY);
  // Common
  public static final BeeColor COMMON = new BeeColor(COMMON_OUTLINE, COMMON_WING, DEFAULT_BODY);
  public static final BeeColor CULTIVATED = new BeeColor(CULTIVATED_OUTLINE, CULTIVATED_WING, DEFAULT_BODY);
  // Noble
  public static final BeeColor NOBLE = new BeeColor(NOBLE_OUTLINE, NOBLE_WING, DEFAULT_BODY);
  public static final BeeColor MAJESTIC = new BeeColor(MAJESTIC_OUTLINE, MAJESTIC_WING, DEFAULT_BODY);
  public static final BeeColor IMPERIAL = new BeeColor(IMPERIAL_OUTLINE, IMPERIAL_WING, DEFAULT_BODY);
  // Diligent
  public static final BeeColor DILIGENT = new BeeColor(DILIGENT_OUTLINE, DILIGENT_WING, DEFAULT_BODY);
  public static final BeeColor UNWEARY = new BeeColor(UNWEARY_OUTLINE, UNWEARY_WING, DEFAULT_BODY);
  public static final BeeColor INDUSTRIOUS = new BeeColor(INDUSTRIOUS_OUTLINE, INDUSTIROUS_WING, DEFAULT_BODY);
  // Festive
  public static final BeeColor LEPORINE = new BeeColor(LEPORINE_OUTLINE, LEPORINE_WING, DEFAULT_BODY);
  public static final BeeColor MERRY = new BeeColor(MERRY_OUTLINE, MERRY_WING, DEFAULT_BODY);
  // Agrarian
  public static final BeeColor RURAL = new BeeColor(RURAL_OUTLINE, RURAL_WING, DEFAULT_BODY);
  public static final BeeColor FARMED = new BeeColor(FARMED_OUTLINE, FARMED_WING, DEFAULT_BODY);
}
