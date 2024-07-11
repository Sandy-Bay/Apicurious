package sandybay.apicurious.bee.characteristics;

import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public enum HumidityTolerance {
    // TODO: Add proper group tags.
    NO_TOLERANCE(0),
    LOW_TOLERANCE(1),
    HIGH_TOLERANCE(2);

    private final int toleranceModifier;

    HumidityTolerance(int toleranceModifier) {
        this.toleranceModifier = toleranceModifier;
    }

    public int getToleranceModifier() {
        return toleranceModifier;
    }
}
