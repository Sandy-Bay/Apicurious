package sandybay.apicurious.bee.characteristics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class HumidityTolerance {
    // TODO: Add proper group tags.
    public static final HumidityTolerance NO_TOLERANCE = new HumidityTolerance(0);
    public static final HumidityTolerance LOW_TOLERANCE = new HumidityTolerance(1);
    public static final HumidityTolerance HIGH_TOLERANCE = new HumidityTolerance(2);

    public static final Codec<HumidityTolerance> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("toleranceModifier").forGetter(HumidityTolerance::getToleranceModifier)
            ).apply(instance, HumidityTolerance::new)
    );

    private final int toleranceModifier;

    public HumidityTolerance(int toleranceModifier) {
        this.toleranceModifier = toleranceModifier;
    }

    public int getToleranceModifier() {
        return toleranceModifier;
    }
}
