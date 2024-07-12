package sandybay.apicurious.bee.characteristics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class TemperatureTolerance {
  public static final TemperatureTolerance NO_TOLERANCE = new TemperatureTolerance(0);
  public static final TemperatureTolerance LOW_TOLERANCE = new TemperatureTolerance(1);
  public static final TemperatureTolerance HIGH_TOLERANCE = new TemperatureTolerance(2);

  public static final Codec<TemperatureTolerance> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("toleranceModifier").forGetter(TemperatureTolerance::getToleranceModifier)
          ).apply(instance, TemperatureTolerance::new)
  );

  private final int toleranceModifier;

  TemperatureTolerance(int toleranceModifier) {
    this.toleranceModifier = toleranceModifier;
  }

  public int getToleranceModifier() {
    return toleranceModifier;
  }
}
