package sandybay.apicurious;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import net.neoforged.neoforge.transfer.item.ItemAccessItemHandler;
import org.slf4j.Logger;
import sandybay.apicurious.api.register.AlleleTypeRegistrar;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.register.MutationTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.client.ApicuriousClientEvents;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.config.ApicuriousMainConfig;
import sandybay.apicurious.common.item.BeeItem;
import sandybay.apicurious.common.network.PacketHandler;
import sandybay.apicurious.common.registrar.*;
import sandybay.apicurious.common.worldgen.ApicuriousWorldGen;
import sandybay.apicurious.data.ApicuriousDataGen;
import sandybay.apicurious.data.server.LootItemFunctionRegistration;

/*
 Todo: Before MVP Alpha Release
  - Must Haves
    - Apiary/BeeHousing
      - Add Particles for active housing
    - Hives
      - Add WorldGen for Hives
 */
/*
 TODO: Before MVP Beta Release
  - Must haves
    - Alveary (Multiblock)
    - Bee Branch Rework
    - Effects
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
    modContainer.registerConfig(ModConfig.Type.COMMON, ApicuriousMainConfig.configPair.getValue(), "apicurious/apicurious.toml");
    ApicuriousSpecies.init();
    bus.addListener(this::registerDMT);
    bus.addListener(ApicuriousRegistries::registerRegistries);
    bus.addListener(ApicuriousRegistries::registerDatapackRegistries);
    bus.addListener(this::registerCapabilities);
    BlockRegistrar.register(bus);
    ItemRegistrar.register(bus);
    DataComponentRegistrar.register(bus);
    LootItemFunctionRegistration.register(bus);
    MenuRegistrar.register(bus);
    AlleleTypeRegistrar.init(bus);
    MutationTypeRegistrar.init(bus);
    ConditionTypeRegistrar.init(bus);
    PacketHandler.init(bus);
    bus.addListener(ApicuriousDataGen::generateClientData);
    bus.addListener(ApicuriousDataGen::generateServerData);
    NeoForge.EVENT_BUS.addListener(ApicuriousWorldGen::hackTheHives);
    NeoForge.EVENT_BUS.addListener(this::loadEmptySpecies);
    CreativeTabRegistrar.register(bus);
    if (FMLLoader.getCurrent().getDist() == Dist.CLIENT)
    {
      ApicuriousClientEvents.registerClientEvents(bus);
    }
  }

  public static Identifier createIdentifier(String path)
  {
    return Identifier.tryBuild(MODID, path);
  }

  private void loadEmptySpecies(final EntityJoinLevelEvent event)
  {
    if (BeeItem.EMPTY_SPECIES == null && event.getEntity() instanceof Player)
    {
      Level level = event.getLevel();
      if (level instanceof ServerLevel serverLevel)
      {
        serverLevel.registryAccess().get(ApicuriousRegistries.ALLELES).ifPresent(registry -> BeeItem.EMPTY_SPECIES = (BeeSpecies) registry.value().getOptional(ApicuriousSpecies.UNDEFINED.species().identifier()).orElseThrow());
      }
      else if (level instanceof ClientLevel)
      {
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection != null)
        {
          connection.registryAccess().get(ApicuriousRegistries.ALLELES).ifPresent(registry -> BeeItem.EMPTY_SPECIES = (BeeSpecies) registry.value().getOptional(ApicuriousSpecies.UNDEFINED.species()).orElseThrow());
        }
      }
    }
  }

  private void registerCapabilities(RegisterCapabilitiesEvent event)
  {
    event.registerItem(Capabilities.Item.ITEM, (stack, itemAccess) -> new ItemAccessItemHandler(itemAccess, DataComponents.CONTAINER, 2), ItemRegistrar.ANALYZER.item().get());
  }

  private void registerDMT(final RegisterDataMapTypesEvent event)
  {
    event.register(DataMapTypeRegistrar.ALLELE_DATA_MAP_TYPE);
    event.register(DataMapTypeRegistrar.MUTATION_DATA_MAP_TYPE);
    event.register(DataMapTypeRegistrar.CONDITIONS_DATA_MAP_TYPE);
    event.register(DataMapTypeRegistrar.FUNCTION_DATA_MAP_TYPE);
    event.register(DataMapTypeRegistrar.OUTPUT_TABLE_DATA_MAP_TYPE);
    event.register(DataMapTypeRegistrar.CENTRIFUGE_RECIPES_DATA_MAP_TYPE);
  }
}
