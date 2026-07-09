package sandybay.apicurious.common.bee.genetic.allele;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.AbstractAllele;
import sandybay.apicurious.api.bee.genetic.allele.AlleleType;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.AlleleTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

import java.util.Objects;

public class Fertility extends AbstractAllele<Fertility>
{

  public static final ResourceKey<IAllele<?>> LOW_FERTILITY = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("fertility/low"));
  public static final ResourceKey<IAllele<?>> AVERAGE_FERTILITY = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("fertility/average"));
  public static final ResourceKey<IAllele<?>> HIGH_FERTILITY = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("fertility/high"));
  public static final ResourceKey<IAllele<?>> MAXIMUM_FERTILITY = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("fertility/maximum"));

  public static final MapCodec<Fertility> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.INT.fieldOf("offspring").forGetter(Fertility::getOffspring), Codec.BOOL.fieldOf("isDominantTrait").forGetter(Fertility::isDominantTrait), Codec.STRING.fieldOf("name").forGetter(Fertility::getName)).apply(instance, Fertility::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, Fertility> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.INT, Fertility::getOffspring, ByteBufCodecs.BOOL, Fertility::isDominantTrait, ByteBufCodecs.STRING_UTF8, Fertility::getName, Fertility::new);

  private final int offspring;
  public Component readableName;

  public Fertility(int offspring, boolean isDominantTrait, String name)
  {
    super(isDominantTrait, name);
    this.offspring = offspring;
  }

  public int getOffspring()
  {
    return offspring;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) {return true;}
    if (!super.equals(o)) {return false;}
    Fertility fertility = (Fertility) o;
    return offspring == fertility.offspring;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(super.hashCode(), offspring);
  }

  @Override
  public MapCodec<Fertility> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, Fertility> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public AlleleType<Fertility> getTraitKey()
  {
    return AlleleTypeRegistrar.FERTILITY_TYPE.get();
  }
}
