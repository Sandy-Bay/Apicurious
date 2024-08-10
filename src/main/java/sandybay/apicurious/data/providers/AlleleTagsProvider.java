package sandybay.apicurious.data.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

import java.util.concurrent.CompletableFuture;

public abstract class AlleleTagsProvider extends TagsProvider<IAllele<?>>
{

  public AlleleTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper)
  {
    super(pOutput, ApicuriousRegistries.ALLELES, pLookupProvider, Apicurious.MODID, existingFileHelper);
  }

  public AlleleTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<IAllele<?>>> pParentProvider, @Nullable ExistingFileHelper existingFileHelper)
  {
    super(pOutput, ApicuriousRegistries.ALLELES, pLookupProvider, pParentProvider, Apicurious.MODID, existingFileHelper);
  }
}
