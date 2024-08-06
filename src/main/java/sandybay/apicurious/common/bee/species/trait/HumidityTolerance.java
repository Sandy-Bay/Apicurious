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

public class HumidityTolerance implements IAllele<HumidityTolerance>
{

  public static final ResourceKey<HumidityTolerance> NO_TOLERANCE = ResourceKey.create(ApicuriousRegistries.HUMIDITY_TOLERANCES, Apicurious.createResourceLocation("none"));
  public static final ResourceKey<HumidityTolerance> LOWEST_TOLERANCE = ResourceKey.create(ApicuriousRegistries.HUMIDITY_TOLERANCES, Apicurious.createResourceLocation("lowest"));
  public static final ResourceKey<HumidityTolerance> LOW_TOLERANCE = ResourceKey.create(ApicuriousRegistries.HUMIDITY_TOLERANCES, Apicurious.createResourceLocation("low"));
  public static final ResourceKey<HumidityTolerance> AVERAGE_TOLERANCE = ResourceKey.create(ApicuriousRegistries.HUMIDITY_TOLERANCES, Apicurious.createResourceLocation("average"));
  public static final ResourceKey<HumidityTolerance> HIGH_TOLERANCE = ResourceKey.create(ApicuriousRegistries.HUMIDITY_TOLERANCES, Apicurious.createResourceLocation("high"));
  public static final ResourceKey<HumidityTolerance> MAXIMUM_TOLERANCE = ResourceKey.create(ApicuriousRegistries.HUMIDITY_TOLERANCES, Apicurious.createResourceLocation("maximum"));

  public static final MapCodec<HumidityTolerance> CODEC = RecordCodecBuilder.mapCodec(
          instance -> instance.group(
                  Codec.INT.fieldOf("toleranceModifier").forGetter(HumidityTolerance::getToleranceModifier),
                  Codec.BOOL.fieldOf("isDominantTrait").forGetter(HumidityTolerance::isDominantTrait),
                  Codec.STRING.fieldOf("humidityTolerance").forGetter(HumidityTolerance::getName)
          ).apply(instance, HumidityTolerance::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, HumidityTolerance> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, HumidityTolerance::getToleranceModifier,
          ByteBufCodecs.BOOL, HumidityTolerance::isDominantTrait,
          ByteBufCodecs.STRING_UTF8, HumidityTolerance::getName,
          HumidityTolerance::new
  );

  private final int toleranceModifier;
  private final boolean isDominantTrait;
  private final String name;
  private Component readableName;

  public HumidityTolerance(int toleranceModifier, boolean isDominantTrait, String name)
  {
    this.toleranceModifier = toleranceModifier;
    this.isDominantTrait = isDominantTrait;
    this.name = name;
  }

  public int getToleranceModifier()
  {
    return toleranceModifier;
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
    HumidityTolerance that = (HumidityTolerance) o;
    return toleranceModifier == that.toleranceModifier && isDominantTrait == that.isDominantTrait && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(toleranceModifier, isDominantTrait, name);
  }

  @Override
  public MapCodec<HumidityTolerance> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, HumidityTolerance> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public AlleleType<HumidityTolerance> getTraitKey()
  {
    return AlleleTypeRegistration.HUMIDITY_TOLERANCE_TYPE.get();
  }
}
