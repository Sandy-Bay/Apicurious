package sandybay.apicurious.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ApicuriousMainConfig
{
  public static Pair<ApicuriousMainConfig, ModConfigSpec> configPair;
  public static ApicuriousMainConfig main_config;

  // Debug
  private ModConfigSpec.ConfigValue<Boolean> debug;
  private ModConfigSpec.ConfigValue<Integer> debugApiaryPollinationTime;

  // General
  //// Defaults
  private ModConfigSpec.ConfigValue<Integer> baseCycleTime;
  private ModConfigSpec.ConfigValue<Integer> baseValidationTimeInTicks;
  //// Minimums
  private ModConfigSpec.ConfigValue<Integer> minOutputInterval;
  private ModConfigSpec.ConfigValue<Integer> minLifespanTicks;

  // Genetics
  private ModConfigSpec.ConfigValue<Float> recessiveLeakChance;

  // Compat
  private ModConfigSpec.ConfigValue<Boolean> shouldJEIMutations;

  // "Totally normal configs"
  private ModConfigSpec.ConfigValue<Boolean> whyAreTheHorsesOnFire;
  private ModConfigSpec.ConfigValue<Boolean> weDontTalkAboutThat;

  public ApicuriousMainConfig(ModConfigSpec.Builder builder)
  {
    builder.push("debug");
    debug = builder.comment("Should debug logging & features be enabled?").define("debug", false);
    debugApiaryPollinationTime = builder.comment("The amount of time before the apiary attempts to pollinate").define("debugApiaryPollinationTime", 20);
    builder.pop();
    builder.push("general");
    builder.push("Defaults");
    baseCycleTime = builder.comment("Default cycle duration, defined in Ticks.", "This is used with the lifespan of the bee to decide the total run duration.").defineInRange("baseCycleTime", 550, 1, Integer.MAX_VALUE);
    baseValidationTimeInTicks = builder.comment("Default time in ticks between validation checks").define("baseValidationTimeInTicks", 20);
    builder.pop();
    builder.push("default minimums");
    minOutputInterval = builder.comment("The minimum number of ticks that must pass between output attempts, regardless of how far Speed alleles and frames reduce the cycle time below this.", "Prevents stacked speed/frame modifiers from collapsing output generation down to firing every tick.").defineInRange("minOutputInterval", 60, 1, Integer.MAX_VALUE);
    minLifespanTicks = builder.comment("The minimum total lifespan (in ticks) a queen can have after Lifespan allele and frame modifiers are applied.").defineInRange("minLifespanTicks", 100, 1, Integer.MAX_VALUE);
    builder.pop();
    builder.pop();
    builder.push("genetics");
    recessiveLeakChance = builder.comment("Chance for a recessive gene to outperform a dominant gene and 'leak through'").define("recessiveLeakChance", 0.1f);
    builder.pop();
    builder.push("compat");
    shouldJEIMutations = builder.comment("Should bee mutations be visible in JEI?").define("shouldJEIMutations", true);
    builder.pop();
    builder.push("totally-normal-configs");
    whyAreTheHorsesOnFire = builder.comment("Why are the horses on fire?").define("whyAreTheHorsesOnFire", false);
    weDontTalkAboutThat = builder.comment("We don't talk about that...").define("weDontTalkAboutThat", false);
    builder.pop();
  }

  public static void init()
  {
    configPair = new ModConfigSpec.Builder().configure(ApicuriousMainConfig::new);
    main_config = configPair.getKey();
  }

  public int getPollinationRate()
  {
    return debug.get() ? debugApiaryPollinationTime.get() : 375;
  }

  public static Integer getBaseCycleTime()
  {
    return main_config.baseCycleTime.get();
  }

  public static Integer getDebugApiaryPollinationTime()
  {
    return main_config.debugApiaryPollinationTime.get();
  }

  public static Integer getBaseValidationTimeInTicks()
  {
    return main_config.baseValidationTimeInTicks.get();
  }

  public static Integer getMinOutputInterval()
  {
    return main_config.minOutputInterval.get();
  }

  public static Boolean getShouldJEIMutations()
  {
    return main_config.shouldJEIMutations.get();
  }

  public static Boolean getWeDontTalkAboutThat()
  {
    return main_config.weDontTalkAboutThat.get();
  }

  public static Integer getMinLifespanTicks()
  {
    return main_config.minLifespanTicks.get();
  }

  public static Boolean getDebug()
  {
    return main_config.debug.get();
  }

  public static Boolean getWhyAreTheHorsesOnFire()
  {
    return main_config.whyAreTheHorsesOnFire.get();
  }

  public static Float getRecessiveLeakChance()
  {
    return main_config.recessiveLeakChance.get();
  }
}