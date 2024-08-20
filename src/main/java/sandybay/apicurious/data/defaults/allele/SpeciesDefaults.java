package sandybay.apicurious.data.defaults.allele;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.data.defaults.allele.species.*;
import sandybay.apicurious.data.defaults.branch.*;

public class SpeciesDefaults
{

  /*
    TODO: Add species for following materials:
      - Overworld:
        - Slime (Clay + Jungle(?))
        - Amethyst
      - Nether:
        - Nether Wart (Fungal + Nether)
        - Nether Quartz (Resilient + Nether)
        - Glowstone (Energized + Nether)
      - End
        - Chorus (Fungal + Ender)
      - Monster Loot
   */
  public static void defaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    BaseSpecies.defaults(bootstrap);
    DebugSpecies.defaults(bootstrap);
    AgrarianBranch.speciesDefaults(bootstrap);
    ApisBranch.speciesDefaults(bootstrap);
    AquaticBranch.speciesDefaults(bootstrap);
    AustereBranch.speciesDefaults(bootstrap);
    BarrenBranch.speciesDefaults(bootstrap);
    BoggyBranch.speciesDefaults(bootstrap);
    DyeBranch.speciesDefaults(bootstrap);
    EndBranch.speciesDefaults(bootstrap);
    EnergeticBranch.speciesDefaults(bootstrap);
    FestiveBranch.speciesDefaults(bootstrap);
    FossilisedBranch.speciesDefaults(bootstrap);
    FrozenBranch.speciesDefaults(bootstrap);
    GemstoneBranch.speciesDefaults(bootstrap);
    HeroicBranch.speciesDefaults(bootstrap);
    HistoricBranch.speciesDefaults(bootstrap);
    ImperialBranch.speciesDefaults(bootstrap);
    IndustriousBranch.speciesDefaults(bootstrap);
    InfernalBranch.speciesDefaults(bootstrap);
    MetallicBranch.speciesDefaults(bootstrap);
    MineralBranch.speciesDefaults(bootstrap);
    MonasticBranch.speciesDefaults(bootstrap);
    ResilientBranch.speciesDefaults(bootstrap);
    TimberedBranch.speciesDefaults(bootstrap);
  }

  public static BeeSpecies.Builder getSpeciesBuilder(BootstrapContext<IAllele<?>> context, ResourceKey<IAllele<?>> key, String name)
  {
    return BeeSpecies.Builder.create(context, key, name);
  }
}
