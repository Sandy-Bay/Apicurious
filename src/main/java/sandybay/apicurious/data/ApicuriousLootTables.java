package sandybay.apicurious.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.item.BaseBeeItem;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.block.ApicuriousBlockRegistration;
import sandybay.apicurious.common.item.ApicuriousItemRegistration;
import sandybay.apicurious.common.item.PrincessBeeItem;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ApicuriousLootTables extends LootTableProvider {

  public ApicuriousLootTables(PackOutput output, Set<ResourceKey<LootTable>> requiredTables, List<SubProviderEntry> subProviders, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, Set.of(), List.of(
            new LootTableProvider.SubProviderEntry(ApicuriousBlockLoot::new, LootContextParamSets.BLOCK)
  }

  public static class ApicuriousBlockLoot extends BlockLootSubProvider {

    private static final Set<Item> EXPLOSION_RESISTANT = Collections.emptySet();

    protected ApicuriousBlockLoot(HolderLookup.Provider provider) {
      super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
      this.add(ApicuriousBlockRegistration.FOREST_HIVE.get(), block -> LootTable.lootTable()
              .setParamSet(LootContextParamSet.builder().required(LootContextParams.TOOL).build())
              .withPool(LootPool.lootPool()
                      .setRolls(ConstantValue.exactly(1.0f))
                      .add(LootItem
                              .lootTableItem(ApicuriousItemRegistration.PRINCESS.get())
                              .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY))
                      )
              ).withPool(LootPool.lootPool()
                      .setRolls(ConstantValue.exactly(2.0f))
              )
      );

    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
      this.generate();
      for (Map.Entry<ResourceKey<LootTable>, LootTable.Builder> entry : this.map.entrySet()) {
        output.accept(entry.getKey(), entry.getValue());
      }
    }
  }

}
