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
import sandybay.apicurious.api.bee.genetic.AlleleType;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.register.AlleleTypeRegistration;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

import java.util.Objects;

public class TemperatureTolerance implements IAllele<TemperatureTolerance>
{

  public static final ResourceKey<IAllele<?>> NO_TOLERANCE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("temperature/tolerance/none"));
  public static final ResourceKey<IAllele<?>> LOWEST_TOLERANCE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("temperature/tolerance/lowest"));
  public static final ResourceKey<IAllele<?>> LOW_TOLERANCE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("temperature/tolerance/low"));
  public static final ResourceKey<IAllele<?>> AVERAGE_TOLERANCE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("temperature/tolerance/average"));
  public static final ResourceKey<IAllele<?>> HIGH_TOLERANCE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("temperature/tolerance/high"));
  public static final ResourceKey<IAllele<?>> MAXIMUM_TOLERANCE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("temperature/tolerance/maximum"));


  public static final MapCodec<TemperatureTolerance> CODEC = RecordCodecBuilder.mapCodec(
          instance -> instance.group(
                  Codec.INT.fieldOf("toleranceModifier").forGetter(TemperatureTolerance::getToleranceModifier),
                  Codec.BOOL.fieldOf("isDominantTrait").forGetter(TemperatureTolerance::isDominantTrait),
                  Codec.STRING.fieldOf("name").forGetter(TemperatureTolerance::getName)
          ).apply(instance, TemperatureTolerance::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, TemperatureTolerance> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, TemperatureTolerance::getToleranceModifier,
          ByteBufCodecs.BOOL, TemperatureTolerance::isDominantTrait,
          ByteBufCodecs.STRING_UTF8, TemperatureTolerance::getName,
          TemperatureTolerance::new
  );

  private final int toleranceModifier;
  private final boolean isDominantTrait;
  private final String name;
  private Component readableName;

  public TemperatureTolerance(int toleranceModifier, boolean isDominantTrait, String name)
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
    TemperatureTolerance that = (TemperatureTolerance) o;
    return toleranceModifier == that.toleranceModifier && isDominantTrait == that.isDominantTrait && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(toleranceModifier, isDominantTrait, name);
  }

  @Override
  public MapCodec<TemperatureTolerance> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, TemperatureTolerance> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public AlleleType<TemperatureTolerance> getTraitKey()
  {
    return AlleleTypeRegistration.TEMPERATURE_TOLERANCE_TYPE.get();
  }
}
