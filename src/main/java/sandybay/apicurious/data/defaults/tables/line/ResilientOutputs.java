package sandybay.apicurious.data.defaults.tables.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

public class ResilientOutputs
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.TOLERANT.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.3f))
                    .withResult(result -> result.withResult(ItemRegistrar.ROCKY_COMB.get()))
            ).build()
    );
    bootstrap.register(ApicuriousSpecies.ROBUST.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.3f))
                    .withResult(result -> result.withResult(ItemRegistrar.ROCKY_COMB.get()))
            ).build()
    );
    bootstrap.register(ApicuriousSpecies.RESILIENT.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.3f))
                    .withResult(result -> result.withResult(ItemRegistrar.ROCKY_COMB.get()))
            ).build()
    );
  }
}
