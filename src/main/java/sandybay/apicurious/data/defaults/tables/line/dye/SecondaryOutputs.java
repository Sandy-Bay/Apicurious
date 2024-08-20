package sandybay.apicurious.data.defaults.tables.line.dye;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

public class SecondaryOutputs
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.AMBER.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.ORANGE_TINTED_COMB.get()))
            )
            .build()
    );
    bootstrap.register(ApicuriousSpecies.TURQUOISE.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.CYAN_TINTED_COMB.get()))
            )
            .build()
    );
    bootstrap.register(ApicuriousSpecies.INDIGO.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.PURPLE_TINTED_COMB.get()))
            )
            .build()
    );
    bootstrap.register(ApicuriousSpecies.SLATE.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.GRAY_TINTED_COMB.get()))
            )
            .build()
    );
    bootstrap.register(ApicuriousSpecies.AZURE.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.LIGHT_BLUE_TINTED_COMB.get()))
            )
            .build()
    );
    bootstrap.register(ApicuriousSpecies.LAVENDER.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.PINK_TINTED_COMB.get()))
            )
            .build()
    );
    bootstrap.register(ApicuriousSpecies.LIME.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.75f))
                    .withResult(result -> result.withResult(Items.HONEYCOMB.builtInRegistryHolder().value()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.25f))
                    .withResult(result -> result.withResult(ItemRegistrar.LIME_TINTED_COMB.get()))
            )
            .build()
    );
  }
}
