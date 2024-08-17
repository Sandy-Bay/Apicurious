package sandybay.apicurious.data.defaults.tables.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

public class MetallicOutputs
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.CUPRUM.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.ROCKY_COMB.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.06f))
                    .withResult(result -> result.withResult(ItemRegistrar.COPPER_COMB.get()))
            ).build()
    );
    bootstrap.register(ApicuriousSpecies.FERRUS.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.ROCKY_COMB.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.05f))
                    .withResult(result -> result.withResult(ItemRegistrar.IRON_COMB.get()))
            ).build()
    );
    bootstrap.register(ApicuriousSpecies.AURUM.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.ROCKY_COMB.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.02f))
                    .withResult(result -> result.withResult(ItemRegistrar.GOLD_COMB.get()))
            ).build()
    );
  }
}
