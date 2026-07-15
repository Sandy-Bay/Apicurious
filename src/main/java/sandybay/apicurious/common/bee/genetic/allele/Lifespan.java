package sandybay.apicurious.common.bee.genetic.allele;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
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

public class Lifespan extends AbstractAllele<Lifespan>
{

  public static final ResourceKey<IAllele<?>> SHORTEST = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("lifespan/shortest"));
  public static final ResourceKey<IAllele<?>> SHORTER = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("lifespan/shorter"));
  public static final ResourceKey<IAllele<?>> SHORT = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("lifespan/short"));
  public static final ResourceKey<IAllele<?>> SHORTENED = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("lifespan/shortened"));
  public static final ResourceKey<IAllele<?>> AVERAGE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("lifespan/average"));
  public static final ResourceKey<IAllele<?>> ELONGATED = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("lifespan/elongated"));
  public static final ResourceKey<IAllele<?>> LONG = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("lifespan/long"));
  public static final ResourceKey<IAllele<?>> LONGER = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("lifespan/longer"));
  public static final ResourceKey<IAllele<?>> LONGEST = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("lifespan/longest"));

  public static final MapCodec<Lifespan> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.INT.fieldOf("cycles").forGetter(Lifespan::getCycles), Codec.BOOL.fieldOf("isDominantTrait").forGetter(Lifespan::isDominantTrait), Codec.STRING.fieldOf("name").forGetter(Lifespan::getName)).apply(instance, Lifespan::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, Lifespan> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.INT, Lifespan::getCycles, ByteBufCodecs.BOOL, Lifespan::isDominantTrait, ByteBufCodecs.STRING_UTF8, Lifespan::getName, Lifespan::new);

  private final int cycles;

  public Lifespan(int cycles, boolean isDominantTrait, String name)
  {
    super(isDominantTrait, name);
    this.cycles = cycles;
  }

  public int getCycles()
  {
    return cycles;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!super.equals(o))
    {
      return false;
    }
    Lifespan that = (Lifespan) o;
    return cycles == that.cycles;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(super.hashCode(), cycles);
  }

  @Override
  public MapCodec<Lifespan> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, Lifespan> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public AlleleType<Lifespan> getTraitKey()
  {
    return AlleleTypeRegistrar.LIFESPAN_TYPE.get();
  }
}
