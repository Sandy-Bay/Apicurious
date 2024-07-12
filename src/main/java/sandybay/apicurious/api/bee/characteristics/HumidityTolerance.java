package sandybay.apicurious.api.bee.characteristics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;

public class HumidityTolerance {
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
}
