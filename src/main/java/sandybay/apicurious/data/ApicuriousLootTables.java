package sandybay.apicurious.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.block.ApicuriousBlockRegistration;
import sandybay.apicurious.common.item.ApicuriousItemRegistration;
import sandybay.apicurious.data.loot.ApicuriousSpeciesFunction;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ApicuriousLootTables extends LootTableProvider {

  public ApicuriousLootTables(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(ApicuriousBlockLoot::new, LootContextParamSets.BLOCK)), registries);
  }

  public static class ApicuriousBlockLoot extends BlockLootSubProvider {

    private static final Set<Item> EXPLOSION_RESISTANT = Collections.emptySet();
    private final HolderLookup.Provider provider;

    protected ApicuriousBlockLoot(HolderLookup.Provider provider) {
      super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags(), provider);
      this.provider = provider;

    }

    @Override
    protected void generate() {
      provider.asGetterLookup().get(Registries.BLOCK, ApicuriousBlockRegistration.FOREST_HIVE.getKey());
      this.add(ApicuriousBlockRegistration.FOREST_HIVE.get(), block -> beeTable(ApicuriousSpecies.FOREST));
      this.add(ApicuriousBlockRegistration.MEADOW_HIVE.get(), block -> beeTable(ApicuriousSpecies.MEADOW));
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
      this.generate();
      for (Map.Entry<ResourceKey<LootTable>, LootTable.Builder> entry : this.map.entrySet()) {
        output.accept(entry.getKey(), entry.getValue());
      }
    }

    public static LootTable.Builder beeTable(ResourceKey<BeeSpecies> speciesKey) {
      return LootTable.lootTable()
              .setParamSet(LootContextParamSet.builder().required(LootContextParams.TOOL).build())
              .withPool(LootPool.lootPool()
                      .setRolls(ConstantValue.exactly(1.0f))
                      .add(LootItem
                              .lootTableItem(ApicuriousItemRegistration.PRINCESS.get())
                              .apply(ApicuriousSpeciesFunction.getBuilder(ApicuriousSpecies.FOREST))
                      )
              ).withPool(LootPool.lootPool()
                      .setRolls(ConstantValue.exactly(2.0f))
                      .add(LootItem
                              .lootTableItem(ApicuriousItemRegistration.DRONE.get())
                              .apply(ApicuriousSpeciesFunction.getBuilder(ApicuriousSpecies.FOREST))
                      )
              );
    }
  }

}
