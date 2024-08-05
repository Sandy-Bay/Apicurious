package sandybay.apicurious.common.bee.species.trait;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.ITrait;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousConstants;

import java.util.Objects;

public class TemperatureTolerance implements ITrait<TemperatureTolerance>
{

  public static final ResourceKey<TemperatureTolerance> NO_TOLERANCE = ResourceKey.create(ApicuriousRegistries.TEMPERATURE_TOLERANCES, Apicurious.createResourceLocation("none"));
  public static final ResourceKey<TemperatureTolerance> LOWEST_TOLERANCE = ResourceKey.create(ApicuriousRegistries.TEMPERATURE_TOLERANCES, Apicurious.createResourceLocation("lowest"));
  public static final ResourceKey<TemperatureTolerance> LOW_TOLERANCE = ResourceKey.create(ApicuriousRegistries.TEMPERATURE_TOLERANCES, Apicurious.createResourceLocation("low"));
  public static final ResourceKey<TemperatureTolerance> AVERAGE_TOLERANCE = ResourceKey.create(ApicuriousRegistries.TEMPERATURE_TOLERANCES, Apicurious.createResourceLocation("average"));
  public static final ResourceKey<TemperatureTolerance> HIGH_TOLERANCE = ResourceKey.create(ApicuriousRegistries.TEMPERATURE_TOLERANCES, Apicurious.createResourceLocation("high"));
  public static final ResourceKey<TemperatureTolerance> MAXIMUM_TOLERANCE = ResourceKey.create(ApicuriousRegistries.TEMPERATURE_TOLERANCES, Apicurious.createResourceLocation("maximum"));


  public static final Codec<TemperatureTolerance> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("toleranceModifier").forGetter(TemperatureTolerance::getToleranceModifier),
                  Codec.STRING.fieldOf("name").forGetter(TemperatureTolerance::getName)
          ).apply(instance, TemperatureTolerance::new)
  );

  public static final StreamCodec<ByteBuf, TemperatureTolerance> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, TemperatureTolerance::getToleranceModifier,
          ByteBufCodecs.STRING_UTF8, TemperatureTolerance::getName,
          TemperatureTolerance::new
  );

  private final int toleranceModifier;
  private final String name;
  private Component readableName;

  public TemperatureTolerance(int toleranceModifier, String name)
  {
    this.toleranceModifier = toleranceModifier;
    this.name = name;
  }

  public int getToleranceModifier()
  {
    return toleranceModifier;
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
    return toleranceModifier == that.toleranceModifier && Objects.equals(name, that.name) && Objects.equals(readableName, that.readableName);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(toleranceModifier, name, readableName);
  }

  @Override
  public Codec<TemperatureTolerance> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, TemperatureTolerance> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public ResourceLocation getTraitKey()
  {
    return ApicuriousConstants.TEMPERATURE_TOLERANCE;
  }
}
