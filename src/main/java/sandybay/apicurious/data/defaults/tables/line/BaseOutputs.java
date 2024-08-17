package sandybay.apicurious.data.defaults.tables.line;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

public class BaseOutputs
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.FOREST.output(), OutputTableDefaults.simpleCombTable(Items.HONEYCOMB.builtInRegistryHolder(), 0.3f));
    bootstrap.register(ApicuriousSpecies.MEADOW.output(), OutputTableDefaults.simpleCombTable(Items.HONEYCOMB.builtInRegistryHolder(), 0.3f));
    bootstrap.register(ApicuriousSpecies.MODEST.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.PARCHED_COMB, 0.2f));
    bootstrap.register(ApicuriousSpecies.TROPICAL.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.SILKY_COMB, 0.2f));
    bootstrap.register(ApicuriousSpecies.WINTRY.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.FROZEN_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.MARSHY.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.MOSSY_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.ROCKY.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.ROCKY_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.WATER.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.DAMP_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.NETHER.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.SIMMERING_COMB, 0.25f));
    bootstrap.register(ApicuriousSpecies.ENDER.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.MYSTERIOUS_COMB, 0.3f));
    bootstrap.register(ApicuriousSpecies.STEADFAST.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.COCOA_COMB, 0.2f));
    bootstrap.register(ApicuriousSpecies.VALIANT.output(), OutputTableDefaults.custom()
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.3f))
                    .withResult(result -> result.withResult(ItemRegistrar.COCOA_COMB.get()))
            )
            .withPool(pool -> pool
                    .when(new ChanceCondition(0.15f))
                    .withResult(result -> result.withResult(Items.SUGAR))
            )
            .build()
    );

  }
}
