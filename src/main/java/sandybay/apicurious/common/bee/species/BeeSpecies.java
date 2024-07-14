package sandybay.apicurious.common.bee.species;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.Block;
import sandybay.apicurious.common.bee.genetics.Allele;
import sandybay.apicurious.common.bee.genetics.Genome;
import sandybay.apicurious.common.bee.traits.groups.EnvironmentalData;
import sandybay.apicurious.common.bee.traits.groups.ProductionData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class BeeSpecies {

  public static final Codec<BeeSpecies> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.STRING.fieldOf("name").forGetter(BeeSpecies::getName),
                  ProductionData.CODEC.fieldOf("productionData").forGetter(BeeSpecies::getProductionData),
                  EnvironmentalData.CODEC.fieldOf("enviromentalData").forGetter(BeeSpecies::getEnviromentalData),
                  TagKey.codec(Registries.BLOCK).fieldOf("flowers").forGetter(BeeSpecies::getFlowers),
                  Codec.INT.fieldOf("workRadius").forGetter(BeeSpecies::getWorkRadius),
                  Codec.list(MobEffectInstance.CODEC).fieldOf("effects").forGetter(BeeSpecies::getEffects)
                  ).apply(instance, BeeSpecies::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, BeeSpecies> NETWORK_CODEC = StreamCodec.composite(
    ByteBufCodecs.STRING_UTF8, BeeSpecies::getName,
    ProductionData.NETWORK_CODEC, BeeSpecies::getProductionData,
    EnvironmentalData.NETWORK_CODEC, BeeSpecies::getEnviromentalData,
    ByteBufCodecs.fromCodec(TagKey.codec(Registries.BLOCK)), BeeSpecies::getFlowers,
    ByteBufCodecs.INT, BeeSpecies::getWorkRadius,
    ByteBufCodecs.collection(ArrayList::new, MobEffectInstance.STREAM_CODEC), BeeSpecies::getEffects,
    BeeSpecies::new
  );

  private final String name;
  private Component readableName;

  private final ProductionData productionData;
  private final EnvironmentalData environmentalData;
  private final TagKey<Block> flowers;
  private final int workRadius;
  private final List<MobEffectInstance> effects;

  public BeeSpecies(String name,
                    ProductionData productionData,
                    EnvironmentalData environmentalData,
                    TagKey<Block> flowers,
                    int workRadius,
                    List<MobEffectInstance> effects) {
    this.name = name;
    this.productionData = productionData;
    this.environmentalData = environmentalData;
    this.flowers = flowers;
    this.workRadius = workRadius;
    this.effects = effects;
  }

  private String getName() {
    return name;
  }

  public Component getReadableName() {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  public ProductionData getProductionData() {
    return productionData;
  }

  public EnvironmentalData getEnviromentalData() {
    return environmentalData;
  }

  public TagKey<Block> getFlowers() {
    return flowers;
  }

  public int getWorkRadius() {
    return workRadius;
  }

  public List<MobEffectInstance> getEffects() {
    return effects;
  }

  public Genome getDefaultGenome() {
    return null;
  }

  public static class Builder {

    private ProductionData productionData;
    private EnvironmentalData environmentalData;
    private TagKey<Block> flowers = BlockTags.FLOWERS; // TODO: Define default flower tag here
    private int workRadius = 4;
    private final List<MobEffectInstance> effects = new ArrayList<>();

    private Builder() {}

    public static Builder create() {
      return new Builder();
    }

    public Builder withProductionData(Consumer<ProductionData.Builder> consumer) {
      ProductionData.Builder builder = ProductionData.Builder.create();
      consumer.accept(builder);
      this.productionData = builder.build();
      return this;
    }

    public Builder withEnvironmentalData(Consumer<EnvironmentalData.Builder> consumer) {
      EnvironmentalData.Builder builder = EnvironmentalData.Builder.create();
      consumer.accept(builder);
      this.environmentalData = builder.build();
      return this;
    }

    public Builder withFlowers(TagKey<Block> flowers) {
      this.flowers = flowers;
      return this;
    }

    public Builder withWorkRadius(int workRadius) {
      this.workRadius = workRadius;
      return this;
    }

    public Builder withEffects(MobEffectInstance... effects) {
      this.effects.addAll(Arrays.asList(effects));
      return this;
    }

    public BeeSpecies build(String name) {
      return new BeeSpecies(
              name,
              this.productionData, this.environmentalData,
              this.flowers, this.workRadius, this.effects
      );
    }
  }
}
