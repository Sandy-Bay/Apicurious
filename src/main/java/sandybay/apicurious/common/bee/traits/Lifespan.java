package sandybay.apicurious.common.bee.traits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.traits.ITrait;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousHolder;

public class Lifespan implements ITrait<Lifespan> {

  public static void load() {}

  public static final ApicuriousHolder<Lifespan> SHORTEST = create(10, "shortest");
  public static final ApicuriousHolder<Lifespan> SHORTER = create(20, "shorter");
  public static final ApicuriousHolder<Lifespan> SHORT = create(30, "short");
  public static final ApicuriousHolder<Lifespan> SHORTENED = create(35, "shortened");
  public static final ApicuriousHolder<Lifespan> AVERAGE = create(40, "average");
  public static final ApicuriousHolder<Lifespan> ELONGATED = create(45, "elongated");
  public static final ApicuriousHolder<Lifespan> LONG = create(50, "long");
  public static final ApicuriousHolder<Lifespan> LONGER = create(60, "longer");
  public static final ApicuriousHolder<Lifespan> LONGEST = create(70, "longest");

  private static ApicuriousHolder<Lifespan> create(int cycles, String name) {
    ResourceKey<Lifespan> key = ResourceKey.create(ApicuriousRegistries.LIFESPANS, Apicurious.createResourceLocation(name));
    Lifespan area = new Lifespan(cycles, "apicurious.cycles." + name);
    return new ApicuriousHolder<>(key, area);
  }

  public static final Codec<Lifespan> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("cycles").forGetter(Lifespan::getCycles),
                  Codec.STRING.fieldOf("name").forGetter(Lifespan::getName)
          ).apply(instance, Lifespan::new)
  );
  public static final StreamCodec<ByteBuf, Lifespan> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, Lifespan::getCycles,
          ByteBufCodecs.STRING_UTF8, Lifespan::getName,
          Lifespan::new
  );

  private final int cycles;
  private final String name;
  private Component readableName;

  public Lifespan(int cycles, String name) {
    this.cycles = cycles;
    this.name = name;
  }

  public int getCycles() {
    return cycles;
  }

  private String getName() {
    return name;
  }

  public Component getReadableName() {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public Codec<Lifespan> getCodec() {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, Lifespan> getStreamCodec() {
    return NETWORK_CODEC;
  }
}
