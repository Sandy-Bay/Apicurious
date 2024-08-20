package sandybay.apicurious.data.defaults.branch;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.neoforged.neoforge.common.Tags;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.condition.BiomeCondition;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.common.registrar.ItemRegistrar;
import sandybay.apicurious.data.defaults.allele.SpeciesDefaults;
import sandybay.apicurious.data.defaults.tables.OutputTableDefaults;

import static sandybay.apicurious.data.defaults.mutation.MutationDefaults.mutation;

public class AgrarianBranch
{
  public static void speciesDefaults(BootstrapContext<IAllele<?>> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.RURAL.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.RURAL.species(), "rural")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.RURAL))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.FASTER).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.WHEAT)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.RURAL.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.FARMERLY.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.FARMERLY.species(), "farmed")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.FARMED))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.FASTER).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.WHEAT)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.FARMERLY.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.AGRARIAN.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.AGRARIAN.species(), "agrarian")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.AGRARIAN).hasEffect())
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.LARGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.FASTER).withProductionSpeed(Speed.SLOW)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.WHEAT)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.AGRARIAN.output()))
                    .build()
    );
    // Growing Line
    bootstrap.register(ApicuriousSpecies.RURAL.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.RURAL.species(), "rural")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.RURAL))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.FASTER).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.WHEAT)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.RURAL.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.FARMERLY.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.FARMERLY.species(), "farmed")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.FARMED))
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.AVERAGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.FASTER).withProductionSpeed(Speed.SLOWER)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.WHEAT)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.FARMERLY.output()))
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.AGRARIAN.species(),
            SpeciesDefaults.getSpeciesBuilder(bootstrap, ApicuriousSpecies.AGRARIAN.species(), "agrarian")
                    .withVisualData(builder -> builder.withBeeColor(ApicuriousConstants.AGRARIAN).hasEffect())
                    .withProductionData(builder ->
                    {
                      builder.withArea(Area.LARGE).withFertility(Fertility.AVERAGE_FERTILITY).withLifespan(Lifespan.SHORTER)
                              .withPollinationRate(Pollination.FASTER).withProductionSpeed(Speed.SLOW)
                              .withWorkCycle(Workcycle.DIURNAL);
                    })
                    .withEnvironmentalData(builder ->
                    {
                      builder.withFlowers(Flowers.WHEAT)
                              .withHumidityPreference(HumidityPreference.AVERAGE).withHumidityTolerance(HumidityTolerance.NO_TOLERANCE)
                              .withTemperaturePreference(TemperaturePreference.AVERAGE).withTemperatureTolerance(TemperatureTolerance.NO_TOLERANCE);
                    })
                    .withOutputData(builder -> builder.withTable(ApicuriousSpecies.AGRARIAN.output()))
                    .build()
    );
  }

  public static void mutationsDefaults(BootstrapContext<IMutation> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.RURAL.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.MEADOW.species())
                    .withSecond(ApicuriousSpecies.DILIGENT.species())
                    .withChance(0.12f)
                    .withCondition(
                            new BiomeCondition(bootstrap.lookup(Registries.BIOME).getOrThrow(Tags.Biomes.IS_PLAINS))
                    )
                    .withOutput(ApicuriousSpecies.RURAL.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.FARMERLY.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.RURAL.species())
                    .withSecond(ApicuriousSpecies.UNWEARY.species())
                    .withChance(0.1f)
                    .withCondition(
                            new BiomeCondition(bootstrap.lookup(Registries.BIOME).getOrThrow(Tags.Biomes.IS_PLAINS))
                    )
                    .withOutput(ApicuriousSpecies.FARMERLY.species())
                    .build()
    );
    bootstrap.register(ApicuriousSpecies.AGRARIAN.mutation(),
            mutation(bootstrap)
                    .withFirst(ApicuriousSpecies.FARMERLY.species())
                    .withSecond(ApicuriousSpecies.INDUSTRIOUS.species())
                    .withChance(0.06f)
                    .withCondition(
                            new BiomeCondition(bootstrap.lookup(Registries.BIOME).getOrThrow(Tags.Biomes.IS_PLAINS))
                    )
                    .withOutput(ApicuriousSpecies.AGRARIAN.species())
                    .build()
    );
  }

  public static void outputsDefaults(BootstrapContext<OutputTable> bootstrap)
  {
    bootstrap.register(ApicuriousSpecies.RURAL.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.WHEATEN_COMB, 0.2f));
    bootstrap.register(ApicuriousSpecies.FARMERLY.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.WHEATEN_COMB, 0.27f));
    bootstrap.register(ApicuriousSpecies.AGRARIAN.output(), OutputTableDefaults.simpleCombTable(ItemRegistrar.WHEATEN_COMB, 0.35f));
  }

}
