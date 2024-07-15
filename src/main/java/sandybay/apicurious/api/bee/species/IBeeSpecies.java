package sandybay.apicurious.api.bee.species;

import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.Block;
import sandybay.apicurious.common.bee.traits.HumidityPreference;
import sandybay.apicurious.common.bee.traits.HumidityTolerance;
import sandybay.apicurious.common.bee.traits.Lifespan;
import sandybay.apicurious.common.bee.traits.Pollination;
import sandybay.apicurious.common.bee.traits.Speed;
import sandybay.apicurious.common.bee.traits.TemperaturePreference;
import sandybay.apicurious.common.bee.traits.TemperatureTolerance;
import sandybay.apicurious.common.bee.traits.WorkCycle;

import java.util.List;

public interface IBeeSpecies {
    Lifespan getLifespan();
    Speed getProductionSpeed();
    Pollination getPollinationRate();
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
