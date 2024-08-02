package sandybay.apicurious.api.bee.output;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceKey;
import org.slf4j.Logger;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.species.BeeSpecies;

import java.util.List;

public class OutputTable {
  private static final Logger LOGGER = LogUtils.getLogger();
  public static final OutputTable EMPTY = new OutputTable(ApicuriousSpecies.EMPTY, Lists.newArrayList());

  //public static final Codec<OutputTable> DIRECT_CODEC;
  //public static final Codec<Holder<OutputTable>> CODEC;

  private final ResourceKey<BeeSpecies> speciesKey;
  private final List<OutputPool> pools;

  OutputTable(ResourceKey<BeeSpecies> speciesKey, List<OutputPool> pools) {
    this.speciesKey = speciesKey;
    this.pools = pools;
  }

}
