package sandybay.apicurious;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.data.ApicuriousDatapackRegistriesDefaults;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Apicurious.MODID)
public class Apicurious
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "apicurious";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Apicurious(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::generateData);
        modEventBus.addListener(ApicuriousRegistries::registerDatapackRegistries);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

    }

    private void generateData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        if (event.includeClient()) {

        }

        if (event.includeServer()) {

        }

        generator.addProvider(
                // Only run datapack generation when server data is being generated
                event.includeServer(),
                new DatapackBuiltinEntriesProvider(
                        output,
                        event.getLookupProvider(),
                        ApicuriousDatapackRegistriesDefaults.registerDataPackRegistryDefaults(),
                        Set.of(MODID)
        ));
    }

    private void registerDatapackRegistryDefaults(final DatapackBuiltinEntriesProvider)

    public static ResourceLocation createResourceLocation(String path) {
        return ResourceLocation.tryBuild(MODID, path);
    }
}
