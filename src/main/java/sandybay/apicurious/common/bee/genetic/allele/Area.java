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

public class Area extends AbstractAllele<Area>
{
  public static final ResourceKey<IAllele<?>> SMALLEST = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("area/smallest"));
  public static final ResourceKey<IAllele<?>> SMALLER = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("area/smaller"));
  public static final ResourceKey<IAllele<?>> SMALL = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("area/small"));
  public static final ResourceKey<IAllele<?>> AVERAGE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("area/average"));
  public static final ResourceKey<IAllele<?>> LARGE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("area/large"));
  public static final ResourceKey<IAllele<?>> LARGER = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("area/larger"));
  public static final ResourceKey<IAllele<?>> LARGEST = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("area/largest"));


  public static final MapCodec<Area> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.INT.fieldOf("xzOffset").forGetter(Area::getXZOffset), Codec.INT.fieldOf("yOffset").forGetter(Area::getYOffset), Codec.BOOL.fieldOf("isDominantTrait").forGetter(Area::isDominantTrait), Codec.STRING.fieldOf("name").forGetter(Area::getName)).apply(instance, Area::new));

  public static final StreamCodec<RegistryFriendlyByteBuf, Area> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.INT, Area::getXZOffset, ByteBufCodecs.INT, Area::getYOffset, ByteBufCodecs.BOOL, Area::isDominantTrait, ByteBufCodecs.STRING_UTF8, Area::getName, Area::new);

  private final int xzOffset;
  private final int yOffset;

  public Area(int xzOffset, int yOffset, boolean isDominantTrait, String name)
  {
    super(isDominantTrait, name);
    this.xzOffset = xzOffset;
    this.yOffset = yOffset;
  }

  public int getXZOffset()
  {
    return xzOffset;
  }

  public int getYOffset()
  {
    return yOffset;
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
    Area area = (Area) o;
    return xzOffset == area.xzOffset && yOffset == area.yOffset;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(super.hashCode(), xzOffset, yOffset);
  }

  @Override
  public MapCodec<Area> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, Area> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public AlleleType<Area> getTraitKey()
  {
    return AlleleTypeRegistrar.AREA_TYPE.get();
  }
}