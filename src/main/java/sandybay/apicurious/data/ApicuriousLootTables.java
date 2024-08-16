package sandybay.apicurious.data;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.loot.function.ApicuriousSpeciesFunction;
import sandybay.apicurious.common.registrar.BlockRegistrar;
import sandybay.apicurious.common.registrar.ItemRegistrar;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class ApicuriousLootTables extends LootTableProvider
{

  public ApicuriousLootTables(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
  {
    super(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(ApicuriousBlockLoot::new, LootContextParamSets.BLOCK)), registries);
  }

  public static class ApicuriousBlockLoot extends BlockLootSubProvider
  {

    private static final Set<Item> EXPLOSION_RESISTANT = Collections.emptySet();
    private final HolderLookup.Provider provider;

    protected ApicuriousBlockLoot(HolderLookup.Provider provider)
    {
      super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags(), provider);
      this.provider = provider;

    }

    public static LootTable.Builder hiveTable(ResourceKey<IAllele<?>> speciesKey)
    {
      return LootTable.lootTable()
              .setParamSet(LootContextParamSet.builder().required(LootContextParams.TOOL).build())
              .withPool(LootPool.lootPool()
                      .setRolls(ConstantValue.exactly(1.0f))
                      .add(LootItem
                              .lootTableItem(ItemRegistrar.PRINCESS.get())
                              .apply(ApicuriousSpeciesFunction.getBuilder(speciesKey))
                              .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ApicuriousTags.ItemTags.IS_SIEVE_TOOL)))
                      )
              ).withPool(LootPool.lootPool()
                      .setRolls(ConstantValue.exactly(1.0f))
                      .add(LootItem
                              .lootTableItem(ItemRegistrar.DRONE.get())
                              .apply(ApicuriousSpeciesFunction.getBuilder(speciesKey))
                              .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ApicuriousTags.ItemTags.IS_SIEVE_TOOL)))
                      )
              )
              .withPool(LootPool.lootPool()
                      .setRolls(ConstantValue.exactly(1.0f))
                      .add(LootItem
                              .lootTableItem(ItemRegistrar.DRONE.get())
                              .apply(ApicuriousSpeciesFunction.getBuilder(speciesKey))
                              .when(LootItemRandomChanceCondition.randomChance(0.5f))
                              .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ApicuriousTags.ItemTags.IS_SIEVE_TOOL)))
                      )
              )
              .withPool(LootPool.lootPool()
                      .setRolls(ConstantValue.exactly(1.0f))
                      .add(LootItem
                              .lootTableItem(ItemRegistrar.DRONE.get())
                              .apply(ApicuriousSpeciesFunction.getBuilder(ApicuriousSpecies.FOREST.species()))
                              .when(LootItemRandomChanceCondition.randomChance(0.333f))
                              .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ApicuriousTags.ItemTags.IS_SIEVE_TOOL)))
                      )
              )
              .withPool(LootPool.lootPool()
                      .setRolls(ConstantValue.exactly(1.0f))
                      .add(LootItem
                              .lootTableItem(ItemRegistrar.DRONE.get())
                              .apply(ApicuriousSpeciesFunction.getBuilder(ApicuriousSpecies.VALIANT.species()))
                              .when(LootItemRandomChanceCondition.randomChance(0.05f))
                              .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ApicuriousTags.ItemTags.IS_SIEVE_TOOL)))
                      )
              );
    }

    @Override
    protected void generate()
    {
      this.add(BlockRegistrar.FOREST_HIVE.asBlock(), block -> hiveTable(ApicuriousSpecies.FOREST.species()));
      this.add(BlockRegistrar.MEADOW_HIVE.asBlock(), block -> hiveTable(ApicuriousSpecies.MEADOW.species()));
      this.add(BlockRegistrar.MODEST_HIVE.asBlock(), block -> hiveTable(ApicuriousSpecies.MODEST.species()));
      this.add(BlockRegistrar.TROPICAL_HIVE.asBlock(), block -> hiveTable(ApicuriousSpecies.TROPICAL.species()));
      this.add(BlockRegistrar.WINTRY_HIVE.asBlock(), block -> hiveTable(ApicuriousSpecies.WINTRY.species()));
      this.add(BlockRegistrar.MARSHY_HIVE.asBlock(), block -> hiveTable(ApicuriousSpecies.MARSHY.species()));
      this.add(BlockRegistrar.ROCKY_HIVE.asBlock(), block -> hiveTable(ApicuriousSpecies.ROCKY.species()));
      this.add(BlockRegistrar.NETHER_HIVE.asBlock(), block -> hiveTable(ApicuriousSpecies.NETHER.species()));
      this.add(BlockRegistrar.ENDER_HIVE.asBlock(), block -> hiveTable(ApicuriousSpecies.ENDER.species()));
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output)
    {
      this.generate();
      for (Map.Entry<ResourceKey<LootTable>, LootTable.Builder> entry : this.map.entrySet())
      {
        output.accept(entry.getKey(), entry.getValue());
      }
    }

    @Override
    protected Iterable<Block> getKnownBlocks()
    {
      return BlockRegistrar.BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toList());
    }
  }

}
