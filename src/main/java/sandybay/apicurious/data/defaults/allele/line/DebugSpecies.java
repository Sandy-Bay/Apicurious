package sandybay.apicurious.data.defaults.allele.line;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.output.OutputResult;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.Area;
import sandybay.apicurious.common.bee.genetic.allele.Flowers;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;
import sandybay.apicurious.data.defaults.tables.OutputTableKeys;

public class DebugSpecies
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.EMPTY.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.EMPTY.species(), "undefined")
            .withOutputData(OutputTableKeys.STANDARD_OUTPUT)
            .build()
    );
    bootstrap.register(ApicuriousSpecies.DEBUG.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.DEBUG.species(), "debug")
            .withVisualData(visual ->
            {
              visual.hasEffect().hasCustomRender().build();
            })
            .withProductionData(production ->
                    production.withArea(Area.LARGEST)
            ).withEnvironmentalData(environment ->
                    environment.withFlowers(Flowers.ROCK)
            )
            .withOutputData(outputs ->
                    outputs.withTable(table ->
                            table.withPool(pool -> pool
                                    .withRolls(1)
                                    .withResult(result ->
                                            result.withResult(
                                                    new OutputResult(
                                                            new ItemStack(Items.HONEYCOMB, 1)
                                                    )
                                            )
                                    )
                            )
                    )
            ).build()
    );
  }
}
