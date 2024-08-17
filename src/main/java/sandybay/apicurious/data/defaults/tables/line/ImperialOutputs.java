package sandybay.apicurious.data.defaults.tables.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

public class ImperialOutputs
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.NOBLE.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.DRIPPING_COMB.get()))
            ).build()
    );
    bootstrap.register(ApicuriousSpecies.MAJESTIC.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.3f))
                    .withResult(result -> result.withResult(ItemRegistrar.DRIPPING_COMB.get()))
            ).build()
    );
    bootstrap.register(ApicuriousSpecies.IMPERIAL.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.DRIPPING_COMB.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.15f))
                    .withResult(result -> result.withResult(ItemRegistrar.ROYAL_JELLY.get()))
            ).build()
    );
  }
}
