package sandybay.apicurious.common.bee.species.trait.groups;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.trait.HumidityPreference;
import sandybay.apicurious.common.bee.species.trait.HumidityTolerance;

import java.util.Objects;

public record HumidityData(Holder<HumidityPreference> preference, Holder<HumidityTolerance> tolerance)
{
  public static final Codec<HumidityData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  RegistryFileCodec.create(ApicuriousRegistries.HUMIDITY_PREFERENCES, HumidityPreference.CODEC).fieldOf("preference").forGetter(HumidityData::preference),
                  RegistryFileCodec.create(ApicuriousRegistries.HUMIDITY_TOLERANCES, HumidityTolerance.CODEC).fieldOf("tolerance").forGetter(HumidityData::tolerance)
          ).apply(instance, HumidityData::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, HumidityData> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holder(ApicuriousRegistries.HUMIDITY_PREFERENCES, HumidityPreference.NETWORK_CODEC), HumidityData::preference,
          ByteBufCodecs.holder(ApicuriousRegistries.HUMIDITY_TOLERANCES, HumidityTolerance.NETWORK_CODEC), HumidityData::tolerance,
          HumidityData::new
  );

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HumidityData that = (HumidityData) o;
    return Objects.equals(preference, that.preference) && Objects.equals(tolerance, that.tolerance);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(preference, tolerance);
  }
}
