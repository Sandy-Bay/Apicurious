package sandybay.apicurious.data.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.datacomponent.ApicuriousDataComponents;

import java.util.List;

public class ApicuriousSpeciesFunction implements LootItemFunction {

  private final ResourceKey<BeeSpecies> speciesKey;

  public ApicuriousSpeciesFunction(ResourceKey<BeeSpecies> speciesKey) {
    this.speciesKey = speciesKey;
  }

  @Override
  public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
    return null;
  }

  @Override
  public ItemStack apply(ItemStack stack, LootContext lootContext) {
    ClientPacketListener connection = Minecraft.getInstance().getConnection();
    if (connection != null) {
      connection.registryAccess().registry(ApicuriousRegistries.BEE_SPECIES).ifPresent(registry -> {
        BeeSpecies species = registry.get(speciesKey);
        stack.set(ApicuriousDataComponents.BEE_SPECIES, species);
      });
    }
    return stack;
  }
}
