package sandybay.apicurious;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.client.ApicuriousClientEvents;
import sandybay.apicurious.common.block.ApicuriousBlockRegistration;
import sandybay.apicurious.common.creativetab.ApicuriousCreativeTabs;
import sandybay.apicurious.common.datacomponent.ApicuriousDataComponents;
import sandybay.apicurious.common.item.ApicuriousItemRegistration;
import sandybay.apicurious.common.worldgen.ApicuriousWorldGen;
import sandybay.apicurious.data.ApicuriousDatapackRegistriesDefaults;
import sandybay.apicurious.data.ApicuriousLootTables;
import sandybay.apicurious.data.ApicuriousTagProvider;
import sandybay.apicurious.data.loot.ApicuriousLootItemFunctions;

import java.util.Set;

// TODO: Finish Bees, including Genetics (Allele, Genome, Mutations) and Items
// TODO: Finish Housing, including Apiary & BeeHousing, excluding Alveary
// TODO: Finish Data Generation

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Apicurious.MODID)
public class Apicurious
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "apicurious";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Apicurious(IEventBus bus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        bus.addListener(this::commonSetup);
        bus.addListener(this::generateData);
        bus.addListener(ApicuriousRegistries::registerDatapackRegistries);
        ApicuriousBlockRegistration.register(bus);
        ApicuriousItemRegistration.register(bus);
        ApicuriousDataComponents.register(bus);
        ApicuriousCreativeTabs.register(bus);
        ApicuriousLootItemFunctions.register(bus);
        NeoForge.EVENT_BUS.addListener(ApicuriousWorldGen::hackTheHives);
        if (FMLLoader.getDist() == Dist.CLIENT) {
            ApicuriousClientEvents.registerClientEvents(bus);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

    }

    private void generateData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(
                event.includeServer(),
                new ApicuriousTagProvider.BlocksProvider(output, event.getLookupProvider(), event.getExistingFileHelper())
        );
        generator.addProvider(
                event.includeServer(),
                new ApicuriousTagProvider.ItemsProvider(output, event.getLookupProvider(), event.getExistingFileHelper())
        );
        generator.addProvider(
                // Only run datapack generation when server data is being generated
                event.includeServer(),
                new DatapackBuiltinEntriesProvider(
                        output,
                        event.getLookupProvider(),
                        ApicuriousDatapackRegistriesDefaults.registerDataPackRegistryDefaults(),
                        Set.of(MODID)
        ));
        generator.addProvider(
                event.includeServer(),
                new ApicuriousLootTables(output, event.getLookupProvider())
        );
    }

    public static ResourceLocation createResourceLocation(String path) {
        return ResourceLocation.tryBuild(MODID, path);
    }
}
