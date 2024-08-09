package sandybay.apicurious.api.util;

import net.minecraft.resources.ResourceLocation;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.bee.species.BeeColor;

public class ApicuriousConstants
{
  // Apiary Constants
  /**
   * Duration in Ticks per Workcycle.
   * Default: 550 ticks or 27.5 seconds
   */
  public static final int WORKCYCLE_DURATION = 550;

  // Trait Constants
  public static final ResourceLocation SPECIES = Apicurious.createResourceLocation("species");
  public static final ResourceLocation AREA = Apicurious.createResourceLocation("area");
  public static final ResourceLocation FERTILITY = Apicurious.createResourceLocation("fertility");
  public static final ResourceLocation FLOWERS = Apicurious.createResourceLocation("flowers");
  public static final ResourceLocation HUMIDITY_PREFERENCE = Apicurious.createResourceLocation("humidity_preference");
  public static final ResourceLocation HUMIDITY_TOLERANCE = Apicurious.createResourceLocation("humidity_tolerance");
  public static final ResourceLocation LIFESPAN = Apicurious.createResourceLocation("lifespan");
  public static final ResourceLocation POLLINATION = Apicurious.createResourceLocation("pollination");
  public static final ResourceLocation SPEED = Apicurious.createResourceLocation("speed");
  public static final ResourceLocation TEMPERATURE_PREFERENCE = Apicurious.createResourceLocation("temperature_preference");
  public static final ResourceLocation TEMPERATURE_TOLERANCE = Apicurious.createResourceLocation("temperature_tolerance");
  public static final ResourceLocation WORKCYCLE = Apicurious.createResourceLocation("workcycle");

  // Color-values
  //// Bodies
  public static final String DEFAULT_BODY = "ffdc16";
  public static final String WINTRY_BODY = "daf5f3";
  public static final String ROCKY_BODY = "999999";
  public static final String NETHER_BODY = "9a2323";
  public static final String ENDER_BODY = "d9de9e";

  //// Outlines & Wings
  public static final String UNDEFIEND_OUTLINE = "999999";
  public static final String UNDEFINED_WING = "bcbcbc";
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

  public static final String COMMON_OUTLINE = "b2b2b2";
  public static final String COMMON_WING = "ffdc16";
  public static final String CULTIVATED_OUTLINE = "5734ec";
  public static final String CULTIVATED_WING = "ffdc16";


  // BeeColor(s)
  //// Debug
  public static final BeeColor UNDEFINED = new BeeColor(UNDEFIEND_OUTLINE, UNDEFINED_WING, DEFAULT_BODY);

  //// Base Type(s)
  public static final BeeColor FOREST = new BeeColor(FOREST_OUTLINE, FOREST_WING, DEFAULT_BODY);
  public static final BeeColor MEADOW = new BeeColor(MEADOW_OUTLINE, MEADOW_WING, DEFAULT_BODY);
  public static final BeeColor MODEST = new BeeColor(MODEST_OUTLINE, MODEST_WING, DEFAULT_BODY);
  public static final BeeColor TROPICAL = new BeeColor(TROPICAL_OUTLINE, TROPICAL_WING, DEFAULT_BODY);
  public static final BeeColor WINTRY = new BeeColor(WINTRY_OUTLINE, WINTRY_WING, WINTRY_BODY);
  public static final BeeColor MARSHY = new BeeColor(MARSHY_OUTLINE, MARSHY_WING, DEFAULT_BODY);
  public static final BeeColor ROCKY = new BeeColor(ROCKY_OUTLINE, ROCKY_WING, ROCKY_BODY);
  public static final BeeColor NETHER = new BeeColor(NETHER_OUTLINE, NETHER_WING, NETHER_BODY);
  public static final BeeColor ENDER = new BeeColor(ENDER_OUTLINE, ENDER_WING, ENDER_BODY);

  public static final BeeColor COMMON = new BeeColor(COMMON_OUTLINE, COMMON_WING, DEFAULT_BODY);
  public static final BeeColor CULTIVATED = new BeeColor(CULTIVATED_OUTLINE, CULTIVATED_WING, DEFAULT_BODY);

  public static final BeeColor INDUSTRIOUS = new BeeColor(COMMON_OUTLINE, COMMON_WING, DEFAULT_BODY);
  public static final BeeColor IMPERIAL = new BeeColor(COMMON_OUTLINE, COMMON_WING, DEFAULT_BODY);
  public static final BeeColor AUSTERE = new BeeColor(COMMON_OUTLINE, COMMON_WING, DEFAULT_BODY);

}
