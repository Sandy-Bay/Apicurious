package sandybay.apicurious.data.defaults.tables.line.dye;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

public class PrimaryOutputs
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.MAROON.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.RED_TINTED_COMB.get()))
            )
            .build()
    );
    bootstrap.register(ApicuriousSpecies.SAFFRON.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.YELLOW_TINTED_COMB.get()))
            )
            .build()
    );
    bootstrap.register(ApicuriousSpecies.PRUSSIAN.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.BLUE_TINTED_COMB.get()))
            )
            .build()
    );
    bootstrap.register(ApicuriousSpecies.NATURAL.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.GREEN_TINTED_COMB.get()))
            )
            .build()
    );
    bootstrap.register(ApicuriousSpecies.SEPIA.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.BROWN_TINTED_COMB.get()))
            )
            .build()
    );
    bootstrap.register(ApicuriousSpecies.BLEACHED.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.WHITE_TINTED_COMB.get()))
            )
            .build()
    );
    bootstrap.register(ApicuriousSpecies.EBONY.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.BLACK_TINTED_COMB.get()))
            )
            .build()
    );
  }
}
