package sandybay.apicurious.common.bee.species.trait;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.ITrait;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class Lifespan implements ITrait<Lifespan> {

    public static final ResourceKey<Lifespan> SHOREST = ResourceKey.create(ApicuriousRegistries.LIFESPANS, Apicurious.createResourceLocation("shorest"));
    public static final ResourceKey<Lifespan> SHORTER = ResourceKey.create(ApicuriousRegistries.LIFESPANS, Apicurious.createResourceLocation("shorter"));
    public static final ResourceKey<Lifespan> SHORT = ResourceKey.create(ApicuriousRegistries.LIFESPANS, Apicurious.createResourceLocation("short"));
    public static final ResourceKey<Lifespan> SHORTENED = ResourceKey.create(ApicuriousRegistries.LIFESPANS, Apicurious.createResourceLocation("shortened"));
    public static final ResourceKey<Lifespan> AVERAGE = ResourceKey.create(ApicuriousRegistries.LIFESPANS, Apicurious.createResourceLocation("average"));
    public static final ResourceKey<Lifespan> ELONGATED = ResourceKey.create(ApicuriousRegistries.LIFESPANS, Apicurious.createResourceLocation("elongated"));
    public static final ResourceKey<Lifespan> LONG = ResourceKey.create(ApicuriousRegistries.LIFESPANS, Apicurious.createResourceLocation("long"));
    public static final ResourceKey<Lifespan> LONGER = ResourceKey.create(ApicuriousRegistries.LIFESPANS, Apicurious.createResourceLocation("longer"));
    public static final ResourceKey<Lifespan> LONGEST = ResourceKey.create(ApicuriousRegistries.LIFESPANS, Apicurious.createResourceLocation("longest"));

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
