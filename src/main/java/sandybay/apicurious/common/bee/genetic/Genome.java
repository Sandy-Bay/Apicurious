package sandybay.apicurious.common.bee.genetic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.Genotype;
import sandybay.apicurious.api.bee.genetic.IDefaultGenomeProvider;
import sandybay.apicurious.api.bee.genetic.IGenome;
import sandybay.apicurious.api.bee.genetic.allele.AlleleType;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.AlleleTypeRegistrar;
import sandybay.apicurious.api.util.GeneticHelper;
import sandybay.apicurious.common.bee.species.BeeSpecies;

import java.util.*;

public class Genome implements IGenome
{
  public static Codec<Genome> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.unboundedMap(AlleleType.CODEC, Genotype.CODEC).fieldOf("genome").forGetter(Genome::getGenome)).apply(instance, Genome::new));

  public static StreamCodec<RegistryFriendlyByteBuf, Genome> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.map(HashMap::newHashMap, AlleleType.NETWORK_CODEC, Genotype.NETWORK_CODEC), Genome::getGenome, Genome::new);

  private Map<AlleleType<? extends IAllele<?>>, Genotype> genome;

  public Genome()
  {
    this.genome = Collections.emptyMap();
  }

  public Genome(Map<AlleleType<? extends IAllele<?>>, Genotype> genome)
  {
    this.genome = Collections.unmodifiableMap(new HashMap<>(genome));
  }

  private Map<AlleleType<? extends IAllele<?>>, Genotype> getGenome()
  {
    return genome;
  }

  public boolean setAllelePair(Genotype genotype)
  {
    AlleleType<? extends IAllele<?>> key = genotype.getActive().value().getTraitKey();
    Genotype prev = this.genome.get(key);

    Map<AlleleType<? extends IAllele<?>>, Genotype> updated = new HashMap<>(this.genome);
    updated.put(key, genotype);
    this.genome = Collections.unmodifiableMap(updated);

    return prev == null || !prev.equals(genotype);
  }

  @Override
  public <T extends IAllele<T>> Genotype getGenotype(AlleleType<T> traitKey)
  {
    return this.genome.get(traitKey);
  }

  @Override
  public IGenome combineGenomes(IGenome other, RandomSource random)
  {
    if (!(other instanceof Genome otherGenome))
    {
      throw new IllegalArgumentException("Cannot combine Genome with unknown IGenome implementation: " + other.getClass());
    }

    // Union of both parents' trait keys, not just this.genome's.
    Set<AlleleType<? extends IAllele<?>>> allKeys = new HashSet<>(this.genome.keySet());
    allKeys.addAll(otherGenome.genome.keySet());

    Map<AlleleType<? extends IAllele<?>>, Genotype> childTraits = new HashMap<>();

    for (AlleleType<? extends IAllele<?>> key : allKeys)
    {
      Genotype thisGenotype = this.genome.get(key);
      Genotype otherGenotype = otherGenome.genome.get(key);

      if (thisGenotype == null && otherGenotype == null)
      {
        // Shouldn't happen since key came from one of the maps, but guard anyway.
        continue;
      }
      else if (thisGenotype == null)
      {
        Apicurious.LOGGER.warn("Parent genome missing trait {} during combineGenomes; inheriting from other parent only", key);
        childTraits.put(key, otherGenotype);
      }
      else if (otherGenotype == null)
      {
        Apicurious.LOGGER.warn("Other parent genome missing trait {} during combineGenomes; inheriting from this parent only", key);
        childTraits.put(key, thisGenotype);
      }
      else
      {
        childTraits.put(key, GeneticHelper.getGenotypeFromParents(thisGenotype, otherGenotype, random));
      }
    }

    return new Genome(childTraits);
  }

  @Override
  public void initializeDefaults(Holder<IAllele<?>> species)
  {
    IAllele<?> value = species.value();
    if (!(value instanceof IDefaultGenomeProvider provider))
    {
      throw new IllegalArgumentException(
              "Expected holder " + species.getRegisteredName() + " to implement IDefaultGenomeProvider, but found " +
                      value.getClass().getSimpleName() + " instead. Cannot build a default genome from this allele.");
    }

    Map<AlleleType<? extends IAllele<?>>, Genotype> updated = new HashMap<>(this.genome);
    updated.put(AlleleTypeRegistrar.SPECIES_TYPE.get(), Genotype.defaultOf(species));
    for (Map.Entry<AlleleType<? extends IAllele<?>>, Holder<IAllele<?>>> entry : provider.getDefaultTraits().entrySet())
    {
      updated.put(entry.getKey(), Genotype.defaultOf(entry.getValue()));
    }
    this.genome = Collections.unmodifiableMap(updated);
  }

  private Genotype requireGenotype(AlleleType<? extends IAllele<?>> traitKey)
  {
    Genotype genotype = this.genome.get(traitKey);
    if (genotype == null)
    {
      throw new IllegalStateException("Genome is missing required trait '" + traitKey + "'. " + "This usually means getDefaultGenome() was never called, or a custom genome was built incompletely.");
    }
    return genotype;
  }

  public Holder<IAllele<?>> getTrait(AlleleType<? extends IAllele<?>> traitKey, boolean active)
  {
    Genotype genotype = requireGenotype(traitKey);
    return active ? genotype.getActive() : genotype.getInactive();
  }

  public Holder<IAllele<?>> getSpecies(boolean active) { return getTrait(AlleleTypeRegistrar.SPECIES_TYPE.get(), active); }
  public Holder<IAllele<?>> getArea(boolean active) { return getTrait(AlleleTypeRegistrar.AREA_TYPE.get(), active); }
  public Holder<IAllele<?>> getFertility(boolean active) { return getTrait(AlleleTypeRegistrar.FERTILITY_TYPE.get(), active); }
  public Holder<IAllele<?>> getFlowers(boolean active) { return getTrait(AlleleTypeRegistrar.FLOWERS_TYPE.get(), active); }
  public Holder<IAllele<?>> getHumidityPreference(boolean active) { return getTrait(AlleleTypeRegistrar.HUMIDITY_PREFERENCE_TYPE.get(), active); }
  public Holder<IAllele<?>> getHumidityTolerance(boolean active) { return getTrait(AlleleTypeRegistrar.HUMIDITY_TOLERANCE_TYPE.get(), active); }
  public Holder<IAllele<?>> getLifespan(boolean active) { return getTrait(AlleleTypeRegistrar.LIFESPAN_TYPE.get(), active); }
  public Holder<IAllele<?>> getPollination(boolean active) { return getTrait(AlleleTypeRegistrar.POLLINATION_TYPE.get(), active); }
  public Holder<IAllele<?>> getSpeed(boolean active) { return getTrait(AlleleTypeRegistrar.SPEED_TYPE.get(), active); }
  public Holder<IAllele<?>> getTemperaturePreference(boolean active) { return getTrait(AlleleTypeRegistrar.TEMPERATURE_PREFERENCE_TYPE.get(), active); }
  public Holder<IAllele<?>> getTemperatureTolerance(boolean active) { return getTrait(AlleleTypeRegistrar.TEMPERATURE_TOLERANCE_TYPE.get(), active); }
  public Holder<IAllele<?>> getWorkcycle(boolean active) { return getTrait(AlleleTypeRegistrar.WORKCYCLE_TYPE.get(), active); }

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
