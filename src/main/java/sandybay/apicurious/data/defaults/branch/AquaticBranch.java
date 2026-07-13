package sandybay.apicurious.data.defaults.branch;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
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

import static sandybay.apicurious.data.defaults.MutationDefaults.mutation;

public class AquaticBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.WATER.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.WATER.species(), "water").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.WATER)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORTER).withPollination(Pollination.SLOW).withSpeed(Speed.SLOWEST)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.LILY_PAD).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOW_TOLERANCE).ignoresRain()).withOutputData(builder -> builder.withTable(ApicuriousSpecies.WATER.output())).build());
    bootstrap.register(ApicuriousSpecies.RIVER.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.RIVER.species(), "river").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.RIVER)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORTER).withPollination(Pollination.SLOW).withSpeed(Speed.SLOWEST)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.LILY_PAD).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOW_TOLERANCE).ignoresRain()).withOutputData(builder -> builder.withTable(ApicuriousSpecies.RIVER.output())).build());
    bootstrap.register(ApicuriousSpecies.OCEAN.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.OCEAN.species(), "ocean").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.OCEAN)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORTER).withPollination(Pollination.SLOW).withSpeed(Speed.SLOWEST)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.LILY_PAD).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOW_TOLERANCE).ignoresRain()).withOutputData(builder -> builder.withTable(ApicuriousSpecies.OCEAN.output())).recessive().build());
    bootstrap.register(ApicuriousSpecies.STAINED.species(), SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.STAINED.species(), "stained").withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.STAINED)).withProductionData(builder -> builder.withLifespan(Lifespan.SHORTER).withPollination(Pollination.SLOW).withSpeed(Speed.SLOWEST)).withEnvironmentalData(builder -> builder.withFlowers(Flowers.LILY_PAD).withHumidityPreference(HumidityPreference.DAMP).withHumidityTolerance(HumidityTolerance.LOW_TOLERANCE).ignoresRain()).withOutputData(builder -> builder.withTable(ApicuriousSpecies.WATER.output())).recessive().build());
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.RIVER.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.WATER.species()).withSecond(ApicuriousSpecies.COMMON.species()).withChance(0.1f).withOutput(ApicuriousSpecies.RIVER.species()).build());
    bootstrap.register(ApicuriousSpecies.OCEAN.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.RIVER.species()).withSecond(ApicuriousSpecies.DILIGENT.species()).withChance(0.1f).withOutput(ApicuriousSpecies.OCEAN.species()).build());
    bootstrap.register(ApicuriousSpecies.STAINED.mutation(), mutation(bootstrap).withFirst(ApicuriousSpecies.EBONY.species()).withSecond(ApicuriousSpecies.OCEAN.species()).withChance(0.08f).withOutput(ApicuriousSpecies.STAINED.species()).build());
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.WATER.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.DAMP_COMB.comb(), 0.3f));
    bootstrap.register(ApicuriousSpecies.RIVER.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.3f)).withResult(result -> result.withResult(ItemRegistrar.DAMP_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.2f)).withResult(result -> result.withResult(ItemRegistrar.CLAY_COMB.comb().get()))).build());
    bootstrap.register(ApicuriousSpecies.OCEAN.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.UNSTABLE_COMB.comb(), 0.3f));
    bootstrap.register(ApicuriousSpecies.STAINED.output(), OutputTableDefaults.custom().withPool(pool -> pool.when(new ChanceCondition(0.3f)).withResult(result -> result.withResult(ItemRegistrar.DAMP_COMB.comb().get()))).withPool(pool -> pool.when(new ChanceCondition(0.1f)).withResult(result -> result.withResult(Items.INK_SAC))).build());
  }
}
