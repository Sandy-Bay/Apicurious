package sandybay.apicurious.common.bee.genetic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import sandybay.apicurious.api.bee.genetic.AlleleType;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.bee.genetic.IGenome;
import sandybay.apicurious.api.register.AlleleTypeRegistration;
import sandybay.apicurious.api.util.GeneticHelper;
import sandybay.apicurious.common.bee.genetic.allele.*;
import sandybay.apicurious.common.bee.species.BeeSpecies;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Genome implements IGenome
{
  public static Codec<Genome> CODEC = RecordCodecBuilder.create(instance ->
          instance.group(
                  Codec.unboundedMap(AlleleType.CODEC, Genotype.CODEC).fieldOf("genome").forGetter(Genome::getGenome)
          ).apply(instance, Genome::new)
  );

  public static StreamCodec<RegistryFriendlyByteBuf, Genome> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.map(HashMap::newHashMap, AlleleType.NETWORK_CODEC, Genotype.NETWORK_CODEC), Genome::getGenome,
          Genome::new
  );

  private final Map<AlleleType<? extends IAllele<?>>, Genotype> genome;

  public Genome()
  {
    this.genome = new HashMap<>();
  }

  public Genome(Map<AlleleType<? extends IAllele<?>>, Genotype> genome)
  {
    this.genome = genome;
  }

  private Map<AlleleType<? extends IAllele<?>>, Genotype> getGenome()
  {
    return genome;
  }

  @Override
  public <T extends IAllele<T>> boolean setAllelePair(Genotype genotype)
  {
    Genotype prev = this.genome.put(genotype.getActive().getTraitKey(), genotype);
    return prev != null || prev != genotype;
  }

  @Override
  public <T extends IAllele<T>> Genotype getGenotype(AlleleType<T> traitKey)
  {
    return this.genome.get(traitKey);
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
    this.genome.put(AlleleTypeRegistration.SPECIES_TYPE.get(), Genotype.defaultOf(species));
    this.genome.put(AlleleTypeRegistration.AREA_TYPE.get(), Genotype.defaultOf(species.getProductionData().getArea()));
    this.genome.put(AlleleTypeRegistration.FERTILITY_TYPE.get(), Genotype.defaultOf(species.getProductionData().getFertility()));
    this.genome.put(AlleleTypeRegistration.FLOWERS_TYPE.get(), Genotype.defaultOf(species.getEnvironmentalData().getFlowers()));
    this.genome.put(AlleleTypeRegistration.HUMIDITY_PREFERENCE_TYPE.get(), Genotype.defaultOf(species.getEnvironmentalData().getHumidityData().getPreference()));
    this.genome.put(AlleleTypeRegistration.HUMIDITY_TOLERANCE_TYPE.get(), Genotype.defaultOf(species.getEnvironmentalData().getHumidityData().getTolerance()));
    this.genome.put(AlleleTypeRegistration.LIFESPAN_TYPE.get(), Genotype.defaultOf(species.getProductionData().getLifespan()));
    this.genome.put(AlleleTypeRegistration.POLLINATION_TYPE.get(), Genotype.defaultOf(species.getProductionData().getPollination()));
    this.genome.put(AlleleTypeRegistration.SPEED_TYPE.get(), Genotype.defaultOf(species.getProductionData().getSpeed()));
    this.genome.put(AlleleTypeRegistration.TEMPERATURE_PREFERENCE_TYPE.get(), Genotype.defaultOf(species.getEnvironmentalData().getTemperatureData().getPreference()));
    this.genome.put(AlleleTypeRegistration.TEMPERATURE_TOLERANCE_TYPE.get(), Genotype.defaultOf(species.getEnvironmentalData().getTemperatureData().getTolerance()));
    this.genome.put(AlleleTypeRegistration.WORKCYCLE_TYPE.get(), Genotype.defaultOf(species.getProductionData().getWorkcycle()));
  }

  public <T extends IAllele<T>> BeeSpecies getSpecies(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistration.SPECIES_TYPE.get());
    return active ? (BeeSpecies) genotype.getActive() : (BeeSpecies) genotype.getInactive();
  }

  public <T extends IAllele<T>> Area getArea(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistration.AREA_TYPE.get());
    return active ? (Area) genotype.getActive() : (Area) genotype.getInactive();
  }

  public <T extends IAllele<T>> Fertility getFertility(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistration.FERTILITY_TYPE.get());
    return active ? (Fertility) genotype.getActive() : (Fertility) genotype.getInactive();
  }

  public <T extends IAllele<T>> Flowers getFlowers(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistration.FLOWERS_TYPE.get());
    return active ? (Flowers) genotype.getActive() : (Flowers) genotype.getInactive();
  }

  public <T extends IAllele<T>> HumidityPreference getHumidityPreference(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistration.HUMIDITY_PREFERENCE_TYPE.get());
    return active ? (HumidityPreference) genotype.getActive() : (HumidityPreference) genotype.getInactive();
  }

  public <T extends IAllele<T>> HumidityTolerance getHumidityTolerance(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistration.HUMIDITY_TOLERANCE_TYPE.get());
    return active ? (HumidityTolerance) genotype.getActive() : (HumidityTolerance) genotype.getInactive();
  }

  public <T extends IAllele<T>> Lifespan getLifespan(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistration.LIFESPAN_TYPE.get());
    return active ? (Lifespan) genotype.getActive() : (Lifespan) genotype.getInactive();
  }

  public <T extends IAllele<T>> Pollination getPollination(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistration.POLLINATION_TYPE.get());
    return active ? (Pollination) genotype.getActive() : (Pollination) genotype.getInactive();
  }

  public <T extends IAllele<T>> Speed getSpeed(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistration.SPEED_TYPE.get());
    return active ? (Speed) genotype.getActive() : (Speed) genotype.getInactive();
  }

  public <T extends IAllele<T>> TemperaturePreference getTemperaturePreference(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistration.TEMPERATURE_PREFERENCE_TYPE.get());
    return active ? (TemperaturePreference) genotype.getActive() : (TemperaturePreference) genotype.getInactive();
  }

  public <T extends IAllele<T>> TemperatureTolerance getTemperatureTolerance(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistration.TEMPERATURE_TOLERANCE_TYPE.get());
    return active ? (TemperatureTolerance) genotype.getActive() : (TemperatureTolerance) genotype.getInactive();
  }

  public <T extends IAllele<T>> Workcycle getWorkcycle(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistration.WORKCYCLE_TYPE.get());
    return active ? (Workcycle) genotype.getActive() : (Workcycle) genotype.getInactive();
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Genome genome1 = (Genome) o;
    return Objects.equals(genome, genome1.genome);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(genome);
  }

  public record Genotype(IAllele<?> first, IAllele<?> second)
  {
    public static Codec<Genotype> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    IAllele.TYPED_CODEC.fieldOf("first").forGetter(Genotype::first),
                    IAllele.TYPED_CODEC.fieldOf("second").forGetter(Genotype::second)
            ).apply(instance, Genotype::new)
    );

    public static StreamCodec<RegistryFriendlyByteBuf, Genotype> NETWORK_CODEC = StreamCodec.composite(
            IAllele.NETWORK_TYPED_CODEC, Genotype::first,
            IAllele.NETWORK_TYPED_CODEC, Genotype::second,
            Genotype::new
    );

    public static <T extends IAllele<T>> Genotype defaultOf(IAllele<?> trait)
    {
      return new Genotype(trait, trait);
    }

    public static <T extends IAllele<T>> Genotype of(IAllele<?> active, IAllele<?> inactive)
    {
      return new Genotype(active, inactive);
    }

    public IAllele<?> getActive()
    {
      if (first.isDominantTrait()) return first;
      if (second.isDominantTrait()) return second;
      return first;
    }

    public IAllele<?> getInactive()
    {
      IAllele<?> active = getActive();
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

    @Override
    public boolean equals(Object o)
    {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Genotype genotype = (Genotype) o;
      return Objects.equals(first, genotype.first) && Objects.equals(second, genotype.second);
    }

    @Override
    public int hashCode()
    {
      return Objects.hash(first, second);
    }
  }
}
