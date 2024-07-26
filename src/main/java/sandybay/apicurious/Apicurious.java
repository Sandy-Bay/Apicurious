package sandybay.apicurious;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.client.ApicuriousClientEvents;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;
import sandybay.apicurious.common.register.ApicuriousCreativeTabRegistration;
import sandybay.apicurious.common.register.ApicuriousDataComponentRegistration;
import sandybay.apicurious.common.register.ApicuriousItemRegistration;
import sandybay.apicurious.common.register.ApicuriousMenuRegistration;
import sandybay.apicurious.common.worldgen.ApicuriousWorldGen;
import sandybay.apicurious.data.loot.ApicuriousLootItemFunctions;

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
        bus.addListener(ApicuriousRegistries::registerDatapackRegistries);
        ApicuriousBlockRegistration.register(bus);
        ApicuriousItemRegistration.register(bus);
        ApicuriousDataComponentRegistration.register(bus);
        ApicuriousCreativeTabRegistration.register(bus);
        ApicuriousLootItemFunctions.register(bus);
        ApicuriousMenuRegistration.register(bus);
        NeoForge.EVENT_BUS.addListener(ApicuriousWorldGen::hackTheHives);
        if (FMLLoader.getDist() == Dist.CLIENT) {
            ApicuriousClientEvents.registerClientEvents(bus);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

    }

    public static ResourceLocation createResourceLocation(String path) {
        return ResourceLocation.tryBuild(MODID, path);
    }
}
