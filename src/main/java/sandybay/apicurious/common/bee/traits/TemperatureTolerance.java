package sandybay.apicurious.common.bee.traits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.traits.ITrait;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousHolder;

public class TemperatureTolerance implements ITrait<TemperatureTolerance> {

  public static void load() {}

  public static final ApicuriousHolder<TemperatureTolerance> NO_TOLERANCE = create(0, "none");
  public static final ApicuriousHolder<TemperatureTolerance> LOWEST_TOLERANCE = create(1, "lowest");
  public static final ApicuriousHolder<TemperatureTolerance> LOW_TOLERANCE = create(2, "low");
  public static final ApicuriousHolder<TemperatureTolerance> AVERAGE_TOLERANCE = create(3, "average");
  public static final ApicuriousHolder<TemperatureTolerance> HIGH_TOLERANCE = create(4, "high");
  public static final ApicuriousHolder<TemperatureTolerance> MAX_TOLERANCE = create(5, "max");

  private static ApicuriousHolder<TemperatureTolerance> create(int toleranceModifier, String name) {
    ResourceKey<TemperatureTolerance> key = ResourceKey.create(ApicuriousRegistries.TEMPERATURE_TOLERANCES, Apicurious.createResourceLocation(name));
    TemperatureTolerance area = new TemperatureTolerance(toleranceModifier, "apicurious.tolerance.temperature." + name);
    return new ApicuriousHolder<>(key, area);
  }

  public static final Codec<TemperatureTolerance> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("toleranceModifier").forGetter(TemperatureTolerance::getToleranceModifier),
                  Codec.STRING.fieldOf("name").forGetter(TemperatureTolerance::getName)
          ).apply(instance, TemperatureTolerance::new)
  );

  public static final StreamCodec<ByteBuf, TemperatureTolerance> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, TemperatureTolerance::getToleranceModifier,
          ByteBufCodecs.STRING_UTF8, TemperatureTolerance::getName,
          TemperatureTolerance::new
  );

  private final int toleranceModifier;
  private final String name;
  private Component readableName;

  TemperatureTolerance(int toleranceModifier, String name) {
    this.toleranceModifier = toleranceModifier;
    this.name = name;
  }

  public int getToleranceModifier() {
    return toleranceModifier;
  }

  private String getName() {
    return name;
  }

  public Component getReadableName() {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public Codec<TemperatureTolerance> getCodec() {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, TemperatureTolerance> getStreamCodec() {
    return NETWORK_CODEC;
  }
}
