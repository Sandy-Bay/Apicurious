package sandybay.apicurious.common.bee.traits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.bee.traits.ITrait;

import java.util.ArrayList;
import java.util.List;

public class WorkCycle implements ITrait<WorkCycle> {
    /**
     * Matutinal (Dawn Time)
     * Bees will only produce between 4000 and 10000 time-wise.
     */
    public static final WorkCycle MATUTINAL = new WorkCycle(List.of(new Interval(4000, 10000)), "apicurious.workcycle.matutinal");
    /**
     * Diurnal (Day Time)
     * Bees will only produce between 6000 and 18000 time-wise.
     */
    public static final WorkCycle DIURNAL = new WorkCycle(List.of(new Interval(6000, 18000)), "apicurious.workcycle.diurnal");
    /**
     * Vespertinal (Evening Time)
     * Bees will only produce between 14000 and 20000 time-wise.
     */
    public static final WorkCycle VESPERTINAL = new WorkCycle(List.of(new Interval(14000, 20000)), "apicurious.workcycle.vespertinal");
    /**
     * Nocturnal (Night Time)
     * Bees will only produce between 18000 and 6000 time-wise.
     */
    public static final WorkCycle NOCTURNAL = new WorkCycle(List.of(new Interval(18000, 24000), new Interval(0, 6000)), "apicurious.workcycle.nocturnal");
    /**
     * Always active work cycle.
     * Bees will always produce an output.
     */
    public static final WorkCycle ALWAYS = new WorkCycle(List.of(new Interval(0, 24000)), "apicurious.workcycle.always");

    public static final Codec<WorkCycle> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codec.list(Interval.CODEC)
                                    .fieldOf("activeTimes")
                                    .forGetter(workCycle -> workCycle.activeTimes),
                            Codec.STRING
                                    .fieldOf("name")
                                    .forGetter(workCycle -> workCycle.name)
                    )
                    .apply(instance, WorkCycle::new)
    );

    public static final StreamCodec<ByteBuf, WorkCycle> NETWORK_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(ArrayList::new, Interval.NETWORK_CODEC), WorkCycle::getActiveTimes,
            ByteBufCodecs.STRING_UTF8, WorkCycle::getName,
            WorkCycle::new
    );

    private final List<Interval> activeTimes;
    private final String name;
    private Component readableName;

    public WorkCycle(List<Interval> activeTimes, String name) {
        this.activeTimes = activeTimes;
        this.name = name;
    }

    public boolean isValidTime(int time) {
        boolean isValid = false;
        for (Interval period : activeTimes) {
            if (period.isValid(time)) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }

    private List<Interval> getActiveTimes() {
        return this.activeTimes;
    }

    private String getName() {
        return this.name;
    }

    public Component getReadableName() {
        if (readableName == null) this.readableName = Component.translatable(this.name);
        return this.readableName;
    }

    @Override
    public Codec<WorkCycle> getCodec() {
        return CODEC;
    }

    @Override
    public StreamCodec<FriendlyByteBuf, WorkCycle> getStreamCodec() {
        return null;
    }

    public static class Interval {
        public static Codec<Interval> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Codec.INT.fieldOf("minTime").forGetter(interval -> interval.minTime),
                        Codec.INT.fieldOf("maxTime").forGetter(interval -> interval.maxTime)
                ).apply(instance, Interval::new)
        );

        public static final StreamCodec<ByteBuf, Interval> NETWORK_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, interval -> interval.minTime,
                ByteBufCodecs.INT, interval -> interval.maxTime,
                Interval::new
        );

        private final int minTime;
        private final int maxTime;

        public Interval(int minTime, int maxTime) {
            this.minTime = minTime;
            this.maxTime = maxTime;
        }

        public boolean isValid(int time) {
            if (minTime > maxTime) {
                return (time >= minTime && time <= 24000) || (time >= 0 && time <= maxTime);
            }
            return time >= minTime && time <= maxTime;
        }
    }
}
