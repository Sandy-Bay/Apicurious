package sandybay.apicurious.common.bee.species;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.bee.IBeeSpecies;
import sandybay.apicurious.common.bee.output.OutputData;
import sandybay.apicurious.common.bee.species.trait.groups.EnvironmentalData;
import sandybay.apicurious.common.bee.species.trait.groups.ProductionData;
import sandybay.apicurious.common.bee.species.trait.groups.VisualData;

import java.util.Objects;
import java.util.function.Consumer;

// TODO: Implement custom effect system, not just potion effects.
public class BeeSpecies implements IBeeSpecies
{

  public static final Codec<BeeSpecies> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.STRING.fieldOf("name").forGetter(BeeSpecies::getName),
                  VisualData.CODEC.optionalFieldOf("visualData", VisualData.DEFAULT).forGetter(BeeSpecies::getVisualData),
                  ProductionData.CODEC.fieldOf("productionData").forGetter(BeeSpecies::getProductionData),
                  EnvironmentalData.CODEC.fieldOf("environmentalData").forGetter(BeeSpecies::getEnvironmentalData),
                  OutputData.CODEC.fieldOf("outputData").forGetter(BeeSpecies::getOutputData)
                  //Codec.list(MobEffectInstance.CODEC).fieldOf("effects").forGetter(BeeSpecies::getEffects)
          ).apply(instance, BeeSpecies::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, BeeSpecies> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.STRING_UTF8, BeeSpecies::getName,
          VisualData.NETWORK_CODEC, BeeSpecies::getVisualData,
          ProductionData.NETWORK_CODEC, BeeSpecies::getProductionData,
          EnvironmentalData.NETWORK_CODEC, BeeSpecies::getEnvironmentalData,
          OutputData.NETWORK_CODEC, BeeSpecies::getOutputData,
          //ByteBufCodecs.collection(ArrayList::new, MobEffectInstance.STREAM_CODEC), BeeSpecies::getEffects,
          BeeSpecies::new
  );

  private final String name;
  private final VisualData visualData;
  private final ProductionData productionData;
  private final EnvironmentalData environmentalData;
  private final OutputData outputs;
  private Component readableName;
  //private final List<MobEffectInstance> effects;

  public BeeSpecies(String name, VisualData visualData,
                    ProductionData productionData,
                    EnvironmentalData environmentalData,
                    OutputData outputs)
  {
    this.name = name;
    this.visualData = visualData;
    this.productionData = productionData;
    this.environmentalData = environmentalData;
    this.outputs = outputs;
    //this.effects = effects;
  }

  @Override
  public String toString()
  {
    return super.toString(); //+ " BeeSpecies{" + "name='" + name + '\'' + ", visualData=" + visualData + ", productionData=" + productionData + ", environmentalData=" + environmentalData + ", readableName=" + readableName + '}';
  }


  private String getName()
  {
    return name;
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
  public Component getReadableName()
  {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BeeSpecies species = (BeeSpecies) o;
    return Objects.equals(name, species.name) &&
            Objects.equals(visualData, species.visualData) &&
            Objects.equals(productionData, species.productionData) &&
            Objects.equals(environmentalData, species.environmentalData) &&
            Objects.equals(readableName, species.readableName);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(name, visualData, productionData, environmentalData, readableName);
  }

  //public List<MobEffectInstance> getEffects() {
  //  return effects;
  //}

  //public Genome getDefaultGenome() {
  //  return null;
  //}

  public static class Builder
  {
    private final BootstrapContext<BeeSpecies> context;
    private final String name;
    private VisualData visualData;
    private ProductionData productionData;
    private EnvironmentalData environmentalData;
    private OutputData outputs;
    //private final List<MobEffectInstance> effects = new ArrayList<>();

    private Builder(BootstrapContext<BeeSpecies> context, String name)
    {
      this.context = context;
      this.name = name;
      this.visualData = VisualData.Builder.create().build();
      this.productionData = ProductionData.Builder.create(context).build();
      this.environmentalData = EnvironmentalData.Builder.create(context).build();
      this.outputs = OutputData.Builder.create(context).build();
    }

    public static Builder create(BootstrapContext<BeeSpecies> context, String name)
    {
      return new Builder(context, "apicurious.species." + name);
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

    public Builder withOutputData(Consumer<OutputData.Builder> consumer) {
      OutputData.Builder builder = OutputData.Builder.create(context);
      consumer.accept(builder);
      this.outputs = builder.build();
      return this;
    }

//    public Builder withEffects(MobEffectInstance... effects) {
//      this.effects.addAll(Arrays.asList(effects));
//      return this;
//    }

    public BeeSpecies build()
    {
      return new BeeSpecies(
              this.name, this.visualData,
              this.productionData, this.environmentalData,
              this.outputs
              //this.effects
      );
    }
  }
}
