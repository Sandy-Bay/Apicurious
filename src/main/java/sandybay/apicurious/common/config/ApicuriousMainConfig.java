package sandybay.apicurious.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ApicuriousMainConfig
{
  public static Pair<ApicuriousMainConfig, ModConfigSpec> configPair;
  public static ApicuriousMainConfig main_config;

  // Debug
  public ModConfigSpec.ConfigValue<Boolean> debug;
  public ModConfigSpec.ConfigValue<Integer> debugApiaryCycleTime;
  public ModConfigSpec.ConfigValue<Integer> debugApiaryPollinationTime;
  public ModConfigSpec.ConfigValue<Integer> debugApiaryOutputTime;

  // General
  public ModConfigSpec.ConfigValue<Integer> baseCycleTime;

  public ApicuriousMainConfig(ModConfigSpec.Builder builder)
  {
    builder.push("debug");
    debug = builder
            .comment("Should debug logging & features be enabled?")
            .define("debug", false);
    debugApiaryCycleTime = builder
            .comment("The amount of time the apiary should take to run")
            .define("debugApiaryCycleTime", 100);
    debugApiaryPollinationTime = builder
            .comment("The amount of time before the apiary attempts to pollinate")
            .define("debugApiaryPollinationTime", 20);
    debugApiaryOutputTime = builder
            .comment("The amount of time before the apiary attempts to produce an output")
            .define("debugApiaryOutputTime", 20);
    builder.pop();
    builder.push("general");
    baseCycleTime = builder
            .comment("Default cycle duration, defined in Ticks.", "This is used with the lifespan of the bee to decide the total run duration.", "Default: 550")
            .defineInRange("baseCycleTime", 550, 1, Integer.MAX_VALUE);
    builder.pop();
  }

  public static void init()
  {
    configPair = new ModConfigSpec.Builder().configure(ApicuriousMainConfig::new);
    main_config = configPair.getKey();
  }

  public int getApiaryRunTime(int normalRunTime)
  {
    return debug.get() ? debugApiaryCycleTime.get() : normalRunTime;
  }

  public int getPollinationRate()
  {
    return debug.get() ? debugApiaryPollinationTime.get() : 375;
  }

  public int getOutputRate(int normalOutputTime)
  {
    return debug.get() ? debugApiaryOutputTime.get() : normalOutputTime;
  }


}
