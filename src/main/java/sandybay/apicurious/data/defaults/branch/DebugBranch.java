package sandybay.apicurious.data.defaults.branch;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.Area;
import sandybay.apicurious.common.bee.genetic.allele.Flowers;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;

public class DebugBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.EMPTY.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.EMPTY.species(), "undefined")
            .withOutputData(builder -> builder.withTable(ApicuriousSpecies.COMMON.output()))
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
                    environment.withFlowers(Flowers.STONE)
            )
            .withOutputData(outputs ->
                    outputs.withTable(table ->
                            table.withPool(pool -> pool
                                    .withRolls(1)
                                    .withResult(result -> result.withResult(Items.HONEYCOMB))
                            )
                    )
            ).build()
    );
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {

  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {

  }
}
