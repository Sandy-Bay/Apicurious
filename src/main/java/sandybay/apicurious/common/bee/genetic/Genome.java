package sandybay.apicurious.common.bee.genetic;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import sandybay.apicurious.api.bee.genetic.AlleleType;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.bee.genetic.IGenome;
import sandybay.apicurious.api.register.AlleleTypeRegistration;
import sandybay.apicurious.common.bee.species.BeeSpecies;

import java.util.HashMap;
import java.util.Map;

public class Genome implements IGenome
{
  public static Codec<Genome> CODEC;

  private final Map<AlleleType<?>, AllelePair<?>> genome;

  public Genome()
  {
    this.genome = new HashMap<>();
  }

  @Override
  public boolean setAllelePair(AllelePair<?> allelePair)
  {
    AllelePair<?> prev = this.genome.put(allelePair.active.getTraitKey(), allelePair);
    return prev != null || prev != allelePair;
  }

  @Override
  public AllelePair<?> getAllelePair(AlleleType<?> traitKey)
  {
    return this.genome.get(traitKey);
  }


  @Override
  public IGenome combineGenomes(IGenome other)
  {

    return null;
  }

  @Override
  public void getDefaultGenome(BeeSpecies species)
  {
    this.genome.put(species.getTraitKey(), AllelePair.of(species, species));
    this.genome.put(AlleleTypeRegistration.AREA_TYPE.get(), AllelePair.defaultOf(species.getProductionData().getArea()));
    this.genome.put(AlleleTypeRegistration.FERTILITY_TYPE.get(), AllelePair.defaultOf(species.getProductionData().getFertility()));
    this.genome.put(AlleleTypeRegistration.FLOWERS_TYPE.get(), AllelePair.defaultOf(species.getEnvironmentalData().getFlowers()));
    this.genome.put(AlleleTypeRegistration.HUMIDITY_PREFERENCE_TYPE.get(), AllelePair.defaultOf(species.getEnvironmentalData().getHumidityData().preference()));
    this.genome.put(AlleleTypeRegistration.HUMIDITY_TOLERANCE_TYPE.get(), AllelePair.defaultOf(species.getEnvironmentalData().getHumidityData().tolerance()));
    this.genome.put(AlleleTypeRegistration.LIFESPAN_TYPE.get(), AllelePair.defaultOf(species.getProductionData().getLifespan()));
    this.genome.put(AlleleTypeRegistration.POLLINATION_TYPE.get(), AllelePair.defaultOf(species.getProductionData().getPollination()));
    this.genome.put(AlleleTypeRegistration.SPEED_TYPE.get(), AllelePair.defaultOf(species.getProductionData().getSpeed()));
    this.genome.put(AlleleTypeRegistration.TEMPERATURE_PREFERENCE_TYPE.get(), AllelePair.defaultOf(species.getEnvironmentalData().getTemperatureData().preference()));
    this.genome.put(AlleleTypeRegistration.TEMPERATURE_TOLERANCE_TYPE.get(), AllelePair.defaultOf(species.getEnvironmentalData().getTemperatureData().tolerance()));
    this.genome.put(AlleleTypeRegistration.WORKCYCLE_TYPE.get(), AllelePair.defaultOf(species.getProductionData().getWorkCycle()));
  }

  public record AllelePair<T extends IAllele<T>>(IAllele<T> active, IAllele<T> inactive)
  {

      public Component getRenderableName()
      {
        // Output Example:
        // Active Allele: Average, Inactive Allele: Average
        return Component.translatable("apicurious.genetics.active")
                .append(this.active.getReadableName())
                .append(Component.literal(", "))
                .append(Component.translatable("apicurious.genetics.inactive"))
                .append(this.inactive.getReadableName());
      }

      public static <T extends IAllele<T>> AllelePair<T> defaultOf(Holder<T> trait)
      {
        return new AllelePair<>(trait.value(), trait.value());
      }

      public static <T extends IAllele<T>> AllelePair<T> of(IAllele<T> active, IAllele<T> inactive)
      {
        return new AllelePair<>(active, inactive);
      }
    }
}
