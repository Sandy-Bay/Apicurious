package sandybay.apicurious.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.loot.function.ApicuriousSpeciesFunction;

public class ApicuriousLootItemFunctions
{

  public static final DeferredRegister<LootItemFunctionType<?>> FUNCTION_TYPES = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, Apicurious.MODID);

  public static final DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<? extends LootItemConditionalFunction>> SPECIES_FUNCTION = FUNCTION_TYPES.register("species", () -> new LootItemFunctionType<>(ApicuriousSpeciesFunction.CODEC));

  public static void register(IEventBus bus)
  {
    FUNCTION_TYPES.register(bus);
  }
}
