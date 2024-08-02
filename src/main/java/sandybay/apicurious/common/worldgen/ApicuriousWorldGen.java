package sandybay.apicurious.common.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import sandybay.apicurious.common.block.HiveBlock;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApicuriousWorldGen
{

  //TODO: Figure out Worldgen because this approach didnt work :feelsbadman:
  public static void hackTheHives(final ServerAboutToStartEvent event)
  {
    MinecraftServer serverRef = event.getServer();
    serverRef.getAllLevels().forEach(server ->
    {
      Registry<ConfiguredFeature<?, ?>> registry = server.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
      registry.keySet().forEach(id ->
      {
        ConfiguredFeature<?, ?> configuredFeature = registry.get(id);
        if (configuredFeature == null || !(configuredFeature.feature() instanceof TreeFeature treeFeature)) return;
        TreeConfiguration configuration = (TreeConfiguration) configuredFeature.config();
        switch (id.getPath())
        {
          case "fancy_oak_bees", "super_birch_bees" ->
                  configuration.decorators.add(new HiveDecorator(server, BiomeTags.IS_FOREST, ApicuriousBlockRegistration.FOREST_HIVE.asBlock(), 1f));
          case "cherry_bees_005", "fancy_oak_bees_005", "birch_bees_005", "oak_bees_005" ->
                  configuration.decorators.add(new HiveDecorator(server, BiomeTags.IS_FOREST, ApicuriousBlockRegistration.FOREST_HIVE.asBlock(), 0.05f));
          case "fancy_oak_bees_002", "birch_bees_002", "oak_bees_002" ->
                  configuration.decorators.add(new HiveDecorator(server, BiomeTags.IS_FOREST, ApicuriousBlockRegistration.FOREST_HIVE.asBlock(), 0.02f));
          case "fancy_oak_bees_0002", "birch_bees_0002", "oak_bees_0002", "super_birch_bees_0002" ->
                  configuration.decorators.add(new HiveDecorator(server, BiomeTags.IS_FOREST, ApicuriousBlockRegistration.FOREST_HIVE.asBlock(), 0.002f));
          case "mangrove", "tall_mangrove", "swamp_oak" ->
                  configuration.decorators.add(new HiveDecorator(server, BiomeTags.IS_FOREST, ApicuriousBlockRegistration.MARSHY_HIVE.asBlock(), 0.01f)); // Replace with appropriate biome tag.
        }
      });
    });
  }

  public static class HiveDecorator extends TreeDecorator
  {

    private static final Direction WORLDGEN_FACING = Direction.SOUTH;
    private static final Direction[] SPAWN_DIRECTIONS = Direction.Plane.HORIZONTAL
            .stream()
            .filter(p_202307_ -> p_202307_ != WORLDGEN_FACING.getOpposite())
            .toArray(Direction[]::new);
    private final ServerLevel level;
    private final TagKey<Biome> biomeTagKey;
    private final HiveBlock hive;
    private final float probability;

    public HiveDecorator(ServerLevel level, TagKey<Biome> biomeTagKey, HiveBlock hive, float probability)
    {
      this.level = level;
      this.biomeTagKey = biomeTagKey;
      this.hive = hive;
      this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> type()
    {
      return null;
    }

    @Override
    public void place(Context context)
    {
      if (!level.getBiome(context.logs().get(0)).is(biomeTagKey)) return;
      RandomSource randomsource = context.random();
      if (!(randomsource.nextFloat() >= this.probability))
      {
        List<BlockPos> leaves = context.leaves();
        List<BlockPos> logs = context.logs();
        int targetY = !leaves.isEmpty()
                ? Math.max(leaves.get(0).getY() - 1, logs.get(0).getY() + 1)
                : Math.min(logs.get(0).getY() + 1 + randomsource.nextInt(3), logs.get(logs.size() - 1).getY());
        List<BlockPos> validPositions = logs.stream()
                .filter(pos -> pos.getY() == targetY)
                .flatMap(pos -> Stream.of(SPAWN_DIRECTIONS).map(pos::relative))
                .collect(Collectors.toList());
        if (!validPositions.isEmpty())
        {
          Collections.shuffle(validPositions);
          Optional<BlockPos> randomlyChosenPosition = validPositions.stream()
                  .filter(pos -> context.isAir(pos) && context.isAir(pos.relative(WORLDGEN_FACING)))
                  .findFirst();
          randomlyChosenPosition.ifPresent(pos -> context.setBlock(pos, hive.defaultBlockState().setValue(BeehiveBlock.FACING, WORLDGEN_FACING)));
        }
      }
    }
  }
}
