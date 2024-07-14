package sandybay.apicurious.common.bee.traits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import sandybay.apicurious.api.bee.traits.ITrait;

public class HumidityTolerance implements ITrait<HumidityTolerance> {
    // TODO: Add proper group tags.
    public static final HumidityTolerance NO_TOLERANCE = new HumidityTolerance(0, "apicurious.tolerance.humidity.none");
    public static final HumidityTolerance LOW_TOLERANCE = new HumidityTolerance(1, "apicurious.tolerance.humidity.low");
    public static final HumidityTolerance HIGH_TOLERANCE = new HumidityTolerance(2, "apicurious.tolerance.humidity.high");

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
