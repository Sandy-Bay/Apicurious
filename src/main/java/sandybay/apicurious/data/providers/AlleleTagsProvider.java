package sandybay.apicurious.data.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

import java.util.concurrent.CompletableFuture;

public abstract class AlleleTagsProvider extends TagsProvider<IAllele<?>>
{

  public AlleleTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider)
  {
    super(pOutput, ApicuriousRegistries.ALLELES, pLookupProvider, Apicurious.MODID);
  }

  public AlleleTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                            CompletableFuture<TagLookup<IAllele<?>>> pParentProvider)
  {
    super(pOutput, ApicuriousRegistries.ALLELES, pLookupProvider, pParentProvider, Apicurious.MODID);
  }
}
