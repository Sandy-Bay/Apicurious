package sandybay.apicurious.bee.species;

import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.Block;
import sandybay.apicurious.bee.characteristics.WorkCycle;
import sandybay.apicurious.bee.characteristics.HumidityPreference;
import sandybay.apicurious.bee.characteristics.HumidityTolerance;
import sandybay.apicurious.bee.characteristics.Lifespan;
import sandybay.apicurious.bee.characteristics.PollinationRate;
import sandybay.apicurious.bee.characteristics.ProductionSpeed;
import sandybay.apicurious.bee.characteristics.TemperaturePreference;
import sandybay.apicurious.bee.characteristics.TemperatureTolerance;

import java.util.Set;

public interface IBeeSpecies {
    Lifespan getLifespan();
    ProductionSpeed getProductionSpeed();
    PollinationRate getPollinationRate();
    TagKey<Block> getFlowers();
    int getWorkRadius();
    Set<MobEffectInstance> getEffects();
    TemperaturePreference getTemperaturePreference();
    TemperatureTolerance getTemperatureTolerance();
    HumidityPreference getHumidityPreference();
    HumidityTolerance getHumidityTolerance();
    WorkCycle getWorkCycle();
    boolean ignoresRain();
    boolean ignoresSky();
}
