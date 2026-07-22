package sandybay.apicurious.common.registrar;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.loot.function.ApicuriousSpeciesFunction;

public class LootItemFunctionRegistration
{

  public static final DeferredRegister<MapCodec<? extends LootItemFunction>> FUNCTION_TYPES = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, Apicurious.MODID);

  public static final DeferredHolder<MapCodec<? extends LootItemFunction>, MapCodec<? extends LootItemFunction>> SPECIES_FUNCTION = FUNCTION_TYPES.register("species", () -> ApicuriousSpeciesFunction.CODEC);

  public static void register(IEventBus bus)
  {
    FUNCTION_TYPES.register(bus);
  }
}
