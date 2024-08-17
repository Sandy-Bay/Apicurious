package sandybay.apicurious.data.defaults.tables.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

public class IndustriousOutputs
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.DILIGENT.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.STRINGY_COMB.get()))
            ).build()
    );
    bootstrap.register(ApicuriousSpecies.UNWEARY.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.3f))
                    .withResult(result -> result.withResult(ItemRegistrar.STRINGY_COMB.get()))
            ).build()
    );
    bootstrap.register(ApicuriousSpecies.INDUSTRIOUS.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.STRINGY_COMB.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.15f))
                    .withResult(result -> result.withResult(ItemRegistrar.POLLEN.get()))
            ).build()
    );
  }
}
