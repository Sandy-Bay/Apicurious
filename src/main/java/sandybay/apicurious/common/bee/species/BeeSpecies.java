package sandybay.apicurious.common.bee.species;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import sandybay.apicurious.api.bee.IBeeSpecies;
import sandybay.apicurious.api.bee.genetic.IDefaultGenomeProvider;
import sandybay.apicurious.api.bee.genetic.allele.AlleleType;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.AlleleTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.AlleleNaming;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.genetic.allele.groups.EnvironmentalData;
import sandybay.apicurious.common.bee.genetic.allele.groups.ProductionData;
import sandybay.apicurious.common.bee.genetic.allele.groups.VisualData;
import sandybay.apicurious.common.bee.output.OutputData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

// Todo: Implement custom effect system, not just potion effects.
public class BeeSpecies implements IBeeSpecies, IAllele<BeeSpecies>, IDefaultGenomeProvider
{

  public static final MapCodec<BeeSpecies> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(ResourceKey.codec(ApicuriousRegistries.ALLELES).fieldOf("key").forGetter(BeeSpecies::getSpeciesKey), Codec.STRING.fieldOf("name").forGetter(BeeSpecies::getName), VisualData.CODEC.optionalFieldOf("visualData", VisualData.DEFAULT).forGetter(BeeSpecies::getVisualData), ProductionData.CODEC.fieldOf("productionData").forGetter(BeeSpecies::getProductionData), EnvironmentalData.CODEC.fieldOf("environmentalData").forGetter(BeeSpecies::getEnvironmentalData), OutputData.CODEC.fieldOf("outputData").forGetter(BeeSpecies::getOutputData), Codec.BOOL.fieldOf("isDominant").forGetter(BeeSpecies::isDominantTrait)).apply(instance, BeeSpecies::new));

  public static final StreamCodec<RegistryFriendlyByteBuf, BeeSpecies> NETWORK_CODEC = StreamCodec.composite(ResourceKey.streamCodec(ApicuriousRegistries.ALLELES), BeeSpecies::getSpeciesKey, ByteBufCodecs.STRING_UTF8, BeeSpecies::getName, VisualData.NETWORK_CODEC, BeeSpecies::getVisualData, ProductionData.NETWORK_CODEC, BeeSpecies::getProductionData, EnvironmentalData.NETWORK_CODEC, BeeSpecies::getEnvironmentalData, OutputData.NETWORK_CODEC, BeeSpecies::getOutputData, ByteBufCodecs.BOOL, BeeSpecies::isDominantTrait, BeeSpecies::new);

  private final String name;
  private final ResourceKey<IAllele<?>> key;
  private final VisualData visualData;
  private final ProductionData productionData;
  private final EnvironmentalData environmentalData;
  private final OutputData outputs;
  private final boolean isDominant;

  private Component readableName;

  public BeeSpecies(ResourceKey<IAllele<?>> key, String name, VisualData visualData, ProductionData productionData,
                    EnvironmentalData environmentalData, OutputData outputs, boolean isDominant)
  {
    this.key = key;
    this.name = name;
    this.visualData = visualData;
    this.productionData = productionData;
    this.environmentalData = environmentalData;
    this.outputs = outputs;
    this.isDominant = isDominant;
  }

  @Override
  public String toString()
  {
    return "BeeSpecies{" + "key=" + key + ", name='" + name + '\'' + ", visualData=" + visualData + ", productionData=" + productionData + ", environmentalData=" + environmentalData + ", readableName=" + readableName + ",  isDominant=" + isDominant + '}';
  }


  private String getName()
  {
    return name;
  }

  @Override
  public ResourceKey<IAllele<?>> getSpeciesKey()
  {
    return key;
  }

  @Override
  public VisualData getVisualData()
  {
    return this.visualData;
  }

  @Override
  public ProductionData getProductionData()
  {
    return productionData;
  }

  @Override
  public EnvironmentalData getEnvironmentalData()
  {
    return environmentalData;
  }

  @Override
  public OutputData getOutputData()
  {
    return outputs;
  }

  @Override
  public AlleleType<BeeSpecies> getTraitKey()
  {
    return AlleleTypeRegistrar.SPECIES_TYPE.get();
  }

  @Override
  public Component getReadableName()
  {
    if (readableName == null)
    {
      readableName = Component.translatable(this.name);
    }
    return readableName;
  }

  @Override
  public MapCodec<BeeSpecies> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, BeeSpecies> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public boolean isDominantTrait()
  {
    return isDominant;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }
    BeeSpecies species = (BeeSpecies) o;
    return Objects.equals(key, species.key) && Objects.equals(name, species.name) && Objects.equals(visualData, species.visualData) && Objects.equals(productionData, species.productionData) && Objects.equals(environmentalData, species.environmentalData) && Objects.equals(outputs, species.outputs);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(key, name, visualData, productionData, environmentalData, outputs);
  }

  @Override
  public Genome getSpeciesDefaultGenome(Level level)
  {
    Genome genome = new Genome();
    genome.initializeDefaults(level.registryAccess().holderOrThrow(getSpeciesKey()));
    return genome;
  }

  @Override
  public Genome getSpeciesDefaultGenome(HolderLookup.Provider provider)
  {
    Genome genome = new Genome();
    Holder<IAllele<?>> holder = provider.lookupOrThrow(ApicuriousRegistries.ALLELES).get(getSpeciesKey()).orElseThrow(() -> new IllegalStateException("Species " + getSpeciesKey() + " not found in registry"));
    genome.initializeDefaults(holder);
    return genome;
  }

  @Override
  public Map<AlleleType<? extends IAllele<?>>, Holder<IAllele<?>>> getDefaultTraits()
  {
    Map<AlleleType<? extends IAllele<?>>, Holder<IAllele<?>>> defaults = new HashMap<>();
    defaults.put(AlleleTypeRegistrar.AREA_TYPE.get(), productionData.getAreaHolder());
    defaults.put(AlleleTypeRegistrar.FERTILITY_TYPE.get(), productionData.getFertilityHolder());
    defaults.put(AlleleTypeRegistrar.FLOWERS_TYPE.get(), environmentalData.getFlowersHolder());
    defaults.put(AlleleTypeRegistrar.HUMIDITY_PREFERENCE_TYPE.get(), environmentalData.getHumidityData().preferenceHolder());
    defaults.put(AlleleTypeRegistrar.HUMIDITY_TOLERANCE_TYPE.get(), environmentalData.getHumidityData().toleranceHolder());
    defaults.put(AlleleTypeRegistrar.LIFESPAN_TYPE.get(), productionData.getLifespanHolder());
    defaults.put(AlleleTypeRegistrar.POLLINATION_TYPE.get(), productionData.getPollinationHolder());
    defaults.put(AlleleTypeRegistrar.SPEED_TYPE.get(), productionData.getSpeedHolder());
    defaults.put(AlleleTypeRegistrar.TEMPERATURE_PREFERENCE_TYPE.get(), environmentalData.getTemperatureData().preferenceHolder());
    defaults.put(AlleleTypeRegistrar.TEMPERATURE_TOLERANCE_TYPE.get(), environmentalData.getTemperatureData().toleranceHolder());
    defaults.put(AlleleTypeRegistrar.WORKCYCLE_TYPE.get(), productionData.getWorkcycleHolder());
    return defaults;
  }

  public static class Builder
  {
    private final BootstrapContext<IAllele<?>> context;
    private final ResourceKey<IAllele<?>> key;
    private final String name;
    private VisualData visualData;
    private ProductionData productionData;
    private EnvironmentalData environmentalData;
    private OutputData outputs;
    private boolean isDominant;

    private Builder(BootstrapContext<IAllele<?>> context, ResourceKey<IAllele<?>> key, String name)
    {
      this.context = context;
      this.key = key;
      this.name = name;
      this.visualData = VisualData.Builder.create().build();
      this.productionData = ProductionData.Builder.create(context).build();
      this.environmentalData = EnvironmentalData.Builder.create(context).build();
      this.outputs = OutputData.Builder.create(context).build();
      this.isDominant = true;
    }

    public static Builder create(BootstrapContext<IAllele<?>> context, ResourceKey<IAllele<?>> key, String name)
    {
      return new Builder(context, key, AlleleNaming.key("species", name));
    }

    public Builder withVisualData(Consumer<VisualData.Builder> consumer)
    {
      VisualData.Builder builder = VisualData.Builder.create();
      consumer.accept(builder);
      this.visualData = builder.build();
      return this;
    }

    public Builder withProductionData(Consumer<ProductionData.Builder> consumer)
    {
      ProductionData.Builder builder = ProductionData.Builder.create(context);
      consumer.accept(builder);
      this.productionData = builder.build();
      return this;
    }

    public Builder withEnvironmentalData(Consumer<EnvironmentalData.Builder> consumer)
    {
      EnvironmentalData.Builder builder = EnvironmentalData.Builder.create(context);
      consumer.accept(builder);
      this.environmentalData = builder.build();
      return this;
    }

    public Builder withOutputData(Consumer<OutputData.Builder> consumer)
    {
      OutputData.Builder builder = OutputData.Builder.create(context);
      consumer.accept(builder);
      this.outputs = builder.build();
      return this;
    }

    public Builder recessive()
    {
      this.isDominant = false;
      return this;
    }

    public BeeSpecies build()
    {
      return new BeeSpecies(this.key, this.name, this.visualData, this.productionData, this.environmentalData, this.outputs, this.isDominant);
    }
  }
}