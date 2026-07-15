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

/**
 * Speed is a trait inherited by Bees which alters the chance of a bee to produce output.
 * The faster the speed, the higher the chance of a bee creating a product per bee cycle update.
 */
public class Speed extends AbstractAllele<Speed>
{

  public static final ResourceKey<IAllele<?>> SLOWEST = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("speed/slowest"));
  public static final ResourceKey<IAllele<?>> SLOWER = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("speed/slower"));
  public static final ResourceKey<IAllele<?>> SLOW = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("speed/slow"));
  public static final ResourceKey<IAllele<?>> AVERAGE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("speed/average"));
  public static final ResourceKey<IAllele<?>> FAST = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("speed/fast"));
  public static final ResourceKey<IAllele<?>> FASTER = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("speed/faster"));
  public static final ResourceKey<IAllele<?>> FASTEST = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("speed/fastest"));


  public static final MapCodec<Speed> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.FLOAT.fieldOf("productionModifier").forGetter(Speed::getProductionModifier), Codec.BOOL.fieldOf("isDominantTrait").forGetter(Speed::isDominantTrait), Codec.STRING.fieldOf("name").forGetter(Speed::getName)).apply(instance, Speed::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, Speed> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.FLOAT, Speed::getProductionModifier, ByteBufCodecs.BOOL, Speed::isDominantTrait, ByteBufCodecs.STRING_UTF8, Speed::getName, Speed::new);

  private final float productionModifier;

  public Speed(float productionModifier, boolean isDominantTrait, String name)
  {
    super(isDominantTrait, name);
    this.productionModifier = productionModifier;
  }

  public float getProductionModifier()
  {
    return productionModifier;
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
    Speed that = (Speed) o;
    return Float.compare(productionModifier, that.productionModifier) == 0;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(super.hashCode(), productionModifier);
  }

  @Override
  public MapCodec<Speed> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, Speed> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public AlleleType<Speed> getTraitKey()
  {
    return AlleleTypeRegistrar.SPEED_TYPE.get();
  }
}
