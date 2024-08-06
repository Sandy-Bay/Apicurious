package sandybay.apicurious.api.register;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.TraitType;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.trait.Area;

public class ApicuriousTraitTypeRegistry
{
  private static final DeferredRegister<TraitType> TRAIT_TYPES = DeferredRegister.create(ApicuriousRegistries.TRAIT_TYPES, Apicurious.MODID);

  public static final DeferredHolder<TraitType, TraitType> AREA_TYPE = TRAIT_TYPES.register("area", () -> new TraitType(Area.CODEC, Area.NETWORK_CODEC));
}
