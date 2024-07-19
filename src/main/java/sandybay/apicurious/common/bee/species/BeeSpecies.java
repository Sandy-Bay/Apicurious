package sandybay.apicurious.common.bee.species;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.bee.IBeeSpecies;

import java.util.*;
import java.util.function.Consumer;

// TODO: Implement custom effect system, not just potion effects.
public class BeeSpecies implements IBeeSpecies {

  public static final Codec<BeeSpecies> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.STRING.fieldOf("name").forGetter(BeeSpecies::getName),
                  VisualData.CODEC.optionalFieldOf("visualData", VisualData.DEFAULT).forGetter(BeeSpecies::getVisualData)
                  //ProductionData.CODEC.fieldOf("productionData").forGetter(BeeSpecies::getProductionData),
                  //EnvironmentalData.CODEC.fieldOf("environmentalData").forGetter(BeeSpecies::getEnvironmentalData)
                  //Codec.list(MobEffectInstance.CODEC).fieldOf("effects").forGetter(BeeSpecies::getEffects)
                  ).apply(instance, BeeSpecies::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, BeeSpecies> NETWORK_CODEC = StreamCodec.composite(
    ByteBufCodecs.STRING_UTF8, BeeSpecies::getName,
    VisualData.NETWORK_CODEC, BeeSpecies::getVisualData,
    //ProductionData.NETWORK_CODEC, BeeSpecies::getProductionData,
    //EnvironmentalData.NETWORK_CODEC, BeeSpecies::getEnvironmentalData,
    //ByteBufCodecs.collection(ArrayList::new, MobEffectInstance.STREAM_CODEC), BeeSpecies::getEffects,
    BeeSpecies::new
  );

  private final String name;
  private Component readableName;
  private final VisualData visualData;

  //private final ProductionData productionData;
  //private final EnvironmentalData environmentalData;
  //private final List<MobEffectInstance> effects;

  public BeeSpecies(String name, VisualData visualData)
                    //ProductionData productionData,
                    //EnvironmentalData environmentalData)
  {
    this.name = name;
    this.visualData = visualData;
    //this.productionData = productionData;
    //this.environmentalData = environmentalData;
    //this.effects = effects;
  }

  private String getName() {
    return name;
  }

  @Override
  public VisualData getVisualData() {
    return this.visualData;
  }

  @Override
  public Component getReadableName() {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BeeSpecies species = (BeeSpecies) o;
    return Objects.equals(name, species.name) && Objects.equals(visualData, species.visualData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, visualData);
  }

  //public ProductionData getProductionData() {
  //  return productionData;
  //}

  //public EnvironmentalData getEnvironmentalData() {
  //  return environmentalData;
  //}

  //public List<MobEffectInstance> getEffects() {
  //  return effects;
  //}

  //public Genome getDefaultGenome() {
  //  return null;
  //}

  public static class Builder {
    private String name;
    private VisualData visualData = VisualData.Builder.create().build();

    //private ProductionData productionData;
    //private EnvironmentalData environmentalData;
    //private final List<MobEffectInstance> effects = new ArrayList<>();

    private Builder(String name) {
      this.name = name;
    }

    public static Builder create(String name) {
      return new Builder("apicurious.species." + name);
    }

    public Builder withVisualData(Consumer<VisualData.Builder> consumer) {
      VisualData.Builder builder = VisualData.Builder.create();
      consumer.accept(builder);
      this.visualData = builder.build();
      return this;
    }


//    public Builder withProductionData(Consumer<ProductionData.Builder> consumer) {
//      ProductionData.Builder builder = ProductionData.Builder.create();
//      consumer.accept(builder);
//      this.productionData = builder.build();
//      return this;
//    }
//
//    public Builder withEnvironmentalData(Consumer<EnvironmentalData.Builder> consumer) {
//      EnvironmentalData.Builder builder = EnvironmentalData.Builder.create();
//      consumer.accept(builder);
//      this.environmentalData = builder.build();
//      return this;
//    }
//
//    public Builder withEffects(MobEffectInstance... effects) {
//      this.effects.addAll(Arrays.asList(effects));
//      return this;
//    }

    public BeeSpecies build() {
      return new BeeSpecies(
              name, visualData
              //this.productionData, this.environmentalData
              //this.effects
      );
    }
  }
}
