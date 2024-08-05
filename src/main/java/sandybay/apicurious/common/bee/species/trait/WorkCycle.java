package sandybay.apicurious.common.bee.species.trait;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.ITrait;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkCycle implements ITrait<WorkCycle>
{

  /**
   * Matutinal (Dawn Time)
   * Bees will only produce between 4000 and 10000 time-wise.
   */
  public static final ResourceKey<WorkCycle> MATUTINAL = ResourceKey.create(ApicuriousRegistries.WORKCYCLES, Apicurious.createResourceLocation("matutinal"));

  /**
   * Diurnal (Day Time)
   * Bees will only produce between 6000 and 18000 time-wise.
   */
  public static final ResourceKey<WorkCycle> DIURNAL = ResourceKey.create(ApicuriousRegistries.WORKCYCLES, Apicurious.createResourceLocation("diurnal"));

  /**
   * Vespertinal (Evening Time)
   * Bees will only produce between 14000 and 20000 time-wise.
   */
  public static final ResourceKey<WorkCycle> VESPERTINAL = ResourceKey.create(ApicuriousRegistries.WORKCYCLES, Apicurious.createResourceLocation("vespertinal"));

  /**
   * Nocturnal (Night Time)
   * Bees will only produce between 18000 and 6000 time-wise.
   */
  public static final ResourceKey<WorkCycle> NOCTURNAL = ResourceKey.create(ApicuriousRegistries.WORKCYCLES, Apicurious.createResourceLocation("nocturnal"));

  /**
   * Always active work cycle.
   * Bees will always produce an output.
   */
  public static final ResourceKey<WorkCycle> ALWAYS = ResourceKey.create(ApicuriousRegistries.WORKCYCLES, Apicurious.createResourceLocation("always"));

  public static final Codec<WorkCycle> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                          Codec.list(Interval.CODEC).fieldOf("activeTimes").forGetter(WorkCycle::getActiveTimes),
                          Codec.BOOL.fieldOf("isDominantTrait").forGetter(WorkCycle::isDominantTrait),
                          Codec.STRING.fieldOf("name").forGetter(WorkCycle::getName)
                  )
                  .apply(instance, WorkCycle::new)
  );
  public static final StreamCodec<RegistryFriendlyByteBuf, WorkCycle> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.collection(ArrayList::new, Interval.NETWORK_CODEC), WorkCycle::getActiveTimes,
          ByteBufCodecs.BOOL, WorkCycle::isDominantTrait,
          ByteBufCodecs.STRING_UTF8, WorkCycle::getName,
          WorkCycle::new
  );

  private final List<Interval> activeTimes;
  private final boolean isDominantTrait;
  private final String name;
  private Component readableName;

  public WorkCycle(List<Interval> activeTimes, boolean isDominantTrait, String name)
  {
    this.activeTimes = activeTimes;
    this.isDominantTrait = isDominantTrait;
    this.name = name;
  }

  public boolean isValidTime(int time)
  {
    boolean isValid = false;
    for (Interval period : activeTimes)
    {
      if (period.isValid(time))
      {
        isValid = true;
        break;
      }
    }
    return isValid;
  }

  private List<Interval> getActiveTimes()
  {
    return this.activeTimes;
  }

  @Override
  public boolean isDominantTrait()
  {
    return isDominantTrait;
  }

  private String getName()
  {
    return this.name;
  }

  public Component getReadableName()
  {
    if (readableName == null) this.readableName = Component.translatable(this.name);
    return this.readableName;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WorkCycle workCycle = (WorkCycle) o;
    return Objects.equals(activeTimes, workCycle.activeTimes) && isDominantTrait == workCycle.isDominantTrait && Objects.equals(name, workCycle.name);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(activeTimes, isDominantTrait, name);
  }

  @Override
  public Codec<WorkCycle> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, WorkCycle> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public ResourceLocation getTraitKey()
  {
    return ApicuriousConstants.WORKCYCLE;
  }

  public static class Interval
  {
    public static final StreamCodec<ByteBuf, Interval> NETWORK_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, interval -> interval.minTime,
            ByteBufCodecs.INT, interval -> interval.maxTime,
            Interval::new
    );
    public static Codec<Interval> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("minTime").forGetter(interval -> interval.minTime),
                    Codec.INT.fieldOf("maxTime").forGetter(interval -> interval.maxTime)
            ).apply(instance, Interval::new)
    );
    private final int minTime;
    private final int maxTime;

    public Interval(int minTime, int maxTime)
    {
      this.minTime = minTime;
      this.maxTime = maxTime;
    }

    public boolean isValid(int time)
    {
      if (minTime > maxTime)
      {
        return (time >= minTime && time <= 24000) || (time >= 0 && time <= maxTime);
      }
      return time >= minTime && time <= maxTime;
    }

    @Override
    public boolean equals(Object o)
    {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Interval interval = (Interval) o;
      return minTime == interval.minTime && maxTime == interval.maxTime;
    }

    @Override
    public int hashCode()
    {
      return Objects.hash(minTime, maxTime);
    }
  }
}
