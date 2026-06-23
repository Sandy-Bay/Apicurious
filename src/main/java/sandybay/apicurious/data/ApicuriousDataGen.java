package sandybay.apicurious.data;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import sandybay.apicurious.Apicurious;

import java.util.Set;

public class ApicuriousDataGen
{
  public static void generateClientData(final GatherDataEvent.Client event)
  {
    Apicurious.LOGGER.error("HELLO123");
    event.createProvider(ApicuriousLangProvider::new);
  }

  public static void generateServerData(final GatherDataEvent.Server event)
  {
    Apicurious.LOGGER.error("HELLO123");
    event.createProvider(ApicuriousLootTables::new);
    event.createProvider(ApicuriousTagProviders.BlocksProvider::new);
    event.createProvider(ApicuriousTagProviders.ItemsProvider::new);
    event.createProvider(ApicuriousTagProviders.BiomesProvider::new);
    event.createProvider((output, lookupProvider) -> new DatapackBuiltinEntriesProvider(output, lookupProvider, ApicuriousDatapackRegistriesDefaults.registerDataPackRegistryDefaults(), Set.of(Apicurious.MODID)));
    event.createProvider(ApicuriousTagProviders.AlleleProvider::new);
  }
}
