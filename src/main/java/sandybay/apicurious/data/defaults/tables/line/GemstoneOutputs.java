package sandybay.apicurious.data.defaults.tables.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

public class GemstoneOutputs
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.DIAMANTINE.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.ROCKY_COMB.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.01f))
                    .withResult(result -> result.withResult(ItemRegistrar.DIAMOND_COMB.get()))
            )
            .build()
    );
    bootstrap.register(ApicuriousSpecies.EMERALDINE.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.ROCKY_COMB.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.04f))
                    .withResult(result -> result.withResult(ItemRegistrar.EMERALD_COMB.get()))
            )
            .build()
    );
  }
}
