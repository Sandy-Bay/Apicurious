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
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.util.ApicuriousTags;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.loot.function.ApicuriousSpeciesFunction;
import sandybay.apicurious.common.register.BlockRegistration;
import sandybay.apicurious.common.register.ItemRegistration;

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

    public static LootTable.Builder beeTable(ResourceKey<IAllele<?>> speciesKey)
    {
      return LootTable.lootTable()
              .setParamSet(LootContextParamSet.builder().required(LootContextParams.TOOL).build())
              .withPool(LootPool.lootPool()
                      .setRolls(ConstantValue.exactly(1.0f))
                      .add(LootItem
                              .lootTableItem(ItemRegistration.PRINCESS.get())
                              .apply(ApicuriousSpeciesFunction.getBuilder(speciesKey))
                              .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ApicuriousTags.ItemTags.IS_SIEVE_TOOL)))
                      )
              ).withPool(LootPool.lootPool()
                      .setRolls(ConstantValue.exactly(1.0f))
                      .add(LootItem
                              .lootTableItem(ItemRegistration.DRONE.get())
                              .apply(ApicuriousSpeciesFunction.getBuilder(speciesKey))
                              .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ApicuriousTags.ItemTags.IS_SIEVE_TOOL)))
                      )
              )
              .withPool(LootPool.lootPool()
                      .setRolls(ConstantValue.exactly(1.0f))
                      .add(LootItem
                              .lootTableItem(ItemRegistration.DRONE.get())
                              .apply(ApicuriousSpeciesFunction.getBuilder(speciesKey))
                              .when(LootItemRandomChanceCondition.randomChance(0.5f))
                              .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ApicuriousTags.ItemTags.IS_SIEVE_TOOL)))
                      )
              )
              .withPool(LootPool.lootPool()
                      .setRolls(ConstantValue.exactly(1.0f))
                      .add(LootItem
                              .lootTableItem(ItemRegistration.DRONE.get())
                              .apply(ApicuriousSpeciesFunction.getBuilder(ApicuriousSpecies.FOREST))
                              .when(LootItemRandomChanceCondition.randomChance(0.333f))
                              .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ApicuriousTags.ItemTags.IS_SIEVE_TOOL)))
                      )
              );
    }

    @Override
    protected void generate()
    {
      this.add(BlockRegistration.FOREST_HIVE.asBlock(), block -> beeTable(ApicuriousSpecies.FOREST));
      this.add(BlockRegistration.MEADOW_HIVE.asBlock(), block -> beeTable(ApicuriousSpecies.MEADOW));
      this.add(BlockRegistration.MODEST_HIVE.asBlock(), block -> beeTable(ApicuriousSpecies.MODEST));
      this.add(BlockRegistration.TROPICAL_HIVE.asBlock(), block -> beeTable(ApicuriousSpecies.TROPICAL));
      this.add(BlockRegistration.WINTRY_HIVE.asBlock(), block -> beeTable(ApicuriousSpecies.WINTRY));
      this.add(BlockRegistration.MARSHY_HIVE.asBlock(), block -> beeTable(ApicuriousSpecies.MARSHY));
      this.add(BlockRegistration.ROCKY_HIVE.asBlock(), block -> beeTable(ApicuriousSpecies.ROCKY));
      this.add(BlockRegistration.NETHER_HIVE.asBlock(), block -> beeTable(ApicuriousSpecies.NETHER));
      this.add(BlockRegistration.ENDER_HIVE.asBlock(), block -> beeTable(ApicuriousSpecies.ENDER));
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
      return BlockRegistration.BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toList());
    }
  }

}
