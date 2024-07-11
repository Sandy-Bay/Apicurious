package sandybay.apicurious.bee.species;

import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import sandybay.apicurious.bee.characteristics.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BeeSpecies implements IBeeSpecies {

  private final Lifespan lifespan;
  private final ProductionSpeed productionSpeed;
  private final PollinationRate pollinationRate;
  private final TagKey<Block> flowers;
  private final int workRadius;
  private final Set<MobEffectInstance> effects;
  private final TemperaturePreference temperaturePreference;
  private final TemperatureTolerance temperatureTolerance;
  private final HumidityPreference humidityPreference;
  private final HumidityTolerance humidityTolerance;
  private final WorkCycle workCycle;
  private final boolean ignoresRain;
  private final boolean ignoresSky;

  public BeeSpecies(Lifespan lifespan,
                    ProductionSpeed productionSpeed, PollinationRate pollinationRate,
                    TagKey<Block> flowers,
                    int workRadius,
                    Set<MobEffectInstance> effects,
                    TemperaturePreference temperaturePreference, TemperatureTolerance temperatureTolerance,
                    HumidityPreference humidityPreference, HumidityTolerance humidityTolerance,
                    WorkCycle workCycle,
                    boolean ignoresRain, boolean ignoresSky) {
    this.lifespan = lifespan;
    this.productionSpeed = productionSpeed;
    this.pollinationRate = pollinationRate;
    this.flowers = flowers;
    this.workRadius = workRadius;
    this.effects = effects;
    this.temperaturePreference = temperaturePreference;
    this.temperatureTolerance = temperatureTolerance;
    this.humidityPreference = humidityPreference;
    this.humidityTolerance = humidityTolerance;
    this.workCycle = workCycle;
    this.ignoresRain = ignoresRain;
    this.ignoresSky = ignoresSky;
  }

  @Override
  public Lifespan getLifespan() {
    return lifespan;
  }

  @Override
  public ProductionSpeed getProductionSpeed() {
    return productionSpeed;
  }

  @Override
  public PollinationRate getPollinationRate() {
    return pollinationRate;
  }

  @Override
  public TagKey<Block> getFlowers() {
    return flowers;
  }

  @Override
  public int getWorkRadius() {
    return workRadius;
  }

  @Override
  public Set<MobEffectInstance> getEffects() {
    return effects;
  }

  @Override
  public TemperaturePreference getTemperaturePreference() {
    return temperaturePreference;
  }

  @Override
  public TemperatureTolerance getTemperatureTolerance() {
    return temperatureTolerance;
  }

  @Override
  public HumidityPreference getHumidityPreference() {
    return humidityPreference;
  }

  @Override
  public HumidityTolerance getHumidityTolerance() {
    return humidityTolerance;
  }

  @Override
  public WorkCycle getWorkCycle() {
    return workCycle;
  }

  @Override
  public boolean ignoresRain() {
    return ignoresRain;
  }

  @Override
  public boolean ignoresSky() {
    return ignoresSky;
  }

  public static class Builder {

    private Lifespan lifespan = Lifespan.NORMAL;
    private ProductionSpeed productionSpeed = ProductionSpeed.NORMAL;
    private PollinationRate pollinationRate = PollinationRate.NORMAL;
    private TagKey<Block> flowers = null; // TODO: Define default flower tag here
    private int workRadius = 4;
    private final Set<MobEffectInstance> effects = new HashSet<>();
    private TemperaturePreference temperaturePreference = TemperaturePreference.NORMAL;
    private TemperatureTolerance temperatureTolerance = TemperatureTolerance.NO_TOLERANCE;
    private HumidityPreference humidityPreference = HumidityPreference.NORMAL;
    private HumidityTolerance humidityTolerance = HumidityTolerance.NO_TOLERANCE;
    private WorkCycle workCycle = WorkCycle.DIURNAL;
    private boolean ignoresRain = false;
    private boolean ignoresSky = false;

    private Builder() {}

    public static Builder create() {
      return new Builder();
    }

    public Builder withLifespan(Lifespan lifespan) {
      this.lifespan = lifespan;
      return this;
    }

    public Builder withProductionSpeed(ProductionSpeed productionSpeed) {
      this.productionSpeed = productionSpeed;
      return this;
    }

    public Builder withPollinationRate(PollinationRate pollinationRate) {
      this.pollinationRate = pollinationRate;
      return this;
    }

    public Builder withFlowers(TagKey<Block> flowers) {
      this.flowers = flowers;
      return this;
    }

    public Builder withWorkRadius(int workRadius) {
      this.workRadius = workRadius;
      return this;
    }

    public Builder withEffects(MobEffectInstance... effects) {
      this.effects.addAll(Arrays.asList(effects));
      return this;
    }

    public Builder withTemperaturePreference(TemperaturePreference temperaturePreference) {
      this.temperaturePreference = temperaturePreference;
      return this;
    }

    public Builder withTemperatureTolerance(TemperatureTolerance temperatureTolerance) {
      this.temperatureTolerance = temperatureTolerance;
      return this;
    }

    public Builder withHumidityPreference(HumidityPreference humidityPreference) {
      this.humidityPreference = humidityPreference;
      return this;
    }

    public Builder withHumidityTolerance(HumidityTolerance humidityTolerance) {
      this.humidityTolerance = humidityTolerance;
      return this;
    }

    public Builder withWorkCycle(WorkCycle workCycle) {
      this.workCycle = workCycle;
      return this;
    }

    public Builder ignoresRain() {
      this.ignoresRain = true;
      return this;
    }

    public Builder ignoresSky() {
      this.ignoresSky = true;
      return this;
    }

    public BeeSpecies build() {
      return new BeeSpecies(
              this.lifespan,
              this.productionSpeed, this.pollinationRate,
              this.flowers, this.workRadius, this.effects,
              this.temperaturePreference, this.temperatureTolerance,
              this.humidityPreference, this.humidityTolerance,
              this.workCycle,
              this.ignoresRain, this.ignoresSky
      );
    }
  }
}
