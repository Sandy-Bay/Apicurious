package sandybay.apicurious.common.bee.genetic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import sandybay.apicurious.api.bee.IBeeSpecies;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.bee.genetic.IGenome;
import sandybay.apicurious.api.bee.genetic.ITrait;
import sandybay.apicurious.api.util.ApicuriousConstants;

import java.util.HashMap;
import java.util.Map;

public class Genome implements IGenome
{
  public static Codec<Genome> CODEC = RecordCodecBuilder.create(instance ->
          instance.group(
                  Codec.compoundList(ResourceLocation.CODEC, Allele.CODEC)
          )
  )

  private final Map<ResourceLocation, IAllele<?>> genome;

  public Genome()
  {
    this.genome = new HashMap<>();
  }

  @Override
  public Map<ResourceLocation, IAllele<?>> getGenomeMap()
  {
    return this.genome;
  }

  @Override
  public boolean setAllele(IAllele<?> allele)
  {
    IAllele<?> prev = this.genome.put(allele.getTraitKey(), allele);
    return prev == null || prev != allele;
  }

  @Override
  public <T extends ITrait<T>> IAllele<?> getAllele(ResourceLocation traitKey)
  {
    return this.genome.get(traitKey);
  }

  @Override
  public IGenome combineGenomes(IGenome other)
  {

    return null;
  }

  @Override
  public void getGenomeFromSpecies(IBeeSpecies species)
  {
    this.genome.put(ApicuriousConstants.SPECIES, Allele.of(Holder.direct(species)));
    this.genome.put(ApicuriousConstants.AREA, Allele.of(species.getProductionData().getArea()));
    this.genome.put(ApicuriousConstants.FERTILITY, Allele.of(species.getProductionData().getFertility()));
    this.genome.put(ApicuriousConstants.FLOWERS, Allele.of(species.getEnvironmentalData().getFlowers()));
    this.genome.put(ApicuriousConstants.HUMIDITY_PREFERENCE, Allele.of(species.getEnvironmentalData().getHumidityData().preference()));
    this.genome.put(ApicuriousConstants.HUMIDITY_TOLERANCE, Allele.of(species.getEnvironmentalData().getHumidityData().tolerance()));
    this.genome.put(ApicuriousConstants.LIFESPAN, Allele.of(species.getProductionData().getLifespan()));
    this.genome.put(ApicuriousConstants.POLLINATION, Allele.of(species.getProductionData().getPollination()));
    this.genome.put(ApicuriousConstants.SPEED, Allele.of(species.getProductionData().getSpeed()));
    this.genome.put(ApicuriousConstants.TEMPERATURE_PREFERENCE, Allele.of(species.getEnvironmentalData().getTemperatureData().preference()));
    this.genome.put(ApicuriousConstants.TEMPERATURE_TOLERANCE, Allele.of(species.getEnvironmentalData().getTemperatureData().tolerance()));
    this.genome.put(ApicuriousConstants.WORKCYCLE, Allele.of(species.getProductionData().getWorkCycle()));
  }
}
