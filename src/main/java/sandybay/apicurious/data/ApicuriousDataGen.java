package sandybay.apicurious.data;


import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.data.client.ApicuriousLangProvider;
import sandybay.apicurious.data.client.ApicuriousModelProvider;
import sandybay.apicurious.data.server.ApicuriousDatapackRegistriesDefaults;
import sandybay.apicurious.data.server.ApicuriousLootTables;
import sandybay.apicurious.data.server.ApicuriousTagProviders;

import java.util.Set;

public class ApicuriousDataGen
{
  public static void generateClientData(final GatherDataEvent.Client event)
  {
    event.createProvider(ApicuriousLangProvider::new);
    event.createProvider(ApicuriousModelProvider::new);
  }

  public static void generateServerData(final GatherDataEvent.Server event)
  {
    event.createProvider(ApicuriousLootTables::new);
    event.createProvider(ApicuriousTagProviders.BlocksProvider::new);
    event.createProvider(ApicuriousTagProviders.ItemsProvider::new);
    event.createProvider(ApicuriousTagProviders.BiomesProvider::new);
    event.createProvider(ApicuriousTagProviders.VillagerTradeTagsProvider::new);
    DatapackBuiltinEntriesProvider provider = event.createProvider((output, lookupProvider) -> new DatapackBuiltinEntriesProvider(output, lookupProvider, ApicuriousDatapackRegistriesDefaults.registerDataPackRegistryDefaults(), Set.of(Apicurious.MODID)));
    event.createProvider(output -> new ApicuriousTagProviders.AlleleProvider(output, provider.getRegistryProvider()));
  }
}
