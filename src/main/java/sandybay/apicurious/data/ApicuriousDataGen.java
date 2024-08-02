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
    generator.addProvider(event.includeServer(), new ApicuriousTagProviders.BlocksProvider(output, event.getLookupProvider(), event.getExistingFileHelper()));
    generator.addProvider(event.includeServer(), new ApicuriousTagProviders.ItemsProvider(output, event.getLookupProvider(), event.getExistingFileHelper()));
    generator.addProvider(event.includeServer(), new ApicuriousTagProviders.BiomesProvider(output, event.getLookupProvider(), event.getExistingFileHelper()));
    generator.addProvider(// Only run datapack generation when server data is being generated
            event.includeServer(), new DatapackBuiltinEntriesProvider(output, event.getLookupProvider(),
                    ApicuriousDatapackRegistriesDefaults.registerDataPackRegistryDefaults(), Set.of(Apicurious.MODID)));

    generator.addProvider(event.includeServer(), new ApicuriousLootTables(output, event.getLookupProvider()));

    generator.addProvider(event.includeClient(), new ApicuriousLangProvider(output));
  }
}
