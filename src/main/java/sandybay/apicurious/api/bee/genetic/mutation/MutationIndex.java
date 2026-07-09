// api/bee/genetic/mutation/MutationIndex.java
package sandybay.apicurious.api.bee.genetic.mutation;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.Stream;

final class MutationIndex
{
  private static final Map<Registry<IMutation>, Map<Holder<IAllele<?>>, List<IMutation>>> CACHE = new WeakHashMap<>();

  private MutationIndex() {}

  static List<IMutation> candidatesFor(Registry<IMutation> mutations, Registry<IAllele<?>> alleles,
                                       Holder<IAllele<?>> first, Holder<IAllele<?>> second)
  {
    Map<Holder<IAllele<?>>, List<IMutation>> index = CACHE.computeIfAbsent(mutations, reg -> build(reg, alleles));

    List<IMutation> forFirst = index.getOrDefault(first, List.of());
    List<IMutation> forSecond = index.getOrDefault(second, List.of());
    if (forFirst.isEmpty()) {return forSecond;}
    if (forSecond.isEmpty()) {return forFirst;}
    return Stream.concat(forFirst.stream(), forSecond.stream()).distinct().toList();
  }

  private static Map<Holder<IAllele<?>>, List<IMutation>> build(Registry<IMutation> mutations, Registry<IAllele<?>> alleles)
  {
    Map<Holder<IAllele<?>>, List<IMutation>> index = new HashMap<>();
    for (Holder.Reference<IAllele<?>> allele : alleles.listElements().toList())
    {
      List<IMutation> matching = mutations.stream()
              .filter(mutation -> mutation.first().contains(allele) || mutation.second().contains(allele))
              .toList();
      if (!matching.isEmpty()) {index.put(allele, matching);}
    }
    return index;
  }
}