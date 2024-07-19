package sandybay.apicurious.api.util;

import sandybay.apicurious.common.bee.species.BeeColor;

public class ApicuriousConstants {
  /**
   * Duration in Ticks per Workcycle.
   * Default: 550 ticks or 27.5 seconds
   */
  public static final int WORKCYCLE = 550;

  // BeeColors
  public static final String DEFAULT_BODY = "e9ce17";
  public static final String UNDEFIEND_OUTLINE = "999999";
  public static final String UNDEFINED_WING = "bcbcbc";
  public static final String FOREST_OUTLINE = "62c1e5";
  public static final String FOREST_WING = "a0d9ef";
  public static final String MEADOW_OUTLINE = "c72b20";
  public static final String MEADOW_WING = "ed2d1f";
  public static final String COMMON_OUTLINE = "d1d5d4";
  public static final String COMMON_WING = "eceeee";
  public static final String CULTIVATED_OUTLINE = "813885";
  public static final String CULTIVATED_WING = "ae4fb3";

  public static final BeeColor UNDEFINED = new BeeColor(UNDEFIEND_OUTLINE, UNDEFINED_WING, DEFAULT_BODY);
  public static final BeeColor FOREST = new BeeColor(FOREST_OUTLINE, FOREST_WING, DEFAULT_BODY);
  public static final BeeColor MEADOW = new BeeColor(MEADOW_OUTLINE, MEADOW_WING, DEFAULT_BODY);
  public static final BeeColor COMMON = new BeeColor(COMMON_OUTLINE, COMMON_WING, DEFAULT_BODY);
  public static final BeeColor CULTIVATED = new BeeColor(CULTIVATED_OUTLINE, CULTIVATED_WING, DEFAULT_BODY);
  public static final BeeColor INDUSTRIOUS = new BeeColor(COMMON_OUTLINE, COMMON_WING, DEFAULT_BODY);
  public static final BeeColor IMPERIAL = new BeeColor(COMMON_OUTLINE, COMMON_WING, DEFAULT_BODY);
  public static final BeeColor AUSTERE = new BeeColor(COMMON_OUTLINE, COMMON_WING, DEFAULT_BODY);

}
