package sandybay.apicurious;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.slf4j.Logger;
import sandybay.apicurious.api.register.AlleleTypeRegistration;
import sandybay.apicurious.api.register.DataComponentRegistration;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.client.ApicuriousClientEvents;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.config.ApicuriousMainConfig;
import sandybay.apicurious.common.item.BaseBeeItem;
import sandybay.apicurious.common.network.PacketHandler;
import sandybay.apicurious.common.register.BlockRegistration;
import sandybay.apicurious.common.register.CreativeTabRegistration;
import sandybay.apicurious.common.register.ItemRegistration;
import sandybay.apicurious.common.register.MenuRegistration;
import sandybay.apicurious.common.worldgen.ApicuriousWorldGen;
import sandybay.apicurious.data.LootItemFunctionRegistration;

/*
 TODO: Before MVP Alpha Release
 - Must Haves:
   - Allele/Genome (Genetics)
   - Mutations (Genetics and Species)
   - Particles for active housing
   - Proper errors for housing
     - Error Message
     - Error Tooltip
   - Worldgen for Hives
 - Optionals:
   - Effects
   - Output System
   - General Code Clean-Up
 */
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
  public Apicurious(IEventBus bus, ModContainer modContainer)
  {
    // Register the commonSetup method for modloading
    ApicuriousMainConfig.init();
    modContainer.registerConfig(ModConfig.Type.COMMON, ApicuriousMainConfig.configPair.getValue());
    bus.addListener(this::commonSetup);
    bus.addListener(ApicuriousRegistries::registerRegistries);
    bus.addListener(ApicuriousRegistries::registerDatapackRegistries);
    BlockRegistration.register(bus);
    ItemRegistration.register(bus);
    DataComponentRegistration.register(bus);
    CreativeTabRegistration.register(bus);
    LootItemFunctionRegistration.register(bus);
    MenuRegistration.register(bus);
    AlleleTypeRegistration.init(bus);
    PacketHandler.init(bus);
    NeoForge.EVENT_BUS.addListener(ApicuriousWorldGen::hackTheHives);
    NeoForge.EVENT_BUS.addListener(Apicurious::loadEmptySpecies);
    if (FMLLoader.getDist() == Dist.CLIENT)
    {
      ApicuriousClientEvents.registerClientEvents(bus);
    }
  }

  private static void loadEmptySpecies(final EntityJoinLevelEvent event)
  {
    if (BaseBeeItem.EMPTY_SPECIES == null && event.getEntity() instanceof Player)
    {
      Level level = event.getLevel();
      if (level instanceof ServerLevel serverLevel)
      {
        serverLevel.registryAccess().registry(ApicuriousRegistries.ALLELES).ifPresent(registry ->
                BaseBeeItem.EMPTY_SPECIES = (BeeSpecies) registry.get(ApicuriousSpecies.EMPTY));
      } else if (level instanceof ClientLevel)
      {
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection != null)
        {
          connection.registryAccess().registry(ApicuriousRegistries.ALLELES).ifPresent(registry ->
                  BaseBeeItem.EMPTY_SPECIES = (BeeSpecies) registry.get(ApicuriousSpecies.EMPTY));
        }
      }
    }
  }

  public static ResourceLocation createResourceLocation(String path)
  {
    return ResourceLocation.tryBuild(MODID, path);
  }

  private void commonSetup(final FMLCommonSetupEvent event)
  {
    // Some common setup code
    LOGGER.info("HELLO FROM COMMON SETUP");

  }
}
