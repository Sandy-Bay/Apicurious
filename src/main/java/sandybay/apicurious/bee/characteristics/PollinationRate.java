package sandybay.apicurious.bee.characteristics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class PollinationRate {
  public static final PollinationRate SLOWEST = new PollinationRate(600);
  public static final PollinationRate SLOWER = new PollinationRate(500);
  public static final PollinationRate SLOW = new PollinationRate(400);
  public static final PollinationRate NORMAL = new PollinationRate(300);
  public static final PollinationRate FAST = new PollinationRate(200);
  public static final PollinationRate FASTER = new PollinationRate(100);
  public static final PollinationRate FASTEST = new PollinationRate(50);

  public static final Codec<PollinationRate> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("pollinationDuration").forGetter(PollinationRate::getPollinationDuration)
          ).apply(instance, PollinationRate::new)
  );

  private final int pollinationDuration;

  PollinationRate(int pollinationDuration) {
    this.pollinationDuration = pollinationDuration;
  }

  public int getPollinationDuration() {
    return pollinationDuration;
  }
}
