package sandybay.apicurious.common.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;

public class ApicuriousBlockRegistration {

  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Apicurious.MODID);

  public static final BlockBehaviour.Properties HIVE_PROPS = BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE);

  public static final DeferredHolder<Block, HiveBlock> FOREST_HIVE = BLOCKS.register("forest_hive", () -> new HiveBlock(HIVE_PROPS));
  public static final DeferredHolder<Block, HiveBlock> MEADOW_HIVE = BLOCKS.register("meadow_hive", () -> new HiveBlock(HIVE_PROPS));
  // TODO: Add Modest, Tropical, Ender, Wintry & Marshy Hives

  public static void register(IEventBus bus) {
    BLOCKS.register(bus);
  }
}
