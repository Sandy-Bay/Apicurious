package sandybay.apicurious.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import sandybay.apicurious.Apicurious;

import java.util.Set;

@EventBusSubscriber(modid = Apicurious.MODID)
public class ApicuriousDataGen
{
  @SubscribeEvent
  private static void generateClientData(final GatherDataEvent.Client event)
  {
    DataGenerator generator = event.getGenerator();
    PackOutput output = generator.getPackOutput();
    generator.addProvider(true, new ApicuriousLangProvider(output));
  }

  @SubscribeEvent
  private static void generateServerData(final GatherDataEvent.Server event)
  {
    DataGenerator generator = event.getGenerator();
    PackOutput output = generator.getPackOutput();
    generator.addProvider(true, new ApicuriousLootTables(output, event.getLookupProvider()));
    generator.addProvider(true, new ApicuriousTagProviders.BlocksProvider(output, event.getLookupProvider()));
    generator.addProvider(true, new ApicuriousTagProviders.ItemsProvider(output, event.getLookupProvider()));
    generator.addProvider(true, new ApicuriousTagProviders.BiomesProvider(output, event.getLookupProvider()));
    var registryDefaults = new DatapackBuiltinEntriesProvider(output, event.getLookupProvider(), ApicuriousDatapackRegistriesDefaults.registerDataPackRegistryDefaults(), Set.of(Apicurious.MODID));
    generator.addProvider(true, registryDefaults);
    var datapackProvider = registryDefaults.getRegistryProvider();
    generator.addProvider(true, new ApicuriousTagProviders.AlleleProvider(output, datapackProvider));
  }
}
