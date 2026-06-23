package sandybay.apicurious.data.defaults.branch;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.ChanceCondition;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.OutputTableDefaults;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;
import sandybay.apicurious.data.defaults.condition.ConditionKeys;

import static sandybay.apicurious.data.defaults.MutationDefaults.mutation;

/*
    Todo:
      - Lucky   (St. Patricks)
      - Native  (Thanksgiving)
      - Pagan   (Midsummer Solstice)
      - Zodiac  (Chinese New Years)
      - Haunted (Day of the Dead)
   */
public class FestiveBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.LEPORINE.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.LEPORINE.species(), "leporine").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.LEPORINE)).withProductionData(builder -> builder.withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER)).withEnvironmentalData(builder -> builder.withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.LEPORINE.output())).build());
    bootstrap.register(ApicuriousSpecies.MERRY.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.MERRY.species(), "merry").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.MERRY)).withProductionData(builder -> builder.withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.ICY).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.MERRY.output())).build());
    bootstrap.register(ApicuriousSpecies.TIPSY.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.TIPSY.species(), "tipsy").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.TIPSY)).withProductionData(builder -> builder.withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.ICY).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.TIPSY.output())).build());
    bootstrap.register(ApicuriousSpecies.CELEBRATORY.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.CELEBRATORY.species(), "celebratory").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.CELEBRATORY)).withProductionData(builder -> builder.withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.ICY).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.CELEBRATORY.output())).build());
    bootstrap.register(ApicuriousSpecies.TRICKY.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.TRICKY.species(), "tricky").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.TRICKY)).withProductionData(builder -> builder.withPollination(Pollination.SLOWEST).withSpeed(Speed.SLOWER).withWorkCycle(Workcycle.ALWAYS)).withEnvironmentalData(builder -> builder.withHumidityTolerance(HumidityTolerance.LOWEST_TOLERANCE).withTemperaturePreference(TemperaturePreference.ICY).withTemperatureTolerance(TemperatureTolerance.LOW_TOLERANCE)).withOutputData(builder -> builder.withTable(ApicuriousSpecies.TRICKY.output())).build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    /*
    bootstrap.register(ApicuriousSpecies.LEPORINE.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.FOREST)
                    .withSecond(ApicuriousSpecies.MEADOW)
                    .withChance(0.1f)
                    //.withCondition() Todo: Figure out how to do date conditions that respect date changes...
                    .withOutput(ApicuriousSpecies.LEPORINE)
                    .build()
    );
     */
    bootstrap.register(ApicuriousSpecies.MERRY.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.FOREST.species()).withSecond(ApicuriousSpecies.WINTRY.species()).withChance(0.1f).withCondition(ConditionKeys.IS_CHRISTMAS).withOutput(ApicuriousSpecies.MERRY.species()).build());
    bootstrap.register(ApicuriousSpecies.TIPSY.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.MEADOW.species()).withSecond(ApicuriousSpecies.WINTRY.species()).withChance(0.1f).withCondition(ConditionKeys.IS_NEW_YEARS).withOutput(ApicuriousSpecies.TIPSY.species()).build());
    bootstrap.register(ApicuriousSpecies.CELEBRATORY.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.AUSTERE.species()).withSecond(ApicuriousSpecies.EXCITED.species()).withChance(0.05f).withOutput(ApicuriousSpecies.CELEBRATORY.species()).build());
    bootstrap.register(ApicuriousSpecies.TRICKY.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.SINISTER.species()).withSecond(ApicuriousSpecies.COMMON.species()).withChance(0.1f).withCondition(ConditionKeys.IS_HALLOWEEN).withOutput(ApicuriousSpecies.TRICKY.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.LEPORINE.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.3f)).withResult(result -> result.withResult(ItemRegistrar.SILKY_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.1f)).withResult(result -> result.withResult(Items.EGG))).build());
    bootstrap.register(ApicuriousSpecies.MERRY.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.3f)).withResult(result -> result.withResult(ItemRegistrar.FROZEN_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(ItemRegistrar.ICE_SHARD.item().get()))).build());
    bootstrap.register(ApicuriousSpecies.TIPSY.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.3f)).withResult(result -> result.withResult(ItemRegistrar.FROZEN_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(ItemRegistrar.ICE_SHARD.item().get()))).build());
    bootstrap.register(ApicuriousSpecies.CELEBRATORY.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.3f)).withResult(result -> result.withResult(ItemRegistrar.PARCHED_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(Items.GUNPOWDER))).build());
    bootstrap.register(ApicuriousSpecies.TRICKY.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.4f)).withResult(result -> result.withResult(Items.HONEYCOMB))).withPool(pool -> pool.when(new ChanceCondition(0.15f)).withResult(result -> result.withResult(Items.COOKIE))).withPool(pool -> pool.withResult(result -> result.when(new ChanceCondition(0.02f))
            .withResult(Blocks.ZOMBIE_HEAD.asItem())
            .withResult(Blocks.CREEPER_HEAD.asItem())
            .withResult(Blocks.SKELETON_SKULL.asItem())))
            .build()
    );
  }
}
