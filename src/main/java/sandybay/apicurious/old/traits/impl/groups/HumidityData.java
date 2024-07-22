//package sandybay.apicurious.old.traits.impl.groups;
//
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import net.minecraft.core.Holder;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.ByteBufCodecs;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.resources.RegistryFixedCodec;
//import sandybay.apicurious.api.registry.ApicuriousRegistries;
//import sandybay.apicurious.common.old.traits.HumidityPreference;
//import sandybay.apicurious.common.old.traits.HumidityTolerance;
//
//public record HumidityData(Holder<HumidityPreference> preference, Holder<HumidityTolerance> tolerance) {
//  public static final Codec<HumidityData> CODEC = RecordCodecBuilder.create(
//          instance -> instance.group(
//                  RegistryFixedCodec.create(ApicuriousRegistries.HUMIDITY_PREFERENCES).fieldOf("preference").forGetter(HumidityData::preference),
//                  RegistryFixedCodec.create(ApicuriousRegistries.HUMIDITY_TOLERANCES).fieldOf("tolerance").forGetter(HumidityData::tolerance)
//          ).apply(instance, HumidityData::new)
//  );
//
//  public static final StreamCodec<RegistryFriendlyByteBuf, HumidityData> NETWORK_CODEC = StreamCodec.composite(
//          ByteBufCodecs.holderRegistry(ApicuriousRegistries.HUMIDITY_PREFERENCES), HumidityData::preference,
//          ByteBufCodecs.holderRegistry(ApicuriousRegistries.HUMIDITY_TOLERANCES), HumidityData::tolerance,
//          HumidityData::new
//  );
//}
