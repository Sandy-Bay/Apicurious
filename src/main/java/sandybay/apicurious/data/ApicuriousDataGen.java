package sandybay.apicurious.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import sandybay.apicurious.Apicurious;

import java.util.Set;

@EventBusSubscriber(modid = Apicurious.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ApicuriousDataGen
{
  @SubscribeEvent
  private static void generateData(final GatherDataEvent event)
  {
    DataGenerator generator = event.getGenerator();
    PackOutput output = generator.getPackOutput();
    generator.addProvider(event.includeServer(), new ApicuriousLootTables(output, event.getLookupProvider()));
    generator.addProvider(event.includeClient(), new ApicuriousLangProvider(output));
    generator.addProvider(event.includeServer(), new ApicuriousTagProviders.BlocksProvider(output, event.getLookupProvider(), event.getExistingFileHelper()));
    generator.addProvider(event.includeServer(), new ApicuriousTagProviders.ItemsProvider(output, event.getLookupProvider(), event.getExistingFileHelper()));
    generator.addProvider(event.includeServer(), new ApicuriousTagProviders.BiomesProvider(output, event.getLookupProvider(), event.getExistingFileHelper()));
    // INFO: Save the provider to a variable, so we can get the completable future for custom DPR tag support.
    var registryDefaults = new DatapackBuiltinEntriesProvider(output, event.getLookupProvider(), ApicuriousDatapackRegistriesDefaults.registerDataPackRegistryDefaults(), Set.of(Apicurious.MODID));
    generator.addProvider(event.includeServer(), registryDefaults);
    // INFO: We need to do this so that the custom DPR type tag provider knows about the existence of the data-pack registries.
    var datapackProvider = registryDefaults.getRegistryProvider();
    generator.addProvider(event.includeServer(), new ApicuriousTagProviders.AlleleProvider(output, datapackProvider, event.getExistingFileHelper()));
  }
}
