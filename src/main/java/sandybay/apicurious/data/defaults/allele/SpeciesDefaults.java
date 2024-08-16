package sandybay.apicurious.data.defaults.allele;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.common.bee.genetic.allele.Workcycle;
import sandybay.apicurious.common.bee.species.BeeColor;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.data.defaults.allele.line.*;

public class SpeciesDefaults
{
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    AgrarianSpecies.defaults(bootstrap);
    BaseSpecies.defaults(bootstrap);
    CommonSpecies.defaults(bootstrap);
    DebugSpecies.defaults(bootstrap);
    DiligentSpecies.defaults(bootstrap);
    FestiveSpecies.defaults(bootstrap);
    HeroicSpecies.defaults(bootstrap);
    NobleSpecies.defaults(bootstrap);
    WoodenSpecies.defaults(bootstrap);
  }

  public static BeeSpecies.Builder getSpeciesBuilder(BootstrapContext<IAllele<?>> context, ResourceKey<IAllele<?>> key, String name)
  {
    return BeeSpecies.Builder.create(context, key, name);
  }
}
