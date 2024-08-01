package sandybay.apicurious.common.bee.species.trait.groups;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.trait.TemperaturePreference;
import sandybay.apicurious.common.bee.species.trait.TemperatureTolerance;

import java.util.Objects;

public record TemperatureData(Holder<TemperaturePreference> preference, Holder<TemperatureTolerance> tolerance) {
  public static final Codec<TemperatureData> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  RegistryFixedCodec.create(ApicuriousRegistries.TEMPERATURE_PREFERENCES).fieldOf("preference").forGetter(TemperatureData::preference),
                  RegistryFixedCodec.create(ApicuriousRegistries.TEMPERATURE_TOLERANCES).fieldOf("tolerance").forGetter(TemperatureData::tolerance)
          ).apply(instance, TemperatureData::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, TemperatureData> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.TEMPERATURE_PREFERENCES), TemperatureData::preference,
          ByteBufCodecs.holderRegistry(ApicuriousRegistries.TEMPERATURE_TOLERANCES), TemperatureData::tolerance,
          TemperatureData::new
  );

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TemperatureData that = (TemperatureData) o;
    return Objects.equals(preference, that.preference) && Objects.equals(tolerance, that.tolerance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(preference, tolerance);
  }
}
