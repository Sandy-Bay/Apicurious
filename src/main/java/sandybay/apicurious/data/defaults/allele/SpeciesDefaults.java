package sandybay.apicurious.data.defaults.allele;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.data.defaults.allele.line.*;

public class SpeciesDefaults
{

  /*
    TODO: Add species for following materials:
      - Overworld:
        - Clay (Water + ?)
        - Slime (Clay + Jungle(?))
        - Amethyst
      - Nether:
        - Nether Wart (Fungal + Nether)
        - Nether Quartz (Resilient + Nether)
        - Glowstone (Energized + Nether)
        - Ancient Debris (Fossilized + Nether)
      - End
        - Chorus (Fungal + Ender)
      - Monster Loot
   */
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    AgrarianSpecies.defaults(bootstrap);
    BaseSpecies.defaults(bootstrap);
    CultivatedSpecies.defaults(bootstrap);
    DebugSpecies.defaults(bootstrap);
    DyeSpecies.defaults(bootstrap);
    EcstaticSpecies.defaults(bootstrap);
    FestiveSpecies.defaults(bootstrap);
    GemstoneSpecies.defaults(bootstrap);
    HeroicSpecies.defaults(bootstrap);
    ImperialSpecies.defaults(bootstrap);
    IndustriousSpecies.defaults(bootstrap);
    MetallicSpecies.defaults(bootstrap);
    MineralSpecies.defaults(bootstrap);
    ResilientSpecies.defaults(bootstrap);
    TimberedSpecies.defaults(bootstrap);
  }

  public static BeeSpecies.Builder getSpeciesBuilder(BootstrapContext<IAllele<?>> context, ResourceKey<IAllele<?>> key, String name)
  {
    return BeeSpecies.Builder.create(context, key, name);
  }
}
