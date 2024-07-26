package sandybay.apicurious.common.bee.species.trait;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.ITrait;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class HumidityTolerance implements ITrait<HumidityTolerance> {

    public static final ResourceKey<HumidityTolerance> NO_TOLERANCE = ResourceKey.create(ApicuriousRegistries.HUMIDITY_TOLERANCES, Apicurious.createResourceLocation("none"));
    public static final ResourceKey<HumidityTolerance> LOWEST_TOLERANCE = ResourceKey.create(ApicuriousRegistries.HUMIDITY_TOLERANCES, Apicurious.createResourceLocation("lowest"));
    public static final ResourceKey<HumidityTolerance> LOW_TOLERANCE = ResourceKey.create(ApicuriousRegistries.HUMIDITY_TOLERANCES, Apicurious.createResourceLocation("low"));
    public static final ResourceKey<HumidityTolerance> AVERAGE_TOLERANCE = ResourceKey.create(ApicuriousRegistries.HUMIDITY_TOLERANCES, Apicurious.createResourceLocation("average"));
    public static final ResourceKey<HumidityTolerance> HIGH_TOLERANCE = ResourceKey.create(ApicuriousRegistries.HUMIDITY_TOLERANCES, Apicurious.createResourceLocation("high"));
    public static final ResourceKey<HumidityTolerance> MAXIMUM_TOLERANCE = ResourceKey.create(ApicuriousRegistries.HUMIDITY_TOLERANCES, Apicurious.createResourceLocation("maximum"));

    // TODO: Add proper group tags.

    public static final Codec<HumidityTolerance> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("toleranceModifier").forGetter(HumidityTolerance::getToleranceModifier),
                    Codec.STRING.fieldOf("humidityTolerance").forGetter(HumidityTolerance::getName)
            ).apply(instance, HumidityTolerance::new)
    );

    public static final StreamCodec<ByteBuf, HumidityTolerance> NETWORK_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, HumidityTolerance::getToleranceModifier,
            ByteBufCodecs.STRING_UTF8, HumidityTolerance::getName,
            HumidityTolerance::new
    );

    private final int toleranceModifier;
    private final String name;
    private Component readableName;

    public HumidityTolerance(int toleranceModifier, String name) {
        this.toleranceModifier = toleranceModifier;
        this.name = name;
    }

    public int getToleranceModifier() {
        return toleranceModifier;
    }

    private String getName() {
        return name;
    }

    public Component getReadableName() {
        if (readableName == null) readableName = Component.translatable(this.name);
        return readableName;
    }

    @Override
    public Codec<HumidityTolerance> getCodec() {
        return CODEC;
    }

    @Override
    public StreamCodec<ByteBuf, HumidityTolerance> getStreamCodec() {
        return NETWORK_CODEC;
    }
}