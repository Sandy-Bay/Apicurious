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
import sandybay.apicurious.common.bee.genetic.allele.HumidityPreference;
import sandybay.apicurious.common.bee.genetic.allele.HumidityTolerance;

import java.util.Objects;

public class HumidityData
{
  private final Holder<IAllele<?>> preferenceHolder;
  private HumidityPreference preference;
  private final Holder<IAllele<?>> toleranceHolder;
  private HumidityTolerance tolerance;

  public static final Codec<HumidityData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("preferenceHolder").forGetter(HumidityData::getPreferenceHolder),
                  RegistryFileCodec.create(ApicuriousRegistries.ALLELES, IAllele.TYPED_CODEC).fieldOf("toleranceHolder").forGetter(HumidityData::getToleranceHolder)
          ).apply(instance, HumidityData::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, HumidityData> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), HumidityData::getPreferenceHolder,
          ByteBufCodecs.holder(ApicuriousRegistries.ALLELES, IAllele.NETWORK_TYPED_CODEC), HumidityData::getToleranceHolder,
          HumidityData::new
  );

  public HumidityData(Holder<IAllele<?>> preferenceHolder, Holder<IAllele<?>> toleranceHolder)
  {
    this.preferenceHolder = preferenceHolder;
    this.toleranceHolder = toleranceHolder;
  }

  private Holder<IAllele<?>> getPreferenceHolder()
  {
    return preferenceHolder;
  }

  public HumidityPreference getPreference()
  {
    if (preference == null && preferenceHolder.isBound()) preference = (HumidityPreference) preferenceHolder.value();
    return preference;
  }

  private Holder<IAllele<?>> getToleranceHolder()
  {
    return toleranceHolder;
  }

  public HumidityTolerance getTolerance()
  {
    if (tolerance == null && toleranceHolder.isBound()) tolerance = (HumidityTolerance) toleranceHolder.value();
    return tolerance;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HumidityData that = (HumidityData) o;
    return Objects.equals(preferenceHolder, that.preferenceHolder) && Objects.equals(toleranceHolder, that.toleranceHolder);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(preferenceHolder, toleranceHolder);
  }
}
