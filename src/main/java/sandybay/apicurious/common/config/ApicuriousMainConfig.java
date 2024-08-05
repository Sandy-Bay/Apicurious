package sandybay.apicurious.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ApicuriousMainConfig
{
  public static Pair<ApicuriousMainConfig, ModConfigSpec> configPair;
  public static ApicuriousMainConfig main_config;

  public ModConfigSpec.ConfigValue<Boolean> debug;

  public ApicuriousMainConfig(ModConfigSpec.Builder builder)
  {
    debug = builder.comment("Should debug logging & features be enabled?").define("debug", false);
  }

  public static void init()
  {
    configPair = new ModConfigSpec.Builder().configure(ApicuriousMainConfig::new);
    main_config = configPair.getKey();
  }

}
