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

public class Pollination extends AbstractAllele<Pollination>
{

  public static final ResourceKey<IAllele<?>> SLOWEST = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("pollination/slowest"));
  public static final ResourceKey<IAllele<?>> SLOWER = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("pollination/slower"));
  public static final ResourceKey<IAllele<?>> SLOW = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("pollination/slow"));
  public static final ResourceKey<IAllele<?>> AVERAGE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("pollination/average"));
  public static final ResourceKey<IAllele<?>> FAST = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("pollination/fast"));
  public static final ResourceKey<IAllele<?>> FASTER = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("pollination/faster"));
  public static final ResourceKey<IAllele<?>> FASTEST = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("pollination/fastest"));

  public static final MapCodec<Pollination> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.FLOAT.fieldOf("pollinationChance").forGetter(Pollination::getPollinationChance), Codec.BOOL.fieldOf("isDominantTrait").forGetter(Pollination::isDominantTrait), Codec.STRING.fieldOf("name").forGetter(Pollination::getName)).apply(instance, Pollination::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, Pollination> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.FLOAT, Pollination::getPollinationChance, ByteBufCodecs.BOOL, Pollination::isDominantTrait, ByteBufCodecs.STRING_UTF8, Pollination::getName, Pollination::new);

  private final float pollinationChance;

  public Pollination(float pollinationChance, boolean isDominantTrait, String name)
  {
    super(isDominantTrait, name);
    this.pollinationChance = pollinationChance;
  }

  public float getPollinationChance()
  {
    return pollinationChance;
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
    Pollination that = (Pollination) o;
    return pollinationChance == that.pollinationChance;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(super.hashCode(), pollinationChance);
  }

  @Override
  public MapCodec<Pollination> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, Pollination> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public AlleleType<Pollination> getTraitKey()
  {
    return AlleleTypeRegistrar.POLLINATION_TYPE.get();
  }
}
