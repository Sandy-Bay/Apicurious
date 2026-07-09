package sandybay.apicurious.api.bee.genetic.mutation;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.SimpleBlockHousingHelper;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class MutationResolver
{
  private MutationResolver() {}

  /**
   * Resolves which mutation (if any) fires for the current parents in the housing.
   * Every matching mutation is collected first, with no RNG rolled yet, then rolled
   * in ascending order of base chance (rarest first) so registration order never
   * decides the outcome and no valid mutation is starved out by an earlier one.
   */
  public static IMutation resolve(Level level, SimpleBlockHousingBE housing)
  {
    if (level == null) {return null;}
    Holder<IAllele<?>> first = SimpleBlockHousingHelper.getSpeciesInSlot(housing, 0, true);
    Holder<IAllele<?>> second = SimpleBlockHousingHelper.getSpeciesInSlot(housing, 1, true);
    if (first == null || second == null || first.is(second)) {return null;}

    Optional<Registry<IMutation>> mutationsLookup = level.registryAccess().lookup(ApicuriousRegistries.MUTATIONS);
    Optional<Registry<IAllele<?>>> allelesLookup = level.registryAccess().lookup(ApicuriousRegistries.ALLELES);
    if (mutationsLookup.isEmpty() || allelesLookup.isEmpty()) {return null;}

    List<IMutation> candidates = MutationIndex.candidatesFor(mutationsLookup.get(), allelesLookup.get(), first, second).stream()
            .filter(mutation -> mutation.matches(housing))
            .sorted(Comparator.comparingDouble(IMutation::chance))
            .toList();

    for (IMutation candidate : candidates)
    {
      if (candidate.rollSuccess(housing)) {return candidate;}
    }
    return null;
  }
}