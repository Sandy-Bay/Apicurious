package sandybay.apicurious.common.bee.species.trait;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.AlleleType;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.register.AlleleTypeRegistration;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

import java.util.Objects;

public class Area implements IAllele<Area>
{
  public static final ResourceKey<Area> SMALLEST = ResourceKey.create(ApicuriousRegistries.AREAS, Apicurious.createResourceLocation("smallest"));
  public static final ResourceKey<Area> SMALLER = ResourceKey.create(ApicuriousRegistries.AREAS, Apicurious.createResourceLocation("smaller"));
  public static final ResourceKey<Area> SMALL = ResourceKey.create(ApicuriousRegistries.AREAS, Apicurious.createResourceLocation("small"));
  public static final ResourceKey<Area> AVERAGE = ResourceKey.create(ApicuriousRegistries.AREAS, Apicurious.createResourceLocation("average"));
  public static final ResourceKey<Area> LARGE = ResourceKey.create(ApicuriousRegistries.AREAS, Apicurious.createResourceLocation("large"));
  public static final ResourceKey<Area> LARGER = ResourceKey.create(ApicuriousRegistries.AREAS, Apicurious.createResourceLocation("larger"));
  public static final ResourceKey<Area> LARGEST = ResourceKey.create(ApicuriousRegistries.AREAS, Apicurious.createResourceLocation("largest"));


  public static final MapCodec<Area> CODEC = RecordCodecBuilder.mapCodec(
          instance -> instance.group(
                  Codec.INT.fieldOf("xzOffset").forGetter(Area::getXZOffset),
                  Codec.INT.fieldOf("yOffset").forGetter(Area::getYOffset),
                  Codec.BOOL.fieldOf("isDominantTrait").forGetter(Area::isDominantTrait),
                  Codec.STRING.fieldOf("name").forGetter(Area::getName)
          ).apply(instance, Area::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, Area> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, Area::getXZOffset,
          ByteBufCodecs.INT, Area::getYOffset,
          ByteBufCodecs.BOOL, Area::isDominantTrait,
          ByteBufCodecs.STRING_UTF8, Area::getName,
          Area::new
  );

  private final int xzOffset;
  private final int yOffset;
  private final boolean isDominantTrait;
  private final String name;
  private Component readableName;

  public Area(int xzOffset, int yOffset, boolean isDominantTrait, String name)
  {
    this.xzOffset = xzOffset;
    this.yOffset = yOffset;
    this.isDominantTrait = isDominantTrait;
    this.name = name;
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
  public boolean isDominantTrait()
  {
    return isDominantTrait;
  }

  private String getName()
  {
    return name;
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
    Area area = (Area) o;
    return xzOffset == area.xzOffset && yOffset == area.yOffset && isDominantTrait == area.isDominantTrait && Objects.equals(name, area.name);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(xzOffset, yOffset, isDominantTrait, name);
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
  public AlleleType getTraitKey()
  {
    return AlleleTypeRegistration.AREA_TYPE.get();
  }
}
