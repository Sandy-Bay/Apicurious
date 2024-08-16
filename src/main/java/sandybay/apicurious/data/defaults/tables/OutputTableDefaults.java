package sandybay.apicurious.data.defaults.tables;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.register.ItemRegistration;

public class OutputTableDefaults
{
  public static void defaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(OutputTableKeys.STANDARD_OUTPUT, simpleCombTable(Items.HONEYCOMB.builtInRegistryHolder()));
    bootstrap.register(OutputTableKeys.DRIPPING_OUTPUT, simpleCombTable(ItemRegistration.DRIPPING_COMB));
    bootstrap.register(OutputTableKeys.FROZEN_OUTPUT, simpleCombTable(ItemRegistration.FROZEN_COMB));
    bootstrap.register(OutputTableKeys.MOSSY_OUTPUT, simpleCombTable(ItemRegistration.MOSSY_COMB));
    bootstrap.register(OutputTableKeys.MYSTERIOUS_OUTPUT, simpleCombTable(ItemRegistration.MYSTERIOUS_COMB));
    bootstrap.register(OutputTableKeys.PARCHED_OUTPUT, simpleCombTable(ItemRegistration.PARCHED_COMB));
    bootstrap.register(OutputTableKeys.SIMMERING_OUTPUT, simpleCombTable(ItemRegistration.SIMMERING_COMB));
    bootstrap.register(OutputTableKeys.WHEATEN_OUTPUT, simpleCombTable(ItemRegistration.WHEATEN_COMB));
    bootstrap.register(OutputTableKeys.ROCKY_OUTPUT, simpleCombTable(ItemRegistration.ROCKY_COMB));
    bootstrap.register(OutputTableKeys.SILKY_OUTPUT, simpleCombTable(ItemRegistration.SILKY_COMB));
    bootstrap.register(OutputTableKeys.STRINGY_OUTPUT, simpleCombTable(ItemRegistration.STRINGY_COMB));
    bootstrap.register(OutputTableKeys.DUSTY_OUTPUT, simpleCombTable(ItemRegistration.DUSTY_COMB));
    bootstrap.register(OutputTableKeys.IMPERIAL_OUTPUT, custom()
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(ItemRegistration.DRIPPING_COMB.get())))
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(ItemRegistration.ROYAL_JELLY.get()).when(new ChanceCondition(0.33f)))
            ).build()
    );
    bootstrap.register(OutputTableKeys.INDUSTRIOUS_OUTPUT, custom()
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(ItemRegistration.STRINGY_COMB.get())))
            .withPool(pool -> pool.withRolls(1).withResult(entry -> entry.withResult(ItemRegistration.POLLEN.get()).when(new ChanceCondition(0.33f)))
            ).build()
    );
    bootstrap.register(OutputTableKeys.LEPORINE_OUTPUT, custom()
            .withPool(pool -> pool.withRolls(1)
                    .withResult(entry -> entry.withResult(ItemRegistration.SILKY_COMB.get()))
            )
            .withPool(pool -> pool.withRolls(1)
                    .withResult(entry -> entry.withResult(Items.EGG).when(new ChanceCondition(0.33f)))
            ).build()
    );
    bootstrap.register(OutputTableKeys.MERRY_OUTPUT, custom()
            .withPool(pool -> pool.withRolls(1)
                    .withResult(entry -> entry.withResult(ItemRegistration.STRINGY_COMB.get()))
            )
            .withPool(pool -> pool.withRolls(1)
                    .withResult(entry -> entry.withResult(ItemRegistration.ICE_SHARD.get()).when(new ChanceCondition(0.65f)))
            ).build()
    );
    bootstrap.register(OutputTableKeys.FARMED_OUTPUT, custom()
            .withPool(pool -> pool.withRolls(1)
                    .withResult(entry -> entry.withResult(ItemRegistration.WHEATEN_COMB.get()))
            )
            .withPool(pool -> pool.withRolls(1)
                    .withResult(entry -> entry.withResult(ItemRegistration.SEEDY_COMB.get()).when(new ChanceCondition(0.33f)))
            ).build()
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
