package sandybay.apicurious.common.bee.species.trait.groups;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.trait.HumidityPreference;
import sandybay.apicurious.common.bee.species.trait.HumidityTolerance;

import java.util.Objects;

public record HumidityData(Holder<HumidityPreference> preference, Holder<HumidityTolerance> tolerance) {
  public static final Codec<HumidityData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  RegistryFixedCodec.create(ApicuriousRegistries.HUMIDITY_PREFERENCES).fieldOf("preference").forGetter(HumidityData::preference),
                  RegistryFixedCodec.create(ApicuriousRegistries.HUMIDITY_TOLERANCES).fieldOf("tolerance").forGetter(HumidityData::tolerance)
          ).apply(instance, HumidityData::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, HumidityData> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.HUMIDITY_PREFERENCES), HumidityData::preference,
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.HUMIDITY_TOLERANCES), HumidityData::tolerance,
          HumidityData::new
  );

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HumidityData that = (HumidityData) o;
    return Objects.equals(preference, that.preference) && Objects.equals(tolerance, that.tolerance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(preference, tolerance);
  }
}
