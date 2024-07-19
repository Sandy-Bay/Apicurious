package sandybay.apicurious.common.old.traits.groups;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.old.traits.TemperaturePreference;
import sandybay.apicurious.common.old.traits.TemperatureTolerance;

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
}
