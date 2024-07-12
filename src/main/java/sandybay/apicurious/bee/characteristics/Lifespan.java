package sandybay.apicurious.bee.characteristics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Lifespan {
  public static final Lifespan SHORTEST = new Lifespan(400);
  public static final Lifespan SHORTER = new Lifespan(900);
  public static final Lifespan SHORT = new Lifespan(1200);
  public static final Lifespan NORMAL = new Lifespan(1500);
  public static final Lifespan LONG = new Lifespan(1800);
  public static final Lifespan LONGER = new Lifespan(2100);
  public static final Lifespan LONGEST = new Lifespan(2400);

  public static final Codec<Lifespan> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("lifespan").forGetter(Lifespan::getLifespan)
          ).apply(instance, Lifespan::new)
  );

  private final int lifespan;

  public Lifespan(int lifespan) {
    this.lifespan = lifespan;
  }

  public int getLifespan() {
    return lifespan;
  }

}
