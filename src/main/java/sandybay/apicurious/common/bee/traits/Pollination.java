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

public class Pollination implements ITrait<Pollination> {

  public static void load() {}

  public static final ApicuriousHolder<Pollination> SLOWEST = create(0.05f, "slowest");
  public static final ApicuriousHolder<Pollination> SLOWER = create(0.1f, "slower");
  public static final ApicuriousHolder<Pollination> SLOW = create(0.15f, "slow");
  public static final ApicuriousHolder<Pollination> AVERAGE = create(0.2f, "average");
  public static final ApicuriousHolder<Pollination> FAST = create(0.25f, "fast");
  public static final ApicuriousHolder<Pollination> FASTER = create(0.3f, "faster");
  public static final ApicuriousHolder<Pollination> FASTEST = create(0.35f, "fastest");

  private static ApicuriousHolder<Pollination> create(float pollinationChance, String name) {
    ResourceKey<Pollination> key = ResourceKey.create(ApicuriousRegistries.POLLINATIONS, Apicurious.createResourceLocation(name));
    Pollination area = new Pollination(pollinationChance, "apicurious.pollination." + name);
    return new ApicuriousHolder<>(key, area);
  }

  public static final Codec<Pollination> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.FLOAT.fieldOf("pollinationChance").forGetter(Pollination::getPollinationChance),
                  Codec.STRING.fieldOf("name").forGetter(Pollination::getName)
          ).apply(instance, Pollination::new)
  );
  public static final StreamCodec<ByteBuf, Pollination> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.FLOAT, Pollination::getPollinationChance,
          ByteBufCodecs.STRING_UTF8, Pollination::getName,
          Pollination::new
  );

  private final float pollinationChance;
  private final String name;
  private Component readableName;

  Pollination(float pollinationChance, String name) {
    this.pollinationChance = pollinationChance;
    this.name = name;
  }

  public float getPollinationChance() {
    return pollinationChance;
  }

  private String getName() {
    return name;
  }

  public Component getReadableName() {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public Codec<Pollination> getCodec() {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, Pollination> getStreamCodec() {
    return NETWORK_CODEC;
  }
}
