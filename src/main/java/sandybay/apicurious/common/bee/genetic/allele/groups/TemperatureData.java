package sandybay.apicurious.common.bee.genetic.allele.groups;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.genetic.allele.TemperaturePreference;
import sandybay.apicurious.common.bee.genetic.allele.TemperatureTolerance;

import java.util.Objects;

public class TemperatureData
{
  public static final Codec<TemperatureData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("preferenceHolder").forGetter(TemperatureData::getPreferenceHolder),
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("toleranceHolder").forGetter(TemperatureData::getToleranceHolder)
          ).apply(instance, TemperatureData::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, TemperatureData> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), TemperatureData::getPreferenceHolder,
          ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), TemperatureData::getToleranceHolder,
          TemperatureData::new
  );

  private final Holder<IAllele<?>> preferenceHolder;
  private TemperaturePreference preference;
  private final Holder<IAllele<?>> toleranceHolder;
  private TemperatureTolerance tolerance;

  public TemperatureData(Holder<IAllele<?>> preferenceHolder, Holder<IAllele<?>> toleranceHolder)
  {
    this.preferenceHolder = preferenceHolder;
    this.toleranceHolder = toleranceHolder;
  }

  private Holder<IAllele<?>> getPreferenceHolder()
  {
    return preferenceHolder;
  }

  public TemperaturePreference getPreference()
  {
    if (preference == null && preferenceHolder.isBound()) preference = (TemperaturePreference) preferenceHolder.value();
    return preference;
  }

  private Holder<IAllele<?>> getToleranceHolder()
  {
    return toleranceHolder;
  }

  public TemperatureTolerance getTolerance()
  {
    if (tolerance == null && toleranceHolder.isBound()) tolerance = (TemperatureTolerance) toleranceHolder.value();
    return tolerance;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TemperatureData that = (TemperatureData) o;
    return Objects.equals(preference, that.preference) && Objects.equals(tolerance, that.tolerance);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(preference, tolerance);
  }
}
