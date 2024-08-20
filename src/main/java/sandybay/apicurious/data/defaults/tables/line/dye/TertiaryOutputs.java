package sandybay.apicurious.data.defaults.tables.line.dye;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

public class TertiaryOutputs
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.ASHEN.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.LIGHT_GRAY_TINTED_COMB.get()))
            )
            .build()
    );
    bootstrap.register(ApicuriousSpecies.FUCHSIA.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.MAGENTA_TINTED_COMB.get()))
            )
            .build()
    );
  }
}
