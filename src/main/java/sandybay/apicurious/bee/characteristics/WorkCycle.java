package sandybay.apicurious.bee.characteristics;

import java.util.function.Function;

public enum WorkCycle {
    /**
     * Matutinal (Dawn Time)
     * Bees will only produce between 4000 and 10000 time-wise.
     */
    MATUTINAL(time -> time >= 4000 && time <= 10000),   // Active 04000-10000,
    /**
     * Diurnal (Day Time)
     * Bees will only produce between 6000 and 18000 time-wise.
     */
    DIURNAL(time -> time >= 6000 && time <= 18000),     // Active 06000-18000
    /**
     * Vespertinal (Evening Time)
     * Bees will only produce between 14000 and 20000 time-wise.
     */
    VESPERTINAL(time -> time >= 14000 && time <= 20000), // Active 14000-20000
    /**
     * Nocturnal (Night Time)
     * Bees will only produce between 18000 and 24000 time-wise.
     */
    NOCTURNAL(time -> (time >= 18000 && time <= 24000) || time >= 0 && time <= 6000),   // Active 18000-06000
    /**
     * Always active work cycle.
     * Bees will always produce an output.
     */
    ALWAYS(time -> true);       // Active 00000 - 24000

    private final Function<int, boolean> isActive;

    WorkCycle(Function<int, boolean> isActive) {
        this.isActive = isActive;
    }

    public boolean isActive(int time) {
        return this.isActive.apply(time);
    }
}
