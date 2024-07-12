package sandybay.apicurious.api.bee.species;

import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.Block;
import sandybay.apicurious.api.bee.characteristics.HumidityPreference;
import sandybay.apicurious.api.bee.characteristics.HumidityTolerance;
import sandybay.apicurious.api.bee.characteristics.Lifespan;
import sandybay.apicurious.api.bee.characteristics.PollinationRate;
import sandybay.apicurious.api.bee.characteristics.ProductionSpeed;
import sandybay.apicurious.api.bee.characteristics.TemperaturePreference;
import sandybay.apicurious.api.bee.characteristics.TemperatureTolerance;
import sandybay.apicurious.api.bee.characteristics.WorkCycle;

import java.util.List;

public interface IBeeSpecies {
    Lifespan getLifespan();
    ProductionSpeed getProductionSpeed();
    PollinationRate getPollinationRate();
    TagKey<Block> getFlowers();
    int getWorkRadius();
    List<MobEffectInstance> getEffects();
    TemperaturePreference getTemperaturePreference();
    TemperatureTolerance getTemperatureTolerance();
    HumidityPreference getHumidityPreference();
    HumidityTolerance getHumidityTolerance();
    WorkCycle getWorkCycle();
    boolean ignoresRain();
    boolean ignoresSky();
}
