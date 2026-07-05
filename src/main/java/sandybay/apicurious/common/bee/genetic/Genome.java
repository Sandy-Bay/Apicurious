package sandybay.apicurious.common.bee.genetic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import sandybay.apicurious.api.bee.genetic.Genotype;
import sandybay.apicurious.api.bee.genetic.IGenome;
import sandybay.apicurious.api.bee.genetic.allele.AlleleType;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.AlleleTypeRegistrar;
import sandybay.apicurious.api.util.GeneticHelper;
import sandybay.apicurious.common.bee.species.BeeSpecies;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Genome implements IGenome
{
  public static Codec<Genome> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.unboundedMap(AlleleType.CODEC, Genotype.CODEC).fieldOf("genome").forGetter(Genome::getGenome)).apply(instance, Genome::new));

  public static StreamCodec<RegistryFriendlyByteBuf, Genome> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.map(HashMap::newHashMap, AlleleType.NETWORK_CODEC, Genotype.NETWORK_CODEC), Genome::getGenome, Genome::new);

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

  public boolean setAllelePair(Genotype genotype)
  {
    Genotype prev = this.genome.put(genotype.getActive().value().getTraitKey(), genotype);
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
  public void getDefaultGenome(Holder<IAllele<?>> species)
  {
    BeeSpecies raw = (BeeSpecies) species.value();
    this.genome.put(AlleleTypeRegistrar.SPECIES_TYPE.get(), Genotype.defaultOf(species));
    this.genome.put(AlleleTypeRegistrar.AREA_TYPE.get(), Genotype.defaultOf(raw.getProductionData().getAreaHolder()));
    this.genome.put(AlleleTypeRegistrar.FERTILITY_TYPE.get(), Genotype.defaultOf(raw.getProductionData().getFertilityHolder()));
    this.genome.put(AlleleTypeRegistrar.FLOWERS_TYPE.get(), Genotype.defaultOf(raw.getEnvironmentalData().getFlowersHolder()));
    this.genome.put(AlleleTypeRegistrar.HUMIDITY_PREFERENCE_TYPE.get(), Genotype.defaultOf(raw.getEnvironmentalData().getHumidityData().preferenceHolder()));
    this.genome.put(AlleleTypeRegistrar.HUMIDITY_TOLERANCE_TYPE.get(), Genotype.defaultOf(raw.getEnvironmentalData().getHumidityData().toleranceHolder()));
    this.genome.put(AlleleTypeRegistrar.LIFESPAN_TYPE.get(), Genotype.defaultOf(raw.getProductionData().getLifespanHolder()));
    this.genome.put(AlleleTypeRegistrar.POLLINATION_TYPE.get(), Genotype.defaultOf(raw.getProductionData().getPollinationHolder()));
    this.genome.put(AlleleTypeRegistrar.SPEED_TYPE.get(), Genotype.defaultOf(raw.getProductionData().getSpeedHolder()));
    this.genome.put(AlleleTypeRegistrar.TEMPERATURE_PREFERENCE_TYPE.get(), Genotype.defaultOf(raw.getEnvironmentalData().getTemperatureData().preferenceHolder()));
    this.genome.put(AlleleTypeRegistrar.TEMPERATURE_TOLERANCE_TYPE.get(), Genotype.defaultOf(raw.getEnvironmentalData().getTemperatureData().toleranceHolder()));
    this.genome.put(AlleleTypeRegistrar.WORKCYCLE_TYPE.get(), Genotype.defaultOf(raw.getProductionData().getWorkcycleHolder()));
  }

  public Holder<IAllele<?>> getSpecies(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistrar.SPECIES_TYPE.get());
    return active ? genotype.getActive() : genotype.getInactive();
  }

  public Holder<IAllele<?>> getArea(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistrar.AREA_TYPE.get());
    return active ? genotype.getActive() : genotype.getInactive();
  }

  public Holder<IAllele<?>> getFertility(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistrar.FERTILITY_TYPE.get());
    return active ? genotype.getActive() : genotype.getInactive();
  }

  public Holder<IAllele<?>> getFlowers(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistrar.FLOWERS_TYPE.get());
    return active ? genotype.getActive() : genotype.getInactive();
  }

  public Holder<IAllele<?>> getHumidityPreference(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistrar.HUMIDITY_PREFERENCE_TYPE.get());
    return active ? genotype.getActive() : genotype.getInactive();
  }

  public Holder<IAllele<?>> getHumidityTolerance(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistrar.HUMIDITY_TOLERANCE_TYPE.get());
    return active ? genotype.getActive() : genotype.getInactive();
  }

  public Holder<IAllele<?>> getLifespan(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistrar.LIFESPAN_TYPE.get());
    return active ? genotype.getActive() : genotype.getInactive();
  }

  public Holder<IAllele<?>> getPollination(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistrar.POLLINATION_TYPE.get());
    return active ? genotype.getActive() : genotype.getInactive();
  }

  public Holder<IAllele<?>> getSpeed(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistrar.SPEED_TYPE.get());
    return active ? genotype.getActive() : genotype.getInactive();
  }

  public <T extends IAllele<T>> Holder<IAllele<?>> getTemperaturePreference(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistrar.TEMPERATURE_PREFERENCE_TYPE.get());
    return active ? genotype.getActive() : genotype.getInactive();
  }

  public Holder<IAllele<?>> getTemperatureTolerance(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistrar.TEMPERATURE_TOLERANCE_TYPE.get());
    return active ? genotype.getActive() : genotype.getInactive();
  }

  public Holder<IAllele<?>> getWorkcycle(boolean active)
  {
    Genotype genotype = getGenotype(AlleleTypeRegistrar.WORKCYCLE_TYPE.get());
    return active ? genotype.getActive() : genotype.getInactive();
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) {return true;}
    if (o == null || getClass() != o.getClass()) {return false;}
    Genome genome1 = (Genome) o;
    return Objects.equals(genome, genome1.genome);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(genome);
  }

}
