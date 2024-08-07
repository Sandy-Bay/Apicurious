package sandybay.apicurious.common.bee.genetic;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import sandybay.apicurious.api.bee.genetic.AlleleType;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.bee.genetic.IGenome;
import sandybay.apicurious.api.register.AlleleTypeRegistration;
import sandybay.apicurious.api.util.GeneticHelper;
import sandybay.apicurious.common.bee.species.BeeSpecies;

import java.util.HashMap;
import java.util.Map;

public class Genome implements IGenome
{
  public static Codec<Genome> CODEC;

  private final Map<AlleleType<? extends IAllele<?>>, Genotype<? extends IAllele<?>>> genome;

  public Genome()
  {
    this.genome = new HashMap<>();
  }

  @Override
  public <T extends IAllele<T>> boolean setAllelePair(Genotype<T> genotype)
  {
    Genotype<?> prev = this.genome.put(genotype.getActive().getTraitKey(), genotype);
    return prev != null || prev != genotype;
  }

  @Override
  public <T extends IAllele<T>> Genotype<T> getGenotype(AlleleType<T> traitKey)
  {
    return (Genotype<T>) this.genome.get(traitKey);
  }


  @Override
  public IGenome combineGenomes(IGenome other, RandomSource random)
  {
    Genome childGenome = new Genome();
    for (AlleleType<?> key : this.genome.keySet())
      childGenome.genome.put(key, GeneticHelper.getGenotypeFromParents(getGenotype(key), other.getGenotype(key), random));
    return childGenome;
  }

  @Override
  public void getDefaultGenome(BeeSpecies species)
  {
    this.genome.put(AlleleTypeRegistration.SPECIES_TYPE.get(),                Genotype.defaultOf(Holder.direct(species)));
    this.genome.put(AlleleTypeRegistration.AREA_TYPE.get(),                   Genotype.defaultOf(species.getProductionData().getArea()));
    this.genome.put(AlleleTypeRegistration.FERTILITY_TYPE.get(),              Genotype.defaultOf(species.getProductionData().getFertility()));
    this.genome.put(AlleleTypeRegistration.FLOWERS_TYPE.get(),                Genotype.defaultOf(species.getEnvironmentalData().getFlowers()));
    this.genome.put(AlleleTypeRegistration.HUMIDITY_PREFERENCE_TYPE.get(),    Genotype.defaultOf(species.getEnvironmentalData().getHumidityData().preference()));
    this.genome.put(AlleleTypeRegistration.HUMIDITY_TOLERANCE_TYPE.get(),     Genotype.defaultOf(species.getEnvironmentalData().getHumidityData().tolerance()));
    this.genome.put(AlleleTypeRegistration.LIFESPAN_TYPE.get(),               Genotype.defaultOf(species.getProductionData().getLifespan()));
    this.genome.put(AlleleTypeRegistration.POLLINATION_TYPE.get(),            Genotype.defaultOf(species.getProductionData().getPollination()));
    this.genome.put(AlleleTypeRegistration.SPEED_TYPE.get(),                  Genotype.defaultOf(species.getProductionData().getSpeed()));
    this.genome.put(AlleleTypeRegistration.TEMPERATURE_PREFERENCE_TYPE.get(), Genotype.defaultOf(species.getEnvironmentalData().getTemperatureData().preference()));
    this.genome.put(AlleleTypeRegistration.TEMPERATURE_TOLERANCE_TYPE.get(),  Genotype.defaultOf(species.getEnvironmentalData().getTemperatureData().tolerance()));
    this.genome.put(AlleleTypeRegistration.WORKCYCLE_TYPE.get(),              Genotype.defaultOf(species.getProductionData().getWorkCycle()));
  }

  public record Genotype<T extends IAllele<T>>(IAllele<T> first, IAllele<T> second)
  {

    public IAllele<T> getActive()
    {
      if (first.isDominantTrait()) return first;
      if (second.isDominantTrait()) return second;
      return first;
    }

    public IAllele<T> getInactive()
    {
      IAllele<T> active = getActive();
      return active == first ? second : first;
    }

    public Component getRenderableName()
      {
        // Output Example:
        // Active Allele: Average, Inactive Allele: Average
        return Component.translatable("apicurious.genetics.active")
                .append(getActive().getReadableName())
                .append(Component.literal(", "))
                .append(Component.translatable("apicurious.genetics.inactive"))
                .append(getInactive().getReadableName());
      }

      public static <T extends IAllele<T>> Genotype<T> defaultOf(Holder<T> trait)
      {
        return new Genotype<>(trait.value(), trait.value());
      }

      public static <T extends IAllele<T>> Genotype<T> of(IAllele<T> active, IAllele<T> inactive)
      {
        return new Genotype<>(active, inactive);
      }
    }
}
