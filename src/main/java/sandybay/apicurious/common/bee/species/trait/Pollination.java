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

public class Pollination implements ITrait<Pollination> {

    public static final ResourceKey<Pollination> SLOWEST = ResourceKey.create(ApicuriousRegistries.POLLINATIONS, Apicurious.createResourceLocation("slowest"));
    public static final ResourceKey<Pollination> SLOWER = ResourceKey.create(ApicuriousRegistries.POLLINATIONS, Apicurious.createResourceLocation("slower"));
    public static final ResourceKey<Pollination> SLOW = ResourceKey.create(ApicuriousRegistries.POLLINATIONS, Apicurious.createResourceLocation("slow"));
    public static final ResourceKey<Pollination> AVERAGE = ResourceKey.create(ApicuriousRegistries.POLLINATIONS, Apicurious.createResourceLocation("average"));
    public static final ResourceKey<Pollination> FAST = ResourceKey.create(ApicuriousRegistries.POLLINATIONS, Apicurious.createResourceLocation("fast"));
    public static final ResourceKey<Pollination> FASTER = ResourceKey.create(ApicuriousRegistries.POLLINATIONS, Apicurious.createResourceLocation("faster"));
    public static final ResourceKey<Pollination> FASTEST = ResourceKey.create(ApicuriousRegistries.POLLINATIONS, Apicurious.createResourceLocation("fastest"));

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

  public Pollination(float pollinationChance, String name) {
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
