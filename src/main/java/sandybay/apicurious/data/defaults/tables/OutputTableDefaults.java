package sandybay.apicurious.data.defaults.tables;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.registrar.ItemRegistrar;

public class OutputTableDefaults
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(OutputTableKeys.STANDARD_OUTPUT, simpleCombTable(Items.HONEYCOMB.builtInRegistryHolder()));
    bootstrap.register(OutputTableKeys.DRIPPING_OUTPUT, simpleCombTable(ItemRegistrar.DRIPPING_COMB));
    bootstrap.register(OutputTableKeys.FROZEN_OUTPUT, simpleCombTable(ItemRegistrar.FROZEN_COMB));
    bootstrap.register(OutputTableKeys.MOSSY_OUTPUT, simpleCombTable(ItemRegistrar.MOSSY_COMB));
    bootstrap.register(OutputTableKeys.MYSTERIOUS_OUTPUT, simpleCombTable(ItemRegistrar.MYSTERIOUS_COMB));
    bootstrap.register(OutputTableKeys.PARCHED_OUTPUT, simpleCombTable(ItemRegistrar.PARCHED_COMB));
    bootstrap.register(OutputTableKeys.SIMMERING_OUTPUT, simpleCombTable(ItemRegistrar.SIMMERING_COMB));
    bootstrap.register(OutputTableKeys.WHEATEN_OUTPUT, simpleCombTable(ItemRegistrar.WHEATEN_COMB));
    bootstrap.register(OutputTableKeys.ROCKY_OUTPUT, simpleCombTable(ItemRegistrar.ROCKY_COMB));
    bootstrap.register(OutputTableKeys.SILKY_OUTPUT, simpleCombTable(ItemRegistrar.SILKY_COMB));
    bootstrap.register(OutputTableKeys.STRINGY_OUTPUT, simpleCombTable(ItemRegistrar.STRINGY_COMB));
    bootstrap.register(OutputTableKeys.DUSTY_OUTPUT, simpleCombTable(ItemRegistrar.DUSTY_COMB));
    bootstrap.register(OutputTableKeys.COCOA_OUTPUT, simpleCombTable(ItemRegistrar.COCOA_COMB));
    bootstrap.register(OutputTableKeys.VALIANT_OUTPUT, custom()
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(ItemRegistrar.COCOA_COMB.get())))
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(Items.SUGAR).when(new ChanceCondition(0.5f))))
            .build()
    );
    bootstrap.register(OutputTableKeys.IMPERIAL_OUTPUT, custom()
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(ItemRegistrar.DRIPPING_COMB.get())))
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(ItemRegistrar.ROYAL_JELLY.get()).when(new ChanceCondition(0.33f))))
            .build()
    );
    bootstrap.register(OutputTableKeys.INDUSTRIOUS_OUTPUT, custom()
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(ItemRegistrar.STRINGY_COMB.get())))
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(ItemRegistrar.POLLEN.get()).when(new ChanceCondition(0.33f))))
            .build()
    );
    bootstrap.register(OutputTableKeys.LEPORINE_OUTPUT, custom()
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(ItemRegistrar.SILKY_COMB.get())))
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(Items.EGG).when(new ChanceCondition(0.33f))))
            .build()
    );
    bootstrap.register(OutputTableKeys.MERRY_OUTPUT, custom()
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(ItemRegistrar.STRINGY_COMB.get())))
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(ItemRegistrar.ICE_SHARD.get()).when(new ChanceCondition(0.65f))))
            .build()
    );
    bootstrap.register(OutputTableKeys.FARMED_OUTPUT, custom()
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(ItemRegistrar.WHEATEN_COMB.get())))
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(ItemRegistrar.SEEDY_COMB.get()).when(new ChanceCondition(0.33f))))
            .build()
    );
  }

  public static OutputTable.Builder custom()
  {
    return OutputTable.builder();
  }

  public static OutputTable simpleCombTable(Holder<Item> item)
  {
    return custom().withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(item.value()))).build();
  }
}
