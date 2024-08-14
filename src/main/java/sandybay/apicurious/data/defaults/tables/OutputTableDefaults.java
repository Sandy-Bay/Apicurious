package sandybay.apicurious.data.defaults.tables;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.output.OutputResult;
import sandybay.apicurious.api.bee.output.OutputTable;
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
  }

  public static OutputTable.Builder custom()
  {
    return OutputTable.builder();
  }

  public static OutputTable simpleCombTable(Holder<Item> item) {
    return custom().withPool(pool -> pool.withRolls(1)
            .withResult(entry -> entry.withResult(new OutputResult(new ItemStack(item)))))
            .build();
  }
}
