package sandybay.apicurious.data.defaults.tables.line;

import net.minecraft.data.worldgen.BootstrapContext;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

public class EcstaticOutputs
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.EXCITED.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.ENERGETIC_COMB, 0.1f));
    bootstrap.register(ApicuriousSpecies.ENERGETIC.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.ENERGETIC_COMB, 0.12f));
    bootstrap.register(ApicuriousSpecies.ECSTATIC.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.2f))
                    .withResult(result -> result.withResult(ItemRegistrar.ENERGETIC_COMB.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.08f))
                    .withResult(result -> result.withResult(ItemRegistrar.STATIC_COMB.get()))
            )
            .build()
    );
  }
}
