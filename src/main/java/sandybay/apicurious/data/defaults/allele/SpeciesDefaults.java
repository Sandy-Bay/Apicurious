package sandybay.apicurious.data.defaults.allele;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.allele.Area;
import sandybay.apicurious.common.bee.genetic.allele.Flowers;
import sandybay.apicurious.common.bee.species.BeeColor;
import sandybay.apicurious.common.bee.species.BeeSpecies;

public class SpeciesDefaults
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.EMPTY, getSpeciesBuilder(bootstrap, ApicuriousSpecies.EMPTY, "undefined").build());
    bootstrap.register(ApicuriousSpecies.FOREST, speciesWithColor(bootstrap, ApicuriousSpecies.FOREST, "forest", ApicuriousConstants.FOREST));
    bootstrap.register(ApicuriousSpecies.MEADOW, speciesWithColor(bootstrap, ApicuriousSpecies.MEADOW, "meadow", ApicuriousConstants.MEADOW));
    bootstrap.register(ApicuriousSpecies.MODEST, speciesWithColor(bootstrap, ApicuriousSpecies.MODEST, "modest", ApicuriousConstants.MODEST));
    bootstrap.register(ApicuriousSpecies.TROPICAL, speciesWithColor(bootstrap, ApicuriousSpecies.TROPICAL, "tropical", ApicuriousConstants.TROPICAL));
    bootstrap.register(ApicuriousSpecies.WINTRY, speciesWithColor(bootstrap, ApicuriousSpecies.WINTRY, "wintry", ApicuriousConstants.WINTRY));
    bootstrap.register(ApicuriousSpecies.MARSHY, speciesWithColor(bootstrap, ApicuriousSpecies.MARSHY, "marshy", ApicuriousConstants.MARSHY));
    bootstrap.register(ApicuriousSpecies.ROCKY, speciesWithColor(bootstrap, ApicuriousSpecies.ROCKY, "rocky", ApicuriousConstants.ROCKY));
    bootstrap.register(ApicuriousSpecies.NETHER, speciesWithColor(bootstrap, ApicuriousSpecies.NETHER, "nether", ApicuriousConstants.NETHER));
    bootstrap.register(ApicuriousSpecies.ENDER, speciesWithColor(bootstrap, ApicuriousSpecies.ENDER, "ender", ApicuriousConstants.ENDER));
    bootstrap.register(ApicuriousSpecies.DEBUG, getSpeciesBuilder(bootstrap, ApicuriousSpecies.DEBUG, "debug")
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
                    outputs.withStack(Items.HONEYCOMB, 1)
            ).build()
    );
  }

  private static BeeSpecies speciesWithColor(BootstrapContext<IAllele<?>> context, ResourceKey<IAllele<?>> key, String name, BeeColor color)
  {
    return BeeSpecies.Builder.create(context, key, name).withVisualData(visual -> visual.withBeeColor(color).build()).build();
  }

  private static BeeSpecies.Builder getSpeciesBuilder(BootstrapContext<IAllele<?>> context, ResourceKey<IAllele<?>> key, String name)
  {
    return BeeSpecies.Builder.create(context, key, name);
  }
}
